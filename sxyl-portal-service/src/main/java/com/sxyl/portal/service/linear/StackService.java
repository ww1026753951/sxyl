package com.sxyl.portal.service.linear;

import com.sxyl.portal.domain.linear.StackConstruct;

/**
 * @author: create by wei.wang
 * @version: v1.0
 * @description: com.sxyl.portal.service.linear
 * @date:2020/11/22
 */
public interface StackService {


    /****
     * 获取线程池的结构
     * @return
     */
    StackConstruct getPoolConstruct(StackConstruct stackConstruct);


    /***
     * 压栈
     * @return
     */
    StackConstruct pushStack(StackConstruct stackConstruct);


    /****
     * 出栈
     * @param stackConstruct
     * @return
     */
    StackConstruct popStack(StackConstruct stackConstruct);
}
