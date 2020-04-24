package cn.shiwensama.vo;

import cn.shiwensama.eneity.Permission;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author: shiwensama
 * @create: 2020-04-23
 * @description:
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public class PermissionVo extends Permission {

    private static final long serialVersionUID = 1L;

    private Integer pagenum=1;
    private Integer pagesize=10;
}
