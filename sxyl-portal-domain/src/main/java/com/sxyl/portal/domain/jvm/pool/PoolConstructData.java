package com.sxyl.portal.domain.jvm.pool;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sxyl.portal.domain.jvm.CoreThreadPool;
import com.sxyl.portal.domain.jvm.MaxThreadPool;
import com.sxyl.portal.domain.jvm.QueuePool;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: create by wei.wang
 * @version: v1.0
 * @description: com.sxyl.portal.domain.jvm.pool
 * @date:2020/10/1
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class PoolConstructData implements Serializable {

    private static final long serialVersionUID = 1L;




    /***
     * 核心线程池相关参数
     */
    private CoreThreadPool coreThreadPool;


    /****
     * 最大线程数相关参数
     */
    private MaxThreadPool maxThreadPool;

    /***
     * 阻塞队列相关参数
     */
    private QueuePool queuePool ;

    /***
     * 核心线程中的 数量
     */
    private int coreThreadPoolCount;


    /****
     * 最大线程中的数量
     */
    private int maxThreadPoolCount;


    /***
     * 最大队列中的数量
     */
    private int queuePoolCount;

    /***
     * 最大线程数
     */
    private int corePoolSize = 3;

    /***
     * 队列数量
     */
    private int queueSize = 7;

    /****
     * 最大线程数量
     */
    private int maxPoolSize = 6 ;

    //-----------------------
    /***
     * 核心线程数的 行下标
     */
    private int coreRowIndex =0;

    /****
     * 核心线程数的列下标
     */
    private int coreColumnIndex = 0 ;




    /***
     * 最大线程数的 行下标
     */
    private int maxRowIndex =0;

    /****
     * 最大线程数的列下标
     */
    private int maxColumnIndex = 0 ;



}
