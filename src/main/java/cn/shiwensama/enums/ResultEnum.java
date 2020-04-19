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
    ERROR(400,"操作失败,接口异常"),
    NOT_LOGIN(401,"当前账号未登录,无效token"),
    PARAMS_ERROR(402,"参数错误"),
    UNAUTHORIZED(403,"权限不足"),
    DATA_NOT_FOUND(404,"查询失败"),
    ;

    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
