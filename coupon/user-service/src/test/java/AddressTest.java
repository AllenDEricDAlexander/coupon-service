import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.atluofu.UserServiceApplication;
import top.atluofu.model.AddressDO;
import top.atluofu.service.AddressService;
import top.atluofu.vo.AddressVO;

import java.util.ArrayList;

/**
 * @ClassName: AddressTest
 * @description: TODO
 * @author: 有罗敷的马同学
 * @datetime: 2023Year-08Month-06Day-21:19
 * @Version: 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserServiceApplication.class)
@Slf4j
public class AddressTest {

    @Autowired
    private AddressService addressService;


    @Test
    public void testAddressDetail() {
        AddressVO addressDO = addressService.detail(1L);
        log.info(addressDO.toString());
    }


}