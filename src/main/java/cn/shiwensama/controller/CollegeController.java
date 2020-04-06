package cn.shiwensama.controller;


import cn.shiwensama.eneity.College;
import cn.shiwensama.enums.ResultEnum;
import cn.shiwensama.exception.SysException;
import cn.shiwensama.service.CollegeService;
import cn.shiwensama.utils.Result;
import cn.shiwensama.vo.CollegeVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
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

    /**
     * 分页查询学院
     *
     * @param collegeVo
     * @param request
     * @return
     */
    @RequestMapping(value = "/college", method = RequestMethod.GET)
    public Result<Object> getAllCollege(CollegeVo collegeVo, HttpServletRequest request) {
        IPage<College> page = new Page<>(collegeVo.getPagenum(), collegeVo.getPagesize());

        QueryWrapper<College> qw = new QueryWrapper<>();
        qw.eq(StringUtils.isNotBlank(collegeVo.getName()), "name", collegeVo.getName());

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
    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/college/{id}", method = RequestMethod.DELETE)
    public Result<Object> deleteCollege(@PathVariable String id) {

        try {
            collegeService.removeById(id);

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
    @RequestMapping(value = "/registered/{name}",method = RequestMethod.GET)
    public Result<Object> getOneCollege(@PathVariable String name) {
        QueryWrapper<College> qw = new QueryWrapper<>();
        qw.eq("name",name);
        College one = this.collegeService.getOne(qw);

        if(one != null) {
            //已存在账号
            return new Result<>(1);
        }else {
            //账号不重复，可以注册
            return new Result<>(0);
        }
    }

}

