package com.sxyl.portal.domain.nn.dnn.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sxyl.portal.domain.d.AnimationTotal;
import com.sxyl.portal.domain.formula.MathFormulaTemplate;
import com.sxyl.portal.domain.graph.Group;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/****
 * 全连接神经网络结构对象
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class DnnConstruct implements Serializable {

    private static final long serialVersionUID = 1L;


    /****
     * dom结构
     */
    private Group group;

    /****
     * 动画的组件
     */
    private AnimationTotal at ;

    /****
     * 数学公式数组
     */
    private List<MathFormulaTemplate> mfs;


    /****
     * Formula-Animation
     */
    private Group fa;





}
