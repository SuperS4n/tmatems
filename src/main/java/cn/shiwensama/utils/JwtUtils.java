package cn.shiwensama.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Map;

/**
 * @author: shiwensama
 * @create: 2020-03-29
 * @description: jwt工具类
 **/
@Getter
@Setter
public class JwtUtils {

    /**
     * 签名的私钥
     */
    private String key = "tmatems";

    /**
     * 过期时间
     */
    private long ttl = 7200000;

    /**
     * 设置认证token
     */
    public String createJWT(String id, String subject) {

        long now = System.currentTimeMillis();
        //失效时间
        long exp = now + ttl;
        //创建jwtBuilder
        JwtBuilder jwtBuilder = Jwts.builder().setId(id)
                .setSubject(subject).setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, key);

        if (ttl > 0) {
            jwtBuilder.setExpiration(new Date(exp));
        }

        //返回token
        return jwtBuilder.compact();
    }

    /**
     * 设置认证token 可以携带用户信息
     */
    public String createJWT(String id, String subject, Map<String, Object> map) {

        long now = System.currentTimeMillis();
        //失效时间
        long exp = now + ttl;
        //创建jwtBuilder
        JwtBuilder jwtBuilder = Jwts.builder().setId(id)
                .setSubject(subject).setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, key);

        if (ttl > 0) {
            jwtBuilder.setExpiration(new Date(exp));
        }
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            jwtBuilder.claim(entry.getKey(), entry.getValue());
        }
        //返回token
        return jwtBuilder.compact();
    }

    /**
     * 解析JWT
     */
    public Claims parseJWT(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token).getBody();
        } catch (Exception e) {
            return null;
        }
        return claims;
    }
}
