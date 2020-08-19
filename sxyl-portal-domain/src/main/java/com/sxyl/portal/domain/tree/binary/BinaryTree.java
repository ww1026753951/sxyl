package com.sxyl.portal.domain.tree.binary;

import com.sxyl.portal.domain.constant.*;
import com.sxyl.portal.domain.constant.tree.CommonTreeConstant;
import com.sxyl.portal.domain.d.*;
import com.sxyl.portal.domain.graph.*;
import com.sxyl.portal.domain.sort.ArrayNode;
import com.sxyl.portal.domain.tree.BaseTree;
import com.sxyl.portal.domain.tree.BinaryTreeNode;
import com.sxyl.portal.domain.tree.rb.RBTNode;
import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * @author: create by wei.wang
 * @version: v1.0
 * @description: com.sxyl.portal.domain.tree.binary
 * @date:2020/4/5
 */
public class BinaryTree extends BaseTree {
    //表示根节点
    private BinaryTreeNode root;


    private boolean animationFlag = false;


    private AnimationTotal animationTotal = new AnimationTotal();


    /***
     * string cid 为子节点
     * BinaryTreeNode 为父级节点
     */
    private Map<String , BinaryTreeNode> widthMap = new HashMap<>();

    public BinaryTree(){
    }

    public BinaryTree(BinaryTreeNode root){
        this.root=root;
    }

    public BinaryTreeNode getRoot(){
        return this.root;
    }

    //查找节点
    public BinaryTreeNode find(int key) {
        BinaryTreeNode current = root;

        List<String> findIds = new ArrayList<>();
        if(animationFlag){
            findIds.add(root.getCid());
            ChangeColor changeColor = new ChangeColor(ColorEnum.GRAY.getHtmlCode() , root.getCid()) ;
            animationTotal.addComponent(changeColor);
        }
        while(current != null){
            if(current.getData() > key){//当前值比查找值大，搜索左子树
                current = current.getLeftNode();
                if(current!=null && animationFlag){
                    findIds.add(current.getCid());
                    ChangeColor changeColor = new ChangeColor(ColorEnum.GRAY.getHtmlCode() , current.getCid()) ;
                    animationTotal.addComponent(changeColor);
                }
            }else if(current.getData() < key){//当前值比查找值小，搜索右子树
                current = current.getRightNode();

                if(current != null && animationFlag){
                    findIds.add(current.getCid());
                    ChangeColor changeColor = new ChangeColor(ColorEnum.GRAY.getHtmlCode() , current.getCid()) ;
                    animationTotal.addComponent(changeColor);

                }
            }else{
                if(animationFlag){
                    findIds.add(current.getCid());
                    ChangeColor changeColor = new ChangeColor(ColorEnum.RED.getHtmlCode() , current.getCid()) ;
                    animationTotal.addComponent(changeColor);
                    resetColorByList(findIds);
                }
                return current;
            }
        }
        resetColorByList(findIds);
        return null;//遍历完整个树没找到，返回null
    }

    private void resetColorByList(List<String> aids ){

        if (animationFlag){
            ChangeColor changeColor = new ChangeColor() ;
            changeColor.setTotalColor(ColorEnum.WHITE.getHtmlCode());
            String[] aidsArray = aids.toArray(new String[aids.size()]);
            changeColor.setIds(aidsArray);
            animationTotal.addComponent(changeColor);
        }
    }


