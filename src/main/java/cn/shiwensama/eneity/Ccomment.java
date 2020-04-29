package cn.shiwensama.eneity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author supers4n
 * @since 2020-04-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Ccomment implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * ID
     */
    private String id;

    /**
     * 评论课程ID
     */
    private Integer cid;

    /**
     * 评论时间
     */
    private Date time;

    /**
     * 问题1打分
     */
    private Integer question1;

    /**
     * 问题2打分
     */
    private Integer question2;

    /**
     * 问题3打分
     */
    private Integer question3;

    /**
     * 问题4打分
     */
    private Integer question4;

    /**
     * 问题5打分
     */
    private Integer question5;

    /**
     * 建议与意见
     */
    private String suggest;

    /**
     * 逻辑删除，0：否，1：是
     */
    private Integer deleted;


}
