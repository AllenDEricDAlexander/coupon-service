import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.atluofu.OrderServiceApplication;

/**
 * @ClassName: DemoApplicationTests
 * @description: TODO
 * @author: 有罗敷的马同学
 * @datetime: 2023Year-10Month-09Day-21:46
 * @Version: 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderServiceApplication.class)
@Slf4j
public class DemoApplicationTests {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void send(){
        rabbitTemplate.convertAndSend("order.event.exchange","order.close.delay.routing.key","23342342");

    }

}
