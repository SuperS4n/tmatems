package cn.shiwensama.eneity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author supers4n
 * @since 2020-04-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Log implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 日志id
     */
    private String id;

    /**
     * 请求路径
     */
    private String url;

    /**
     * 参数
     */
    private String params;

    /**
     * 访问状态，1正常0异常
     */
    private Integer status;

    /**
     * 异常信息
     */
    private String message;

    /**
     * 请求方式，get、post
     */
    private String method;

    /**
     * 响应时间
     */
    private Long time;

    /**
     * 返回值
     */
    private String result;

    /**
     * 请求IP
     */
    private String ip;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;


}
