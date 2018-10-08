package com.sxyl.portal.controller;


import com.sxyl.portal.domain.ExecutionSequence;
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

    private Map<String , String> bubbleStep = new HashMap<String,String>(){{
        put("A1","1-相邻的俩个元素比较大小。");
        put("A2","2-如果前一个元素比后一个元素大,交换位置,执行下次比较。");
        put("A3","3-如果前一个元素比后一个元素小。直接执行下次循环");
        put("A4","4-元素排序完成");
        put("A-END","5-数组排序完毕");
    }};

    /***
     * bubbleStep
     * @return
     * @throws Exception
     */
    @RequestMapping("/bubbleStep")
    @ResponseBody
    public Object bubbleStep() throws Exception{
        return mapToStep(bubbleStep);
    }

    private List<ExecutionSequence> mapToStep(Map<String,String> stepMap){

        List<ExecutionSequence> r = new ArrayList<ExecutionSequence>();
        for (Map.Entry<String, String> m : stepMap.entrySet()) {
            r.add(new ExecutionSequence(m.getKey(),m.getValue()));
        }
        return r;
    }

}