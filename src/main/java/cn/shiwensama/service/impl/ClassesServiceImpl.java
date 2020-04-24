package cn.shiwensama.service.impl;

import cn.shiwensama.eneity.Classes;
import cn.shiwensama.mapper.ClassesMapper;
import cn.shiwensama.service.ClassesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author supers4n
 * @since 2020-04-11
 */
@Service
public class ClassesServiceImpl extends ServiceImpl<ClassesMapper, Classes> implements ClassesService {

    @Override
    public List<Classes> getAllClasses() {
        return this.getBaseMapper().selectList(null);
    }
}
