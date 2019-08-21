package com.sxyl.portal.service.sort.impl;

import com.sxyl.portal.common.JUUID;
import com.sxyl.portal.domain.constant.*;
import com.sxyl.portal.domain.d.AnimationTotal;
import com.sxyl.portal.domain.graph.*;
import com.sxyl.portal.domain.sort.ArrayNode;
import com.sxyl.portal.domain.sort.NodeExecuteStep;
import com.sxyl.portal.domain.tree.BinaryTreeNode;
import com.sxyl.portal.domain.tree.TreeConstruct;
import com.sxyl.portal.domain.tree.rb.RBTNode;
import com.sxyl.portal.domain.tree.rb.RBTree;
import com.sxyl.portal.domain.tree.rb.RBTreeColor;
import com.sxyl.portal.domain.tree.rb.v.Node;
import com.sxyl.portal.service.CommonService;
import com.sxyl.portal.service.ExecutionSequenceService;
import com.sxyl.portal.service.sort.RBTreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

import static com.sxyl.portal.domain.tree.rb.RBTreeColor.BLACK;
import static com.sxyl.portal.domain.tree.rb.RBTreeColor.RED;

@Service
public class RBTreeServiceImpl extends CommonService implements RBTreeService {



    // 圆的半径
    private final int r = 20;


    private final int defaultMt = 20 ;
    //rate
    double rate = 0.5;

    //层与层的高度
    int height = 80 ;

    int defaultBuffer = 250 ;

    int defaultML = 600 ;

    @Autowired
    private ExecutionSequenceService executionSequenceService ;

    @Override
    public TreeConstruct getRbSortConstruct(int[] arrays) {

        RBTree<Integer> tree=new RBTree<Integer>();



        /***
         * 构建数组
         */
        List<ArrayNode> arrayNodes = createArrayNode(arrays) ;

        for(ArrayNode arrayNode : arrayNodes) {
            tree.insert(arrayNode , arrayNode.getValue());
        }

        //dnn动画
        AnimationTotal animationTotal = null;

        /***
         * 层序遍历树生成结构
         */
        Group group = getArrayAndTreeConstruct(arrayNodes , tree);

        group = createLineConstruct(group ,  tree.getRoot());

        return new TreeConstruct(arrayNodes , group , animationTotal);
    }



