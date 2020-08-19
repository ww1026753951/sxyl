package com.sxyl.portal.service.tree.impl;

import com.sxyl.portal.domain.constant.ComponentCompositeEnum;
import com.sxyl.portal.domain.constant.ExecuteEnum;
import com.sxyl.portal.domain.d.AnimationTotal;
import com.sxyl.portal.domain.graph.Circle;
import com.sxyl.portal.domain.graph.Group;
import com.sxyl.portal.domain.graph.RectAndText;
import com.sxyl.portal.domain.sort.ArrayNode;
import com.sxyl.portal.domain.sort.NodeExecuteStep;
import com.sxyl.portal.domain.tree.TreeConstruct;
import com.sxyl.portal.domain.tree.avl.AVLTree;
import com.sxyl.portal.domain.tree.avl.AVLTreeNode;
import com.sxyl.portal.domain.tree.rb.RBTNode;
import com.sxyl.portal.service.tree.AVLTreeService;
import com.sxyl.portal.service.tree.TreeCommonService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @author: create by wei.wang
 * @version: v1.0
 * @description: com.sxyl.portal.service.tree.impl
 * @date:2020/6/9
 */
@Service
public class AVLTreeServiceImpl extends TreeCommonService implements AVLTreeService {
    @Override
    public TreeConstruct getAVLConstruct(int[] arrays) {

        AVLTree tree=new AVLTree();

        /***
         * 构建数组
         */
        List<ArrayNode> arrayNodes = createArrayNode(arrays) ;

        for(ArrayNode arrayNode : arrayNodes) {
            tree.setRoot(tree.insert(tree.getRoot() , arrayNode.getValue() , arrayNode));
        }




//        //dnn动画
        AnimationTotal animationTotal = null;
//
//        /***
//         * 层序遍历树生成结构
//         */
        Group group = getArrayAndTreeConstruct(arrayNodes , tree.getRoot());
//
        group = createLineConstruct(group ,  tree.getRoot() , false);

        return new TreeConstruct(arrayNodes , group , animationTotal);
    }

