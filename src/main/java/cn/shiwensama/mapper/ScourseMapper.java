package cn.shiwensama.mapper;

import cn.shiwensama.eneity.Scourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author supers4n
 * @since 2020-04-08
 */
public interface ScourseMapper extends BaseMapper<Scourse> {

    /**
     * 更新 已评论
     * @param cid
     */
    void isReview(Integer cid);

}
