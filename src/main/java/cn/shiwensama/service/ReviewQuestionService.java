package cn.shiwensama.service;

import cn.shiwensama.eneity.ReviewQuestion;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author supers4n
 * @since 2020-04-28
 */
public interface ReviewQuestionService extends IService<ReviewQuestion> {

    /**
     * 获得所有问题
     * @return
     */
    List<ReviewQuestion> getAllQuestion();

}
