package cn.shiwensama.controller;


import cn.shiwensama.eneity.Permission;
import cn.shiwensama.service.PermissionService;
import cn.shiwensama.utils.Result;
import cn.shiwensama.vo.PermissionVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author supers4n
 * @since 2020-04-02
 */
@RestController
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    /**
     * 分页查询班级
     *
     * @param permissionVo
     * @return
     */
    @RequiresPermissions("permission:view")
    @RequestMapping(value = "/permission", method = RequestMethod.GET)
    public Result<Object> getAllPermission(PermissionVo permissionVo) {
        IPage<Permission> page = new Page<>(permissionVo.getPagenum(), permissionVo.getPagesize());

        QueryWrapper<Permission> qw = new QueryWrapper<>();
        qw.eq("type","permission");

        permissionService.page(page,qw);

        Map<String, Object> resultMap = new HashMap<>(4);
        resultMap.put("permissions", page.getRecords());
        resultMap.put("total", page.getTotal());
        return new Result<>("分页查询权限成功", resultMap);
    }

}

