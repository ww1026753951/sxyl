package com.sxyl.portal.domain.jvm.pool;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sxyl.portal.domain.jvm.CoreThreadPool;
import com.sxyl.portal.domain.jvm.MaxThreadPool;
import com.sxyl.portal.domain.jvm.PoolCommonAttribute;
import com.sxyl.portal.domain.jvm.QueuePool;
import lombok.Data;

import java.io.Serializable;
import java.util.*;

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
     * 拒绝策略的方框
     */
    private PoolCommonAttribute reject;

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
     * 最大线程数 4
     */
    private int corePoolSize = 2;

    /***
     * 队列数量 6
     */
    private int queueSize = 6;

    /****
     * 最大线程数量 8
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

    //----------------------------
    /****
     * 活跃线程的数量
     */
    private int threadCount = 0 ;

    /***
     * 任务数量
     */
    private int taskCount = 0;


    /****
     * 任务和线程的id 所对应的 map
     * key 是任务编号,value 是线程号
     */
    private Map<Integer , String> map = new HashMap<>();

    /***
     * 队列的list
     */
    private List<Integer> queueList = new ArrayList<>();

    /****
     * 核心线程的 set, key是任务编号
     */
    private Set<Integer> coreTaskNo= new LinkedHashSet<>();


    /****
     * 总线程的set , key 是任务编号
     */
    private Set<Integer> maxTaskNo = new LinkedHashSet<>();


    /****
     * 线程的map,由于不能嵌套map,嵌套map时,页面传参失败 ， 所以这里用的
     * 第一个为线程id,
     * 第二个为 rgb
     * 第三个为 boolean 是否隐藏
     * 第四个为 boolean 是否有线程。
     *
     * 当
     */
    private List<Object[]> threadMap = new ArrayList<>();

}
