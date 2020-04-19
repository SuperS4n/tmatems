package cn.shiwensama.interceptor;

import cn.shiwensama.utils.JwtUtils;
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

    final String Bearer = "Bearer ";

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // //1.通过request获取到authorization
        // String authorization = request.getHeader("Authorization");
        // //2.判断
        // if (StringUtils.isNotBlank(authorization) && authorization.startsWith(Bearer)) {
        //     return true;
        // }
        //throw new SysException(ResultEnum.NOT_LOGIN);
        return true;
    }
}
