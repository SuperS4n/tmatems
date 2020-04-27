package cn.shiwensama.service.impl;

import cn.shiwensama.eneity.Course;
import cn.shiwensama.mapper.CourseMapper;
import cn.shiwensama.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author supers4n
 * @since 2020-04-07
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    /**
     * 根据课程ID 删除scourse表中的数据
     * @param cid
     */
    @Override
    public void deleteStudentCourse(int cid) {
        this.getBaseMapper().deleteStudentCourse(cid);
    }

    /**
     * 根据课程ID 删除tcourse表中的数据
     * @param cid
     */
    @Override
    public void deleteTeacherCourse(int cid) {
        this.getBaseMapper().deleteTeacherCourse(cid);
    }

    /**
     * 开启评论
     * @param cid
     */
    @Override
    public void openComment(int cid) {
        this.getBaseMapper().openComment(cid);
    }

    /**
     * 添加教师课表
     * @param cid 课程编号
     * @param uid 教师ID
     */
    @Override
    public void addTcourse(int cid, String uid) {
        this.getBaseMapper().addTcourse(cid,uid);
    }

    /**
     * 添加学生课表
     * @param cid 课程编号
     * @param uid 学生ID
     */
    @Override
    public void addScourse(int cid, String uid) {
        this.getBaseMapper().addScourse(cid,uid);
    }

    /**
     * 根据学生ID 查出已选课程ID
     * @param uid
     * @return
     */
    @Override
    public List<Integer> getPicked(String uid) {
        return this.getBaseMapper().getPicked(uid);
    }
}
