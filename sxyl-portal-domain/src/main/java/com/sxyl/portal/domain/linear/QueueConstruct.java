package com.sxyl.portal.domain.linear;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sxyl.portal.domain.CommonConstruct;
import com.sxyl.portal.domain.jvm.PoolCommonAttribute;
import lombok.Data;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author: create by wei.wang
 * @version: v1.0
 * @description: com.sxyl.portal.domain.linear
 * @date:2020/12/4
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class QueueConstruct extends CommonConstruct {

    /***
     * 线程的数量
     */
    private int queueSize = 0;


    /***
     * 栈的基础属性
     */
    private PoolCommonAttribute queue ;

    /****
     * 栈对象
     */
    private Queue<String> stringQueue = new LinkedList<>();
}
