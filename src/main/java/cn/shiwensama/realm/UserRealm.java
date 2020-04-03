package cn.shiwensama.realm;

import cn.shiwensama.eneity.Admin;
import cn.shiwensama.eneity.Student;
import cn.shiwensama.eneity.Teacher;
import cn.shiwensama.enums.ResultEnum;
import cn.shiwensama.enums.StateEnum;
import cn.shiwensama.exception.SysException;
import cn.shiwensama.service.AdminService;
import cn.shiwensama.service.StudentService;
import cn.shiwensama.service.TeacherService;
import cn.shiwensama.token.UsernamePasswordToken;
import cn.shiwensama.utils.ActiveUser;
import cn.shiwensama.utils.GetPerCodes;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author: shiwensama
 * @create: 2020-04-02
 * @description: 自定义realm域
 **/
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private AdminService adminService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private GetPerCodes getPerCodes;

    /**
     * 做授权
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        ActiveUser activeUser = (ActiveUser) principals.getPrimaryPrincipal();
        List<String> permissions = activeUser.getPermissions();
        //1.管理员授权
        if (activeUser.getAdmin() != null) {
            if (activeUser.getAdmin().getCollege() == 0) {
                //超级管理员拥有全部权限
                authorizationInfo.addStringPermission("*:*");
            } else {
                if (null != permissions && permissions.size() > 0) {
                    authorizationInfo.addStringPermissions(permissions);
                }
            }
        }

        //2.学生授权
        if (activeUser.getStudent() != null) {
            if (null != permissions && permissions.size() > 0) {
                authorizationInfo.addStringPermissions(permissions);
            }
        }

        //3.教师授权
        if (activeUser.getTeacher() != null) {
            if (null != permissions && permissions.size() > 0) {
                authorizationInfo.addStringPermissions(permissions);
            }
        }
        return authorizationInfo;
    }

    /**
     * 做认证
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //1.把AuthenticationToken 强转为 usernamePasswordToken
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;

        //2.拿到账号且判断登录账号类型。
        String username = usernamePasswordToken.getUsername();
        int state = usernamePasswordToken.getState();

        //管理员
        if (state == StateEnum.ADMIN.getCode()) {
            QueryWrapper<Admin> adminQueryWrapper = new QueryWrapper<>();
            adminQueryWrapper.eq("username", username);
            Admin admin = adminService.getOne(adminQueryWrapper);

            if (admin == null) {
                //用户不存在
                throw new SysException(ResultEnum.ERROR.getCode(), "用户不存在！");
            }
            //获取授权码
            ActiveUser activeUser = getPerCodes.doGetPerCodes(admin, null, null, admin.getId());
            System.out.println(activeUser);
            System.out.println(activeUser.getPermissions().size());
            return new SimpleAuthenticationInfo(activeUser, admin.getPassword(), this.getName());
        }

        //学生
        if (state == StateEnum.STUDENT.getCode()) {
            QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<>();
            studentQueryWrapper.eq("username", username);
            Student student = studentService.getOne(studentQueryWrapper);
            if (student == null) {
                //用户不存在
                throw new SysException(ResultEnum.ERROR.getCode(), "用户不存在！");
            }
            //获取授权码
            ActiveUser activeUser = getPerCodes.doGetPerCodes(null, student, null, student.getId());
            System.out.println(activeUser);
            System.out.println(activeUser.getPermissions().size());
            return new SimpleAuthenticationInfo(activeUser, student.getPassword(), this.getName());
        }

        //教师
        if (state == StateEnum.TEACHER.getCode()) {
            QueryWrapper<Teacher> teacherQueryWrapper = new QueryWrapper<>();
            teacherQueryWrapper.eq("username", username);
            Teacher teacher = teacherService.getOne(teacherQueryWrapper);
            if (teacher == null) {
                //用户不存在
                throw new SysException(ResultEnum.ERROR.getCode(), "用户不存在！");
            }
            //获取授权码
            ActiveUser activeUser = getPerCodes.doGetPerCodes(null, null, teacher, teacher.getId());
            System.out.println(activeUser);
            System.out.println(activeUser.getPermissions().size());
            return new SimpleAuthenticationInfo(activeUser, teacher.getPassword(), this.getName());
        }

        return null;
    }
}
