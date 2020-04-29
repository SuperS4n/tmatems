package cn.shiwensama.controller;


import cn.shiwensama.eneity.College;
import cn.shiwensama.eneity.Course;
import cn.shiwensama.eneity.Scourse;
import cn.shiwensama.eneity.Teacher;
import cn.shiwensama.enums.ResultEnum;
import cn.shiwensama.exception.SysException;
import cn.shiwensama.service.CollegeService;
import cn.shiwensama.service.CourseService;
import cn.shiwensama.service.TeacherService;
import cn.shiwensama.utils.Result;
import cn.shiwensama.vo.CourseVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author supers4n
 * @since 2020-04-07
 */
@RestController
public class CourseController {
    
    @Autowired
    private CourseService courseService;

    @Autowired
    private CollegeService collegeService;

    @Autowired
    private TeacherService teacherService;

    /**
     * 分页查询课程
     *
     * @param courseVo
     * @return
     */
    @RequiresPermissions("course:view")
    @RequestMapping(value = "/course", method = RequestMethod.GET)
    public Result<Object> getAllCourse(CourseVo courseVo) {
        IPage<Course> page = new Page<>(courseVo.getPagenum(), courseVo.getPagesize());

        QueryWrapper<Course> qw = new QueryWrapper<>();
        qw.like(StringUtils.isNotBlank(courseVo.getName()), "name", courseVo.getName());
        qw.eq(courseVo.getLevel() != null,"level",courseVo.getLevel());
        qw.eq(courseVo.getCollege() != null,"college",courseVo.getCollege());


        this.courseService.page(page, qw);

        //循环设置学院名称和教师名字
        List<Course> courses = page.getRecords();
        for (Course course : courses) {
            Integer courseCollege = course.getCollege();
            String teacherId = course.getTeacher();
            if (courseCollege != null && teacherId != null) {
                College college = collegeService.getById(courseCollege);
                Teacher teacher = teacherService.getById(teacherId);

                course.setCollegeName(college.getName());
                course.setTeacherName(teacher.getName());
            }
        }

        Map<String, Object> resultMap = new HashMap<>(4);
        resultMap.put("total", page.getTotal());
        resultMap.put("courses", page.getRecords());
        return new Result<>("分页查询课程成功", resultMap);
    }

    /**
     * 添加课程
     *
     * @param course
     * @return
     */
    @RequiresPermissions("course:create")
    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/course", method = RequestMethod.POST)
    public Result<Object> addCourse(@RequestBody Course course) {
        //判断是否重名 交由前端处理

        try {
            //逻辑删除置为 0
            course.setDeleted(0);

            course.setIsok(0);

            this.courseService.save(course);

            //往教师课表插入课程ID
            this.courseService.addTcourse(course.getId(),course.getTeacher());

            return new Result<>("添加成功");
        } catch (Exception e) {
            throw new SysException(ResultEnum.ERROR.getCode(), "操作失败,接口异常");
        }
    }

    /**
     * 修改课程
     *
     * @param course
     * @return
     */
    @RequiresPermissions("course:update")
    @RequestMapping(value = "/course", method = RequestMethod.PUT)
    @Transactional(rollbackFor = Exception.class)
    public Result<Object> updateCourse(@RequestBody Course course) {

        try {
            this.courseService.updateById(course);
            return new Result<>("修改成功");
        } catch (Exception e) {
            throw new SysException(ResultEnum.ERROR.getCode(), "操作失败,接口异常");
        }
    }

    /**
     * 根据ID删除课程
     *
     * @param id
     * @return
     */
    @RequiresPermissions("course:delete")
    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/course/{id}", method = RequestMethod.DELETE)
    public Result<Object> deleteCourse(@PathVariable int id) {

        try {
            //删除课程
            courseService.removeById(id);

            //删除学生课表中的课程
            courseService.deleteStudentCourse(id);

            //删除教师课表中的课程
            courseService.deleteTeacherCourse(id);

            return new Result<>("删除成功");
        } catch (Exception e) {
            throw new SysException(ResultEnum.ERROR.getCode(), "操作失败,接口异常");
        }
    }

    /**
     * 批量删除课程
     *
     * @param ids
     * @return
     */
    @RequiresPermissions("course:delete")
    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/course/multiDelete", method = RequestMethod.PUT)
    public Result<Object> batchDeleteStudent(@RequestBody int[] ids) {

        try {

            for (int id : ids) {
                courseService.removeById(id);

                //删除学生课表中的课程
                courseService.deleteStudentCourse(id);

                //删除教师课表中的课程
                courseService.deleteTeacherCourse(id);
            }

            return new Result<>("删除成功");
        } catch (Exception e) {
            throw new SysException(ResultEnum.ERROR.getCode(), "操作失败,接口异常");
        }
    }

    /**
     * 根据名称查询课程
     * @param name
     * @return
     */
    @RequiresPermissions("course:view")
    @RequestMapping(value = "/course/{name}",method = RequestMethod.GET)
    public Result<Object> getOneCourse(@PathVariable String name) {
        QueryWrapper<Course> qw = new QueryWrapper<>();
        qw.eq("name",name);
        Course one = this.courseService.getOne(qw);

        if(one != null) {
            //已存在课程
            return new Result<>("查询成功",1);
        }else {
            return new Result<>("查询成功",0);
        }
    }

    /**
     * 课程开启或关闭评论
     *
     * @param id
     * @return
     */
    @RequiresPermissions("course:update")
    @RequestMapping(value = "/course/{id}", method = RequestMethod.PUT)
    @Transactional(rollbackFor = Exception.class)
    public Result<Object> openOrCloseComment(@PathVariable int id) {
        Course course = courseService.getById(id);

        try {
            if(course.getIsok() == 1) {
                //关闭评论
                courseService.closeComment(id);
                return new Result<>(course.getName()+"教评关闭");
            }else {
                courseService.openComment(id);
                return new Result<>(course.getName()+"教评开启");
            }

        } catch (Exception e) {
            throw new SysException(ResultEnum.ERROR.getCode(), "操作失败,接口异常");
        }
    }

    /**
     * 学生选课
     *
     * @return
     */
    @RequestMapping(value = "/pickcourse", method = RequestMethod.POST)
    @Transactional(rollbackFor = Exception.class)
    public Result<Object> pickCourse(@RequestBody Scourse scourse) {

        try {

            courseService.addScourse(scourse.getCid(),scourse.getUid());

        } catch (Exception e) {
            e.printStackTrace();
            throw new SysException(ResultEnum.ERROR.getCode(),"选课失败");
        }

        return new Result<>("选课成功");
    }

}

