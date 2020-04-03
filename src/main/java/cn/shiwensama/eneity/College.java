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
public class College implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * ID
     */
    private Integer id;

    /**
     * 学院名称
     */
    private String name;

    /**
     * 逻辑删除
     */
    private Integer deleted;


}
