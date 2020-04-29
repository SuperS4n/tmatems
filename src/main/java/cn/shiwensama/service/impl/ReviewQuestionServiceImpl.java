package cn.shiwensama.service.impl;

import cn.shiwensama.eneity.ReviewQuestion;
import cn.shiwensama.mapper.ReviewQuestionMapper;
import cn.shiwensama.service.ReviewQuestionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author supers4n
 * @since 2020-04-28
 */
@Service
public class ReviewQuestionServiceImpl extends ServiceImpl<ReviewQuestionMapper, ReviewQuestion> implements ReviewQuestionService {

    /**
     * 获得所有问题
     * @return
     */
    @Override
    public List<ReviewQuestion> getAllQuestion() {
        return this.baseMapper.selectList(null);
    }
}
