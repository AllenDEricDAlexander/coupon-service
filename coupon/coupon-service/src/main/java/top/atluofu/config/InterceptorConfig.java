package top.atluofu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.atluofu.interceptor.LoginInterceptor;

/**
 * @ClassName: InterceptorConfig
 * @description: TODO
 * @author: 有罗敷的马同学
 * @datetime: 2023Year-08Month-08Day-18:42
 * @Version: 1.0
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {


    @Bean
    LoginInterceptor loginInterceptor() {
        return new LoginInterceptor();
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(loginInterceptor())
                .addPathPatterns("/api/coupon_record/*/**")
                .addPathPatterns("/api/coupon/*/**")

                //不拦截的路径
                .excludePathPatterns("/api/coupon/*/page_coupon");

        WebMvcConfigurer.super.addInterceptors(registry);
    }


}

