package com.sxyl.portal.service.impl;

import com.sxyl.portal.domain.ExecutionSequence;
import com.sxyl.portal.service.ExecutionSequenceService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExecutionSequenceServiceImpl implements ExecutionSequenceService {



    @Override
    public List<ExecutionSequence> queryExecutionSequence() {

        List<ExecutionSequence> bubbleStep = new ArrayList<ExecutionSequence>(){{
            add(new ExecutionSequence("step-content","background1","background1","根据数组长度循环,0-&gt;{SIZE},当前下标{INDEX}"));
            add(new ExecutionSequence("step-content","background2","background2","根据数组长度循环,0-&gt;{SIZE},当前下标{INDEX}"));
            add(new ExecutionSequence("step-content","a","A1","1-相邻的俩个元素比较大小。"));
            add(new ExecutionSequence("step-content","a","A2","2-如果前一个元素比后一个元素大,交换位置,执行下次比较。"));
            add(new ExecutionSequence("step-content","a","A3","3-如果前一个元素比后一个元素小。直接执行下次循环"));
            add(new ExecutionSequence("step-content","a","A4","4-元素排序完成"));
            add(new ExecutionSequence("step-content","a","A-END","5-数组排序完毕"));



            add(new ExecutionSequence("pseudo-code","a","A1","for(int i=0;i&lt;array.length;i++){"));
            add(new ExecutionSequence("pseudo-code","a","A2","&nbsp;&nbsp;&nbsp;&nbsp;for(int j=0;j&lt;array.length;j++){"));
            add(new ExecutionSequence("pseudo-code","a","A3","&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;if(array[i]&gt;array[i+1]){"));
            add(new ExecutionSequence("pseudo-code","a","A4","&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;swap(array[i],array[i+1])"));

            add(new ExecutionSequence("pseudo-code","","","&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}"));
            add(new ExecutionSequence("pseudo-code","","","&nbsp;&nbsp;&nbsp;&nbsp;}"));
            add(new ExecutionSequence("pseudo-code","","","}"));
        }};

        return bubbleStep;
    }
}
