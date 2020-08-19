package com.sxyl.portal.controller.tree;

import com.sxyl.portal.controller.BaseController;
import com.sxyl.portal.domain.DomainContent;
import com.sxyl.portal.domain.sort.ArrayNode;
import com.sxyl.portal.domain.sort.NodeExecuteStep;
import com.sxyl.portal.domain.tree.TreeConstruct;
import com.sxyl.portal.domain.tree.rb.RBExecuteVo;
import com.sxyl.portal.service.tree.BinaryTreeService;
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
 * @description: com.sxyl.portal.controller.tree
 * @date:2020/4/4
 */

@Controller
@RequestMapping("/tree/binary")
public class BinaryController extends BaseController {


    @Resource
    private BinaryTreeService binaryTreeService;


    /***
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/binaryTreePortal")
    public String binaryTreePortal(ModelMap modelMap , HttpServletRequest request) throws Exception {
        DomainContent dc = super.getDomainByReferer(request);
        modelMap.put("dc", dc);
        return "/screen/algorithm/tree/binary-tree-portal";
    }

    /***
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/binaryTreeConstruct")
    @ResponseBody
    public Object binaryTreeConstruct(int[] arrays,String arrayStr){

//        arrayStr = "20,18,22,5,13,30,6,27,13,16,3,11";
//        arrayStr = "6,3,8,4,2,7,6,9,10,8,12";
//        arrayStr = "6,3,8,4,2,7,6,9,10,12";
        arrays = getArrays(arrays , arrayStr);

        TreeConstruct treeConstruct = binaryTreeService.getBinaryTreeService(arrays );
        return treeConstruct;
    }


    /***
     * 前序遍历
     * @return
     * @throws Exception
     */
    @RequestMapping("/binaryTreePreOrder")
    @ResponseBody
    public Object preOrder( @RequestBody RBExecuteVo rbExecuteVo){
        TreeConstruct treeConstruct = binaryTreeService.preOrder(rbExecuteVo );
        return treeConstruct;
    }



    /***
     * 后序遍历
     * @return
     * @throws Exception
     */
    @RequestMapping("/binaryTreePostOrder")
    @ResponseBody
    public Object postOrder( @RequestBody RBExecuteVo rbExecuteVo){
        TreeConstruct treeConstruct = binaryTreeService.postOrder(rbExecuteVo );
        return treeConstruct;
    }



    /***
     * 中序遍历
     * @return
     * @throws Exception
     */
    @RequestMapping("/binaryTreeInfixOrder")
    @ResponseBody
    public Object infixOrder( @RequestBody RBExecuteVo rbExecuteVo){
        TreeConstruct treeConstruct = binaryTreeService.infixOrder(rbExecuteVo );
        return treeConstruct;
    }


    /***
     * 新增节点
     * @return
     * @throws Exception
     */
    @RequestMapping("/insertBinaryTreeNode")
    @ResponseBody
    public Object insertBinaryTreeNode( @RequestBody RBExecuteVo rbExecuteVo){
        TreeConstruct treeConstruct = binaryTreeService.insertBinaryNode(rbExecuteVo );
        return treeConstruct;
    }


    //



    /***
     * 删除节点
     * @return
     * @throws Exception
     */
    @RequestMapping("/delBinaryTreeNode")
    @ResponseBody
    public Object delBinaryTreeNode( @RequestBody RBExecuteVo rbExecuteVo){
        TreeConstruct treeConstruct = binaryTreeService.delBinaryNode(rbExecuteVo );
        return treeConstruct;
    }


    /***
     * 删除节点
     * @return
     * @throws Exception
     */
    @RequestMapping("/findBinaryTreeNode")
    @ResponseBody
    public Object findBinaryTreeNode( @RequestBody RBExecuteVo rbExecuteVo){
        TreeConstruct treeConstruct = binaryTreeService.findBinaryNode(rbExecuteVo );
        return treeConstruct;
    }
}
