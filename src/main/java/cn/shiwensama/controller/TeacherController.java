package cn.shiwensama.controller;


import cn.shiwensama.eneity.Teacher;
import cn.shiwensama.enums.ResultEnum;
import cn.shiwensama.enums.StateEnum;
import cn.shiwensama.token.UsernamePasswordToken;
import cn.shiwensama.utils.JwtUtils;
import cn.shiwensama.utils.Result;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author supers4n
 * @since 2020-04-02
 */
@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private JwtUtils jwtUtils;


    /**
     * 登录方法
     *
     * @param teacher
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
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
            e.printStackTrace();
            return new Result<>(ResultEnum.PARAMS_ERROR.getCode(), "用户名或密码错误！");
        }
        //3.登录成功,设置token
        String jwt = jwtUtils.createJWT(teacher.getId(), teacher.getUsername());

        return new Result<>("登录成功", jwt);
    }
}

