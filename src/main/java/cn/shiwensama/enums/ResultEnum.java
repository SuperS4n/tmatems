package cn.shiwensama.enums;

import lombok.Getter;

/**
 * @author: shiwenSama
 * @create: 2020-03-29
 * @description: 返回结果枚举
 **/
@Getter
public enum ResultEnum {

    /**
     * 每个枚举代表一个返回状态
     */

    //操作相关
    SUCCESS(200,"操作成功"),
    ERROR(400,"操作失败"),
    DATA_NOT_FOUND(4001,"查询失败"),
    PARAMS_ERROR(4002,"参数错误"),

    //用户相关
    USERNAMEORPASSWORDERROR(4004,"用户名或者密码错误"),
    NOT_LOGIN(4003,"当前账号未登录,无效token"),
    ;

    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
