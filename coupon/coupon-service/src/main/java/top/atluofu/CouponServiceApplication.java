package top.atluofu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @ClassName: UserServiceApplication
 * @description: TODO
 * @author: 有罗敷的马同学
 * @datetime: 2023Year-08Month-06Day-19:09
 * @Version: 1.0
 */
@SpringBootApplication
@MapperScan("top.atluofu.mapper")
@EnableFeignClients
@EnableDiscoveryClient
@EnableTransactionManagement
public class CouponServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CouponServiceApplication.class, args);
    }
}
