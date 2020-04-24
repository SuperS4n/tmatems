package cn.shiwensama.enums;

import lombok.Getter;

/**
 * @author: shiwenSama
 * @create: 2020-04-23
 * @description:
 **/
@Getter
public enum GradeEnum {

    /**
     * 年级标识
     */
    DAYI(1,"大一"),
    DAER(2,"大二"),
    DASAN(3,"大三"),
    DASI(4,"大四");

    private Integer id;
    private String name;

    GradeEnum(Integer id, String name) {
        this.id = id;
        this.name =name;
    }

    public static String getName(int code) {
        for(GradeEnum gradeEnum : GradeEnum.values()) {
            if(code ==gradeEnum.getId()) {
                return gradeEnum.name;
            }
        }
        return null;
    }
}
