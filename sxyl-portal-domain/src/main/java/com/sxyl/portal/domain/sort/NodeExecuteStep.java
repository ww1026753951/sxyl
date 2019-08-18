package com.sxyl.portal.domain.sort;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: create by wei.wang
 * @version: v1.0
 * @description: com.sxyl.portal.domain.sort
 * @date:2019/8/14
 */
@Data
public class NodeExecuteStep implements Serializable {

    private static final long serialVersionUID = 1L;


    /***
     * 节点操作的值
     */
    private Integer value ;

    /***
     * 操作类型
     * @see com.sxyl.portal.domain.constant.ExecuteEnum
     */
    private Integer type ;

    /***
     * 对象的cid
     */
    private String cid;
}
