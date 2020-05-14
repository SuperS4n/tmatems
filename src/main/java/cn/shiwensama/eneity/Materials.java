package cn.shiwensama.eneity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
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
 * @since 2020-05-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Materials implements Serializable {

    private static final long serialVersionUID=1L;


    private String id;

    /**
     * 课程ID
     */
    private Integer cid;

    /**
     * 上传的文件名
     */
    private String name;

    /**
     * 上传时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date time;

    /**
     * 下载地址
     */
    private String url;

    /**
     * 备注
     */
    private String remark;

    /**
     * 逻辑删除
     */
    @TableLogic
    private Integer deleted;


}