    @Override
    public TreeConstruct insertAVLNode(List<ArrayNode> arrayNodeList, int node, List<NodeExecuteStep> nodeExecuteStepList) {

        AVLTree tree = new AVLTree();

        Map<String , AVLTreeNode> treeMap = new HashMap<>(arrayNodeList.size());
        //循环赋值对象
        for(ArrayNode arrayNode : arrayNodeList) {
            tree.setRoot(tree.insert(tree.getRoot() , arrayNode.getValue() , arrayNode));
        }
        /***
         * 处理操作历史的逻辑，此处应该只有删除
         */
        if (CollectionUtils.isEmpty(nodeExecuteStepList)){
            nodeExecuteStepList = new ArrayList<>();
        }else {
            for (NodeExecuteStep nodeExecuteStep : nodeExecuteStepList){
                if(nodeExecuteStep.getType() == ExecuteEnum.DELETE.getType()){
                    tree.deleteNode(tree.getRoot() , nodeExecuteStep.getValue());
                }else if(nodeExecuteStep.getType() == ExecuteEnum.INSERT.getType()){
                    ArrayNode arrayNode = new ArrayNode();
                    arrayNode.setCid(nodeExecuteStep.getCid());
                    arrayNode.setValue(nodeExecuteStep.getValue());
                    arrayNode.setValueTextId(nodeExecuteStep.getValueTextId());

                    tree.setRoot(tree.insert(tree.getRoot() , nodeExecuteStep.getValue() ,arrayNode ));
//                    tree.setRoot(result);
//                    result.getBuffer();
                }
            }
        }


        AVLTreeNode temp ;
        //层序遍历
        Queue<AVLTreeNode> nodeQueue = new ArrayDeque<>();
        //层序遍历，赋值buffer和宽度
        int currentIndex = 0 ;//当前层节点的序号
        int nextLevel = 0;//记录下一层需要打印的节点的数量
        int currentLevel = 1;    //记录当前层需要打印的节点的数量

        int level = 1;

        tree.getRoot().setWidth(defaultML);
        nodeQueue.add(tree.getRoot());
        int buffer = defaultBuffer;
        while ((temp = nodeQueue.poll()) != null) {
            currentIndex = currentIndex + 1 ;
            if (temp.getLeft() != null) {
                //赋值左节点的宽度
                temp.getLeft().setWidth( temp.getWidth() - buffer );
                temp.getLeft().setBuffer(buffer);
                temp.getLeft().setLevel(level + 1);
                nodeQueue.add(temp.getLeft());
                nextLevel++;
            }
            if (temp.getRight() != null) {
                //赋值右节点 的宽度
                temp.getRight().setWidth(temp.getWidth() + buffer);
                temp.getRight().setBuffer(buffer);
                temp.getRight().setLevel(level + 1);
                nodeQueue.add(temp.getRight());
                nextLevel++;
            }
            currentLevel--;
            if(currentLevel == 0) {
                level = level + 1;
                currentIndex = 0;
                buffer =  new Double(buffer*rate).intValue();
                currentLevel = nextLevel;
                nextLevel = 0;
            }
//            temp.setLevel(level );

            if(temp.getBuffer()==null){
                temp.setBuffer(defaultBuffer);
            }
            if(temp.getWidth()== null){
                temp.setWidth(defaultML);
            }
            if(temp.getLevel() == null) {
                temp.setLevel(1);
            }

            treeMap.put(temp.getCid() , temp);
        }



        ArrayNode arrayNode = createSingleArrayNode(node);

        AVLTreeNode target = new AVLTreeNode(node , arrayNode) ;

        //生产对象，顺便赋值 宽，高， buffer 等
        Group group = this.getSingleAndTreeConstruct(tree , target , treeMap) ;


        tree.setAnimationFlag(true);
        //插入树节点
        tree.insert(tree.getRoot() , node , arrayNode) ;


        TreeConstruct treeConstruct = new TreeConstruct();
        treeConstruct.setGroup(group);
        //设置数组列表
//        arrayNodeList.add(arrayNode);
        treeConstruct.setArrayLists(arrayNodeList);
        //设置动画
        treeConstruct.setAt(tree.getAnimationTotal());



        //增加删除节点
        NodeExecuteStep nodeExecuteStep = new NodeExecuteStep();
        nodeExecuteStep.setValue(node);
        nodeExecuteStep.setCid(arrayNode.getCid());
        nodeExecuteStep.setValueTextId(arrayNode.getValueTextId());
        nodeExecuteStep.setType(ExecuteEnum.INSERT.getType());
        nodeExecuteStepList.add(nodeExecuteStep);
        treeConstruct.setExecuteSteps(nodeExecuteStepList);


        return treeConstruct;
    }


    /****
     * 获取单个元素的结构和
     * @return
     */
    private Group getSingleAndTreeConstruct(AVLTree avlTree , AVLTreeNode avlTreeNode , Map<String , AVLTreeNode> avlTreeNodeMap) {
        Group all = new Group();

        AVLTreeNode parent = avlTree.findInserParent(avlTree.getRoot(),null ,avlTreeNode.getKey());
        //找寻插入节点

        AVLTreeNode mapParentNode = avlTreeNodeMap.get(parent.getCid());
        int buffer = mapParentNode.getBuffer();
        if(mapParentNode.getLevel() > 1){
            buffer =  new Double(buffer*rate).intValue();
        }


//        //获取缓冲数值
//

        int width = mapParentNode.getWidth();


        if (avlTreeNode.getKey() < parent.getKey()){
            width = width - buffer;
        }
        else{
            width = width + buffer ;
        }
        //设置宽 和 buffer
        avlTreeNode.setWidth(width);
        avlTreeNode.setBuffer(buffer);
        avlTreeNode.setLevel(parent.getLevel() + 1);
        //设置元
        create(all , avlTreeNode.getCid() , avlTreeNode.getNodeTextId()  , avlTreeNode.getNodeText(), width ,(mapParentNode.getLevel()+1)*height + defaultMt, false);

        all.addChild(createLine(parent.getCid() , avlTreeNode.getCid() , false));

        return all;
    }



