package cn.shiwensama.utils;

import cn.shiwensama.eneity.Admin;
import cn.shiwensama.eneity.Student;
import cn.shiwensama.eneity.Teacher;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: shiwensama
 * @create: 2020-03-31
 * @description: 封装用户权限
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActiveUser {

    private Admin admin;

    private Student student;

    private Teacher teacher;

    /**
     * 用户的角色
     */
    private List<String> role;

    /**
     *  用户拥有的权限
     */
    private List<String> permissions;
}
