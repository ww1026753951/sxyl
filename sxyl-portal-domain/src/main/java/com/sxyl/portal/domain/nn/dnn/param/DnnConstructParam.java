package com.sxyl.portal.domain.nn.dnn.param;

import com.sxyl.portal.domain.nn.dnn.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class DnnConstructParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /***
     * 输入层的结构
     */
    private DnnInputLayer dnnInputLayer;

    /***
     * 隐藏层结构
     */
    private List<DnnHiddenLayer> dnnHiddenLayerList;

    /****
     * 输出层结构
     */
    private DnnOutputLayer outputLayer;


    /***
     * 输入神经单元的map , key 是 id , 值是对象
     */
    private Map<String , DnnInputNeuron> inputNeuronMap;


    /***
     * 隐藏神经单元的map , key 是 id , 值是对象
     */
    private Map<String , DnnHiddenNeuron> hiddenNeuronMap;


}
