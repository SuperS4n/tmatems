package cn.shiwensama.utils;

import cn.shiwensama.eneity.Admin;
import cn.shiwensama.eneity.Permission;
import cn.shiwensama.eneity.Student;
import cn.shiwensama.eneity.Teacher;
import cn.shiwensama.service.PermissionService;
import cn.shiwensama.service.RoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author: shiwensama
 * @create: 2020-04-02
 * @description: 获取数据库中的授权码
 *              用户ID --> 角色ID --> 权限ID --> 查询percode
 **/
public class GetPerCodes {

    @Autowired
    @Lazy
    private RoleService roleService;

    @Autowired
    @Lazy
    private PermissionService permissionService;

    public ActiveUser doGetPerCodes(Admin admin, Student student, Teacher teacher, String userId) {
        ActiveUser activeUser = new ActiveUser();
        activeUser.setAdmin(admin);
        activeUser.setStudent(student);
        activeUser.setTeacher(teacher);

        QueryWrapper<Permission> qw = new QueryWrapper<>();
        qw.eq("type", "permission");
        qw.eq("available",1);

        //1.获取角色ID
        List<Integer> currentUserRoleIds = roleService.queryUserRoleIdsByUid(userId);

        //获取权限ID
        Set<Integer> pids = new HashSet<>();
        for (Integer rid : currentUserRoleIds) {
            List<Integer> permissionIds = roleService.queryRolePermissionIdsByRid(rid);
            pids.addAll(permissionIds);
        }

        //获取权限列表
        List<Permission> list = new ArrayList<>();
        if(pids.size()>0) {
            qw.in("id",pids);
            list = permissionService.list(qw);
        }
        //获取授权码
        List<String> percodes = new ArrayList<>();
        for (Permission permission : list) {
            percodes.add(permission.getPercode());
        }
        //把权限码放到ActiveUser中
        activeUser.setPermissions(percodes);

        return activeUser;
    }
}
