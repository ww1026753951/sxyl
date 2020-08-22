package com.sxyl.portal.controller.tree;

import com.sxyl.portal.controller.BaseController;
import com.sxyl.portal.domain.DomainContent;
import com.sxyl.portal.domain.tree.TreeConstruct;
import com.sxyl.portal.domain.tree.rb.RBExecuteVo;
import com.sxyl.portal.service.tree.AVLTreeService;
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
 * @description: com.sxyl.portal.controller.tree
 * @date:2020/6/9
 */
@Controller
@RequestMapping("/tree/avl")
public class AVLController extends BaseController {


    /***
     *
     */
    @Autowired
    private AVLTreeService avlTreeService;


    /***
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/avlTreePortal")
    public String avlTreePortal(ModelMap modelMap , HttpServletRequest request) throws Exception {

        DomainContent dc = super.getDomainByReferer(request);
        modelMap.put("dc", dc);
        return "/screen/algorithm/tree/avl-tree-portal";

    }

    /***
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/avlTreeConstruct")
    @ResponseBody
    public Object avlTreeConstruct(int[] arrays,String arrayStr){



// 10,15,85,70,20,60,30,50
//        tree.root = tree.insert(tree.root, 10);
//        tree.root = tree.insert(tree.root, 20);
//        tree.root = tree.insert(tree.root, 30);
//        tree.root = tree.insert(tree.root, 40);
//        tree.root = tree.insert(tree.root, 50);
//        tree.root = tree.insert(tree.root, 25);

        //大个的左旋
//        arrays = new int[]{10,20,30,40,50,25 };
//        arrays = new int[]{10,20,30,40,50,25,51,52,53,54,55 };

        arrays = new int[]{50,51,52,53,54,55,49 };


        arrays = getArrays(arrays , arrayStr);

        TreeConstruct treeConstruct = avlTreeService.getAVLConstruct(arrays );
        return treeConstruct;
    }

    /***
     *
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/insertAvlNode")
    @ResponseBody
    public Object insertRbNode( @RequestBody RBExecuteVo rbExecuteVo) throws Exception{
        TreeConstruct treeConstruct = avlTreeService.insertAVLNode(rbExecuteVo.getArrayNodeList() ,rbExecuteVo.getNode() ,rbExecuteVo.getExecuteHistory());
        return treeConstruct;
    }



    /***
     *
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/delRbNode")
    @ResponseBody
    public Object delRbNode( @RequestBody RBExecuteVo rbExecuteVo) throws Exception{
        TreeConstruct treeConstruct = avlTreeService.delAVLNode(rbExecuteVo.getArrayNodeList() ,rbExecuteVo.getNode() ,rbExecuteVo.getExecuteHistory() );
        return treeConstruct;
    }

}
