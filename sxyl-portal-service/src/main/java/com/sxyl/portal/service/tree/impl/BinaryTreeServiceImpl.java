package com.sxyl.portal.service.tree.impl;

import com.sxyl.portal.domain.constant.*;
import com.sxyl.portal.domain.constant.tree.CommonTreeConstant;
import com.sxyl.portal.domain.d.*;
import com.sxyl.portal.domain.graph.Circle;
import com.sxyl.portal.domain.graph.Group;
import com.sxyl.portal.domain.graph.RectAndText;
import com.sxyl.portal.domain.sort.ArrayNode;
import com.sxyl.portal.domain.sort.NodeExecuteStep;
import com.sxyl.portal.domain.tree.BinaryTreeNode;
import com.sxyl.portal.domain.tree.TreeConstruct;
import com.sxyl.portal.domain.tree.binary.BinaryTree;
import com.sxyl.portal.domain.tree.rb.RBExecuteVo;
import com.sxyl.portal.service.ExecutionSequenceService;
import com.sxyl.portal.service.tree.BinaryTreeService;
import com.sxyl.portal.service.tree.TreeCommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @author: create by wei.wang
 * @version: v1.0
 * @description: com.sxyl.portal.service.tree.impl
 * @date:2020/4/4
 */
@Service
public class BinaryTreeServiceImpl  extends TreeCommonService implements BinaryTreeService {


    @Autowired
    private ExecutionSequenceService executionSequenceService ;


    @Override
    public TreeConstruct getBinaryTreeService(int[] arrays) {
        /***
         * 构建数组
         */
        List<ArrayNode> arrayNodes = createArrayNode(arrays) ;

        BinaryTree binaryTree = new BinaryTree();

        binaryTree = super.buildBinaryTree(binaryTree , arrayNodes  );

        BinaryTreeNode root = binaryTree.getRoot();

        /***
         * 层序遍历树生成结构
         */
        Group group = getArrayAndTreeConstruct(arrayNodes , root);
        group = createLineConstruct(group , root,false);

        //dnn动画
        AnimationTotal animationTotal =this.getSortAnimation( arrayNodes );

        return new TreeConstruct(arrayNodes , group , animationTotal);
    }

    @Override
    public TreeConstruct preOrder(RBExecuteVo rbExecuteVo) {
        BinaryTree binaryTree = new BinaryTree();
        //初始化数组
        for (ArrayNode arrayNode :rbExecuteVo.getArrayNodeList()){
            binaryTree.insert(arrayNode.getValue() , arrayNode.getValueTextId() , arrayNode.getCid());
        }
        binaryTree.setAnimationFlag(true);

        binaryTree.preOrder(binaryTree.getRoot());
        binaryTree.resetColor(rbExecuteVo.getArrayNodeList());
        return new TreeConstruct(rbExecuteVo.getArrayNodeList() , null , binaryTree.getAnimationTotal() );
    }

    @Override
    public TreeConstruct postOrder(RBExecuteVo rbExecuteVo) {
        BinaryTree binaryTree = new BinaryTree();
        //初始化数组
        for (ArrayNode arrayNode :rbExecuteVo.getArrayNodeList()){
            binaryTree.insert(arrayNode.getValue() , arrayNode.getValueTextId() , arrayNode.getCid());
        }
        binaryTree.setAnimationFlag(true);
        binaryTree.postOrder(binaryTree.getRoot());
        binaryTree.resetColor(rbExecuteVo.getArrayNodeList());
        return new TreeConstruct(rbExecuteVo.getArrayNodeList() , null , binaryTree.getAnimationTotal() );
    }


    @Override
    public TreeConstruct infixOrder(RBExecuteVo rbExecuteVo) {
        BinaryTree binaryTree = new BinaryTree();
        //初始化数组
        for (ArrayNode arrayNode :rbExecuteVo.getArrayNodeList()){
            binaryTree.insert(arrayNode.getValue() , arrayNode.getValueTextId() , arrayNode.getCid());
        }
        binaryTree.setAnimationFlag(true);
        binaryTree.infixOrder(binaryTree.getRoot());
        binaryTree.resetColor(rbExecuteVo.getArrayNodeList());
        return new TreeConstruct(rbExecuteVo.getArrayNodeList() , null , binaryTree.getAnimationTotal() );
    }

