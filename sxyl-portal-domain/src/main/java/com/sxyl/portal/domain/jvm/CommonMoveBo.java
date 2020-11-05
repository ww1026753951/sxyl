package com.sxyl.portal.domain.jvm;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: create by wei.wang
 * @version: v1.0
 * @description: com.sxyl.portal.domain.jvm
 * @date:2020/10/27
 */
@Data
public class CommonMoveBo implements Serializable {

    private static final long serialVersionUID = 1L;

    private int initX1  ;

    private int initY1  ;

    private int initX2  ;

    private int initY2  ;

    private int initX3  ;

    private int initY3  ;
}
