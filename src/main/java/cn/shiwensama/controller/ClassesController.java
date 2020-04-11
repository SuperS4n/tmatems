package cn.shiwensama.controller;


import cn.shiwensama.eneity.Classes;
import cn.shiwensama.eneity.Student;
import cn.shiwensama.enums.ResultEnum;
import cn.shiwensama.exception.SysException;
import cn.shiwensama.service.ClassesService;
import cn.shiwensama.service.StudentService;
import cn.shiwensama.utils.Result;
import cn.shiwensama.vo.ClassesVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author supers4n
 * @since 2020-04-11
 */
@RestController
public class ClassesController {
    @Autowired
    private ClassesService classesService;

    @Autowired
    private StudentService studentService;

    /**
     * 分页查询班级
     *
     * @param classesVo
     * @return
     */
    @RequiresPermissions("classes:view")
    @RequestMapping(value = "/classes", method = RequestMethod.GET)
    public Result<Object> getAllClasses(ClassesVo classesVo) {
        IPage<Classes> page = new Page<>(classesVo.getPagenum(), classesVo.getPagesize());

        QueryWrapper<Classes> qw = new QueryWrapper<>();
        qw.like(StringUtils.isNotBlank(classesVo.getName()), "name", classesVo.getName());

        this.classesService.page(page, qw);

        Map<String, Object> resultMap = new HashMap<>(4);
        resultMap.put("total", page.getTotal());
        resultMap.put("classess", page.getRecords());
        return new Result<>("分页查询班级成功", resultMap);
    }

    /**
     * 添加班级
     *
     * @param classes
     * @return
     */
    @RequiresPermissions("classes:create")
    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/classes", method = RequestMethod.POST)
    public Result<Object> addClasses(@RequestBody Classes classes) {
        //判断是否重名 交由前端处理

        try {
            //逻辑删除置为 0
            classes.setDeleted(0);

            this.classesService.save(classes);

            return new Result<>("添加成功");
        } catch (Exception e) {
            throw new SysException(ResultEnum.ERROR.getCode(), "操作失败,接口异常");
        }
    }

    /**
     * 修改班级
     *
     * @param classes
     * @return
     */
    @RequiresPermissions("classes:update")
    @RequestMapping(value = "/classes", method = RequestMethod.PUT)
    @Transactional(rollbackFor = Exception.class)
    public Result<Object> updateClasses(@RequestBody Classes classes) {

        try {
            this.classesService.updateById(classes);
            return new Result<>("修改成功");
        } catch (Exception e) {
            throw new SysException(ResultEnum.ERROR.getCode(), "操作失败,接口异常");
        }
    }

    /**
     * 删除班级
     *
     * @param id
     * @return
     */
    @RequiresPermissions("classes:delete")
    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/classes/{id}", method = RequestMethod.DELETE)
    public Result<Object> deleteClasses(@PathVariable String id) {

        try {
            //删除班级
            classesService.removeById(id);

            //删除班级下的学生
            QueryWrapper<Student> qw = new QueryWrapper<>();
            qw.eq("classes", id);
            studentService.remove(qw);

            return new Result<>("删除成功");
        } catch (Exception e) {
            throw new SysException(ResultEnum.ERROR.getCode(), "操作失败,接口异常");
        }
    }

    /**
     * 根据名称查询班级
     *
     * @param name
     * @return
     */
    @RequiresPermissions("classes:view")
    @RequestMapping(value = "/classes/{name}", method = RequestMethod.GET)
    public Result<Object> getOneClasses(@PathVariable String name) {
        QueryWrapper<Classes> qw = new QueryWrapper<>();
        qw.eq("name", name);
        Classes one = this.classesService.getOne(qw);

        if (one != null) {
            //已存在班级
            return new Result<>("查询成功", 1);
        } else {
            return new Result<>("查询成功", 0);
        }
    }

}

