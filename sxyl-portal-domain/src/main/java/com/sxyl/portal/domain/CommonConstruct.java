package com.sxyl.portal.domain;

import com.sxyl.portal.domain.d.AnimationTotal;
import com.sxyl.portal.domain.graph.Group;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: create by wei.wang
 * @version: v1.0
 * @description: com.sxyl.portal.domain
 * @date:2020/9/14
 */
@Data
public class CommonConstruct  implements Serializable {

    private static final long serialVersionUID = 1L;


    private Integer width;

    private Integer height;



    /****
     * dom结构
     */
    private Group group;


    /****
     * 动画的组件
     */
    private AnimationTotal at ;

    /***
     * 错误原因
     */
    private String errorMsg;


}