    @Override
    public TreeConstruct insertRbNode(List<ArrayNode> arrayNodeList, int node, List<NodeExecuteStep> nodeExecuteStepList) {


        RBTree<Integer> tree=new RBTree<Integer>();

        /**
         * 设置执行步骤的文案
         */
        tree.setStepExecute(executionSequenceService.getExecutionSequenceByType(RBTreeStepConstant.TYPE));

        //构造树结构
        for(ArrayNode arrayNode : arrayNodeList) {
            tree.insert(arrayNode , arrayNode.getValue());
        }


        /***
         * 处理操作历史的逻辑，此处应该只有删除
         */
        if (CollectionUtils.isEmpty(nodeExecuteStepList)){
            nodeExecuteStepList = new ArrayList<>();
        }else {
            for (NodeExecuteStep nodeExecuteStep : nodeExecuteStepList){
                if(nodeExecuteStep.getType() == ExecuteEnum.DELETE.getType()){
                    tree.remove(nodeExecuteStep.getValue());
                }else if(nodeExecuteStep.getType() == ExecuteEnum.INSERT.getType()){

                    ArrayNode arrayNode = new ArrayNode();
                    arrayNode.setCid(nodeExecuteStep.getCid());
                    arrayNode.setValue(nodeExecuteStep.getValue());
                    arrayNode.setValueTextId(nodeExecuteStep.getValueTextId());
                    tree.insert(arrayNode , nodeExecuteStep.getValue());
                }
            }
        }


        RBTNode temp ;
        Map<String , RBTNode> treeMap = new HashMap<>(arrayNodeList.size());

        //层序遍历
        Queue<RBTNode> nodeQueue = new ArrayDeque<>();
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

        tree.setNodeMap(treeMap);


        //开启获取动画的功能
        tree.setAnimationFlag(true);

        //新增节点
        RBTNode<Integer> rbNode=new RBTNode<Integer>(node ,RED,null,null,null);
        rbNode.setCid(arrayNode.getCid());
        rbNode.setNodeTextId(arrayNode.getValueTextId());



        //生产对象，顺便赋值 宽，高， buffer 等
        Group group = this.getSingleAndTreeConstruct(tree , rbNode , treeMap) ;


        //插入节点
        tree.insert(arrayNode , rbNode);

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

    @Override
    public TreeConstruct delRbNode(List<ArrayNode> arrayNodeList, int node,List<NodeExecuteStep> nodeExecuteStepList) {


        RBTree<Integer> tree=new RBTree<Integer>();


        /**
         * 设置执行步骤的文案
         */
        tree.setStepExecute(executionSequenceService.getExecutionSequenceByType(RBTreeStepConstant.TYPE));

        //构造树结构
        for(ArrayNode arrayNode : arrayNodeList) {
            tree.insert(arrayNode , arrayNode.getValue());
        }

        /***
         * 处理操作历史的逻辑，此处应该只有删除
         */
        if (CollectionUtils.isEmpty(nodeExecuteStepList)){
            nodeExecuteStepList = new ArrayList<>();
        }else {
            for (NodeExecuteStep nodeExecuteStep : nodeExecuteStepList){
                if(nodeExecuteStep.getType() == ExecuteEnum.DELETE.getType()){
                    tree.remove(nodeExecuteStep.getValue());
                }else if(nodeExecuteStep.getType() == ExecuteEnum.INSERT.getType()){

                    ArrayNode arrayNode = new ArrayNode();
                    arrayNode.setCid(nodeExecuteStep.getCid());
                    arrayNode.setValue(nodeExecuteStep.getValue());
                    arrayNode.setValueTextId(nodeExecuteStep.getValueTextId());
                    tree.insert(arrayNode , nodeExecuteStep.getValue());
                }
            }
        }

        /***
         * 层序遍历树生成结构 ,赋值宽和高
         */
        getArrayAndTreeConstruct(arrayNodeList , tree);

        //开启获取动画的功能
        tree.setAnimationFlag(true);
        RBTNode delNode = tree.remove(node);



        TreeConstruct treeConstruct = new TreeConstruct();
        treeConstruct.setArrayLists(arrayNodeList);

        //设置动画
        treeConstruct.setAt(tree.getAnimationTotal());

        //增加删除节点
        NodeExecuteStep nodeExecuteStep = new NodeExecuteStep();
        nodeExecuteStep.setValue(node);
        nodeExecuteStep.setCid(delNode.getCid());
        nodeExecuteStep.setValueTextId(delNode.getNodeTextId());
        nodeExecuteStep.setType(ExecuteEnum.DELETE.getType());
        nodeExecuteStepList.add(nodeExecuteStep);
        treeConstruct.setExecuteSteps(nodeExecuteStepList);


        return treeConstruct;
    }


    /***
     * 根据数组构建列表
     * @param arrays
     * @return
     */
    private List<ArrayNode> createArrayNode(int[] arrays){
        List<ArrayNode> arrayNodes = new ArrayList<>();
        ArrayNode arrayNode ;
        for (int i : arrays){
//            arrayNode = new ArrayNode(JUUID.getUUID() ,JUUID.getUUID() , JUUID.getUUID() , new Integer(i)) ;
            arrayNode = new ArrayNode(JUUID.getUUID() ,"array-"+i +"-"+JUUID.getUUID() , JUUID.getUUID() , new Integer(i)) ;
            arrayNodes.add(arrayNode);
        }
        return arrayNodes;
    }

    /***
     * 返回节点
     * @param array
     * @return
     */
    private ArrayNode createSingleArrayNode(int array){
        return new ArrayNode(JUUID.getUUID() ,"array-"+array +"-"+JUUID.getUUID() , JUUID.getUUID() , new Integer(array));
//        return new ArrayNode(JUUID.getUUID() ,JUUID.getUUID() , JUUID.getUUID() , new Integer(array));
    }








    /***
     * 层序遍历构建数组和树结构
     * @param rbTree
     * @return
     */
    private Group getArrayAndTreeConstruct(List<ArrayNode> arrayNodes , RBTree rbTree) {
        Group all = new Group();

//        all.setMl(500);
        all.setMt(defaultMt);
        all.setCompose(ComponentCompositeEnum.NONE.getType());

        Group array = new Group();
        array.setCompose(ComponentCompositeEnum.HORIZONTAL.getType());
        int ml=50;
        for (ArrayNode arrayNode : arrayNodes){
            ml = ml + 50;
            array.addChild(new RectAndText(arrayNode.getRid() , arrayNode.getValue(), 30,30 ,ml,"white"));
        }

        all.addChild(array);


        int level = 1;
        int buffer = defaultBuffer;
        int defaultML = 600 ;


        //层序遍历
        Queue<RBTNode> nodeQueue = new ArrayDeque<>();
        //每层节点的x队列。
//        Queue<Integer> nodeWidthQueue = new ArrayDeque<>();
        //设置默认宽度
        rbTree.getRoot().setWidth(defaultML);
        nodeQueue.add(rbTree.getRoot());
//        nodeWidthQueue.add(defaultML);
        RBTNode temp ;
        int currentLevel = 1;    //记录当前层需要打印的节点的数量
        int nextLevel = 0;//记录下一层需要打印的节点的数量
        int currentIndex = 0 ;//当前层节点的序号
        Group group = new Group();
        Integer parentWidth = 0;
        Integer left ;
        while ((temp = nodeQueue.poll()) != null) {
            currentIndex = currentIndex + 1 ;


            Circle circle = create(group , temp , temp.getWidth() , 0);
            if (temp.getLeft() != null) {
                //赋值左节点的宽度
                temp.getLeft().setLevel(level + 1);
                temp.getLeft().setWidth( temp.getWidth() - buffer );
                temp.getLeft().setBuffer(buffer);
                nodeQueue.add(temp.getLeft());
                nextLevel++;
            }
            if (temp.getRight() != null) {
                //赋值右节点 的宽度
                temp.getRight().setLevel(level + 1);
                temp.getRight().setWidth(temp.getWidth() + buffer);
                temp.getRight().setBuffer(buffer);
                nodeQueue.add(temp.getRight());
                nextLevel++;
            }

            currentLevel--;
            if(currentLevel == 0) {
                level = level + 1;
                currentIndex = 0;
                group.setMt(height);
                buffer =  new Double(buffer*rate).intValue();
                group.setCompose(ComponentCompositeEnum.NONE.getType());
                all.addChild(group);
                group = new Group();
                currentLevel = nextLevel;
                nextLevel = 0;


                if(temp.getBuffer()==null){
                    temp.setBuffer(defaultBuffer);
                }
                if(temp.getWidth()== null){
                    temp.setWidth(defaultML);
                }
                if(temp.getLevel() == null) {
                    temp.setLevel(1);
                }
            }
        }
        return all;
    }


    /****
     * 获取单个元素的结构和
     * @param rbtNode
     * @return
     */
    private Group getSingleAndTreeConstruct(RBTree rbTree , RBTNode rbtNode , Map<String , RBTNode> rbtNodeMap) {
        Group all = new Group();

        RBTNode parent = rbtNode.getParent();
        //找寻插入节点
        if(parent==null){
            parent = rbTree.findInsertNode(rbtNode);
        }
        RBTNode mapParentNode = rbtNodeMap.get(parent.getCid());
        //获取缓冲数值
        int buffer = mapParentNode.getBuffer();
        if(mapParentNode.getLevel() > 1){
            buffer =  new Double(buffer*rate).intValue();
        }
        int width = mapParentNode.getWidth();


        int cmp = rbtNode.getKey().compareTo(parent.getKey());
        if (cmp < 0){
            width = width - buffer;
        }
        else{
            width = width + buffer ;
        }
        if(parent.getLeft() != null){
            if(parent.getLeft().equals(rbtNode)){

            }
        }

        if (parent.getRight() != null) {
            if(parent.getRight().equals(rbtNode)){

            }
        }
        //设置宽 和 buffer
        rbtNode.setWidth(width);
        rbtNode.setBuffer(buffer);
        rbtNode.setLevel(parent.getLevel() + 1);
        //设置元
        create(all , rbtNode , width , (mapParentNode.getLevel()+1)*height + defaultMt);

        all.addChild(createLine(parent.getCid() , rbtNode.getCid() , false));

        return all;
    }

    /***
     * 创建圆
     * @param group
     * @param node
     */
    public Circle create(Group group , RBTNode node , Integer x , Integer y) {

        Circle circle = new Circle( node.getCid(), r ,"blue");

        if(node.isColor() == RBTreeColor.RED){
            circle.setF("red");
        }else {
            circle.setF("black");
        }

//        circle.setH(DisplayEnum.NONE.getContent());
        circle.setX(x);
        circle.setY(y);
        Text text = new Text(node.getNodeTextId() , x,y, node.getKey().toString() , ShowTextPositionEnum.MIDDLE.getCode());
        text.setF("white");
//        text.setH(DisplayEnum.NONE.getContent());
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
    private Group createLineConstruct(Group g , RBTNode<Integer> root){
        if(root == null){
            return g;
        }

        if (root.getLeft() != null) {
            //左节点的线 , sid 是父节点，   tid是子节点
            g.addChild(createLine(root.getCid() , root.getLeft().getCid() , false));
        }
        if (root.getRight() != null) {
            //右节点的线, sid 是父节点，   tid是子节点
            g.addChild(createLine(root.getCid() , root.getRight().getCid() , false));
        }
        createLineConstruct(g, root.getLeft());
        createLineConstruct(g, root.getRight());
        return g ;
    }


}
