package com.sxyl.portal.domain.linear;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sxyl.portal.domain.CommonConstruct;
import com.sxyl.portal.domain.jvm.PoolCommonAttribute;
import lombok.Data;

import java.util.Stack;

/**
 * @author: create by wei.wang
 * @version: v1.0
 * @description: com.sxyl.portal.domain.linear
 * @date:2020/11/22
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class StackConstruct extends CommonConstruct {

    /***
     * 线程的数量
     */
    private int stackSize = 0;


    /***
     * 栈的基础属性
     */
    private PoolCommonAttribute stack ;

    /****
     * 栈对象
     */
    private Stack<String> stringStack = new Stack<>();


}
