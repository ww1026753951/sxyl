package com.sxyl.portal.domain.constant;

public enum  DisplayEnum {

    //隐藏的枚举
    NONE("none");

    private final String content;

    private DisplayEnum( String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
