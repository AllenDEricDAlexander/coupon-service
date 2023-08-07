package top.atluofu.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName: OSSConfig
 * @description: TODO
 * @author: 有罗敷的马同学
 * @datetime: 2023Year-08Month-07Day-19:13
 * @Version: 1.0
 */
@ConfigurationProperties(prefix = "aliyun.oss")
@Configuration
@Data
public class OSSConfig {

    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;
}