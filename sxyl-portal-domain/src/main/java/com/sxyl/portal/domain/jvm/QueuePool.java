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
public class QueuePool extends PoolCommonAttribute implements Serializable {

    private static final long serialVersionUID = 1L;


    /***
     * 阻塞队列的数量
     */
    private int queueSize;
}
