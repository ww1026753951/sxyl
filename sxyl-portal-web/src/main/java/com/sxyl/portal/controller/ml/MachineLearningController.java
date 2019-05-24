package com.sxyl.portal.controller.ml;

import com.sxyl.portal.controller.BaseController;
import com.sxyl.portal.domain.DomainContent;
import com.sxyl.portal.domain.nn.dnn.param.DnnConstructParam;
import com.sxyl.portal.domain.nn.dnn.param.DnnParam;
import com.sxyl.portal.domain.nn.dnn.result.DnnConstruct;
import com.sxyl.portal.service.nn.NnParamService;
import com.sxyl.portal.service.nn.NnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller()
@RequestMapping("/ml")
public class MachineLearningController extends BaseController {


    @Autowired
    private NnService nnService;


    @Autowired
    private NnParamService nnParamService ;

    /***
     * 首页跳转  
     * @return
     * @throws Exception
     */
    @RequestMapping("/dnnConstruct")
    @ResponseBody
    public Object dnnConstruct(HttpServletRequest request) throws Exception{

//        if(!checkRefer(request)){
//            return new Object();
//        }
        int[] hidden = new int[1];
        hidden[0] = 3;
        super.getDomainByReferer(request);
        // 获取结构
        DnnConstructParam dnnConstructParam = nnParamService.createDnnParam(new DnnParam() , 3 ,hidden , 3);
        DnnConstruct dnnConstruct = nnService.getDnnConstruct(3 ,hidden , 3,dnnConstructParam);
        return dnnConstruct;
    }


    /***
     * 首页跳转
     * @return
     * @throws Exception
     */
    @RequestMapping("/dnnPortal")
    public String dnnPortal(ModelMap modelMap , HttpServletRequest request) throws Exception {

        DomainContent dc = super.getDomainByReferer(request);
        modelMap.put("dc", dc);
        return "/screen/algorithm/dnn-portal";

    }

}

