package cn.shiwensama.aspect;

import cn.shiwensama.eneity.Log;
import cn.shiwensama.enums.StateEnum;
import cn.shiwensama.service.LogService;
import cn.shiwensama.utils.StringUtils;
import cn.shiwensama.utils.ThreadLocalContext;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @author: shiwensama
 * @create: 2020-03-29
 * @description: 切面输出基本信息
 *               以及记录日志
 **/
@Aspect
@Component
@Slf4j
public class RequestAspect {


    @Autowired
    private LogService logService;


    /**
     * 两个..代表所有子目录，最后括号里的两个..代表所有参数
     */
    @Pointcut("execution( * cn.shiwensama.controller..*(..))")
    public void logPointCut() {
    }

    /**
     * 方法执行之前调用，前置通知
     */
    @Before("logPointCut()")
    public void doBefore(JoinPoint joinPoint) throws Exception {


        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        String uri = request.getRequestURI();
        // 记录下请求内容
        printRequestLog(joinPoint, request, uri);
    }

    /**
     * 环绕通知
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around("logPointCut()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object ob = pjp.proceed();
        long time = System.currentTimeMillis() - startTime;
        log.info("耗时 : {}", time);
        //获取日志实体
        Log logger = ThreadLocalContext.get().getLogger();
        //拿到响应时间
        logger.setTime(time);
        return ob;
    }

    /**
     * 后置通知
     *
     * @param ret
     */
    @AfterReturning(returning = "ret", pointcut = "logPointCut()")
    public void doAfterReturning(Object ret) {
        String result = JSON.toJSONString(ret);
        log.info("返回值：{}",JSON.toJSONString(ret));

        Log logger = ThreadLocalContext.get().getLogger();
        //设置返回值
        logger.setResult(result);

        logService.save(logger);
    }

    /**
     * 异常通知
     *
     * @param joinPoint
     * @param e
     */
    @AfterThrowing(pointcut = "logPointCut()", throwing = "e")
    public void saveExceptionLog(JoinPoint joinPoint, Throwable e) {
        Log logger = ThreadLocalContext.get().getLogger();
        logger.setStatus(StateEnum.REQUEST_ERROR.getCode());

        //获取 包名 异常
        String exception = StringUtils.getPackageException(e, "cn.supers4n");
        logger.setMessage(exception);
        //异常时 响应时间为0
        logger.setTime(0L);
        logService.save(logger);

    }

    /**
     * 打印请求日志
     *
     * @param joinPoint
     * @param request
     * @param uri
     */
    private void printRequestLog(JoinPoint joinPoint, HttpServletRequest request, String uri) {
        // 拿到切面方法
        log.info("请求地址 : {}", uri);
        log.info("请求方式 : {}", request.getMethod());
        // 获取真实的ip地址
        String ip = StringUtils.getRemoteIp(request);
        log.info("IP : {}", ip);
        String controllerName = joinPoint.getSignature().getDeclaringTypeName();
        log.info("方法 : {}.{}", controllerName, joinPoint.getSignature().getName());
        String params = Arrays.toString(joinPoint.getArgs());
        log.info("请求参数：{}", params);

        // 获取日志实体
        Log logger = ThreadLocalContext.get().getLogger();

        logger.setUrl(uri);
        logger.setParams(params);
        logger.setStatus(StateEnum.REQUEST_SUCCESS.getCode());
        logger.setMethod(request.getMethod());
        logger.setIp(ip);
    }
}
