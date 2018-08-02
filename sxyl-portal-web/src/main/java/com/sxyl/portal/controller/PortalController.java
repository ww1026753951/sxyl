package com.sxyl.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by wangweiyf on 2017/11/2.
 *
 */
@Controller
public class PortalController extends BaseController {


    /***
     * 首页跳转
     * @return
     * @throws Exception
     */
    @RequestMapping("portal")
    public String portal() throws Exception{
        return "/screen/portal-demo";
    }



    /***
     * 冒泡
     * @return
     * @throws Exception
     */
    @RequestMapping("sort")
    public String sort() throws Exception{
        return "/screen/algorithm/sort";
    }


//    /***
//     * im
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping("im")
//    public String im() throws Exception{
//        return "/screen/sort/im";
//    }
}
