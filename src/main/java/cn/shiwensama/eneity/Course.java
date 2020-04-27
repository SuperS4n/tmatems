package cn.shiwensama.eneity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
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
 * @since 2020-04-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Course implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * ID
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    /**
     * 课程名称
     */
    private String name;

    /**
     * 所属学院ID
     */
    private Integer college;

    /**
     * 学院名称
     */
    @TableField(exist = false)
    private String collegeName;

    /**
     * 级别 1：大一，2：大二，3：大三，4：大四
     */
    private Integer level;

    /**
     * 任课教师ID
     */
    private String teacher;

    /**
     * 任课教师名称
     */
    @TableField(exist = false)
    private String teacherName;

    /**
     * 课程描述
     */
    private String remark;

    /**
     * 是否开启评论 0：未开启，1：开启
     */
    private Integer isok;

    /**
     * 逻辑删除，0：未删除，1：已删除
     */
    @TableLogic
    private Integer deleted;


}
