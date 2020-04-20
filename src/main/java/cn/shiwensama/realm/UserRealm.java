package cn.shiwensama.realm;

import cn.shiwensama.eneity.Admin;
import cn.shiwensama.eneity.Student;
import cn.shiwensama.eneity.Teacher;
import cn.shiwensama.enums.ResultEnum;
import cn.shiwensama.enums.StateEnum;
import cn.shiwensama.exception.SysException;
import cn.shiwensama.service.*;
import cn.shiwensama.token.JwtToken;
import cn.shiwensama.utils.GetPerCodes;
import cn.shiwensama.utils.JwtUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.jsonwebtoken.Claims;
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
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    public UserRealm() {
        this.setCredentialsMatcher(new JwtCredentialsMatcher());
    }

    /**
     * 做授权
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        Object principal = principals.getPrimaryPrincipal();
        //System.out.println(principal);
        GetPerCodes getPerCodes = new GetPerCodes();

        if (principal != null) {
            if (principal instanceof Admin) {
                List<String> perCodes = getPerCodes.doGetPerCodes(((Admin) principal).getId(), roleService, permissionService);
                if (((Admin) principal).getCollege() == 0) {
                    //超级管理员拥有全部权限
                    authorizationInfo.addStringPermission("*:*");
                } else {
                    if (null != perCodes && perCodes.size() > 0) {
                        authorizationInfo.addStringPermissions(perCodes);
                    }
                }
            }
            if (principal instanceof Student) {
                List<String> perCodes = getPerCodes.doGetPerCodes(((Student) principal).getId(), roleService, permissionService);
                if (null != perCodes && perCodes.size() > 0) {
                    authorizationInfo.addStringPermissions(perCodes);
                }
            }
            if (principal instanceof Teacher) {
                List<String> perCodes = getPerCodes.doGetPerCodes(((Teacher) principal).getId(), roleService, permissionService);
                if (null != perCodes && perCodes.size() > 0) {
                    authorizationInfo.addStringPermissions(perCodes);
                }
            }

            return authorizationInfo;

        } else {
            throw new SysException(ResultEnum.NOT_LOGIN.getCode(),"授权失败");
        }

    }

    /**
     * 做认证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        JwtToken jwtToken = (JwtToken) authenticationToken;
        JwtUtils jwtUtils = new JwtUtils();

        if (jwtToken.getToken() != null) {
            String token = jwtToken.getToken().substring(7);

            Claims claims = jwtUtils.parseJWT(token);
            String id = claims.getId();

            if (claims.get("role") == StateEnum.ADMIN.getCode()) {
                Admin admin = adminService.getById(id);
                return new SimpleAuthenticationInfo(admin, jwtToken, this.getName());
            }
            if (claims.get("role") == StateEnum.COLLEGEADMIN.getCode()) {
                Admin admin = adminService.getById(id);
                return new SimpleAuthenticationInfo(admin, jwtToken, this.getName());
            }
            if (claims.get("role") == StateEnum.STUDENT.getCode()) {
                Student student = studentService.getById(id);
                return new SimpleAuthenticationInfo(student, jwtToken, this.getName());
            }

            if (claims.get("role") == StateEnum.TEACHER.getCode()) {
                Teacher teacher = teacherService.getById(id);
                return new SimpleAuthenticationInfo(teacher, jwtToken, this.getName());
            }

        } else {
            //没有token,正常登录
            //拿到账号且判断登录账号类型。
            String username = jwtToken.getUsername();
            String password = String.valueOf(jwtToken.getPassword());
            int state = jwtToken.getState();

            //管理员登录
            if (state == StateEnum.ADMIN.getCode()) {
                QueryWrapper<Admin> adminQueryWrapper = new QueryWrapper<>();
                adminQueryWrapper.eq("username", username);
                Admin admin = adminService.getOne(adminQueryWrapper);
                if (!admin.getPassword().equals(password)) {
                    throw new AuthenticationException();
                }

                return new SimpleAuthenticationInfo(admin, jwtToken, this.getName());
            }
            //学生登录
            if (state == StateEnum.STUDENT.getCode()) {
                QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<>();
                studentQueryWrapper.eq("username", username);
                Student student = studentService.getOne(studentQueryWrapper);
                if (!student.getPassword().equals(password)) {
                    throw new AuthenticationException();
                }

                return new SimpleAuthenticationInfo(student, jwtToken, this.getName());
            }
            //教师登录
            if (state == StateEnum.TEACHER.getCode()) {
                QueryWrapper<Teacher> teacherQueryWrapper = new QueryWrapper<>();
                teacherQueryWrapper.eq("username", username);
                Teacher teacher = teacherService.getOne(teacherQueryWrapper);
                if (!teacher.getPassword().equals(password)) {
                    throw new AuthenticationException();
                }

                return new SimpleAuthenticationInfo(teacher, jwtToken, this.getName());
            }
        }

        return null;
    }
}