    //插入节点
    public boolean insert(int data, String valueTextId, String cid) {

        List<String> findIds = new ArrayList<>();
        if(animationFlag){

            findIds.add(root.getCid());
            ChangeColor changeColor = new ChangeColor(ColorEnum.GRAY.getHtmlCode() , root.getCid()) ;
            animationTotal.addComponent(changeColor);
        }

        BinaryTreeNode newNode = new BinaryTreeNode();
        newNode.setData(data);

        newNode.setNodeText(Integer.valueOf(data).toString());

        newNode.setNodeTextId(valueTextId);
        newNode.setCid(cid);


        if(root == null){//当前树为空树，没有任何节点
            root = newNode;
            return true;
        }else{
            BinaryTreeNode current = root;
            BinaryTreeNode parentNode = null;
            while(current != null){
                parentNode = current;
                boolean left = true;
                if(current.getData() > data){//当前值比插入值大，搜索左子节点
                    current = current.getLeftNode();
                    if(current == null){//左子节点为空，直接将新值插入到该节点
                        newNode.setParentNodeCid(parentNode.getCid());
                        newNode.setNodeType(LeafTypeEnum.LEFT.getType());
                        parentNode.setLeftNode(newNode);

                        //设置插入动画
                        insertAnimation(findIds , newNode);

                        return true;
                    }
                }else{
                    left = false;
                    current = current.getRightNode();
                    if(current == null){//右子节点为空，直接将新值插入到该节点
                        newNode.setParentNodeCid(parentNode.getCid());
                        newNode.setNodeType(LeafTypeEnum.RIGHT.getType());
                        parentNode.setRightNode(newNode);
                        //设置插入动画
                        insertAnimation(findIds , newNode);

                        return true;
                    }
                }
                if(animationFlag && current != null){
                    Map<String , String> nodeMap= new HashMap<>();
                    nodeMap.put(CommonTreeConstant.NEW_NODE, new Integer(newNode.getData()).toString());
                    nodeMap.put(RBTreeStepConstant.NODE , new Integer(parentNode.getData()).toString());
                    findIds.add(current.getCid());
                    ChangeColor changeColor = new ChangeColor(ColorEnum.GRAY.getHtmlCode() , current.getCid()) ;
                    if(left){
                        //文案
                        changeColor.setAd(super.getDesc(BinaryTreeStepConstant.REPLACE_FIND_LEFT_NODE_COLOR, nodeMap ));
                    }else {
                        changeColor.setAd(super.getDesc(BinaryTreeStepConstant.REPLACE_FIND_RIGHT_NODE_COLOR, nodeMap ));
                    }
                    animationTotal.addComponent(changeColor);
                }

            }

        }
        return false;
    }

    private void insertAnimation(List<String> aids ,BinaryTreeNode binaryTreeNode){

        if (animationFlag){
            ChangeColor changeColor = new ChangeColor() ;
            changeColor.setTotalColor(ColorEnum.WHITE.getHtmlCode());
            String[] aidsArray = aids.toArray(new String[aids.size()]);
            changeColor.setIds(aidsArray);
            animationTotal.addComponent(changeColor);

            String lineId = binaryTreeNode.getParentNodeCid() +"-"+ binaryTreeNode.getCid();

            animationTotal.addComponent(new Show(binaryTreeNode.getCid(),binaryTreeNode.getNodeTextId(),lineId));
        }
    }


    //插入节点
    public BinaryTreeNode findInsertNode(int data) {

        if(root == null){//当前树为空树，没有任何节点
            return root;
        }else{
            BinaryTreeNode current = root;
            BinaryTreeNode parentNode = null;
            while(current != null){
                parentNode = current;
                if(current.getData() > data){//当前值比插入值大，搜索左子节点
                    current = current.getLeftNode();
                    if(current == null){//左子节点为空，直接将新值插入到该节点
                        return parentNode;
                    }
                }else{
                    current = current.getRightNode();
                    if(current == null){//右子节点为空，直接将新值插入到该节点
                        return parentNode;
                    }
                }
            }
        }
        return null;
    }

    //中序遍历
    public void infixOrder(BinaryTreeNode current){
        if(current != null){
            infixOrder(current.getLeftNode());

            if(animationFlag){
                //变色
                ChangeColor changeColor = new ChangeColor("red",current.getCid()) ;
                animationTotal.addComponent(changeColor);

            }
            //变色
//            System.out.print(current.getData()+" ");
            infixOrder(current.getRightNode());
        }
    }

    //前序遍历
    public void preOrder(BinaryTreeNode current){
        if(current != null){


            if(animationFlag){
                //设置颜色为红色 start
                ChangeColor changeColor = new ChangeColor("red",current.getCid()) ;
                animationTotal.addComponent(changeColor);
                //设置颜色为红色 end
            }

//            System.out.print(current.getData()+" ");
            preOrder(current.getLeftNode());
            preOrder(current.getRightNode());
        }
    }

