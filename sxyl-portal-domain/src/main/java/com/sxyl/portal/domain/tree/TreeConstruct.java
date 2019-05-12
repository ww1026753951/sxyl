package com.sxyl.portal.domain.tree;

import com.sxyl.portal.domain.d.AnimationTotal;
import com.sxyl.portal.domain.graph.Group;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/***
 * 树的机构
 */
@Data
@AllArgsConstructor
public class TreeConstruct implements Serializable {

    private static final long serialVersionUID = 1L;


    /****
     * dom结构
     */
    private Group group;


    /****
     * 动画的组件
     */
    private AnimationTotal at ;
}
