package cn.shiwensama.controller;


import cn.shiwensama.eneity.College;
import cn.shiwensama.eneity.Student;
import cn.shiwensama.enums.ResultEnum;
import cn.shiwensama.enums.StateEnum;
import cn.shiwensama.service.CollegeService;
import cn.shiwensama.service.StudentService;
import cn.shiwensama.token.UsernamePasswordToken;
import cn.shiwensama.utils.JwtUtils;
import cn.shiwensama.utils.Result;
import cn.shiwensama.vo.StudentVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    private JwtUtils jwtUtils;

    @Autowired
    private StudentService studentService;

    @Autowired
    private CollegeService collegeService;

    /**
     * 登录方法
     *
     * @param student
     * @return
     */
    @RequestMapping(value = "/student/login", method = RequestMethod.POST)
    private Result<Object> login(@RequestBody Student student) {
        //1.先判断前端传过来的登录参数是否正确
        if (student == null || StringUtils.isBlank(student.getUsername()) || StringUtils.isBlank(student.getPassword())) {
            return new Result<>(ResultEnum.PARAMS_ERROR.getCode(), "用户名或密码错误！");
        }

        //2.启用shiro登录
        Subject subject = SecurityUtils.getSubject();
        AuthenticationToken authenticationToken = new UsernamePasswordToken(student.getUsername(),
                student.getPassword(), StateEnum.STUDENT.getCode());
        try {
            subject.login(authenticationToken);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<>(ResultEnum.PARAMS_ERROR.getCode(), "用户名或密码错误！");
        }
        //3.登录成功,设置token
        String jwt = jwtUtils.createJWT(student.getId(), student.getUsername());

        return new Result<>("登录成功", jwt);
    }


    @RequestMapping(value = "/student",method = RequestMethod.GET)
    private Result<Object> getAllStudent (StudentVo studentVo) {
        IPage<Student> page = new Page<>(studentVo.getPagenum(),studentVo.getPagesize());

        QueryWrapper<Student> qw = new QueryWrapper<>();
        qw.eq(StringUtils.isNotBlank(studentVo.getName()),"name",studentVo.getName());
        qw.eq(StringUtils.isNotBlank(studentVo.getClasses()),"classes",studentVo.getClasses());

        this.studentService.page(page, qw);
        
        //循环设置学院名称
        List<Student> students = page.getRecords();
        for (Student student : students) {
            Integer collegeId = student.getCollege();
            if(collegeId!=null) {
                College college = collegeService.getById(collegeId);
                student.setCollegeName(college.getName());
            }
        }

        Map<String,Object> resultMap = new HashMap<>(4);
        resultMap.put("total",page.getTotal());
        resultMap.put("data",students);
        return new Result<>("分页查询学生成功",resultMap);
    }
}

