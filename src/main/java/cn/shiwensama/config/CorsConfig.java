package cn.shiwensama.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @author: shiwensama
 * @create: 2020-04-11
 * @description: 有拦截器用这种方式 把cors放在filter里，就可以优先于权限拦截器执行。
 **/
@Configuration
public class CorsConfig{

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        //1 设置访问源地址
        config.addAllowedOrigin("*");
        // 是否支持安全证书
        config.setAllowCredentials(true);
        // 2 设置访问源请求头
        config.addAllowedMethod("*");
        // 3 设置访问源请求方法
        config.addAllowedHeader("*");
        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
        configSource.registerCorsConfiguration("/**", config);
        return new CorsFilter(configSource);

    }
}
