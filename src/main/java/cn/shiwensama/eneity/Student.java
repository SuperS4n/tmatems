package cn.shiwensama.eneity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

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
public class Student implements Serializable {

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
     * 学院编号
     */
    private Integer college;

    /**
     * 学院名称
     */
    @TableField(exist = false)
    private String collegeName;

    /**
     * 年级，1234
     */
    private Integer level;

    /**
     * 班级
     */
    private String classes;

    /**
     * 头像
     */
    private String header;

    /**
     * 逻辑删除，0否1是
     */
    private Integer deleted;


}
