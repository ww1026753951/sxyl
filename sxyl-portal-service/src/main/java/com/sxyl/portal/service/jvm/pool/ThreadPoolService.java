package com.sxyl.portal.service.jvm.pool;

import com.sxyl.portal.domain.jvm.pool.PoolConstruct;

/**
 * @author: create by wei.wang
 * @version: v1.0
 * @description: com.sxyl.portal.service.jvm
 * @date:2020/9/14
 */
public interface ThreadPoolService {


    /****
     * 获取线程池的结构
     * @return
     */
    PoolConstruct getPoolConstruct(PoolConstruct poolConstruct);


    /***
     * 创建新任务
     * @return
     */
    PoolConstruct createNewThread(PoolConstruct poolConstruct);


    /****
     * 销毁新任务
     * @param poolConstruct
     * @return
     */
    PoolConstruct executeNewTask(PoolConstruct poolConstruct , Integer taskNo);


}
