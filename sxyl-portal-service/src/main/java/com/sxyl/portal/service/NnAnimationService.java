package com.sxyl.portal.service;

import com.sxyl.portal.domain.d.AnimationComponent;
import com.sxyl.portal.domain.graph.GraphComponent;

/****
 * 神经网络的动画效果
 */
public interface NnAnimationService {


    /****
     * 获取nn 的信息
     * @return
     */
    AnimationComponent getNnAnimation(GraphComponent graphComponent);
}
