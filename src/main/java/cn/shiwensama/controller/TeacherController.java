package cn.shiwensama.controller;


import cn.shiwensama.eneity.College;
import cn.shiwensama.eneity.Teacher;
import cn.shiwensama.enums.ResultEnum;
import cn.shiwensama.enums.StateEnum;
import cn.shiwensama.exception.SysException;
import cn.shiwensama.service.CollegeService;
import cn.shiwensama.service.RoleService;
import cn.shiwensama.service.TeacherService;
import cn.shiwensama.token.JwtToken;
import cn.shiwensama.utils.JwtUtils;
import cn.shiwensama.utils.Result;
import cn.shiwensama.vo.TeacherVo;
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
import java.util.List;
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
public class TeacherController {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private CollegeService collegeService;

    /**
     * 登录方法
     *
     * @param teacher
     * @return
     */
    @RequestMapping(value = "teacher/login", method = RequestMethod.POST)
    public Result<Object> login(@RequestBody Teacher teacher) {
        //1.先判断前端传过来的登录参数是否正确
        if (teacher == null || StringUtils.isBlank(teacher.getUsername()) || StringUtils.isBlank(teacher.getPassword())) {
            return new Result<>(ResultEnum.PARAMS_ERROR.getCode(), "用户名或密码错误！");
        }

        //2.启用shiro登录
        Subject subject = SecurityUtils.getSubject();

        JwtToken jwtToken = new JwtToken(teacher.getUsername(),teacher.getPassword(),StateEnum.TEACHER.getCode());
        subject.login(jwtToken);

        return new Result<>("登录成功", jwtToken.getToken());

    }

    /**
     * 分页查询教师
     *
     * @param teacherVo
     * @return
     */
    @RequiresPermissions("teacher:view")
    @RequestMapping(value = "/teacher", method = RequestMethod.GET)
    public Result<Object> getAllTeacher(TeacherVo teacherVo) {
        IPage<Teacher> page = new Page<>(teacherVo.getPagenum(), teacherVo.getPagesize());

        QueryWrapper<Teacher> qw = new QueryWrapper<>();
        qw.eq(StringUtils.isNotBlank(teacherVo.getName()), "name", teacherVo.getName());

        this.teacherService.page(page, qw);

        //循环设置学院名称
        List<Teacher> teachers = page.getRecords();
        for (Teacher teacher : teachers) {
            Integer teacherCollege = teacher.getCollege();
            if (teacherCollege != null) {
                College college = collegeService.getById(teacherCollege);
                teacher.setCollegeName(college.getName());
            }
        }
        Map<String, Object> resultMap = new HashMap<>(4);
        resultMap.put("total", page.getTotal());
        resultMap.put("teachers", teachers);
        return new Result<>("分页查询教师成功", resultMap);
    }

    /**
     * 添加教师
     *
     * @param teacher
     * @return
     */
    @RequiresPermissions("teacher:create")
    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/teacher", method = RequestMethod.POST)
    public Result<Object> addTeacher(@RequestBody Teacher teacher) {
        //判断是否重名 交由前端处理

        try {
            //逻辑删除置为 0
            teacher.setDeleted(0);

            this.teacherService.save(teacher);
            //在role_usr表中添加用户角色的关系连接
            this.roleService.insertUserRole(teacher.getId(), 3);
            return new Result<>("添加成功");
        } catch (Exception e) {
            throw new SysException(ResultEnum.ERROR.getCode(), "操作失败,接口异常");
        }
    }

    /**
     * 修改教师
     *
     * @param teacher
     * @return
     */
    @RequiresPermissions("teacher:update")
    @RequestMapping(value = "/teacher", method = RequestMethod.PUT)
    @Transactional(rollbackFor = Exception.class)
    public Result<Object> updateTeacher(@RequestBody Teacher teacher) {

        try {
            this.teacherService.updateById(teacher);
            return new Result<>("修改成功");
        } catch (Exception e) {
            throw new SysException(ResultEnum.ERROR.getCode(), "操作失败,接口异常");
        }
    }

    /**
     * 删除教师
     *
     * @param id
     * @return
     */
    @RequiresPermissions("teacher:delete")
    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/teacher/{id}", method = RequestMethod.DELETE)
    public Result<Object> deleteTeacher(@PathVariable String id) {

        try {
            teacherService.removeById(id);
            this.roleService.deleteRoleUserByUid(id);
            return new Result<>("删除成功");
        } catch (Exception e) {
            throw new SysException(ResultEnum.ERROR.getCode(), "操作失败,接口异常");
        }
    }

    /**
     * 根据账号查询教师
     * @param username
     * @return
     */
    @RequiresPermissions("teacher:view")
    @RequestMapping(value = "/teacher/{username}",method = RequestMethod.GET)
    public Result<Object> getOneTeacher(@PathVariable String username) {
        QueryWrapper<Teacher> qw = new QueryWrapper<>();
        qw.eq("username",username);
        Teacher one = this.teacherService.getOne(qw);

        if(one != null) {
            //已存在账号
            return new Result<>(1);
        }else {
            //账号不重复，可以注册
            return new Result<>(0);
        }
    }
}

