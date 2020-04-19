package cn.shiwensama;


import cn.shiwensama.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TmatemsApplicationTests {

    @Autowired
    private JwtUtils jwtUtils;

    @Test
    public void contextLoads() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxIiwic3ViIjoiYWRtaW4iLCJpYXQiOjE1ODcyNjk0NjgsImV4cCI6MTU4NzI3NjY2OCwiYWRtaW4iOnsiaWQiOiIxIiwibmFtZSI6Iui2hee6p-euoeeQhuWRmCIsInVzZXJuYW1lIjoiYWRtaW4iLCJwYXNzd29yZCI6ImFkbWluIiwic2V4IjoxLCJjb2xsZWdlIjowLCJkZWxldGVkIjowfSwicm9sZSI6MX0.pp-qrRmD_wyoeDVzNpT9tT2z4NyArf5FZiHkdyltpDE";
        Claims claims = jwtUtils.parseJWT(token);
        Object key = claims.get("admin");

        System.out.println(key);
    }
}
