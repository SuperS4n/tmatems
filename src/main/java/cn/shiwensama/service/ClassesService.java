package cn.shiwensama.service;

import cn.shiwensama.eneity.Classes;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author supers4n
 * @since 2020-04-11
 */
public interface ClassesService extends IService<Classes> {

    /**
     * 拿到所有班级
     * @return
     */
    List<Classes> getAllClasses();
}