    //后序遍历
    public void postOrder(BinaryTreeNode current){
        if(current != null){
            postOrder(current.getLeftNode());
            postOrder(current.getRightNode());

            if(animationFlag){
                //设置颜色为红色 start
                ChangeColor changeColor = new ChangeColor("red",current.getCid()) ;
                animationTotal.addComponent(changeColor);
                //设置颜色为红色 end

            }

//            System.out.print(current.getData()+" ");
        }
    }
    //找到最大值
    public BinaryTreeNode findMax(){
        BinaryTreeNode current = root;
        BinaryTreeNode maxNode = current;
        while(current != null){
            maxNode = current;
            current = current.getRightNode();
        }
        return maxNode;
    }
    //找到最小值
    public BinaryTreeNode findMin(){
        BinaryTreeNode current = root;
        BinaryTreeNode minNode = current;
        while(current != null){
            minNode = current;
            current = current.getLeftNode();
        }
        return minNode;
    }

    public BinaryTreeNode delete(int key) {

        List<String> findIds = new ArrayList<>();

        BinaryTreeNode current = root;
        BinaryTreeNode parent = root;
        boolean isLeftChild = false;

        if(animationFlag){
            findIds.add(root.getCid());
            ChangeColor changeColor = new ChangeColor(ColorEnum.GRAY.getHtmlCode() , root.getCid()) ;
            animationTotal.addComponent(changeColor);
        }

        //查找删除值，找不到直接返回false
        while(current.getData() != key){
            parent = current;
            if(current.getData() > key){
                isLeftChild = true;
                current = current.getLeftNode();
                if(current!=null && animationFlag){
                    findIds.add(current.getCid());
                    ChangeColor changeColor = new ChangeColor(ColorEnum.GRAY.getHtmlCode() , current.getCid()) ;
                    animationTotal.addComponent(changeColor);
                }
            }else{
                isLeftChild = false;
                current = current.getRightNode();
                if(current!=null && animationFlag){
                    findIds.add(current.getCid());
                    ChangeColor changeColor = new ChangeColor(ColorEnum.GRAY.getHtmlCode() , current.getCid()) ;
                    animationTotal.addComponent(changeColor);
                }
            }
            if(current == null){
                return null;
            }
        }



        if(animationFlag){
            //设置颜色为红色 start
            ChangeColor changeColor = new ChangeColor("red",current.getCid()) ;
            animationTotal.addComponent(changeColor);
        }


        List<GraphComponent> list = new ArrayList<>();

        //如果当前节点没有子节点
        if(current.getLeftNode() == null && current.getRightNode() == null){
            if(current == root){
                root = null;
            }else if(isLeftChild){
                parent.setLeftNode(null);
            }else{
                parent.setRightNode(null);
            }
            //删除元素
            removeCircle(current , parent);

            if (animationFlag){
                resetColorByList(findIds);
            }
            return current;
            //当前节点有一个子节点，右子节点
        }else if(current.getLeftNode() == null && current.getRightNode() != null){
            ChangeAttr changeAttr = new ChangeAttr();
            List<ChangeAttrDetail> changeList = new ArrayList<>();

            if(animationFlag){
                //删除元素
                removeCircle(current , parent);
                int lyY = compute(current.getLevel());
                //增加y圆的动画
                super.addCircleAndTextAnimation(list,current.getRightNode().getCid(), current.getRightNode().getNodeTextId()
                        ,current.getWidth() , lyY);
                //增加 x , y 线的动画
                super.addLineAnimation(list ,current.getCid(),current.getRightNode().getCid(), parent.getCid() , current.getRightNode().getCid(),
                        parent.getWidth() , compute(parent.getLevel()) + r  ,current.getWidth() , compute(current.getLevel()) - r
                );
                addChangeLineId(changeList , current.getCid() , current.getRightNode().getCid() , parent.getCid(),current.getRightNode().getCid());

                //赋值子节点的动画
                addChildAnimation(list,current.getRightNode(), current ,false);

                MultiMove multiMove = new MultiMove();
                multiMove.setGcs(list);
                animationTotal.addComponent(multiMove);
                changeAttr.setList(changeList);
                animationTotal.addComponent(changeAttr);
            }

//            if(current == root){
//                root = current.getRightNode();
//            }else if(isLeftChild){
//
//                parent.setLeftNode(current.getLeftNode());
//            }else{
//                parent.setRightNode(current.getRightNode());
//            }

            if(current == root){
                root = current.getRightNode();
            }else if(isLeftChild){
                parent.setLeftNode(current.getRightNode());
            }else{
                parent.setRightNode(current.getRightNode());
            }

            if (animationFlag){
                resetColorByList(findIds);
            }
            return current;
            //当前节点有一个子节点，左子节点
        }else if(current.getLeftNode() != null && current.getRightNode() == null){
            ChangeAttr changeAttr = new ChangeAttr();
            List<ChangeAttrDetail> changeList = new ArrayList<>();


            if(animationFlag){
                //删除元素
                removeCircle(current , parent);
                int lyY = compute(current.getLevel());
                //增加y圆的动画
                super.addCircleAndTextAnimation(list,current.getLeftNode().getCid(), current.getLeftNode().getNodeTextId()
                        ,current.getWidth() , lyY);

                //增加 x , y 线的动画
                super.addLineAnimation(list ,current.getCid(),current.getLeftNode().getCid(), parent.getCid() , current.getLeftNode().getCid(),
                        parent.getWidth() , compute(parent.getLevel()) + r  ,current.getWidth() , compute(current.getLevel()) - r
                );

                addChangeLineId(changeList , current.getCid() , current.getLeftNode().getCid() , parent.getCid(),current.getLeftNode().getCid());

                //赋值子节点的动画
                addChildAnimation(list,current.getLeftNode(), current,false);
                MultiMove multiMove = new MultiMove();
                multiMove.setGcs(list);
                animationTotal.addComponent(multiMove);
                changeAttr.setList(changeList);
                animationTotal.addComponent(changeAttr);
            }
            if(current == root){
                root = current.getLeftNode();
            }else if(isLeftChild){
                parent.setLeftNode(current.getLeftNode());
            }else{
                parent.setRightNode(current.getLeftNode());
            }
            if (animationFlag){
                resetColorByList(findIds);
            }
            return current;
        }else{


            if(animationFlag){
                //删除元素
                removeCircle(current , parent);
            }

            //当前节点存在两个子节点
            //寻找中序后继节点,然后将 中序后继节点修改为删除节点，
            BinaryTreeNode successor = getSuccessor(current, parent);



            //todo
            if(current == root){
                root= successor;
            }else if(isLeftChild){
                parent.setLeftNode(successor);
            }else{
                parent.setRightNode(successor);
            }
            successor.setLeftNode(current.getLeftNode());

            if (animationFlag){
                resetColorByList(findIds);
            }
            return current;
        }
//        return null;

    }

