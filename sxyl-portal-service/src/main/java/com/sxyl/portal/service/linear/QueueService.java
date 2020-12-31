package com.sxyl.portal.service.linear;

import com.sxyl.portal.domain.linear.QueueConstruct;

/**
 * @author: create by wei.wang
 * @version: v1.0
 * @description: com.sxyl.portal.service.linear
 * @date:2020/12/4
 */
public interface QueueService {



    /****
     * 获取的结构
     * @return
     */
    QueueConstruct getConstruct(QueueConstruct stackConstruct);


    /***
     * 插入
     * @return
     */
    QueueConstruct push(QueueConstruct stackConstruct);


    /****
     * 弹出
     * @param stackConstruct
     * @return
     */
    QueueConstruct pop(QueueConstruct stackConstruct);
}
