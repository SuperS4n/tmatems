package cn.shiwensama.exception;

import cn.shiwensama.enums.ResultEnum;
import cn.shiwensama.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;



/**
 * @author: shiwensama
 * @create: 2020-03-29
 * @description: 系统统一异常处理类
 **/
@ControllerAdvice
@Slf4j
public class SysExceptionAdvice {

    /**
     * controller 加上@Transactional做回滚 要手动抛出RuntimeException,
     * 在这里捕获
     * @param exception
     * @return
     */
    @ResponseBody
    @ExceptionHandler(SysException.class)
    public Result<Object> exceptionHandler(SysException exception) {
        log.error("统一异常处理：",exception);
        return new Result<>(exception.getErrorCode(),exception.getMessage());
    }

    /**
     * shiro异常统一处理
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(UnauthorizedException.class)
    public Result<Object> unauthorizedExceptionHandler(Exception e) {
        return new Result<>(ResultEnum.UNAUTHORIZED);
    }

    @ResponseBody
    @ExceptionHandler(AuthenticationException.class)
    public Result<Object> authenticationExceptionHandler(Exception e) {
        return new Result<>(ResultEnum.PARAMS_ERROR.getCode(),"用户名或密码错误");
    }


}
