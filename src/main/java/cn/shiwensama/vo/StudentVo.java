package cn.shiwensama.vo;

import cn.shiwensama.eneity.Student;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author: shiwensama
 * @create: 2020-04-02
 * @description:
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public class StudentVo extends Student {

    private static final long serialVersionUID = 1L;

    private Integer pagenum=1;
    private Integer pagesize=10;
}
