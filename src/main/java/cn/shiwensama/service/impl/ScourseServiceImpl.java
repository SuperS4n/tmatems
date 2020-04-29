package cn.shiwensama.service.impl;

import cn.shiwensama.eneity.Scourse;
import cn.shiwensama.mapper.ScourseMapper;
import cn.shiwensama.service.ScourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author supers4n
 * @since 2020-04-08
 */
@Service
public class ScourseServiceImpl extends ServiceImpl<ScourseMapper, Scourse> implements ScourseService {

    @Override
    public void isReview(Integer cid) {
        this.baseMapper.isReview(cid);
    }
}
