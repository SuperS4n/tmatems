package cn.shiwensama.vo;

import cn.shiwensama.eneity.Materials;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author: shiwensama
 * @create: 2020-05-01
 * @description:
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public class MaterialsVo extends Materials {

    private static final long serialVersionUID = 1L;

    private Integer pagenum=1;
    private Integer pagesize=10;

}
