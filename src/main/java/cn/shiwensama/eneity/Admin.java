package cn.shiwensama.eneity;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author supers4n
 * @since 2020-04-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Admin implements Serializable {

    private static final long serialVersionUID=1L;

    private String id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 账号
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 1男2女
     */
    private Integer sex;

    /**
     * 学院编号，0为超级管理员
     */
    private Integer college;

    /**
     * 头像
     */
    private String header;

    /**
     * 逻辑删除，0否1是
     */
    private Integer deleted;


}
