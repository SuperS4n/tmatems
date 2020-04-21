package cn.shiwensama.controller;


import cn.shiwensama.eneity.Classes;
import cn.shiwensama.eneity.College;
import cn.shiwensama.eneity.Student;
import cn.shiwensama.enums.ResultEnum;
import cn.shiwensama.enums.StateEnum;
import cn.shiwensama.exception.SysException;
import cn.shiwensama.service.ClassesService;
import cn.shiwensama.service.CollegeService;
import cn.shiwensama.service.RoleService;
import cn.shiwensama.service.StudentService;
import cn.shiwensama.token.JwtToken;
import cn.shiwensama.utils.Result;
import cn.shiwensama.vo.StudentVo;
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

import java.util.Arrays;
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
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private CollegeService collegeService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ClassesService classesService;

    /**
     * 登录方法
     *
     * @param student
     * @return
     */
    @RequestMapping(value = "/student/login", method = RequestMethod.POST)
    public Result<Object> login(@RequestBody Student student) {
        //1.先判断前端传过来的登录参数是否正确
        if (student == null || StringUtils.isBlank(student.getUsername()) || StringUtils.isBlank(student.getPassword())) {
            return new Result<>(ResultEnum.PARAMS_ERROR.getCode(), "用户名或密码错误！");
        }

        //2.启用shiro登录
        Subject subject = SecurityUtils.getSubject();

        JwtToken jwtToken = new JwtToken(student.getUsername(),student.getPassword(),StateEnum.STUDENT.getCode());
        subject.login(jwtToken);

        return new Result<>("登录成功", jwtToken.getToken());

    }


    /**
     * 分页查询学生
     *
     * @param studentVo
     * @return
     */
    @RequiresPermissions("student:view")
    @RequestMapping(value = "/student", method = RequestMethod.GET)
    public Result<Object> getAllStudent(StudentVo studentVo) {
        IPage<Student> page = new Page<>(studentVo.getPagenum(), studentVo.getPagesize());

        QueryWrapper<Student> qw = new QueryWrapper<>();
        qw.like(StringUtils.isNotBlank(studentVo.getName()), "name", studentVo.getName());
        qw.eq(null != studentVo.getCollege(),"college",studentVo.getCollege());
        qw.eq(null != studentVo.getClasses(),"classes",studentVo.getClasses());

        this.studentService.page(page, qw);

        //循环设置 学院名称、班级名称、年级
        List<Student> students = page.getRecords();
        for (Student student : students) {

            Integer studentCollege = student.getCollege();
            Integer studentClasses = student.getClasses();

            if (studentCollege != null && studentClasses != null) {
                College college = collegeService.getById(studentCollege);
                Classes classes = classesService.getById(studentClasses);
                //学院名称
                student.setCollegeName(college.getName());
                //班级名称
                student.setClassesName(classes.getName());
                //年级
                student.setLevel(classes.getLevel());
            }
        }

        Map<String, Object> resultMap = new HashMap<>(4);
        resultMap.put("total", page.getTotal());
        resultMap.put("students", students);
        return new Result<>("分页查询学生成功", resultMap);
    }

    /**
     * 添加学生，注册接口
     *
     * @param student
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/registered", method = RequestMethod.POST)
    public Result<Object> addStudent(@RequestBody Student student) {
        //判断是否重名 交由前端处理

        try {
            //逻辑删除置为 0
            student.setDeleted(0);
            this.studentService.save(student);
            //在role_usr表中添加用户角色的关系连接
            this.roleService.insertUserRole(student.getId(), 2);
            return new Result<>("注册成功");
        } catch (Exception e) {
            throw new SysException(ResultEnum.ERROR.getCode(), "操作失败,接口异常");
        }
    }


    /**
     * 修改学生
     *
     * @param student
     * @return
     */
    @RequiresPermissions("student:update")
    @RequestMapping(value = "/student", method = RequestMethod.PUT)
    @Transactional(rollbackFor = Exception.class)
    public Result<Object> updateStudent(@RequestBody Student student) {

        try {
            this.studentService.updateById(student);
            return new Result<>("修改成功");
        } catch (Exception e) {
            throw new SysException(ResultEnum.ERROR.getCode(), "操作失败,接口异常");
        }
    }

    /**
     * 重置学生密码
     *
     * @param student
     * @return
     */
    @RequiresPermissions("student:update")
    @RequestMapping(value = "/student/resetPassword", method = RequestMethod.PUT)
    @Transactional(rollbackFor = Exception.class)
    public Result<Object> resetPassword(@RequestBody Student student) {

        try {
            this.studentService.updateById(student);
            return new Result<>("重置密码成功");
        } catch (Exception e) {
            throw new SysException(ResultEnum.ERROR.getCode(), "操作失败,接口异常");
        }
    }

    /**
     * 删除学生
     *
     * @param id
     * @return
     */
    @RequiresPermissions("student:delete")
    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/student/{id}", method = RequestMethod.DELETE)
    public Result<Object> deleteStudent(@PathVariable String id) {

        try {
            studentService.removeById(id);
            this.roleService.deleteRoleUserByUid(id);
            return new Result<>("删除成功");
        } catch (Exception e) {
            throw new SysException(ResultEnum.ERROR.getCode(), "操作失败,接口异常");
        }
    }

    /**
     * 批量删除学生
     *
     * @param ids
     * @return
     */
    @RequiresPermissions("student:delete")
    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/student/multiDelete", method = RequestMethod.PUT)
    public Result<Object> batchDeleteStudent(@RequestBody String[] ids) {

        try {
            studentService.removeByIds(Arrays.asList(ids));
            for (String id : ids) {
                roleService.deleteRoleUserByUid(id);
            }
            return new Result<>("删除成功");
        } catch (Exception e) {
            throw new SysException(ResultEnum.ERROR.getCode(), "操作失败,接口异常");
        }
    }

    /**
     * 根据账号查询学生
     *
     * @param username
     * @return
     */
    @RequestMapping(value = "/registered/{username}", method = RequestMethod.GET)
    public Result<Object> getOneStudentByUsername(@PathVariable String username) {
        QueryWrapper<Student> qw = new QueryWrapper<>();
        qw.eq("username", username);
        Student one = this.studentService.getOne(qw);

        if (one != null) {
            //已存在账号
            return new Result<>(1);
        } else {
            //账号不重复，可以注册
            return new Result<>(0);
        }
    }

    /**
     * 根据id查询学生
     *
     * @param id
     * @return
     */
    @RequiresPermissions("student:view")
    @RequestMapping(value = "/student/{id}", method = RequestMethod.GET)
    public Result<Object> getOneStudentById(@PathVariable String id) {
        QueryWrapper<Student> qw = new QueryWrapper<>();
        qw.eq("id", id);
        Student one = this.studentService.getOne(qw);

        Map<String, Object> resultMap = new HashMap<>(4);
        resultMap.put("student", one);

        return new Result<>("根据id查询学生成功", resultMap);
    }
}