    //重置颜色
    public void resetColor(List<ArrayNode> list){
        ChangeColor changeColor = new ChangeColor() ;
        String[] arrayList = new String[list.size()];
        for (int i = 0 ; i < list.size(); i++) {
            arrayList[i] = list.get(i).getCid();
        }

        changeColor.setIds(arrayList);
        changeColor.setTotalColor("white");


        animationTotal.addComponent(changeColor);
    }

    public BinaryTreeNode getSuccessor(BinaryTreeNode delNode ,BinaryTreeNode delParentNode){

        List<GraphComponent> list = new ArrayList<>();

        List<ChangeAttrDetail> changeList = new ArrayList<>();
        ChangeAttr changeAttr = new ChangeAttr();

        BinaryTreeNode successorParent = delNode;
        BinaryTreeNode successor = delNode;
        BinaryTreeNode current = delNode.getRightNode();
        while(current != null){
            successorParent = successor;
            successor = current;
            current = current.getLeftNode();
        }

        if (animationFlag){
            //变色
            ChangeColor changeColor = new ChangeColor("blue",successor.getCid()) ;
            animationTotal.addComponent(changeColor);

        }

        //后继节点不是删除节点的右子节点，将后继节点替换删除节点
        if(successor != delNode.getRightNode()){

            if (animationFlag   ){


                //替换节点挪到上面
                super.addCircleAndTextAnimation(list,successor.getCid(), successor.getNodeTextId()
                        ,delNode.getWidth() , compute(delNode.getLevel()));

                //增加 x , y 线的动画
                super.addLineAnimation(list ,successorParent.getCid(),successor.getCid(), delParentNode.getCid() , successor.getCid(),
                        delParentNode.getWidth() , compute(delParentNode.getLevel()) + r  ,delNode.getWidth() , compute(delNode.getLevel()) - r
                );

                addChangeLineId(changeList , successorParent.getCid(),successor.getCid() ,  delParentNode.getCid() , successor.getCid());



                if(successor.getRightNode() != null){

                    //
                    super.addCircleAndTextAnimation(list,successor.getRightNode().getCid(), successor.getRightNode().getNodeTextId()
                            ,successor.getWidth() , compute(successor.getLevel()));
//
//                    //增加 x , y 线的动画
                    super.addLineAnimation(list ,successor.getCid(),successor.getRightNode().getCid(), successorParent.getCid() , successor.getRightNode().getCid(),
                            successorParent.getWidth() , compute(successorParent.getLevel()) + r  ,successor.getWidth() , compute(successor.getLevel()) - r
                    );
//
                    addChangeLineId(changeList , successor.getCid(),successor.getRightNode().getCid() ,  successorParent.getCid() , successor.getRightNode().getCid());


                }

                //赋值子节点的动画
                addChildAnimation(list,successor.getRightNode(), successor ,false);

            }



            //将替换节点的右子节点赋值到 替换节点的父级左侧节点
            successorParent.setLeftNode(successor.getRightNode());
            successor.setRightNode(delNode.getRightNode());

        //如果删除节点为右子节点
        }else {
            if (animationFlag   ){
                //替换节点挪到上面
                super.addCircleAndTextAnimation(list,successor.getCid(), successor.getNodeTextId()
                        ,delNode.getWidth() , compute(delNode.getLevel()));
                //增加 x , y 线的动画
                super.addLineAnimation(list ,successorParent.getCid(),successor.getCid(), delParentNode.getCid() , successor.getCid(),
                        delParentNode.getWidth() , compute(delParentNode.getLevel()) + r  ,delNode.getWidth() , compute(delNode.getLevel()) - r
                );

                addChangeLineId(changeList , successorParent.getCid(),successor.getCid() ,  delParentNode.getCid() , successor.getCid());


                //todo

                if (successor.getRightNode() != null){
                    //赋值子节点的动画
                    addChildAnimation(list,successor.getRightNode(), successor ,false);


                    //增加 x , y 线的动画
                    super.addLineAnimation(list ,successor.getCid(),successor.getRightNode().getCid(), null ,null,
                            successorParent.getWidth() , compute(successorParent.getLevel()) + r  ,successor.getWidth() , compute(successor.getLevel()) - r
                    );

                    if(delNode.getLeftNode() !=null){

                        addChangeLineId(changeList , delNode.getCid(),delNode.getLeftNode().getCid() ,  successor.getCid() , delNode.getLeftNode().getCid());
                    }

                }

            }




        }



        if (animationFlag){
            //移动元素
            MultiMove multiMove = new MultiMove();
            multiMove.setGcs(list);
            animationTotal.addComponent(multiMove);
            changeAttr.setList(changeList);
            animationTotal.addComponent(changeAttr);
            //变色
            ChangeColor changeColor = new ChangeColor(ColorEnum.WHITE.getHtmlCode(),successor.getCid()) ;
            animationTotal.addComponent(changeColor);

        }

        return successor;
    }

