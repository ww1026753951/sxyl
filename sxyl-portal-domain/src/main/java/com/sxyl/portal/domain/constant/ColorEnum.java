package com.sxyl.portal.domain.constant;

/**
 * @author: create by wei.wang
 * @version: v1.0
 * @description: com.sxyl.portal.domain.constant
 * @date:2020/5/12
 */
public enum  ColorEnum {


    //白色
    WHITE(1,"white"),
    //黑色
    BLACK(2,"black"),
    //灰色
    GRAY(3,"gray"),
    //灰色
    RED(4,"red");

    private final int type;
    private final String htmlCode;
    private ColorEnum(int type , String htmlCode) {
        this.type = type;
        this.htmlCode = htmlCode;
    }


    public int getType() {
        return type;
    }

    public String getHtmlCode() {
        return htmlCode;
    }
}
