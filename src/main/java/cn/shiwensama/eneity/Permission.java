package cn.shiwensama.eneity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class Permission implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 父级编号
     */
    private Integer pid;

    /**
     * 权限或编码名称
     */
    private String name;

    /**
     * 权限类型[menu/permission]
     */
    private String type;

    /**
     * 权限编码[只有type= permission才有  user:view]
     */
    private String percode;

    /**
     * 图标
     */
    private String icon;

    /**
     * 路由
     */
    private String href;

    /**
     * 状态 0:未启用，1:启用
     */
    private Integer available;


}
