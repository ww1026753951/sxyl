package com.sxyl.portal.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: create by wei.wang
 * @version: v1.0
 * @description: com.sxyl.portal.domain .通用的移动对象的方法
 * @date:2020/11/24
 */
@Data
public class BaseMove implements Serializable {

    private static final long serialVersionUID = 1L;


    private int x;

    private int y;

}
