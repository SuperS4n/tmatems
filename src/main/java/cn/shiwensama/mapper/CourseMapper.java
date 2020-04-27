package cn.shiwensama.mapper;

import cn.shiwensama.eneity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author supers4n
 * @since 2020-04-07
 */
public interface CourseMapper extends BaseMapper<Course> {

    /**
     * 根据课程ID 删除scourse表中的数据
     * @param cid
     */
    void deleteStudentCourse(int cid);

    /**
     * 根据课程ID 删除tcourse表中的数据
     * @param cid
     */
    void deleteTeacherCourse(int cid);

    /**
     * 开启评论
     * @param cid
     */
    void openComment(int cid);

    /**
     * 添加教师课表
     * @param cid 课程编号
     * @param uid 教师ID
     */
    void addTcourse(int cid,String uid);

    /**
     * 添加学生课表
     * @param cid 课程编号
     * @param uid 教师ID
     */
    void addScourse(int cid,String uid);

    /**
     * 根据学生ID 查出已选课程的集合
     * @param uid
     * @return
     */
    List<Integer> getPicked(String uid);
}
