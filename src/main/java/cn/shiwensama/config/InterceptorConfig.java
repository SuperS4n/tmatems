package cn.shiwensama.config;

import cn.shiwensama.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author: shiwensama
 * @create: 2020-03-29
 * @description: 拦截器配置类
 **/
@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {

    @Bean
    public JwtInterceptor jwtInterceptor() {
        return new JwtInterceptor();
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor())
                //拦截的url
                .addPathPatterns("/**")
                //不拦截的url
                .excludePathPatterns("/admin/login")
                .excludePathPatterns("/student/login")
                .excludePathPatterns("/teacher/login");
        super.addInterceptors(registry);
    }
}
