package top.atluofu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @ClassName: OrderServiceApplication
 * @description: TODO
 * @author: 有罗敷的马同学
 * @datetime: 2023Year-08Month-09Day-22:01
 * @Version: 1.0
 */
@SpringBootApplication
@MapperScan("top.atluofu")
public class OrderServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }

}
