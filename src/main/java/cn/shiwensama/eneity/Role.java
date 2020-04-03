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
public class Role implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * ID
     */
    private Integer id;

    /**
     *  角色名
     */
    private String  name;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否启用，0:未启用，1:启用
     */
    private Integer available;


}
