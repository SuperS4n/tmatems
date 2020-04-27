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
 * @since 2020-04-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Scourse implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 课程编号
     */
    private Integer cid;

    /**
     * 用户编号
     */
    private String uid;

    /**
     * 课程名称
     */
    @TableField(exist = false)
    private String name;

    /**
     * 所属学院
     */
    @TableField(exist = false)
    private String collegeName;

    /**
     * 任课教师名称
     */
    @TableField(exist = false)
    private String teacherName;

    /**
     * 备注
     */
    @TableField(exist = false)
    private String remark;


}
