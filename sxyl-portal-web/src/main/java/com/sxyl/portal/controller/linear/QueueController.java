package com.sxyl.portal.controller.linear;

import com.sxyl.portal.controller.BaseController;
import com.sxyl.portal.domain.DomainContent;
import com.sxyl.portal.domain.linear.QueueConstruct;
import com.sxyl.portal.domain.linear.StackConstruct;
import com.sxyl.portal.service.linear.QueueService;
import com.sxyl.portal.service.linear.StackService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author: create by wei.wang
 * @version: v1.0
 * @description: com.sxyl.portal.controller.linear
 * @date:2020/11/22
 */
@Controller
@RequestMapping("/linear/queue")
public class QueueController extends BaseController {


    @Resource
    private QueueService queueService;

    /***
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/queuePortal")
    public String avlTreePortal(ModelMap modelMap , HttpServletRequest request) throws Exception {

        DomainContent dc = super.getDomainByReferer(request);
        modelMap.put("dc", dc);
        return "/screen/algorithm/linear/queue-portal";

    }

    /***
     * 首页跳转
     * @return
     * @throws Exception
     */
    @RequestMapping("/queueConstruct")
    @ResponseBody
    public Object threadPoolConstruct(HttpServletRequest request) throws Exception{
        super.getDomainByReferer(request);

        QueueConstruct queueConstruct = queueService.getConstruct(null);


        return queueConstruct;
    }



    /***
     * 增加新数据
     * @return
     * @throws Exception
     */
    @RequestMapping("/push")
    @ResponseBody
    public Object push(HttpServletRequest request ,@RequestBody QueueConstruct qc) throws Exception{

        super.getDomainByReferer(request);


        QueueConstruct queueConstruct = queueService.push(qc);


        return queueConstruct;
    }


    /***
     * 增加新线程
     * @return
     * @throws Exception
     */
    @RequestMapping("/pop")
    @ResponseBody
    public Object pop(HttpServletRequest request ,@RequestBody QueueConstruct qc) throws Exception{

        super.getDomainByReferer(request);

        QueueConstruct queueConstruct = queueService.pop(qc);

        return queueConstruct;
    }
}
