package com.sxyl.portal.service;

import com.sxyl.portal.domain.ExecutionSequence;
import com.sxyl.portal.domain.nn.dnn.param.DnnConstructParam;
import com.sxyl.portal.domain.nn.dnn.result.DnnConstruct;

import java.util.List;
import java.util.Map;

public interface ExecutionSequenceService {

    /****
     * 查询动画执行步骤
     * @return
     */
    List<ExecutionSequence> queryExecutionSequence(String type);


    /****
     * 全连接神经网络的执行步骤
     */
    List<ExecutionSequence> queryDnnExecutionSequence(DnnConstructParam dnnConstructParam);


    /****
     *
     * @param type
     * @param code
     * @return
     */
    ExecutionSequence getExecutionSequenceByTypeAndCode(String type , String code , Map<String,String> replace);


    /****
     *
     * @param type
     * @return
     */
    Map<String , String> getExecutionSequenceByType(String type );
}
