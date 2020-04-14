package cn.shiwensama.interceptor;

import cn.shiwensama.enums.ResultEnum;
import cn.shiwensama.exception.SysException;
import cn.shiwensama.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: shiwensama
 * @create: 2020-03-29
 * @description: jwt拦截器
 * Authorization 约定 ： Bearer+空格+token
 **/
public class JwtInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1.通过request获取到authorization
        String authorization = request.getHeader("Authorization");
        //2.判断
        if (!StringUtils.isEmpty(authorization) && authorization.startsWith("Bearer ")) {

            String token = authorization.replace("Bearer ", "");
            Claims claims = jwtUtils.parseJWT(token);
            Object collegeId = null;

            try {
                collegeId = claims.get("collegeId");
            } catch (Exception e) {
                throw new SysException(403,"token无效");
            }

            request.setAttribute("collegeId", collegeId);
            //放行
            return true;
        }
        throw new SysException(ResultEnum.NOT_LOGIN);
    }
}
