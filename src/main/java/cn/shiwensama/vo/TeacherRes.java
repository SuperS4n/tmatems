package cn.shiwensama.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author: shiwensama
 * @create: 2020-04-25
 * @description:
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TeacherRes {

    private String id;

    private String name;

    public TeacherRes(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
