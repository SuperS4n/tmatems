package cn.shiwensama.vo;

import cn.shiwensama.eneity.College;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author: shiwensama
 * @create: 2020-04-06
 * @description:
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public class CollegeVo extends College {
    private static final long serialVersionUID = 1L;

    private Integer pagenum=1;
    private Integer pagesize=10;
}
