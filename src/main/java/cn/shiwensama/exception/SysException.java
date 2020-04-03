package cn.shiwensama.exception;

import cn.shiwensama.enums.ResultEnum;

/**
 * @author: shiwensama
 * @create: 2020-03-29
 * @description: 异常处理类
 **/
public class SysException extends RuntimeException {

    private static final long serialVersionUID = 2450214686001409867L;

    private Integer errorCode = ResultEnum.ERROR.getCode();

    public SysException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.errorCode = resultEnum.getCode();
    }

    public SysException(ResultEnum resultEnum, Throwable throwable) {
        super(resultEnum.getMsg(), throwable);
        this.errorCode = resultEnum.getCode();
    }

    public SysException(Integer errorCode, String msg) {
        super(msg);
        this.errorCode = errorCode;
    }

    public SysException(String msg) {
        super(msg);
    }

    public SysException(Throwable throwable) {
        super(throwable);
    }

    public SysException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

    public Integer getErrorCode() {
        return errorCode;
    }
}
