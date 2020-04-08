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
public class Tcourse implements Serializable {

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


}
