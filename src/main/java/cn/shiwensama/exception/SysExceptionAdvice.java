package cn.shiwensama.exception;

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
        log.error("统一异常处理：",exception);
        return new Result<>(exception.getErrorCode(),exception.getMessage());
    }

    /**
     * shiro权限不足,捕获异常提示
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(UnauthorizedException.class)
    public Result<Object> defaultExceptionHandler(Exception e) {
        return new Result<>("权限不足","/error");
    }

    /**
     * controller 加上@Transactional做回滚 要手动抛出RuntimeException,
     * 在这里捕获
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(RuntimeException.class)
    public Result<Object> getRuntimeException(Exception e) {
        log.error("出现异常：",e);
        return new Result<>(400,"操作失败，接口异常");
    }
}
