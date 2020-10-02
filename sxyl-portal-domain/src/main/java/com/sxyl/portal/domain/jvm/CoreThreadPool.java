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
public class CoreThreadPool extends PoolCommonAttribute implements Serializable {

    private static final long serialVersionUID = 1L;


    /***
     * 核心线程数
     */
    private int coreSize ;
}
