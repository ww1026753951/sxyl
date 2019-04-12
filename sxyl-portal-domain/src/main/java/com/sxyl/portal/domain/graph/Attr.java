package com.sxyl.portal.domain.graph;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/***
 * 自定义属性对象
 * 此处后期需要兼容多端的情况
 */
@Data
@AllArgsConstructor
public class Attr implements Serializable {
    private static final long serialVersionUID = 1L;

    /***
     * 属性key
     */
    private String key;

    /***
     * 属性值
     */
    private String value;
}
