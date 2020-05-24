package cn.shiwensama.controller;


import cn.shiwensama.eneity.Materials;
import cn.shiwensama.enums.ResultEnum;
import cn.shiwensama.exception.SysException;
import cn.shiwensama.service.MaterialsService;
import cn.shiwensama.utils.Result;
import cn.shiwensama.utils.UploadService;
import cn.shiwensama.vo.MaterialsVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author supers4n
 * @since 2020-05-01
 */
@RestController
public class MaterialsController {

    @Autowired
    private MaterialsService materialsService;

    @Autowired
    private UploadService uploadService;

    /**
     * 文件上传
     *
     * @param file
     * @return
     */
    @RequiresPermissions("materials:create")
    @RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
    public Result<Object> fileUpload(@RequestParam("file") MultipartFile file) {

        try {
            Map<String, Object> upload = uploadService.upload(file);

            return new Result<>("文件上传成功", upload);

        } catch (Exception e) {
            throw new SysException(ResultEnum.ERROR.getCode(), "文件上传失败");
        }
    }



    /**
     * 文件删除
     *
     * @param materials
     * @return
     */
    @RequiresPermissions("materials:delete")
    @RequestMapping(value = "/fileDelete", method = RequestMethod.PUT)
    public Result<Object> fileDelete(@RequestBody Materials materials) {

        try {

            boolean b = uploadService.fdfsDelete(materials.getUrl());
            if (b) {
                materialsService.removeById(materials.getId());
                return new Result<>("删除成功");
            } else {
                return new Result<>(ResultEnum.ERROR.getCode(), "文件删除失败");
            }

        } catch (Exception e) {
            throw new SysException(ResultEnum.ERROR);
        }
    }


    /**
     * 添加教学材料
     *
     * @param materials
     * @return
     */
    @RequiresPermissions("materials:create")
    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/materials", method = RequestMethod.POST)
    public Result<Object> addTeacher(@RequestBody Materials materials) {
        //判断是否重名 交由前端处理
        try {
            //逻辑删除置为 0
            materials.setDeleted(0);
            //设置时间
            materials.setTime(new Date());

            materialsService.save(materials);

            return new Result<>("添加成功");
        } catch (Exception e) {
            throw new SysException(ResultEnum.ERROR.getCode(), "操作失败,接口异常");
        }
    }

    /**
     * 分页查询课程
     *
     * @param materialsVo
     * @return
     */
    @RequiresPermissions("materials:view")
    @RequestMapping(value = "/materials", method = RequestMethod.GET)
    public Result<Object> getAllCourse(MaterialsVo materialsVo) {
        IPage<Materials> page = new Page<>(materialsVo.getPagenum(), materialsVo.getPagesize());

        QueryWrapper<Materials> qw = new QueryWrapper<>();
        qw.eq("cid", materialsVo.getCid());

        this.materialsService.page(page, qw);


        Map<String, Object> resultMap = new HashMap<>(4);
        resultMap.put("total", page.getTotal());
        resultMap.put("materials", page.getRecords());
        return new Result<>("分页查询课程成功", resultMap);
    }

}

