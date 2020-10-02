package com.sxyl.portal.domain.jvm;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: create by wei.wang
 * @version: v1.0
 * @description: com.sxyl.portal.domain.jvm
 * @date:2020/9/21
 */
@Data
public class PoolCommonAttribute implements Serializable {

    private static final long serialVersionUID = 1L;

    /***
     * 宽度
     */
    private int width;

    /***
     * 高度
     */
    private int height;


    /****
     * 元素的横坐标
     */
    private int x;

    /***
     * 元素的纵坐标
     */
    private int y;



}
