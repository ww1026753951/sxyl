package com.sxyl.portal.controller.ml;

import com.sxyl.portal.controller.BaseController;
import com.sxyl.portal.domain.nn.DnnConstruct;
import com.sxyl.portal.service.nn.NnConstructService;
import com.sxyl.portal.service.nn.NnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller()
@RequestMapping("/ml")
public class MachineLearningController extends BaseController {


    @Autowired
    private NnService nnService;


    /***
     * 首页跳转
     * @return
     * @throws Exception
     */
    @RequestMapping("/dnnConstruct")
    @ResponseBody
    public Object dnnConstruct() throws Exception{
        int[] hidden = new int[1];
        hidden[0] = 3;
        DnnConstruct dnnConstruct =nnService.getDnnConstruct(3 ,hidden , 3);
        return dnnConstruct;
    }


}

