package com.sxyl.portal.service.nn;

import com.sxyl.portal.domain.d.AnimationComponent;
import com.sxyl.portal.domain.d.AnimationTotal;
import com.sxyl.portal.domain.graph.GraphComponent;

import java.util.List;

/****
 * 神经网络的动画效果
 */
public interface NnAnimationService {


    /****
     * 获取dnn 的动画算法信息
     * @return
     */
    AnimationTotal getNnAnimation(List<String> inputIds, List<List<String>> hiddenIds, List<String> outputIds);
}
