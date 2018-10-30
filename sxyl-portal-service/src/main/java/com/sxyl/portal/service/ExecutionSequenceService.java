package com.sxyl.portal.service;

import com.sxyl.portal.domain.ExecutionSequence;

import java.util.List;

public interface ExecutionSequenceService {

    /****
     * 查询动画执行步骤
     * @return
     */
    List<ExecutionSequence> queryExecutionSequence(String type);
}
