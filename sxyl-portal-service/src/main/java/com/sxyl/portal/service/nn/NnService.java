package com.sxyl.portal.service.nn;

import com.sxyl.portal.domain.nn.dnn.param.DnnConstructParam;
import com.sxyl.portal.domain.nn.dnn.result.DnnConstruct;

/***
 * 神经网络
 */
public interface NnService {


    /****
     * 获取全连接神经网络
     * @return
     */
    DnnConstruct getDnnConstruct(int inputNum , int[] hiddenNum , int outputNum , DnnConstructParam dnnConstructParam) ;
}
