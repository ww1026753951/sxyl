package com.sxyl.portal.domain.jvm.pool;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sxyl.portal.domain.CommonConstruct;
import com.sxyl.portal.domain.jvm.CoreThreadPool;
import com.sxyl.portal.domain.jvm.MaxThreadPool;
import com.sxyl.portal.domain.jvm.QueuePool;
import lombok.Data;

/**
 * @author: create by wei.wang
 * @version: v1.0
 * @description: com.sxyl.portal.domain.jvm.pool
 * @date:2020/9/14
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class PoolConstruct extends CommonConstruct {


    /****
     * 线程池的数据
     */
    private PoolConstructData pcd ;


    /***
     * 错误原因
     */
    private String errorMsg;

//
//    /***
//     * 线程池编号
//     */
//    private Integer taskNo;
}
