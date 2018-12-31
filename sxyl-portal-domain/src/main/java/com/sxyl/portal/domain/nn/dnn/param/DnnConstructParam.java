package com.sxyl.portal.domain.nn.dnn.param;

import com.sxyl.portal.domain.nn.dnn.DnnHiddenLayer;
import com.sxyl.portal.domain.nn.dnn.DnnInputLayer;
import com.sxyl.portal.domain.nn.dnn.DnnOutputLayer;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class DnnConstructParam implements Serializable {

    private static final long serialVersionUID = 1L;

    //输入层的结构
    private DnnInputLayer dnnInputLayer;

    //隐藏层结构
    private List<DnnHiddenLayer> dnnHiddenLayerList;

    //输出层结构
    private DnnOutputLayer outputLayer;

}
