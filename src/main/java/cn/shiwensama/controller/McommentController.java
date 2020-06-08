package cn.shiwensama.controller;


import cn.shiwensama.eneity.Mcomment;
import cn.shiwensama.enums.ResultEnum;
import cn.shiwensama.exception.SysException;
import cn.shiwensama.service.McommentService;
import cn.shiwensama.utils.Result;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author supers4n
 * @since 2020-05-25
 */
@RestController
public class McommentController {

    @Autowired
    private McommentService mcommentService;

    /**
     * 添加
     *
     * @param mcomment
     * @return
     */
    @RequiresPermissions("mcomment:create")
    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/mcomment", method = RequestMethod.POST)
    public Result<Object> addMcomment(@RequestBody Mcomment mcomment) {
        try {
            mcomment.setDeleted(0);
            mcomment.setTime(new Date());
            mcommentService.save(mcomment);

            return new Result<>("添加成功");
        } catch (Exception e) {
            throw new SysException(ResultEnum.ERROR.getCode(), "操作失败,接口异常");
        }
    }

}

