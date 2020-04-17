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
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTU4NzAzNTEwNSwiZXhwIjoxNTg3MDQyMzA1LCJhZG1pbiI6eyJpZCI6IjEiLCJuYW1lIjoi6LaF57qn566h55CG5ZGYIiwidXNlcm5hbWUiOiJhZG1pbiIsInBhc3N3b3JkIjoiYWRtaW4iLCJzZXgiOjEsImNvbGxlZ2UiOjAsImRlbGV0ZWQiOjB9fQ.x3RT3vspw_rXn5md3rUtUydoDTvbqEh3bzwkzZzdzhI";
        Claims claims = jwtUtils.parseJWT(token);
        Object admin = claims.get("admin");

        System.out.println(admin);
    }
}
