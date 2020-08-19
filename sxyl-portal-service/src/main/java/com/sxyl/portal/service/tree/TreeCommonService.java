package com.sxyl.portal.service.tree;

import com.sxyl.portal.common.JUUID;
import com.sxyl.portal.domain.constant.ColorEnum;
import com.sxyl.portal.domain.constant.DisplayEnum;
import com.sxyl.portal.domain.constant.ShowTextPositionEnum;
import com.sxyl.portal.domain.graph.Circle;
import com.sxyl.portal.domain.graph.Group;
import com.sxyl.portal.domain.graph.Text;
import com.sxyl.portal.domain.sort.ArrayNode;
import com.sxyl.portal.domain.tree.BinaryTreeNode;
import com.sxyl.portal.domain.tree.avl.AVLTreeNode;
import com.sxyl.portal.domain.tree.binary.BinaryTree;
import com.sxyl.portal.domain.tree.rb.RBTreeColor;
import com.sxyl.portal.service.CommonService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: create by wei.wang
 * @version: v1.0
 * @description: com.sxyl.portal.service.tree
 * @date:2020/4/5
 */
public class TreeCommonService extends CommonService {
    //rate
    protected double rate = 0.5;


    //层与层的高度
    protected int height = 80 ;

    // 圆的半径
    protected final int r = 20;

    protected int defaultML = 600 ;


    protected final int defaultMt = 20 ;


    protected int defaultBuffer = 250 ;
    /***
     * 根据数组构建列表
     * @param arrays
     * @return
     */
    protected List<ArrayNode> createArrayNode(int[] arrays){
        List<ArrayNode> arrayNodes = new ArrayList<>();
        ArrayNode arrayNode ;
        for (int i : arrays){
            arrayNode = new ArrayNode(JUUID.getUUID() ,JUUID.getUUID() , JUUID.getUUID() , new Integer(i)) ;
            arrayNodes.add(arrayNode);
        }
        return arrayNodes;
    }




    /***
     * 根据数组创建满二叉数
     * @param root
     * @param nums
     * @param index
     * @return
     */
    public BinaryTreeNode buildTree(BinaryTreeNode root, List<ArrayNode> nums, int index) {
        if (index >= nums.size()) {
            return null;
        }
        //如果左子节点大于数组下标,则返回节点
        if(2 * index + 1 >= nums.size()){
            return root;
        }
        root.setLeftNode(buildTree(
                new BinaryTreeNode( nums.get(2 * index + 1).getCid() , nums.get(2 * index + 1).getValueTextId() , nums.get(2 * index + 1).getValue().toString())
                ,nums, 2 * index + 1));

        //如果右子节点大于数组下标 ,则返回节点
        if(2 * index + 2 >= nums.size()){
            return root;
        }
        root.setRightNode(buildTree(new BinaryTreeNode( nums.get(2 * index + 2).getCid() , nums.get(2 * index + 2).getValueTextId() , nums.get(2 * index + 2).getValue().toString()),
                nums, 2 * index + 2));
        return root;
    }

    public BinaryTree buildBinaryTree(BinaryTree tree, List<ArrayNode> nums) {


        for (int x =0 ; x< nums.size() ; x++){
            tree.insert(nums.get(x).getValue(),nums.get(x).getValueTextId(),nums.get(x).getCid());
        }

//        root.insert()
        return tree;
    }


    /***
     * 创建圆
     * @param group
     * @param root
     */
    protected Circle create(Group group , BinaryTreeNode root , Integer x, boolean hidden) {
        Circle circle = new Circle(root.getCid(), r ,"blue");
        if (hidden){
            circle.setH(DisplayEnum.NONE.getContent());
        }
        circle.setX(x);
        Text text = new Text(root.getNodeTextId() , x,0,root.getNodeText() , ShowTextPositionEnum.MIDDLE.getCode());
        if (hidden){
            text.setH(DisplayEnum.NONE.getContent());
        }

        circle.addCurrentComponent(text);
        group.addChild(circle);
        return circle ;
    }


    /***
     * 创建圆
     * @param group
     * @param nodeTextId
     * @param nodeText
     */
    protected Circle create(Group group , String cid, String nodeTextId, String nodeText, Integer x,  Integer y, boolean hidden) {
        Circle circle = new Circle(cid, r ,"blue");
        if (hidden){
            circle.setH(DisplayEnum.NONE.getContent());
        }
        circle.setX(x);
        circle.setY(y);
        Text text = new Text(nodeTextId , x,y, nodeText , ShowTextPositionEnum.MIDDLE.getCode());
        if (hidden){
            text.setH(DisplayEnum.NONE.getContent());
        }

        circle.addCurrentComponent(text);
        group.addChild(circle);
        return circle ;
    }



    /***
     * 创建圆
     * @param group
     * @param root
     */
    protected Circle create(Group group , BinaryTreeNode root , Integer x, Integer y , ColorEnum textColorEnum, boolean hidden) {
        Circle circle = new Circle(root.getCid(), r ,"blue");
        circle.setX(x);
        circle.setY(y);
        if (hidden){
            circle.setH(DisplayEnum.NONE.getContent());
        }
        Text text = new Text(root.getNodeTextId() , x,y, root.getNodeText() , ShowTextPositionEnum.MIDDLE.getCode());
        if (hidden){
            text.setH(DisplayEnum.NONE.getContent());
        }
        text.setF(textColorEnum.getHtmlCode());
        circle.addCurrentComponent(text);
        group.addChild(circle);
        return circle ;
    }





    /***
     * 创建线结构
     * @param g
     * @param root
     * @return
     */
    protected Group createLineConstruct(Group g , BinaryTreeNode root,boolean hidden){

        if(root == null){
            return g;
        }

        if (root.getLeftNode() != null) {
            //左节点的线
            g.addChild(createLine(root.getCid() , root.getLeftNode().getCid() , hidden));
        }
        if (root.getRightNode() != null) {
            //右节点的线
            g.addChild(createLine(root.getCid() , root.getRightNode().getCid() , hidden));
        }

        createLineConstruct(g, root.getLeftNode() , hidden);
        createLineConstruct(g, root.getRightNode() , hidden);

        return g ;
    }



    /***
     * 创建线结构
     * @param g
     * @param root
     * @return
     */
    protected Group createLineConstruct(Group g , AVLTreeNode root, boolean hidden){

        if(root == null){
            return g;
        }

        if (root.getLeft() != null) {
            //左节点的线
            g.addChild(createLine(root.getCid() , root.getLeft().getCid() , hidden));
        }
        if (root.getRight() != null) {
            //右节点的线
            g.addChild(createLine(root.getCid() , root.getRight().getCid() , hidden));
        }

        createLineConstruct(g, root.getLeft() , hidden);
        createLineConstruct(g, root.getRight() , hidden);

        return g ;
    }


    /***
     * 返回节点
     * @param array
     * @return
     */
    protected ArrayNode createSingleArrayNode(int array){
        return new ArrayNode(JUUID.getUUID() ,"array-"+array +"-"+JUUID.getUUID() , JUUID.getUUID() , new Integer(array));
    }


}
