package cn.shiwensama.service.impl;

import cn.shiwensama.eneity.Student;
import cn.shiwensama.mapper.StudentMapper;
import cn.shiwensama.service.StudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author supers4n
 * @since 2020-04-02
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

}
