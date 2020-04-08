package cn.shiwensama.vo;

import cn.shiwensama.eneity.Tcourse;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author: shiwensama
 * @create: 2020-04-08
 * @description:
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public class TcourseVo extends Tcourse {

    private static final long serialVersionUID = 1L;

    private Integer pagenum=1;
    private Integer pagesize=10;
}
