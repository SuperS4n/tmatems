package cn.shiwensama.utils;


import cn.shiwensama.eneity.Log;
import lombok.Data;

/**
 * @author: shiwensama
 * @create: 2020-03-26
 * @description: spirng中为单例模式
 *               用ThreadLocalContext来存储在同一个线程中可能会用到的全局变量
 **/
@Data
public class ThreadLocalContext {

    /**
     * 日志实体
     */
    private Log logger = new Log();

    /**
     * 是否记录日志
     */
    private boolean isLog = false;

    /**
     * 线程本地内存中的变量
     */
    private static ThreadLocal<ThreadLocalContext> threadLocal = new ThreadLocal<>();

    public static ThreadLocalContext get() {
        if (threadLocal.get() == null) {
            ThreadLocalContext threadLocalContext = new ThreadLocalContext();
            threadLocal.set(threadLocalContext);
        }
        ThreadLocalContext threadLocalContext = threadLocal.get();
        return threadLocalContext;
    }

    public void remove() {
        this.logger = null;
        this.isLog = false;
        threadLocal.remove();
    }
}
