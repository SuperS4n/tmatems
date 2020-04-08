package cn.shiwensama.controller;


import cn.shiwensama.eneity.Course;
import cn.shiwensama.eneity.Scourse;
import cn.shiwensama.enums.ResultEnum;
import cn.shiwensama.exception.SysException;
import cn.shiwensama.service.CourseService;
import cn.shiwensama.service.ScourseService;
import cn.shiwensama.service.TeacherService;
import cn.shiwensama.utils.Result;
import cn.shiwensama.vo.ScourseVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

    /**
     * 学生选课
     * @param id
     * @param cid
     * @return
     */
    @RequestMapping(value = "/scourse/{id}/{cid}", method = RequestMethod.PUT)
    public Result<Object> addScourse(@PathVariable String id , int cid) {

        try {
            this.courseService.addScourse(cid,id);
            return new Result<>("选课成功");
        } catch (Exception e) {
            throw new SysException(ResultEnum.ERROR.getCode(), "操作失败,接口异常");
        }
    }

    /**
     * 学生分页查询课程
     *
     * @param scourseVo
     * @return
     */
    @RequiresPermissions("course:view")
    @RequestMapping(value = "/scourse/{uid}", method = RequestMethod.GET)
    public Result<Object> getAllScourse(ScourseVo scourseVo,@PathVariable String uid) {
        IPage<Scourse> page = new Page<>(scourseVo.getPagenum(), scourseVo.getPagesize());

        QueryWrapper<Scourse> qw = new QueryWrapper<>();
        qw.eq("uid",uid);
       this.scourseService.page(page,qw);

        List<Scourse> scourses = page.getRecords();
        for (Scourse scours : scourses) {
            Course course = this.courseService.getById(scours.getCid());
            scours.setName(course.getName());
            scours.setTeacherName(teacherService.getById(course.getTeacher()).getName());
            scours.setRemark(course.getRemark());
        }

        Map<String, Object> resultMap = new HashMap<>(4);
        resultMap.put("total", page.getTotal());
        resultMap.put("courses", page.getRecords());
        return new Result<>("分页查询课程成功", resultMap);
    }
}

