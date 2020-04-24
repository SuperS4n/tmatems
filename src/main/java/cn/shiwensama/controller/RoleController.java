package cn.shiwensama.controller;


import cn.shiwensama.eneity.Role;
import cn.shiwensama.service.RoleService;
import cn.shiwensama.utils.Result;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
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
public class RoleController {

    @Autowired
    private RoleService roleService;

    @RequiresPermissions("role:view")
    @RequestMapping(value = "/role", method = RequestMethod.GET)
    public Result<Object> getAllRole() {

        List<Role> roles = roleService.getAllRole();

        Map<String, Object> resultMap = new HashMap<>(4);

        resultMap.put("roles", roles);
        return new Result<>("查询角色成功", resultMap);
    }

}

