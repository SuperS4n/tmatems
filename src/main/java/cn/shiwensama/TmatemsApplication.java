package cn.shiwensama;

import cn.shiwensama.utils.GetPerCodes;
import cn.shiwensama.utils.JwtUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@MapperScan("cn.shiwensama.mapper")
public class TmatemsApplication {

    public static void main(String[] args) {
        SpringApplication.run(TmatemsApplication.class, args);
    }

    @Bean
    public JwtUtils jwtUtils() {
        return new JwtUtils();
    }

    @Bean
    public GetPerCodes getPerCodes() {
        return new GetPerCodes();
    }
}
