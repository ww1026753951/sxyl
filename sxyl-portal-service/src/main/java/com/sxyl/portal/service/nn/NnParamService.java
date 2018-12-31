package com.sxyl.portal.service.nn;

import com.sxyl.portal.domain.nn.dnn.param.DnnConstructParam;
import com.sxyl.portal.domain.nn.dnn.param.DnnParam;

public interface NnParamService {


    /****
     * 创建dnn 的参数结构
     * @param dnnParam
     * @param inputNum
     * @param hiddenNum
     * @param outputNum
     * @return
     */
    DnnConstructParam createDnnParam(DnnParam dnnParam ,int inputNum, int[] hiddenNum, int outputNum);
}
