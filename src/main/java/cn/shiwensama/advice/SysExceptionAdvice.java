package cn.shiwensama.advice;

import cn.shiwensama.exception.SysException;
import cn.shiwensama.utils.Result;
import lombok.extern.slf4j.Slf4j;
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

    @ResponseBody
    @ExceptionHandler(SysException.class)
    public Result<Object> exceptionHandler(SysException exception) {
        log.error("出现以下异常：",exception);
        return new Result<>(exception.getErrorCode(),exception.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(UnauthorizedException.class)
    public Result<Object> defaultExceptionHandler(Exception e) {
        return new Result<>("权限不足","/error");
    }
}
