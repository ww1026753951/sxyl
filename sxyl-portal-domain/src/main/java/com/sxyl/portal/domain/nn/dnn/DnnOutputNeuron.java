package com.sxyl.portal.domain.nn.dnn;

import com.sxyl.portal.domain.nn.NnWeight;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/***
 * 输出层神经元
 */
@Data
public class DnnOutputNeuron extends Neuron  implements Serializable {

    /***
     * 序列化uid
     */
    private static final long serialVersionUID = 1L;

    /****
     * 神经元层数
     */
    private Integer index;

    /***
     * 求和的文本id
     */
    private String sumTextId;

    /****
     * 求和的文本
     */
    private String sumText;

    /***
     * 求和的值id
     */
    private String sumValueTextId;

    /****
     * 求和的值
     */
    private String sumValueText;

    /***
     * 激活函数后的id
     */
    private String activationTextId;

    /***
     * 激活函数的文本
     */
    private String activationText;

    /***
     * 激活函数后的id
     */
    private String activationValueTextId;

    /***
     * 激活函数的文本
     */
    private String activationValueText;

    /****
     * 实际值得文本
     */
    private String actualText;

    /***
     * 实际值文本id
     */
    private String actualTextId;

    /****
     * 损失值,误差值
     */
    private String costValueText;

    /***
     * 损失值误差项的文本id
     */
    private String costValueTextId;


    /***
     * 神经元权重
     */
    private List<NnWeight> neuronWeight;

}
