package cn.shiwensama.controller;


import cn.shiwensama.eneity.Course;
import cn.shiwensama.eneity.Tcourse;
import cn.shiwensama.service.CourseService;
import cn.shiwensama.service.TcourseService;
import cn.shiwensama.utils.Result;
import cn.shiwensama.vo.TcourseVo;
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
public class TcourseController {

    @Autowired
    private TcourseService tcourseService;

    @Autowired
    private CourseService courseService;

    /**
     * 教师分页查询课程
     *
     * @param tcourseVo
     * @return
     */
    @RequiresPermissions("course:view")
    @RequestMapping(value = "/tcourse/{uid}", method = RequestMethod.GET)
    public Result<Object> getAllScourse(TcourseVo tcourseVo, @PathVariable String uid) {
        IPage<Tcourse> page = new Page<>(tcourseVo.getPagenum(), tcourseVo.getPagesize());

        QueryWrapper<Tcourse> qw = new QueryWrapper<>();
        qw.eq("uid",uid);
        this.tcourseService.page(page,qw);

        List<Tcourse> tcourses = page.getRecords();
        for (Tcourse tcours : tcourses) {
            Course course = this.courseService.getById(tcours.getCid());
            tcours.setName(course.getName());
        }

        Map<String, Object> resultMap = new HashMap<>(4);
        resultMap.put("total", page.getTotal());
        resultMap.put("courses", page.getRecords());
        return new Result<>("分页查询课程成功", resultMap);
    }

}