    @Override
    public TreeConstruct insertBinaryNode(RBExecuteVo rbExecuteVo) {


        BinaryTree tree=new BinaryTree();

        tree.setStepExecute(executionSequenceService.getExecutionSequenceByType(AlgorithmTypeEnum.BINARY.getType()));

        List<NodeExecuteStep> nodeExecuteStepList = rbExecuteVo.getExecuteHistory();

        //构造树结构
        for(ArrayNode arrayNode : rbExecuteVo.getArrayNodeList()) {
            tree.insert(arrayNode.getValue() , arrayNode.getValueTextId() , arrayNode.getCid());
        }


        /***
         * 处理操作历史的逻辑，此处应该只有删除
         */
        if (CollectionUtils.isEmpty(nodeExecuteStepList)){
            nodeExecuteStepList = new ArrayList<>();
        }else {
            for (NodeExecuteStep nodeExecuteStep : nodeExecuteStepList){
                if(nodeExecuteStep.getType() == ExecuteEnum.DELETE.getType()){

                    tree.delete(nodeExecuteStep.getValue());
                }else if(nodeExecuteStep.getType() == ExecuteEnum.INSERT.getType()){

                    ArrayNode arrayNode = new ArrayNode();
                    arrayNode.setValue(nodeExecuteStep.getValue());
                    arrayNode.setCid(nodeExecuteStep.getCid());
                    arrayNode.setValueTextId(nodeExecuteStep.getValueTextId());
                    tree.insert(arrayNode.getValue() , arrayNode.getValueTextId() , arrayNode.getCid());
                }
            }
        }



        Map<String , BinaryTreeNode> binaryNodeMap = new HashMap<>();

        //层序遍历
        Queue<BinaryTreeNode> nodeQueue = new ArrayDeque<>();
        tree.getRoot().setWidth(defaultML);
        nodeQueue.add(tree.getRoot());
        BinaryTreeNode temp ;

        int nextLevel = 0;//记录下一层需要打印的节点的数量

        int level = 1;
        int buffer = defaultBuffer;
        int currentLevel = 1;    //记录当前层需要打印的节点的数量
        while ((temp = nodeQueue.poll()) != null) {
            if (temp.getLeftNode() != null) {
                //赋值左节点的宽度
                temp.getLeftNode().setWidth( temp.getWidth() - buffer );
                temp.getLeftNode().setBuffer(buffer);
                temp.getLeftNode().setLevel(level + 1);
                nodeQueue.add(temp.getLeftNode());
                nextLevel++;
            }
            if (temp.getRightNode() != null) {
                //赋值右节点 的宽度
                temp.getRightNode().setWidth(temp.getWidth() + buffer);
                temp.getRightNode().setBuffer(buffer);
                temp.getRightNode().setLevel(level + 1);
                nodeQueue.add(temp.getRightNode());
                nextLevel++;
            }
            currentLevel--;
            if(currentLevel == 0) {
                level = level + 1;
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
            binaryNodeMap.put(temp.getCid() , temp);
        }

        ArrayNode arrayNode = createSingleArrayNode(rbExecuteVo.getNode());
        //新增节点
        BinaryTreeNode rbNode=new BinaryTreeNode();
        rbNode.setCid(arrayNode.getCid());
        rbNode.setNodeTextId(arrayNode.getValueTextId());
        rbNode.setNodeText(Integer.valueOf(rbExecuteVo.getNode()).toString());
        rbNode.setData(rbExecuteVo.getNode());

        //生产对象，顺便赋值 宽，高， buffer 等
        Group group = this.getSingleAndTreeConstruct(tree , rbNode , binaryNodeMap) ;


        //设置动画开始
        tree.setAnimationFlag(true);
        tree.insert(rbExecuteVo.getNode(), arrayNode.getValueTextId(), arrayNode.getCid());
        TreeConstruct treeConstruct = new TreeConstruct();

        treeConstruct.setGroup(group);
        //设置数组列表
        treeConstruct.setArrayLists(rbExecuteVo.getArrayNodeList());

        //设置动画
        treeConstruct.setAt(tree.getAnimationTotal());

        //增加新增节点
        NodeExecuteStep nodeExecuteStep = new NodeExecuteStep();
        nodeExecuteStep.setValue(rbExecuteVo.getNode());
        nodeExecuteStep.setCid(arrayNode.getCid());
        nodeExecuteStep.setValueTextId(arrayNode.getValueTextId());
        nodeExecuteStep.setType(ExecuteEnum.INSERT.getType());
        nodeExecuteStepList.add(nodeExecuteStep);
        treeConstruct.setExecuteSteps(nodeExecuteStepList);


        return treeConstruct;
    }

    @Override
    public TreeConstruct delBinaryNode(RBExecuteVo rbExecuteVo) {

        BinaryTree tree = new BinaryTree();


        /**
         * 设置执行步骤的文案
         */
//        tree.setStepExecute(executionSequenceService.getExecutionSequenceByType(RBTreeStepConstant.TYPE));

        Map<String , BinaryTreeNode> binaryNodeMap = new HashMap<>();

        List<NodeExecuteStep> nodeExecuteStepList = rbExecuteVo.getExecuteHistory();

        //构造树结构
        for(ArrayNode arrayNode : rbExecuteVo.getArrayNodeList()) {
            tree.insert(arrayNode.getValue() , arrayNode.getValueTextId() , arrayNode.getCid());
        }

        //获取执行步骤
        nodeExecuteStepList = executeStep( nodeExecuteStepList ,tree );

        tree.setAnimationFlag(true);
        computeBaseAttribute(tree.getRoot() , binaryNodeMap);


        /***
         * 层序遍历树生成结构 ,赋值宽和高
         */
        //开启获取动画的功能
        BinaryTreeNode binaryTreeNode = tree.delete(rbExecuteVo.getNode());

        if(binaryTreeNode == null){
            return null;
        }


        TreeConstruct treeConstruct = new TreeConstruct();
        treeConstruct.setArrayLists(rbExecuteVo.getArrayNodeList());

        //设置动画
        treeConstruct.setAt(tree.getAnimationTotal());

        //增加删除节点
        NodeExecuteStep nodeExecuteStep = new NodeExecuteStep();
        nodeExecuteStep.setValue(binaryTreeNode.getData());
        nodeExecuteStep.setCid(binaryTreeNode.getCid());
        nodeExecuteStep.setValueTextId(binaryTreeNode.getNodeTextId());
        nodeExecuteStep.setType(ExecuteEnum.DELETE.getType());
        nodeExecuteStepList.add(nodeExecuteStep);
        treeConstruct.setExecuteSteps(nodeExecuteStepList);

        return treeConstruct;
    }

    @Override
    public TreeConstruct findBinaryNode(RBExecuteVo rbExecuteVo) {


        BinaryTree tree = new BinaryTree();


        /**
         * 设置执行步骤的文案
         */
//        tree.setStepExecute(executionSequenceService.getExecutionSequenceByType(RBTreeStepConstant.TYPE));

        Map<String , BinaryTreeNode> binaryNodeMap = new HashMap<>();

        List<NodeExecuteStep> nodeExecuteStepList = rbExecuteVo.getExecuteHistory();

        //构造树结构
        for(ArrayNode arrayNode : rbExecuteVo.getArrayNodeList()) {
            tree.insert(arrayNode.getValue() , arrayNode.getValueTextId() , arrayNode.getCid());
        }

        tree.setAnimationFlag(true);


        BinaryTreeNode binaryTreeNode = tree.find(rbExecuteVo.getNode());

        if (binaryTreeNode == null) {

        }

        TreeConstruct treeConstruct = new TreeConstruct();
        treeConstruct.setArrayLists(rbExecuteVo.getArrayNodeList());

        //设置动画
        treeConstruct.setAt(tree.getAnimationTotal());


        return treeConstruct;
    }


    /***
     * 层序遍历构建数组和树结构
     * @param binaryTreeNode
     * @return
     */
    private Group getArrayAndTreeConstruct(List<ArrayNode> arrayNodes , BinaryTreeNode binaryTreeNode) {
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
        Queue<BinaryTreeNode> nodeQueue = new ArrayDeque<>();
        //每层节点的x队列。
//        Queue<Integer> nodeWidthQueue = new ArrayDeque<>();
        nodeQueue.add(binaryTreeNode);
//        nodeWidthQueue.add(defaultML);
        BinaryTreeNode temp ;
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


            Circle circle = create(group , temp , left ,false);
            if (temp.getLeftNode() != null) {
                widthMap.put(temp.getLeftNode().getCid() ,left.intValue() ) ;
                leftMap.put(temp.getLeftNode().getCid() ,true) ;
                nodeQueue.add(temp.getLeftNode());
                nextLevel++;
            }
            if (temp.getRightNode() != null) {
                widthMap.put(temp.getRightNode().getCid() ,left.intValue() ) ;
                leftMap.put(temp.getRightNode().getCid() ,false) ;
                nodeQueue.add(temp.getRightNode());
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




    /***
     * 获取动画步骤的对象
     * @return
     */
    private AnimationTotal getSortAnimation(List<ArrayNode> arrayNodes ){
        AnimationTotal animationTotal = new AnimationTotal();

        for (int i=0 ;i< arrayNodes.size() ; i++){
            ArrayNode arrayNode = arrayNodes.get(i);
            animationTotal.addComponent(new Move(arrayNode.getRid() , arrayNode.getCid(),
                    "将数组生成二叉树"
            ));
            animationTotal.addComponent(new Destroy(arrayNode.getRid() ));

            int parentIndex = (int)Math.ceil(i/(double)2)-1;
            if(parentIndex >= 0){
                animationTotal.addComponent(new Show(arrayNodes.get(parentIndex).getCid() +"-"+arrayNodes.get(i).getCid()));
            }
            animationTotal.addComponent(new Show(arrayNode.getCid(),arrayNode.getValueTextId()));
        }
        return animationTotal;
    }





    /****
     * 获取单个元素的结构和
     * @param binaryTreeNode
     * @return
     */
    private Group getSingleAndTreeConstruct(BinaryTree binaryTree , BinaryTreeNode binaryTreeNode , Map<String , BinaryTreeNode> binaryNodeMap) {
        Group all = new Group();

        BinaryTreeNode parent =  binaryTree.findInsertNode(binaryTreeNode.getData());
//        BinaryTreeNode parent = binaryTreeNode.getParentNode();
//        //找寻插入节点
//        if(parent==null){
//            parent = binaryTree.findInsertNode(binaryTreeNode.getData());
//        }
        BinaryTreeNode mapParentNode = binaryNodeMap.get(parent.getCid());
        //获取缓冲数值
        int buffer = mapParentNode.getBuffer();
        if(mapParentNode.getLevel() > 1){
            buffer =  new Double(buffer*rate).intValue();
        }
        int width = mapParentNode.getWidth();


        if (binaryTreeNode.getData() < parent.getData()){
            width = width - buffer;
        }
        else{
            width = width + buffer ;
        }

        //设置宽 和 buffer
        binaryTreeNode.setWidth(width);
        binaryTreeNode.setBuffer(buffer);
        binaryTreeNode.setLevel(parent.getLevel() + 1);
        //设置元
        create(all , binaryTreeNode , width , (mapParentNode.getLevel()+1)*height + defaultMt , ColorEnum.BLACK , true);

        all.addChild(createLine(parent.getCid() , binaryTreeNode.getCid() , true));

        return all;
    }


    private List<NodeExecuteStep> executeStep(List<NodeExecuteStep> nodeExecuteStepList , BinaryTree tree){
        /***
         * 处理操作历史的逻辑，此处应该只有删除
         */
        if (CollectionUtils.isEmpty(nodeExecuteStepList)){
            nodeExecuteStepList = new ArrayList<>();
        }else {
            for (NodeExecuteStep nodeExecuteStep : nodeExecuteStepList){
                if(nodeExecuteStep.getType() == ExecuteEnum.DELETE.getType()){

                    tree.delete(nodeExecuteStep.getValue());
                }else if(nodeExecuteStep.getType() == ExecuteEnum.INSERT.getType()){

                    ArrayNode arrayNode = new ArrayNode();
                    arrayNode.setValue(nodeExecuteStep.getValue());
                    arrayNode.setCid(nodeExecuteStep.getCid());
                    arrayNode.setValueTextId(nodeExecuteStep.getValueTextId());
                    tree.insert(arrayNode.getValue() , arrayNode.getValueTextId() , arrayNode.getCid());
                }
            }
        }
        return nodeExecuteStepList;
    }


    private void computeBaseAttribute(BinaryTreeNode binaryTreeNode , Map<String , BinaryTreeNode> binaryNodeMap){

        if(binaryTreeNode.getWidth() == null){
            binaryTreeNode.setWidth(defaultML);

        }
        BinaryTreeNode temp ;
        //层序遍历
        Queue<BinaryTreeNode> nodeQueue = new ArrayDeque<>();

        int buffer = defaultBuffer;
        int level = 1;

        int nextLevel = 0;//记录下一层需要打印的节点的数量

        int currentLevel = 1;    //记录当前层需要打印的节点的数量

        nodeQueue.add(binaryTreeNode);

        while ((temp = nodeQueue.poll()) != null) {
            if (temp.getLeftNode() != null) {
                //赋值左节点的宽度
                temp.getLeftNode().setWidth( temp.getWidth() - buffer );
                temp.getLeftNode().setBuffer(buffer);
                temp.getLeftNode().setLevel(level + 1);
                nodeQueue.add(temp.getLeftNode());
                nextLevel++;
            }
            if (temp.getRightNode() != null) {
                //赋值右节点 的宽度
                temp.getRightNode().setWidth(temp.getWidth() + buffer);
                temp.getRightNode().setBuffer(buffer);
                temp.getRightNode().setLevel(level + 1);
                nodeQueue.add(temp.getRightNode());
                nextLevel++;
            }
            currentLevel--;
            if(currentLevel == 0) {
                level = level + 1;
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
            binaryNodeMap.put(temp.getCid() , temp);
        }

    }




}
