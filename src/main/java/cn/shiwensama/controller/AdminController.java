package cn.shiwensama.controller;


import cn.shiwensama.eneity.Admin;
import cn.shiwensama.enums.ResultEnum;
import cn.shiwensama.enums.StateEnum;
import cn.shiwensama.exception.SysException;
import cn.shiwensama.service.AdminService;
import cn.shiwensama.service.RoleService;
import cn.shiwensama.token.JwtToken;
import cn.shiwensama.utils.JwtUtils;
import cn.shiwensama.utils.Result;
import cn.shiwensama.vo.AdminVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author supers4n
 * @since 2020-04-02
 */
@RestController
public class AdminController {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleService roleService;

    /**
     * 登录方法
     *
     * @param admin
     * @return
     */
    @RequestMapping(value = "/admin/login", method = RequestMethod.POST)
    public Result<Object> login(@RequestBody Admin admin) {
        //1.先判断前端传过来的登录参数是否正确
        if (admin == null || StringUtils.isBlank(admin.getUsername()) || StringUtils.isBlank(admin.getPassword())) {
            return new Result<>(ResultEnum.PARAMS_ERROR.getCode(), "用户名或密码错误！");
        }

        //2.启用shiro登录
        Subject subject = SecurityUtils.getSubject();

        JwtToken jwtToken = new JwtToken(admin.getUsername(),admin.getPassword(),StateEnum.ADMIN.getCode());
        subject.login(jwtToken);

        return new Result<>("登录成功", jwtToken.getToken());
    }

    /**
     * 分页查询管理员
     *
     * @param adminVo
     * @return
     */
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public Result<Object> getAllAdmin(AdminVo adminVo) {
        IPage<Admin> page = new Page<>(adminVo.getPagenum(), adminVo.getPagesize());

        QueryWrapper<Admin> qw = new QueryWrapper<>();
        qw.eq(StringUtils.isNotBlank(adminVo.getName()), "name", adminVo.getName());

        this.adminService.page(page, qw);

        Map<String, Object> resultMap = new HashMap<>(4);
        resultMap.put("total", page.getTotal());
        resultMap.put("admins", page.getRecords());
        return new Result<>("分页查询管理员成功", resultMap);
    }

    /**
     * 添加管理员
     *
     * @param admin
     * @return
     */
    @RequiresPermissions("admin:create")
    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/admin", method = RequestMethod.POST)
    public Result<Object> addAdmin(@RequestBody Admin admin) {
        //判断是否重名 交由前端处理

        try {
            //逻辑删除置为 0
            admin.setDeleted(0);

            this.adminService.save(admin);
            //在role_usr表中添加用户角色的关系连接
            this.roleService.insertUserRole(admin.getId(), 4);
            return new Result<>("添加成功");
        } catch (Exception e) {
            throw new SysException(ResultEnum.ERROR.getCode(), "操作失败,接口异常");
        }
    }

    /**
     * 修改管理员
     *
     * @param admin
     * @return
     */
    @RequestMapping(value = "/admin", method = RequestMethod.PUT)
    @Transactional(rollbackFor = Exception.class)
    public Result<Object> updateAdmin(@RequestBody Admin admin) {

        try {
            this.adminService.updateById(admin);
            return new Result<>("修改成功");
        } catch (Exception e) {
            throw new SysException(ResultEnum.ERROR.getCode(), "操作失败,接口异常");
        }
    }

    /**
     * 删除管理员
     *
     * @param id
     * @return
     */
    @RequiresPermissions("admin:delete")
    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/admin/{id}", method = RequestMethod.DELETE)
    public Result<Object> deleteAdmin(@PathVariable String id) {

        try {
            adminService.removeById(id);
            this.roleService.deleteRoleUserByUid(id);
            return new Result<>("删除成功");
        } catch (Exception e) {
            throw new SysException(ResultEnum.ERROR.getCode(), "操作失败,接口异常");
        }
    }

    /**
     * 根据账号查询管理员
     *
     * @param username
     * @return
     */
    @RequiresPermissions("admin:view")
    @RequestMapping(value = "/admin/{username}", method = RequestMethod.GET)
    public Result<Object> getOneAdmin(@PathVariable String username) {
        QueryWrapper<Admin> qw = new QueryWrapper<>();
        qw.eq("username", username);
        Admin one = this.adminService.getOne(qw);

        if (one != null) {
            //已存在账号
            return new Result<>(1);
        } else {
            //账号不重复，可以注册
            return new Result<>(0);
        }
    }

}

