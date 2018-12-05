package com.sxyl.portal.controller.ml;

import com.sxyl.portal.controller.BaseController;
import com.sxyl.portal.service.NnConstructService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller()
@RequestMapping("/ml")
public class MachineLearningController extends BaseController {


    @Autowired
    private NnConstructService dnnConstructService;


    /***
     * 首页跳转
     * @return
     * @throws Exception
     */
    @RequestMapping("/dnnConstruct")
    @ResponseBody
    public Object dnnConstruct() throws Exception{

        return dnnConstructService.getDnnConstruct();
    }


}

