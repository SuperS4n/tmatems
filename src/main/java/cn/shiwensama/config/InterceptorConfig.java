package cn.shiwensama.config;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author: shiwensama
 * @create: 2020-03-29
 * @description: 拦截器配置类
 **/
public class InterceptorConfig extends WebMvcConfigurationSupport {

    // @Bean
    // public JwtInterceptor jwtInterceptor() {
    //     return new JwtInterceptor();
    // }
    //
    // @Override
    // protected void addInterceptors(InterceptorRegistry registry) {
    //     registry.addInterceptor(jwtInterceptor())
    //             //拦截的url
    //             .addPathPatterns("/**")
    //             //不拦截的url
    //             .excludePathPatterns("/*/login")
    //             .excludePathPatterns("/registered")
    //             .excludePathPatterns("/registered/*")
    //             .excludePathPatterns("/loadAllCollege")
    //             .excludePathPatterns("//loadAllClasses/*");
    //
    //     super.addInterceptors(registry);
    // }
}
