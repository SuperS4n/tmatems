package cn.shiwensama.vo;

import cn.shiwensama.eneity.Classes;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author: shiwensama
 * @create: 2020-04-11
 * @description:
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public class ClassesVo extends Classes {
    private static final long serialVersionUID = 1L;

    private Integer pagenum=1;
    private Integer pagesize=10;
}
