package com.sxyl.portal.service.nn;

import com.sxyl.portal.domain.graph.Group;

import java.util.List;

public interface NnConstructService {

    /****
     * 获取dnn结构
     * @return
     */
    Group getDnnConstruct(List<String> inputIds,List<List<String>> hiddenIds,List<String> outputIds);
}
