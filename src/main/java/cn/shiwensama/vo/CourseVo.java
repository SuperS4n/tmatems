package cn.shiwensama.vo;

import cn.shiwensama.eneity.Course;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author: shiwensama
 * @create: 2020-04-07
 * @description:
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public class CourseVo extends Course {

    private static final long serialVersionUID = 1L;

    private Integer pagenum=1;
    private Integer pagesize=10;
}
