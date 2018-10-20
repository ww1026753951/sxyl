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


}