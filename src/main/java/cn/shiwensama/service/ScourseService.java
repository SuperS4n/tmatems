package cn.shiwensama.service;

import cn.shiwensama.eneity.Scourse;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author supers4n
 * @since 2020-04-08
 */
public interface ScourseService extends IService<Scourse> {

    /**
     * 更新 已评论
     * @param cid
     */
    void isReview(Integer cid);
}
