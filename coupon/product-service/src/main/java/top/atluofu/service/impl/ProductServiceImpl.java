package top.atluofu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import top.atluofu.config.RabbitMQConfig;
import top.atluofu.enums.BizCodeEnum;
import top.atluofu.enums.CouponPublishEnum;
import top.atluofu.enums.ProductOrderStateEnum;
import top.atluofu.enums.StockTaskStateEnum;
import top.atluofu.exception.BizException;
import top.atluofu.feign.ProductOrderFeignSerivce;
import top.atluofu.mapper.ProductTaskMapper;
import top.atluofu.model.ProductDO;
import top.atluofu.mapper.ProductMapper;
import top.atluofu.model.ProductMessage;
import top.atluofu.model.ProductTaskDO;
import top.atluofu.req.LockProductRequest;
import top.atluofu.req.OrderItemRequest;
import top.atluofu.service.ProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.atluofu.utils.JsonData;
import top.atluofu.vo.ProductVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author atluofu
 * @since 2023-08-09
 */
@Service
@Slf4j
public class ProductServiceImpl extends ServiceImpl<ProductMapper, ProductDO> implements ProductService {

    private final ProductMapper productMapper;


    @Autowired
    private ProductTaskMapper productTaskMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RabbitMQConfig rabbitMQConfig;

    @Autowired
    private ProductOrderFeignSerivce orderFeignSerivce;


    public ProductServiceImpl(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    @Override
    public Map<String, Object> pageCouponActivity(int page, int size) {
        Page<ProductDO> couponPageInfo = new Page<>(page, size);
        Page<ProductDO> couponPage = productMapper.selectPage(couponPageInfo, null);
        HashMap<String, Object> pageMap = new HashMap<>(3);
        pageMap.put("total_record", couponPage.getTotal());
        pageMap.put("total_page", couponPage.getPages());
        pageMap.put("current_data", couponPage.getRecords().stream().map(this::beanProcess).collect(Collectors.toList()));
        return pageMap;
    }

    @Override
    public ProductVO findDetailById(long productId) {
        return beanProcess(productMapper.selectById(productId));
    }

    @Override
    public List<ProductVO> findProductByIdBatch(List<Long> productIdList) {
        List<ProductDO> id = productMapper.selectList(new QueryWrapper<ProductDO>().in("id", productIdList));
        return id.stream().map(this::beanProcess).collect(Collectors.toList());
    }


    /**
     * 锁定商品库存
     * <p>
     * 1)遍历商品，锁定每个商品购买数量
     * 2)每一次锁定的时候，都要发送延迟消息
     *
     * @param lockProductRequest
     * @return
     */
    @Override
    public JsonData lockProductStock(LockProductRequest lockProductRequest) {

        String outTradeNo = lockProductRequest.getOrderOutTradeNo();
        List<OrderItemRequest> itemList = lockProductRequest.getOrderItemList();

        //一行代码，提取对象里面的id并加入到集合里面
        List<Long> productIdList = itemList.stream().map(OrderItemRequest::getProductId).collect(Collectors.toList());
        //批量查询
        List<ProductVO> productVOList = this.findProductByIdBatch(productIdList);
        //分组
        Map<Long, ProductVO> productMap = productVOList.stream().collect(Collectors.toMap(ProductVO::getId, Function.identity()));

        for (OrderItemRequest item : itemList) {
            //锁定商品记录
            int rows = productMapper.lockProductStock(item.getProductId(), item.getBuyNum());
            if (rows != 1) {
                throw new BizException(BizCodeEnum.ORDER_CONFIRM_LOCK_PRODUCT_FAIL);
            } else {
                //插入商品product_task
                ProductVO productVO = productMap.get(item.getProductId());
                ProductTaskDO productTaskDO = new ProductTaskDO();
                productTaskDO.setBuyNum(item.getBuyNum());
                productTaskDO.setLockState(StockTaskStateEnum.LOCK.name());
                productTaskDO.setProductId(item.getProductId());
                productTaskDO.setProductName(productVO.getTitle());
                productTaskDO.setOutTradeNo(outTradeNo);
                productTaskMapper.insert(productTaskDO);
                log.info("商品库存锁定-插入商品product_task成功:{}", productTaskDO);

                // 发送MQ延迟消息，介绍商品库存
                ProductMessage productMessage = new ProductMessage();
                productMessage.setOutTradeNo(outTradeNo);
                productMessage.setTaskId(productTaskDO.getId());

                rabbitTemplate.convertAndSend(rabbitMQConfig.getEventExchange(), rabbitMQConfig.getStockReleaseDelayRoutingKey(), productMessage);
                log.info("商品库存锁定信息延迟消息发送成功:{}", productMessage);

            }

        }
        return JsonData.buildSuccess();
    }

    /**
     * 释放商品库存
     *
     * @param productMessage
     * @return
     */
    @Override
    public boolean releaseProductStock(ProductMessage productMessage) {

        //查询工作单状态
        ProductTaskDO taskDO = productTaskMapper.selectOne(new QueryWrapper<ProductTaskDO>().eq("id", productMessage.getTaskId()));
        if (taskDO == null) {
            log.warn("工作单不存在，消息体为:{}", productMessage);
        }

        //lock状态才处理
        if (taskDO.getLockState().equalsIgnoreCase(StockTaskStateEnum.LOCK.name())) {

            //查询订单状态
            JsonData jsonData = orderFeignSerivce.queryProductOrderState(productMessage.getOutTradeNo());

            if (jsonData.getCode() == 0) {

                String state = jsonData.getData().toString();

                if (ProductOrderStateEnum.NEW.name().equalsIgnoreCase(state)) {
                    //状态是NEW新建状态，则返回给消息队，列重新投递
                    log.warn("订单状态是NEW,返回给消息队列，重新投递:{}", productMessage);
                    return false;
                }

                //如果是已经支付
                if (ProductOrderStateEnum.PAY.name().equalsIgnoreCase(state)) {
                    //如果已经支付，修改task状态为finish
                    taskDO.setLockState(StockTaskStateEnum.FINISH.name());
                    productTaskMapper.update(taskDO, new QueryWrapper<ProductTaskDO>().eq("id", productMessage.getTaskId()));
                    log.info("订单已经支付，修改库存锁定工作单FINISH状态:{}", productMessage);
                    return true;
                }
            }

            //订单不存在，或者订单被取消，确认消息,修改task状态为CANCEL,恢复优惠券使用记录为NEW
            log.warn("订单不存在，或者订单被取消，确认消息,修改task状态为CANCEL,恢复商品库存,message:{}", productMessage);
            taskDO.setLockState(StockTaskStateEnum.CANCEL.name());
            productTaskMapper.update(taskDO, new QueryWrapper<ProductTaskDO>().eq("id", productMessage.getTaskId()));


            //恢复商品库存，集锁定库存的值减去当前购买的值
            productMapper.unlockProductStock(taskDO.getProductId(), taskDO.getBuyNum());

            return true;

        } else {
            log.warn("工作单状态不是LOCK,state={},消息体={}", taskDO.getLockState(), productMessage);
            return true;
        }

    }

    private ProductVO beanProcess(ProductDO obj) {
        ProductVO couponVO = new ProductVO();
        BeanUtils.copyProperties(obj, couponVO);
        return couponVO;
    }
}
