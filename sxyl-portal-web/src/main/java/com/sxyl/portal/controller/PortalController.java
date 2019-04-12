package com.sxyl.portal.controller;

import com.sxyl.portal.domain.DomainContent;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by wangweiyf on 2017/11/2.
 *
 https://visualgo.net/en
 *
 */
@Controller
public class PortalController extends BaseController {


    /***
     * 首页跳转
     * @return
     * @throws Exception
     */
    @RequestMapping("/portal")
    public String portal(ModelMap modelMap ,HttpServletRequest request) throws Exception{
        DomainContent dc = super.getDomainByReferer(request);
        modelMap.put("dc",dc);
        return "/screen/portal-algorithm";
    }


    @RequestMapping("/sortOld")
    public String sortOld() throws Exception{
        return "/screen/algorithm/sort";
    }
    /***
     * 冒泡
     * @return
     * @throws Exception
     */
    @RequestMapping("/sort")
    public String sort() throws Exception{
        return "/screen/algorithm/sort-portal";
    }

    /***
     * im
     * @return
     * @throws Exception
     */
    @RequestMapping("im")
    public String im(ModelMap modelMap ,HttpServletRequest request) throws Exception{
        DomainContent dc = super.getDomainByReferer(request);
        modelMap.put("dc",dc);
        return "/screen/algorithm/im";
    }

    /***
     * im
     * @return
     * @throws Exception
     */
    @RequestMapping("mathjax")
    public String mathjax() throws Exception{
        return "/screen/algorithm/math-jax";
    }


    /***
     * dashboard
     * @return
     * @throws Exception
     */
    @RequestMapping("/test")
    public String dashboard() throws Exception{
        return "/screen/dashboard";
    }
}
