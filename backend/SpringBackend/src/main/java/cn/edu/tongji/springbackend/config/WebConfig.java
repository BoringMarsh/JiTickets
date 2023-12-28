package cn.edu.tongji.springbackend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("*")  // 允许所有的前端域
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // 允许的HTTP方法
                .allowedHeaders("header1", "header2", "header3")  // 允许的HTTP头
                .exposedHeaders("header1", "header2")  // 在响应中公开的头
                .allowCredentials(false)  // 是否支持发送Cookie
                .maxAge(3600);  // 预检请求的有效期，单位秒
    }
}

