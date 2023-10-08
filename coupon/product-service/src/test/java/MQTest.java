import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.atluofu.ProductServiceApplication;
import top.atluofu.model.ProductMessage;

/**
 * @ClassName: MQTest
 * @description: TODO
 * @author: 有罗敷的马同学
 * @datetime: 2023Year-10Month-08Day-18:51
 * @Version: 1.0
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProductServiceApplication.class)
@Slf4j
public class MQTest {


    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testSendDelayMsg() {

        rabbitTemplate.convertAndSend("stock.event.exchange", "stock.release.delay.routing.key", "this is product stock lock msg");

    }


    @Test
    public void testSendProductStockMessage() {
        ProductMessage productMessage = new ProductMessage();

        productMessage.setOutTradeNo("123456abc");
        productMessage.setTaskId(1L);
        rabbitTemplate.convertAndSend("stock.event.exchange", "stock.release.delay.routing.key", productMessage);

    }


}
