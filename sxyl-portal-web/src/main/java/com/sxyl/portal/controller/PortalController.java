package com.sxyl.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by wangweiyf on 2017/11/2.
 */
@Controller
public class PortalController extends BaseController {


    /***
     * 首页跳转
     * @return
     * @throws Exception
     */
    @RequestMapping("portal")
    public String toCart() throws Exception{
        return "/screen/portal";
    }
}
