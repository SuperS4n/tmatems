package cn.shiwensama.controller;


import cn.shiwensama.eneity.Admin;
import cn.shiwensama.eneity.College;
import cn.shiwensama.eneity.Student;
import cn.shiwensama.eneity.Teacher;
import cn.shiwensama.enums.ResultEnum;
import cn.shiwensama.exception.SysException;
import cn.shiwensama.service.AdminService;
import cn.shiwensama.service.CollegeService;
import cn.shiwensama.service.StudentService;
import cn.shiwensama.service.TeacherService;
import cn.shiwensama.utils.Result;
import cn.shiwensama.vo.CollegeVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author supers4n
 * @since 2020-04-02
 */
@RestController
public class CollegeController {

    @Autowired
    private CollegeService collegeService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private StudentService studentService;

    /**
     * 分页查询学院
     *
     * @param collegeVo
     * @param request
     * @return
     */
    @RequiresPermissions("college:view")
    @RequestMapping(value = "/college", method = RequestMethod.GET)
    public Result<Object> getAllCollege(CollegeVo collegeVo, HttpServletRequest request) {
        IPage<College> page = new Page<>(collegeVo.getPagenum(), collegeVo.getPagesize());

        QueryWrapper<College> qw = new QueryWrapper<>();
        qw.like(StringUtils.isNotBlank(collegeVo.getName()), "name", collegeVo.getName());

        this.collegeService.page(page, qw);

        Map<String, Object> resultMap = new HashMap<>(4);
        resultMap.put("total", page.getTotal());
        resultMap.put("colleges", page.getRecords());
        return new Result<>("分页查询学院成功", resultMap);
    }

    /**
     * 添加学院
     *
     * @param college
     * @return
     */
    @RequiresPermissions("college:create")
    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/college", method = RequestMethod.POST)
    public Result<Object> addCollege(@RequestBody College college) {
        //判断是否重名 交由前端处理

        try {
            //逻辑删除置为 0
            college.setDeleted(0);

            this.collegeService.save(college);

            return new Result<>("添加成功");
        } catch (Exception e) {
            throw new SysException(ResultEnum.ERROR.getCode(), "操作失败,接口异常");
        }
    }

    /**
     * 修改学院
     *
     * @param college
     * @return
     */
    @RequiresPermissions("college:update")
    @RequestMapping(value = "/college", method = RequestMethod.PUT)
    @Transactional(rollbackFor = Exception.class)
    public Result<Object> updateCollege(@RequestBody College college) {

        try {
            this.collegeService.updateById(college);
            return new Result<>("修改成功");
        } catch (Exception e) {
            throw new SysException(ResultEnum.ERROR.getCode(), "操作失败,接口异常");
        }
    }

    /**
     * 删除学院
     *
     * @param id
     * @return
     */
    @RequiresPermissions("college:delete")
    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/college/{id}", method = RequestMethod.DELETE)
    public Result<Object> deleteCollege(@PathVariable String id) {

        try {
            //删除学院
            collegeService.removeById(id);

            //删除学院下的教师
            QueryWrapper<Teacher> qw = new QueryWrapper<>();
            qw.eq("college",id);
            teacherService.remove(qw);

            //删除学院下的学生
            QueryWrapper<Student> sqw = new QueryWrapper<>();
            qw.eq("college",id);
            studentService.remove(sqw);

            //删除学院下的管理员
            QueryWrapper<Admin> aqw = new QueryWrapper<>();
            qw.eq("college",id);
            adminService.remove(aqw);

            return new Result<>("删除成功");
        } catch (Exception e) {
            throw new SysException(ResultEnum.ERROR.getCode(), "操作失败,接口异常");
        }
    }

    /**
     * 根据名称查询学院
     * @param name
     * @return
     */
    @RequiresPermissions("college:view")
    @RequestMapping(value = "/college/{name}",method = RequestMethod.GET)
    public Result<Object> getOneCollege(@PathVariable String name) {
        QueryWrapper<College> qw = new QueryWrapper<>();
        qw.eq("name",name);
        College one = this.collegeService.getOne(qw);

        if(one != null) {
            //已存在学院
            return new Result<>("查询成功",1);
        }else {
            return new Result<>("查询成功",0);
        }
    }

    /**
     * 注册时，列出所有学院
     * @return
     */
    @RequestMapping(value = "/loadAllCollege",method = RequestMethod.GET)
    public Result<Object> loadAllCollege() {
        List<College> collegeList = this.collegeService.list();

        return new Result<>("查询成功",collegeList);
    }

}

