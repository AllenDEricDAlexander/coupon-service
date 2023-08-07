import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.atluofu.UserServiceApplication;
import top.atluofu.service.MailService;

/**
 * @ClassName: MailTest
 * @description: TODO
 * @author: 有罗敷的马同学
 * @datetime: 2023Year-08Month-07Day-17:10
 * @Version: 1.0
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserServiceApplication.class)
@Slf4j
public class MailTest {

    @Autowired
    private MailService mailService;

    @Test
    public void sendMail() {
        mailService.sendMail("mqa17530719625@163.com","test send email","content test demo");
    }
}
