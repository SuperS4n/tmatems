package cn.shiwensama.controller;


import cn.shiwensama.eneity.Ccomment;
import cn.shiwensama.eneity.ReviewQuestion;
import cn.shiwensama.enums.ResultEnum;
import cn.shiwensama.exception.SysException;
import cn.shiwensama.service.CcommentService;
import cn.shiwensama.service.CourseService;
import cn.shiwensama.service.ReviewQuestionService;
import cn.shiwensama.service.ScourseService;
import cn.shiwensama.utils.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
 * @since 2020-04-28
 */
@RestController
public class CcommentController {

    @Autowired
    private CcommentService ccommentService;

    @Autowired
    private ReviewQuestionService reviewQuestionService;

    @Autowired
    private ScourseService scourseService;

    @Autowired
    private CourseService courseService;

    /**
     * 查出评论问题
     * @return
     */
    @RequestMapping(value = "/getquestion", method = RequestMethod.GET)
    public Result<Object> getPicked() {

        try {
            List<ReviewQuestion> questions = reviewQuestionService.getAllQuestion();

            Map<String, Object> resultMap = new HashMap<>(4);
            resultMap.put("questions",questions);

            return new Result<>("查询成功",resultMap);

        } catch (Exception e) {
            throw new SysException(ResultEnum.ERROR.getCode(), "操作失败,接口异常");
        }
    }

    /**
     * 添加教评
     *
     * @param ccomment
     * @return
     */
    @RequiresPermissions("ccomment:create")
    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/ccomment", method = RequestMethod.POST)
    public Result<Object> addCcomment(@RequestBody Ccomment ccomment) {
        try {
            //逻辑删除置为 0
            ccomment.setDeleted(0);
            ccomment.setTime(new Date());
            ccommentService.save(ccomment);
            //更新 数据库中 已评论
            scourseService.isReview(ccomment.getCid());

            return new Result<>("添加成功");
        } catch (Exception e) {
            throw new SysException(ResultEnum.ERROR.getCode(), "操作失败,接口异常");
        }
    }

    /**
     * 查询教评
     *
     * @param cid
     * @return
     */
    @RequiresPermissions("ccomment:view")
    @RequestMapping(value = "/ccomment/{cid}", method = RequestMethod.GET)
    public Result<Object> getAllCourse(@PathVariable Integer cid) {
        QueryWrapper<Ccomment> qw = new QueryWrapper<>();
        qw.eq("cid",cid);

        List<Ccomment> ccommentList = ccommentService.list(qw);
        List<Integer> question1List = new ArrayList<>();
        List<Integer> question2List = new ArrayList<>();
        List<Integer> question3List = new ArrayList<>();
        List<Integer> question4List = new ArrayList<>();
        List<Integer> question5List = new ArrayList<>();

        for (Ccomment ccomment : ccommentList) {
            question1List.add(ccomment.getQuestion1());
            question2List.add(ccomment.getQuestion2());
            question3List.add(ccomment.getQuestion3());
            question4List.add(ccomment.getQuestion4());
            question5List.add(ccomment.getQuestion5());
        }


        Map<String, Object> resultMap = new HashMap<>(10);
        resultMap.put("number",ccommentList.size());
        resultMap.put("question1List",question1List);
        resultMap.put("question2List",question2List);
        resultMap.put("question3List",question3List);
        resultMap.put("question4List",question4List);
        resultMap.put("question5List",question5List);

        return new Result<>("分页查询课程成功", resultMap);
    }


}

