package com.sxyl.portal.domain.nn.dnn;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class DnnHiddenLayer  implements Serializable {

    private static final long serialVersionUID = 1L;

    //层id
    private String layerId;

    //
    private Integer layerIndex;

    //
    private String layerName;

    //神经元列表
    private List<DnnHiddenNeuron> neurons;
}