    public static void main(String[] args) {
        BinaryTree bt = new BinaryTree();
        bt.insert(50, UUID.randomUUID().toString(), UUID.randomUUID().toString());
        bt.insert(20, UUID.randomUUID().toString(), UUID.randomUUID().toString());
        bt.insert(80, UUID.randomUUID().toString(), UUID.randomUUID().toString());
        bt.insert(10, UUID.randomUUID().toString(), UUID.randomUUID().toString());
        bt.insert(30, UUID.randomUUID().toString(), UUID.randomUUID().toString());
        bt.insert(60, UUID.randomUUID().toString(), UUID.randomUUID().toString());
        bt.insert(90, UUID.randomUUID().toString(), UUID.randomUUID().toString());
        bt.insert(25, UUID.randomUUID().toString(), UUID.randomUUID().toString());
        bt.insert(85, UUID.randomUUID().toString(), UUID.randomUUID().toString());
        bt.insert(100, UUID.randomUUID().toString(), UUID.randomUUID().toString());
//        bt.delete(10);//删除没有子节点的节点
//        bt.delete(30);//删除有一个子节点的节点
//        bt.delete(80);//删除有两个子节点的节点
        System.out.println(bt.findMax().getData());
        System.out.println(bt.findMin().getData());
        System.out.println(bt.find(100));
        System.out.println(bt.find(200));

    }

