package cn.shiwensama.controller;


import cn.shiwensama.eneity.Teacher;
import cn.shiwensama.enums.ResultEnum;
import cn.shiwensama.enums.StateEnum;
import cn.shiwensama.exception.SysException;
import cn.shiwensama.service.RoleService;
import cn.shiwensama.service.TeacherService;
import cn.shiwensama.token.UsernamePasswordToken;
import cn.shiwensama.utils.JwtUtils;
import cn.shiwensama.utils.Result;
import cn.shiwensama.vo.TeacherVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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
public class TeacherController {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private RoleService roleService;

    /**
     * 登录方法
     *
     * @param teacher
     * @return
     */
    @RequestMapping(value = "teacher/login", method = RequestMethod.POST)
    private Result<Object> login(@RequestBody Teacher teacher) {
        //1.先判断前端传过来的登录参数是否正确
        if (teacher == null || StringUtils.isBlank(teacher.getUsername()) || StringUtils.isBlank(teacher.getPassword())) {
            return new Result<>(ResultEnum.PARAMS_ERROR.getCode(), "用户名或密码错误！");
        }

        //2.启用shiro登录
        Subject subject = SecurityUtils.getSubject();
        AuthenticationToken authenticationToken = new UsernamePasswordToken(teacher.getUsername(),
                teacher.getPassword(), StateEnum.TEACHER.getCode());
        try {
            subject.login(authenticationToken);
        } catch (Exception e) {
            throw new SysException(ResultEnum.PARAMS_ERROR.getCode(), "用户名或密码错误！");
        }
        //3.登录成功,设置token
        String jwt = jwtUtils.createJWT(teacher.getId(), teacher.getUsername());

        return new Result<>("登录成功", jwt);
    }

    /**
     * 分页查询教师
     *
     * @param teacherVo
     * @param request
     * @return
     */
    @RequestMapping(value = "/teacher", method = RequestMethod.GET)
    public Result<Object> getAllTeacher(TeacherVo teacherVo, HttpServletRequest request) {
        IPage<Teacher> page = new Page<>(teacherVo.getPagenum(), teacherVo.getPagesize());

        QueryWrapper<Teacher> qw = new QueryWrapper<>();
        qw.eq(StringUtils.isNotBlank(teacherVo.getName()), "name", teacherVo.getName());
        //设置学院
        Integer collegeId = (Integer) request.getAttribute("collegeId");
        qw.eq(collegeId != null && collegeId != 0, "college", request.getAttribute("collegeId"));

        this.teacherService.page(page, qw);

        Map<String, Object> resultMap = new HashMap<>(4);
        resultMap.put("total", page.getTotal());
        resultMap.put("teachers", page.getRecords());
        return new Result<>("分页查询教师成功", resultMap);
    }

    /**
     * 添加教师
     *
     * @param teacher
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/teacher", method = RequestMethod.POST)
    public Result<Object> addStudent(@RequestBody Teacher teacher, HttpServletRequest request) {
        //判断是否重名 交由前端处理

        try {
            //逻辑删除置为 0
            teacher.setDeleted(0);
            teacher.setCollege((Integer) request.getAttribute("collegeId"));
            this.teacherService.save(teacher);
            //在role_usr表中添加用户角色的关系连接
            this.roleService.insertUserRole(teacher.getId(), 3);
            return new Result<>("添加成功");
        } catch (Exception e) {
            throw new SysException(ResultEnum.ERROR.getCode(), "操作失败,接口异常");
        }
    }
}

