package cn.shiwensama.enums;

import lombok.Getter;

/**
 * @author: shiwenSama
 * @create: 2020-03-29
 * @description: 状态码枚举
 **/
@Getter
public enum StateEnum {

    /**
     * 逻辑删除状态
     */
    DELETED(1, "已删除"),
    NOT_DELETED(0, "未删除"),

    /**
     * 启用状态
     */
    ENABLED(1, "启用"),
    NOT_ENABLE(0, "未启用"),

    /**
     * 性别状态
     */
    SEX_MAN(1, "男"),
    SEX_WOMAN(2, "女"),

    /**
     * 请求访问状态枚举
     */
    REQUEST_SUCCESS(1, "请求正常"),
    REQUEST_ERROR(0, "请求异常"),

    /**
     * 用户标识。
     * 1表示管理员，2表示学生，3表示教师
     */
    ADMIN(1, "管理员"),
    STUDENT(2, "学生"),
    TEACHER(3,"教师"),
    COLLEGEADMIN(4,"学院管理员");


    private Integer code;
    private String msg;

    StateEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