    public void setRoot(BinaryTreeNode root) {
        this.root = root;
    }

    public AnimationTotal getAnimationTotal() {
        return animationTotal;
    }

    /***
     * 删除元素
     */
    private void removeCircle(BinaryTreeNode treeNode , BinaryTreeNode parentId){


        List<String> destroy = new ArrayList<>();
        destroy.add(treeNode.getCid());

        destroy.add(treeNode.getNodeTextId());

        //删除节点已经被放到最末端 ,所以只需要删除
        destroy.add(parentId.getCid() + "-" + treeNode.getCid());



        String[] c = destroy.toArray(new String[destroy.size()]);
        Destroy d = new Destroy(c);
//        d.setAd( this.getDesc(RBTreeStepConstant.DEL_NODE , replaceMap));
        this.animationTotal.addComponent(d);
    }

    /***
     * 增加子对象的位移动画
     * @param current 当前节点
     * @param parent  父节点
     */
    private void addChildAnimation(List<GraphComponent> list ,BinaryTreeNode current ,BinaryTreeNode parent, boolean child ){



        //如果当前节点无值,则返回
        if(current == null){
            return;
        }
//        Queue<BinaryTreeNode> nodeQueue = new ArrayDeque<>();





//        parent =  this.findInsertNode(current.getData());
//        while (parent!=null){
//            parent = this.findInsertNode(parent.getData());
//        }
//        boolean isLeft = current.getData()<parent.getData()? true:false ;

//        this.setWidthMap();

//        BinaryTreeNode deleteRoot = null ;
//        if(getWidthMap(parent.getCid()) == null){
//            setWidthMap(current.getCid() , parent);
//        }
//
//        /***
//         * 先根据当前节点， 从map找到根级节点
//         */
//        while (getWidthMap(current.getCid()) == null){
//            deleteRoot = getWidthMap(current.getCid());
//        }
//
//        deleteRoot.getData();

        int width = 0 ;

        if (child){

            width = super.computeWidth( current.getNodeType() , parent.getNodeType() , parent.getWidth() , parent.getBuffer()) ;
        }else {
            width = parent.getWidth();
        }


        //将新的值赋值到对象中
        current.setWidth(width);


        //设置新宽度
//        current.setWidth(width);

        int lyY = compute(parent.getLevel());
        //增加y圆的动画
        super.addCircleAndTextAnimation(list,current.getCid(), current.getNodeTextId()
                ,width , lyY);



        if(current.getLeftNode() != null){
            //增加 x , y 线的动画
            super.addLineAnimation(list ,current.getCid(),current.getLeftNode().getCid(), null ,null,
                    width , compute(parent.getLevel()) + r  ,current.getWidth()-current.getBuffer() , compute(current.getLevel()) - r
            );

            addChildAnimation(list ,current.getLeftNode() , current ,true);
        }
        if (current.getRightNode() != null ){
            //增加 x , y 线的动画
            super.addLineAnimation(list ,current.getCid(),current.getRightNode().getCid(), null ,null,
                    width , compute(parent.getLevel()) + r  ,current.getWidth()+current.getBuffer() , compute(current.getLevel()) - r
            );

            addChildAnimation(list ,current.getRightNode() , current ,true);
        }
    }

    public boolean isAnimationFlag() {
        return animationFlag;
    }

    public void setAnimationFlag(boolean animationFlag) {
        this.animationFlag = animationFlag;
    }

    public BinaryTreeNode getWidthMap(String cid) {
        return widthMap.get(cid);
    }

    public void setWidthMap(String cid , BinaryTreeNode width) {
        this.widthMap.put(cid,width);
    }
}
