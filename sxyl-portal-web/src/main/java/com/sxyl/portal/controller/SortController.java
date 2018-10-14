package com.sxyl.portal.controller;


import com.sxyl.portal.domain.ExecutionSequence;
import com.sxyl.portal.service.ExecutionSequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sort")
public class SortController {

//    private Map<String , String> bubbleStep = new HashMap<String,String>(){{
////        put("A1","1-相邻的俩个元素比较大小。");
////        put("A2","2-如果前一个元素比后一个元素大,交换位置,执行下次比较。");
////        put("A3","3-如果前一个元素比后一个元素小。直接执行下次循环");
////        put("A4","4-元素排序完成");
////        put("A-END","5-数组排序完毕");
//        put("A0","for(int i=0;i&lt;array.length;i++){");
//        put("A8","&nbsp;&nbsp;&nbsp;&nbsp;for(int j=0;j&lt;array.length;j++){");
//        put("A1","&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;if(array[i]&gt;array[i+1]){");
//        put("A2","&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;swap(array[i],array[i+1])");
//    }};

    @Autowired
    private ExecutionSequenceService executionSequenceService ;

    /***
     * bubbleStep
     * @return
     * @throws Exception
     */
    @RequestMapping("/bubbleStep")
    @ResponseBody
    public Object bubbleStep() throws Exception{
        return executionSequenceService.queryExecutionSequence() ;
    }

//    private List<ExecutionSequence> mapToStep(Map<String,String> stepMap){
//
//        List<ExecutionSequence> r = new ArrayList<ExecutionSequence>();
//        for (Map.Entry<String, String> m : stepMap.entrySet()) {
//            r.add();
//        }
//        return r;
//    }

}