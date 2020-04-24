package cn.shiwensama.eneity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author supers4n
 * @since 2020-04-23
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Grade implements Serializable {

    private static final long serialVersionUID=1L;

    private Integer id;

    /**
     * 年级名称
     */
    private String name;

    /**
     * 班级
     */
    private List<Classes> classesList;

}
