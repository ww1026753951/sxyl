package com.sxyl.portal.service.impl;

import com.sxyl.portal.domain.ExecutionSequence;
import com.sxyl.portal.service.ExecutionSequenceService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExecutionSequenceServiceImpl implements ExecutionSequenceService {


    Map<String , List<ExecutionSequence>> stepSequence = new HashMap<String, List<ExecutionSequence>>(){{
        put("1", getBubbleStep());
        put("2", getCocktailShakerSort());
    }};

    @Override
    public List<ExecutionSequence> queryExecutionSequence(String type) {
        if(type==null){
            type="1";
        }
        List result = stepSequence.get(type);
        if (result==null){
            return new ArrayList<ExecutionSequence>();
        }
        return result;
    }

    /****
     * 获取冒泡算法执行步骤
     * @return
     */
    private List<ExecutionSequence> getBubbleStep(){
        List<ExecutionSequence> bubbleStep = new ArrayList<ExecutionSequence>(){{
            add(new ExecutionSequence("step-content","background1","background1","根据数组长度循环,0-&gt;{SIZE},当前下标{INDEX}"));
            add(new ExecutionSequence("step-content","background2","background2","根据数组长度循环,0-&gt;{SIZE},当前下标{INDEX}"));
            add(new ExecutionSequence("step-content","a","A1","1-相邻的俩个元素比较大小。"));
            add(new ExecutionSequence("step-content","a","A2","2-如果前一个元素比后一个元素大,交换位置,执行下次比较。"));
            add(new ExecutionSequence("step-content","a","A3","3-如果前一个元素比后一个元素小。直接执行下次循环"));
            add(new ExecutionSequence("step-content","a","A4","4-元素排序完成"));
            add(new ExecutionSequence("step-content","a","A-END","5-数组排序完毕"));
            //伪代码
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

    /****
     * 获取鸡尾酒算法执行步骤
     * @return
     */
    private List<ExecutionSequence> getCocktailShakerSort(){
        List<ExecutionSequence> bubbleStep = new ArrayList<ExecutionSequence>(){{
            add(new ExecutionSequence("step-content","background1","background1","根据数组长度循环,0-&gt;{SIZE},当前下标{INDEX}"));
            add(new ExecutionSequence("step-content","background2","background2","根据数组长度循环,{SIZE}-&gt;0,当前下标{INDEX}"));
            add(new ExecutionSequence("step-content","a","A1","1-相邻的俩个元素比较大小。"));
            add(new ExecutionSequence("step-content","a","A2","2-如果前一个元素比后一个元素大,交换位置,执行下次比较。"));
            add(new ExecutionSequence("step-content","a","A3","3-如果前一个元素比后一个元素小。直接执行下次循环"));
            add(new ExecutionSequence("step-content","a","A4","4-元素排序完成"));
            add(new ExecutionSequence("step-content","a","A-END","5-数组排序完毕"));
            //伪代码 Pseudo code
//            add(new ExecutionSequence("pseudo-code","a","A1","for(int i=0;i&lt;array.length;i++){"));
//            add(new ExecutionSequence("pseudo-code","a","A2","&nbsp;&nbsp;&nbsp;&nbsp;for(int j=0;j&lt;array.length;j++){"));
//            add(new ExecutionSequence("pseudo-code","a","A3","&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;if(array[i]&gt;array[i+1]){"));
//            add(new ExecutionSequence("pseudo-code","a","A4","&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;swap(array[i],array[i+1])"));
//            add(new ExecutionSequence("pseudo-code","","","&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}"));
//            add(new ExecutionSequence("pseudo-code","","","&nbsp;&nbsp;&nbsp;&nbsp;}"));
//            add(new ExecutionSequence("pseudo-code","","","}"));



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
