package com.sxyl.portal.controller.jvm;

import com.sxyl.portal.controller.BaseController;
import com.sxyl.portal.domain.DomainContent;
import com.sxyl.portal.domain.jvm.pool.PoolConstruct;
import com.sxyl.portal.domain.nn.dnn.param.DnnConstructParam;
import com.sxyl.portal.domain.nn.dnn.param.DnnParam;
import com.sxyl.portal.domain.nn.dnn.result.DnnConstruct;
import com.sxyl.portal.service.jvm.pool.ThreadPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: create by wei.wang
 * @version: v1.0
 * @description: com.sxyl.portal.controller.jvm
 * @date:2020/9/14
 */

@Controller()
@RequestMapping("/jvm/threadPool")
public class ThreadPoolController extends BaseController {


    @Autowired
    private ThreadPoolService threadPoolService;



    /***
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/threadPoolPortal")
    public String avlTreePortal(ModelMap modelMap , HttpServletRequest request) throws Exception {

        DomainContent dc = super.getDomainByReferer(request);
        modelMap.put("dc", dc);
        return "/screen/jvm/pool/thread-pool-portal";

    }

    /***
     * 首页跳转
     * @return
     * @throws Exception
     */
    @RequestMapping("/threadPoolConstruct")
    @ResponseBody
    public Object threadPoolConstruct(HttpServletRequest request, @RequestBody PoolConstruct pc) throws Exception{
        super.getDomainByReferer(request);

        PoolConstruct poolConstruct = threadPoolService.getPoolConstruct(pc);



        // 获取结构
//        DnnConstructParam dnnConstructParam = nnParamService.createDnnParam(new DnnParam() , inputNum ,hidden , outputNum);
//        DnnConstruct dnnConstruct = nnService.getDnnConstruct(inputNum ,hidden , outputNum,dnnConstructParam);
        return poolConstruct;
    }


    /***
     * 增加新线程
     * @return
     * @throws Exception
     */
    @RequestMapping("/addNewThread")
    @ResponseBody
    public Object addNewThread(HttpServletRequest request ,@RequestBody PoolConstruct pc) throws Exception{

        super.getDomainByReferer(request);


        PoolConstruct poolConstruct = threadPoolService.createNewThread(pc);


        // 获取结构
        return poolConstruct;
    }

    /***
     * 增加新线程
     * @return
     * @throws Exception
     */
    @RequestMapping("/executeTask")
    @ResponseBody
    public Object executeTask(HttpServletRequest request ,@RequestBody PoolConstruct pc , Integer taskNo) throws Exception{

        super.getDomainByReferer(request);


        PoolConstruct poolConstruct = threadPoolService.executeNewTask(pc , taskNo);


        // 获取结构
        return poolConstruct;
    }

}
