package com.sxyl.portal.service.nn;

import com.sxyl.portal.domain.graph.Group;
import com.sxyl.portal.domain.nn.dnn.param.DnnConstructParam;

import java.util.List;

public interface NnConstructService {

    /****
     * 获取dnn结构
     * @return
     */
    Group getDnnConstruct(DnnConstructParam dnnConstructParam);
}
