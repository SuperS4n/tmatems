package cn.shiwensama.controller;


import cn.shiwensama.eneity.Course;
import cn.shiwensama.eneity.Scourse;
import cn.shiwensama.enums.ResultEnum;
import cn.shiwensama.exception.SysException;
import cn.shiwensama.service.CollegeService;
import cn.shiwensama.service.CourseService;
import cn.shiwensama.service.ScourseService;
import cn.shiwensama.service.TeacherService;
import cn.shiwensama.utils.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author supers4n
 * @since 2020-04-08
 */
@RestController
public class ScourseController {

    @Autowired
    private ScourseService scourseService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private CollegeService collegeService;


    /**
     * 查看学生已选课程
     * 
     * @return
     */
    @RequestMapping(value = "/getpicked/{uid}", method = RequestMethod.GET)
    public Result<Object> getPicked(@PathVariable String uid) {

        try {

            List<Integer> picked = courseService.getPicked(uid);
            Map<String, Object> resultMap = new HashMap<>(4);
            resultMap.put("picked",picked);
            return new Result<>("查询成功",resultMap);

        } catch (Exception e) {
            throw new SysException(ResultEnum.ERROR.getCode(), "操作失败,接口异常");
        }
    }

    /**
     * 学生分页查询学生已选课程
     *
     * @return
     */
    @RequiresPermissions("course:view")
    @RequestMapping(value = "/scourse/{uid}/{pagenum}/{pagesize}", method = RequestMethod.GET)
    public Result<Object> getAllScourse(@PathVariable String uid,
                                        @PathVariable int pagenum,
                                        @PathVariable int pagesize) {
        IPage<Scourse> page = new Page<>(pagenum, pagesize);

        QueryWrapper<Scourse> qw = new QueryWrapper<>();
        qw.eq("uid",uid);
       this.scourseService.page(page,qw);

        List<Scourse> scourses = page.getRecords();
        for (Scourse scours : scourses) {
            Course course = this.courseService.getById(scours.getCid());
            //课程名字
            scours.setName(course.getName());
            //教师名字
            scours.setTeacherName(teacherService.getById(course.getTeacher()).getName());
            //所属学院
            scours.setCollegeName(collegeService.getById(course.getCollege()).getName());
            //备注
            scours.setRemark(course.getRemark());
        }

        Map<String, Object> resultMap = new HashMap<>(4);
        resultMap.put("total", page.getTotal());
        resultMap.put("courses", page.getRecords());
        return new Result<>("分页查询课程成功", resultMap);
    }

    /**
     * 学生分页查询 开启评论的已选课程
     *
     * @return
     */
    @RequiresPermissions("course:view")
    @RequestMapping(value = "/isokscourse/{uid}/{pagenum}/{pagesize}", method = RequestMethod.GET)
    public Result<Object> getIsOkScourse(@PathVariable String uid,
                                         @PathVariable int pagenum,
                                         @PathVariable int pagesize) {
        IPage<Scourse> page = new Page<>(pagenum, pagesize);

        QueryWrapper<Scourse> qw = new QueryWrapper<>();
        qw.eq("uid",uid);
        this.scourseService.page(page,qw);

        List<Scourse> scourses = page.getRecords();
        List<Scourse> scourses1 = new ArrayList<>();
        for (Scourse scours : scourses) {
            Course course = this.courseService.getById(scours.getCid());
            if(course.getIsok() == 1) {
                //课程名字
                scours.setName(course.getName());
                //教师名字
                scours.setTeacherName(teacherService.getById(course.getTeacher()).getName());
                //所属学院
                scours.setCollegeName(collegeService.getById(course.getCollege()).getName());
                //备注
                scours.setRemark(course.getRemark());
                scourses1.add(scours);
            }
        }

        Map<String, Object> resultMap = new HashMap<>(4);
        resultMap.put("total", scourses1.size());
        resultMap.put("courses",scourses1);
        return new Result<>("分页查询课程成功", resultMap);
    }
}

