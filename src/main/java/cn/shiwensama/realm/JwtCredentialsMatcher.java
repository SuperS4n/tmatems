package cn.shiwensama.realm;

import cn.shiwensama.eneity.Admin;
import cn.shiwensama.eneity.Student;
import cn.shiwensama.eneity.Teacher;
import cn.shiwensama.enums.StateEnum;
import cn.shiwensama.token.JwtToken;
import cn.shiwensama.utils.JwtUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: shiwensama
 * @create: 2020-04-19
 * @description:
 **/
public class JwtCredentialsMatcher implements CredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        //初始化 jwtUtils 和 jwtToken
        JwtUtils jwtUtils = new JwtUtils();
        JwtToken jwtToken = (JwtToken) token;

        if (jwtToken.getToken() == null) {
            if (jwtToken.getState().equals(StateEnum.ADMIN.getCode())) {
                //管理员
                Admin admin = (Admin) info.getPrincipals().getPrimaryPrincipal();
                String password = String.valueOf(jwtToken.getPassword());
                if (admin.getPassword().equals(password)) {
                    //密码正确 设置token
                    Map<String, Object> map = new HashMap<>(4);
                    map.put("admin", admin);
                    if (admin.getCollege() == 0) {
                        //超级管理员
                        map.put("role", 1);
                    } else {
                        //学院管理员
                        map.put("role", 4);
                    }
                    //设置token
                    String jwt = jwtUtils.createJWT(admin.getId(), admin.getUsername(), map);
                    jwtToken.setToken(jwt);
                    return true;
                }
                return false;
            }
            if (jwtToken.getState().equals(StateEnum.STUDENT.getCode())) {
                //学生
                Student student = (Student) info.getPrincipals().getPrimaryPrincipal();
                String password = String.valueOf(jwtToken.getPassword());
                if (student.getPassword().equals(password)) {
                    //密码正确 设置token
                    Map<String, Object> map = new HashMap<>(4);
                    map.put("student", student);
                    map.put("role", StateEnum.STUDENT.getCode());
                    //设置token
                    String jwt = jwtUtils.createJWT(student.getId(), student.getUsername(), map);
                    jwtToken.setToken(jwt);
                    return true;
                }
                return false;
            }
            if (jwtToken.getState().equals(StateEnum.TEACHER.getCode())) {
                //教师
                Teacher teacher = (Teacher) info.getPrincipals().getPrimaryPrincipal();
                String password = String.valueOf(jwtToken.getPassword());
                if (teacher.getPassword().equals(password)) {
                    //密码正确 设置token
                    Map<String, Object> map = new HashMap<>(4);
                    map.put("student", teacher);
                    map.put("role", StateEnum.TEACHER.getCode());
                    //设置token
                    String jwt = jwtUtils.createJWT(teacher.getId(), teacher.getUsername(), map);
                    jwtToken.setToken(jwt);
                    return true;
                }
                return false;
            }
        }
        return true;
    }
}