    @Override
    public TreeConstruct delAVLNode(List<ArrayNode> arrayNodeList, int node, List<NodeExecuteStep> nodeExecuteStepList) {
        return null;
    }






    /***
     * 层序遍历构建数组和树结构
     * @param avlTreeNode
     * @return
     */
    private Group getArrayAndTreeConstruct(List<ArrayNode> arrayNodes , AVLTreeNode avlTreeNode) {
        Group all = new Group();

//        all.setMl(500);
        all.setMt(20);
        all.setCompose(ComponentCompositeEnum.NONE.getType());

        Group array = new Group();
        array.setCompose(ComponentCompositeEnum.HORIZONTAL.getType());
        int ml=50;
        for (ArrayNode arrayNode : arrayNodes){
            ml = ml + 50;
            array.addChild(new RectAndText(arrayNode.getRid() , arrayNode.getValue(), 30,30 ,ml,"white"));
        }

        all.addChild(array);


        int buffer = 500;
        int defaultML = 1100 ;
        double rate = 0.5;

        //层序遍历
        Queue<AVLTreeNode> nodeQueue = new ArrayDeque<>();
        //每层节点的x队列。
//        Queue<Integer> nodeWidthQueue = new ArrayDeque<>();
        nodeQueue.add(avlTreeNode);
//        nodeWidthQueue.add(defaultML);
        AVLTreeNode temp ;
        int currentLevel = 1;    //记录当前层需要打印的节点的数量
        int nextLevel = 0;//记录下一层需要打印的节点的数量
        int currentIndex = 0 ;//当前层节点的序号
        Group group = new Group();
        Integer parentWidth = 0;
        Integer left ;
        // key 是  cid ,value 是cid 对应的父级宽度
        Map<String , Integer> widthMap = new HashMap<>(arrayNodes.size());
        // key 是  cid ,value 是cid 对应的左还是右
        Map<String , Boolean> leftMap = new HashMap<>(arrayNodes.size());
        while ((temp = nodeQueue.poll()) != null) {
            currentIndex = currentIndex + 1 ;

            parentWidth = widthMap.get(temp.getCid());
            if (parentWidth == null){
                parentWidth = 100 ;
            }

            if (leftMap.get(temp.getCid())!=null &&leftMap.get(temp.getCid()) ) {
                left = parentWidth - buffer;
//                nodeWidthQueue.add(left.intValue());
            } else {
                left = parentWidth + buffer;
//                nodeWidthQueue.add(left.intValue());
            }


            Circle circle = create(group , temp.getCid(),temp.getNodeTextId() , temp.getNodeText() , left,0 ,false);
            if (temp.getLeft() != null) {
                widthMap.put(temp.getLeft().getCid() ,left.intValue() ) ;
                leftMap.put(temp.getLeft().getCid() ,true) ;
                nodeQueue.add(temp.getLeft());
                nextLevel++;
            }
            if (temp.getRight() != null) {
                widthMap.put(temp.getRight().getCid() ,left.intValue() ) ;
                leftMap.put(temp.getRight().getCid() ,false) ;
                nodeQueue.add(temp.getRight());
                nextLevel++;
            }

            currentLevel--;
            if(currentLevel == 0) {
                currentIndex = 0;
                group.setMt(80);
                buffer =  new Double(buffer*rate).intValue();
                group.setCompose(ComponentCompositeEnum.NONE.getType());
                all.addChild(group);
                group = new Group();
                currentLevel = nextLevel;
                nextLevel = 0;
            }
        }
        return all;
    }


}
