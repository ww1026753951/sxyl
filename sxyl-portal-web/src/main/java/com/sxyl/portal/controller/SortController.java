package com.sxyl.portal.controller;


import com.sxyl.portal.domain.DomainContent;
import com.sxyl.portal.domain.ExecutionSequence;
import com.sxyl.portal.domain.tree.TreeConstruct;
import com.sxyl.portal.service.ExecutionSequenceService;
import com.sxyl.portal.service.sort.SortService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sort")
public class SortController extends BaseController{

    @Autowired
    private ExecutionSequenceService executionSequenceService ;

    @Autowired
    private SortService sortService;

    /***
     * bubbleStep
     * @return
     * @throws Exception
     */
    @RequestMapping("/step")
    @ResponseBody
    public Object step(String type) throws Exception{
        return executionSequenceService.queryExecutionSequence(type) ;
    }




    /***
     * 首页跳转
     * @return
     * @throws Exception
     */
    @RequestMapping("/heapSortPortal")
    public String dnnPortal(ModelMap modelMap , HttpServletRequest request) throws Exception {

        DomainContent dc = super.getDomainByReferer(request);
        modelMap.put("dc", dc);
        return "/screen/algorithm/heap-sort-portal";

    }

    /***
     * 首页跳转
     * @return
     * @throws Exception
     */
    @RequestMapping("/heapSortConstruct")
    @ResponseBody
    public Object dnnConstruct(int[] arrays,String arrayStr,boolean minHeap){
        int arraySize = 12 ;
        try {
            if(StringUtils.isNotBlank(arrayStr)){
                String[] str = arrayStr.split(",");
                if(str.length > 0){
                    arrays = new int[str.length];
                }
                for (int i = 0 ; i < str.length ; i ++){
                    String s = str[i];
                    if(NumberUtils.isNumber(s)){
                        arrays[i] = new Integer(s);
                    }else {
                        arrays[i] = 0 ;
                    }
                }
            }
        }catch (Exception e){

        }

        if(arrays==null || arrays.length ==0){
//            List<Integer> random = new ArrayList<>();
            arrays=new int[arraySize];
            for (int i=0 ; i<arraySize;i++){
                arrays[i] = (int)(1+Math.random()*30);
//                random.add((int)(1+Math.random()*30));
            }


        }
        TreeConstruct treeConstruct = sortService.getHeapSortConstruct(arrays ,minHeap);
        return treeConstruct;
    }


}