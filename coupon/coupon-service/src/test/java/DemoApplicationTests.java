import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.atluofu.CouponServiceApplication;
import top.atluofu.model.CouponRecordMessage;

/**
 * @ClassName: DemoApplicationTests
 * @description: TODO
 * @author: 有罗敷的马同学
 * @datetime: 2023Year-09Month-29Day-22:26
 * @Version: 1.0
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CouponServiceApplication.class)
@Slf4j
public class DemoApplicationTests {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void send(){
        rabbitTemplate.convertAndSend("coupon.event.exchange","coupon.release.delay.routing.key","5qeqweqw");

    }

    @Test
    public void testCouponRecordRelease(){

        CouponRecordMessage message = new CouponRecordMessage();
        message.setOutTradeNo("123456abc");
        message.setTaskId(1L);

        rabbitTemplate.convertAndSend("coupon.event.exchange","coupon.release.delay.routing.key",message);



    }

}

