package com.sxyl.portal.controller;

import com.sxyl.portal.domain.DomainContent;
import com.sxyl.portal.domain.sort.ArrayNode;
import com.sxyl.portal.domain.tree.TreeConstruct;
import com.sxyl.portal.domain.tree.rb.RBInsertVo;
import com.sxyl.portal.service.sort.RBTreeService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: create by wei.wang
 * @version: v1.0
 * @description: com.sxyl.portal.controller
 * @date:2019/6/22
 */

@Controller
@RequestMapping("/tree")
public class RbController extends BaseController {


    /***
     *
     */
    @Autowired
    private RBTreeService rbTreeService;


    /***
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/rbTreePortal")
    public String dnnPortal(ModelMap modelMap , HttpServletRequest request) throws Exception {

        DomainContent dc = super.getDomainByReferer(request);
        modelMap.put("dc", dc);
        return "/screen/algorithm/rb-tree-portal";

    }

    /***
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/rbTreeConstruct")
    @ResponseBody
    public Object dnnConstruct(int[] arrays,String arrayStr){



// 10,15,85,70,20,60,30,50


//        arrays = new int[]{7,3,18,10,22,8,11,26,2,6,5,4,3};

//        arrays = new int[]{55};


        //7,3,18,10,22,8,11,26,2,6,13
        //经典多次旋转的例子  new int[]{7,3,18,10,22,8,11,26};


        //另外一个例子   10,15,85,70,20,60,30
//        arrays = new int[]{7,3,18,10,22,8,11,26,2,6,5};
        arrays = new int[]{100,110,120,130,140,150,160};


        TreeConstruct treeConstruct = rbTreeService.getRbSortConstruct(arrays );
        return treeConstruct;
    }

    /***
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/insertRbNode")
    @ResponseBody
    public Object insertRbNode( @RequestBody RBInsertVo rbInsertVo) throws Exception{

        TreeConstruct treeConstruct = rbTreeService.insertRbNode(rbInsertVo.getArrayNodeList() ,rbInsertVo.getNode() );
//        return treeConstruct;
//        rbInsertVo.getArrayNodeList();

        return treeConstruct;
    }

}
