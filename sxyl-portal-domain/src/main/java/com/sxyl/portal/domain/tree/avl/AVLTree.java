package com.sxyl.portal.domain.tree.avl;


import com.sxyl.portal.common.JUUID;
import com.sxyl.portal.domain.constant.ChangeAttrEnum;
import com.sxyl.portal.domain.constant.RBTreeStepConstant;
import com.sxyl.portal.domain.d.*;
import com.sxyl.portal.domain.graph.Circle;
import com.sxyl.portal.domain.graph.GraphComponent;
import com.sxyl.portal.domain.graph.Text;
import com.sxyl.portal.domain.sort.ArrayNode;
import com.sxyl.portal.domain.tree.BaseTree;
import com.sxyl.portal.domain.tree.rb.RBTNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: create by wei.wang
 * @version: v1.0
 * @description: com.sxyl.portal.controller.tree
 * @date:2020/6/8
 */
public class AVLTree extends BaseTree {

    AVLTreeNode root;

    private boolean animationFlag = false;


    private AnimationTotal animationTotal = new AnimationTotal();


    private Map<String ,AVLTreeNode> nodeMap = new HashMap<>();

    // A utility function to get the height of the tree
    public int height(AVLTreeNode N) {
        if (N == null)
            return 0;

        return N.getHeight();
    }

    // A utility function to get maximum of two integers
    public int max(int a, int b) {
        return (a > b) ? a : b;
    }

    public AVLTreeNode rightRotate(AVLTreeNode y) {

        //右旋转
        rightAnimation(y);

        AVLTreeNode x = y.getLeft();
        AVLTreeNode T2 = x.getRight();

        // Perform rotation
        x.setRight(y);
        y.setLeft(T2);

        y.setParent(x);
        if(T2!=null){
            T2.setParent(y);

        }

        // Update heights
        y.setHeight(max(height(y.getLeft()), height(y.getRight())) + 1);
        x.setHeight(max(height(x.getLeft()), height(x.getRight())) + 1);

        // Return new root
        return x;
    }

    public AVLTreeNode leftRotate(AVLTreeNode x) {
        //左旋转
        leftAnimation(x);

        AVLTreeNode y = x.getRight();
        AVLTreeNode T2 = y.getLeft();

        // Perform rotation
        y.setLeft(x);
        x.setRight(T2);

        //ww
        x.setParent(y);
        if (T2 != null){
            T2.setParent(x);

        }

        //  Update heights
        x.setHeight(max(height(x.getLeft()), height(x.getRight())) + 1);
        y.setHeight( max(height(y.getLeft()), height(y.getRight())) + 1);

        // Return new root
        return y;
    }

    // Get Balance factor of node N
    public int getBalance(AVLTreeNode N) {
        if (N == null)
            return 0;

        return height(N.getLeft()) - height(N.getRight());
    }

    public AVLTreeNode insert(AVLTreeNode node, int key,ArrayNode arrayNode ) {

        /* 1.  Perform the normal BST insertion */
        if (node == null){
            return (new AVLTreeNode(key , arrayNode));
        }

        if (key < node.getKey()){
            AVLTreeNode avlTreeNode = insert(node.getLeft(), key ,arrayNode);

            avlTreeNode.setParent(node);
            node.setLeft(avlTreeNode);

        }
        else if (key > node.getKey()){
            AVLTreeNode avlTreeNode =insert(node.getRight(), key ,arrayNode);
            avlTreeNode.setParent(node);
            node.setRight(avlTreeNode);

        }
        else { // Duplicate keys not allowed
            return node;
        }

        /* 2. Update height of this ancestor node */
        node.setHeight(1 + max(height(node.getLeft()),height(node.getRight())));

        /* 3. Get the balance factor of this ancestor
              node to check whether this node became
              unbalanced */
        int balance = getBalance(node);

        // If this node becomes unbalanced, then there
        // are 4 cases Left Left Case
        if (balance > 1 && key < node.getLeft().getKey()){


            return rightRotate(node);

        }


        // Right Right Case
        if (balance < -1 && key > node.getRight().getKey()){


            return leftRotate(node);
        }


        // Left Right Case
        if (balance > 1 && key > node.getLeft().getKey()) {
            node.setLeft(leftRotate(node.getLeft()));
            return rightRotate(node);
        }

        // Right Left Case
        if (balance < -1 && key < node.getRight().getKey()) {
            node.setRight(rightRotate(node.getRight()));
            return leftRotate(node);
        }

        /* return the (unchanged) node pointer */
        return node;
    }

    /***
     * 查询插入节点的父级节点
     * @param key
     * @param parent
     * @return
     */
    public AVLTreeNode findInserParent(AVLTreeNode node,AVLTreeNode parent, int key){
        if (node == null){
            return parent;
        }

        if (key < node.getKey()){
            return findInserParent(node.getLeft(),node , key);
        }else if (key > node.getKey()){
            return findInserParent(node.getRight() , node , key);
        }else {
            return node;
        }

    }

    /***
     * 查询插入节点的父级节点
     * @param key
     * @return
     */
    public AVLTreeNode findDelParent(AVLTreeNode node, int key){
        if (node == null){
            return null;
        }
        if (key < node.getKey()){
            return findDelParent(node.getLeft(), key);
        }else if (key > node.getKey()){
            return findDelParent(node.getRight() ,   key);
        }else if (key == node.getKey()){
            return node;
        }else {
            return null ;
        }

    }

    public void preOrder(AVLTreeNode node) {
        if (node != null) {
            System.out.print(node.getKey() + " ");
            preOrder(node.getLeft());
            preOrder(node.getRight());
        }
    }

    /* Given a non-empty binary search tree, return the
  node with minimum key value found in that tree.
  Note that the entire tree does not need to be
  searched. */

    public AVLTreeNode minValueNode(AVLTreeNode node) {
        AVLTreeNode current = node;

        /* loop down to find the leftmost leaf */
        while (current.getLeft() != null)
            current = current.getLeft();

        return current;
    }



    public AVLTreeNode deleteNode(AVLTreeNode root, int key){



        // STEP 1: PERFORM STANDARD BST DELETE
        if (root == null){
            return root;

        }

        // If the key to be deleted is smaller than
        // the root's key, then it lies in left subtree
        if (key < root.getKey()){
            root.setLeft(deleteNode(root.getLeft(), key));

        }

            // If the key to be deleted is greater than the
            // root's key, then it lies in right subtree
        else if (key > root.getKey()){
            root.setRight(deleteNode(root.getRight(), key));
        }

            // if key is same as root's key, then this is the node
            // to be deleted
        else {
            // node with only one child or no child
            if ((root.getLeft() == null) || (root.getRight() == null)){
                AVLTreeNode temp = null;
                if (temp == root.getLeft())
                    temp = root.getRight();
                else
                    temp = root.getLeft();

                // No child case
                if (temp == null) {
                    temp = root;
                    root = null;
                }
                else{// One child case
                    root = temp; // Copy the contents of
                }
                // the non-empty child



                //属性变更
                ChangeAttr changeAttr = new ChangeAttr();
                List<ChangeAttrDetail> changeList = new ArrayList<>();
                if (animationFlag){



                    changeAttr.setDs(true);
//            replaceMap.put(RBTreeStepConstant.PLACEHOLDER_DEL_NODE , node.getKey().toString()) ;
                    changeAttr.setList(changeList);
                    animationTotal.addComponent(changeAttr);


                    //删除标记虚化
                    ChangeAttr delNodeChangeAttr = new ChangeAttr();
                    List<ChangeAttrDetail> delNodeSignList = new ArrayList<>();
//            String delNodeText = "搜索到删除节点%s,并将其标注为删除节点。";
                    //虚化 删除样式
                    ChangeAttrDetail cad = new ChangeAttrDetail(temp.getCid());
                    cad.addValue("style" , "opacity:0.1");
                    cad.setCt(ChangeAttrEnum.ADD.getType());
                    delNodeSignList.add(cad);
                    //虚化 删除样式
                    ChangeAttrDetail textCad = new ChangeAttrDetail(temp.getNodeTextId());
                    textCad.addValue("style" , "opacity:0.1");
                    textCad.setCt(ChangeAttrEnum.ADD.getType());
                    delNodeSignList.add(cad);
//            delNodeChangeAttr.setAd(this.getDesc(RBTreeStepConstant.FIND_DEL_NODE , replaceMap));
                    delNodeChangeAttr.setList(delNodeSignList);
                    animationTotal.addComponent(delNodeChangeAttr);
                    //删除元素
                    removeCircle(temp.getCid() , temp.getParent().getCid() , temp.getNodeTextId());

                }

            }
            else
            {



                // node with two children: Get the inorder
                // successor (smallest in the right subtree)
                AVLTreeNode temp = minValueNode(root.getRight());

                List<ChangeAttrDetail> changeList = new ArrayList<>();
                ChangeAttr changeAttr = new ChangeAttr();
                if (animationFlag){
                    changeAttr.setList(changeList);
                    //删除标记虚化
                    ChangeAttr delNodeChangeAttr = new ChangeAttr();
                    List<ChangeAttrDetail> delNodeSignList = new ArrayList<>();
                    //虚化 删除样式
                    ChangeAttrDetail cad = new ChangeAttrDetail(root.getCid());
                    cad.addValue("style" , "opacity:0.1");
                    cad.setCt(ChangeAttrEnum.ADD.getType());
                    delNodeSignList.add(cad);
                    //虚化 删除样式
                    ChangeAttrDetail textCad = new ChangeAttrDetail(root.getNodeTextId());
                    textCad.addValue("style" , "opacity:0.1");
                    textCad.setCt(ChangeAttrEnum.ADD.getType());
                    delNodeSignList.add(cad);
//                    delNodeChangeAttr.setAd(this.getDesc(RBTreeStepConstant.FIND_DEL_NODE , replaceMap));
                    delNodeChangeAttr.setList(delNodeSignList);
                    animationTotal.addComponent(delNodeChangeAttr);



                    //将后继结点设置为替换颜色
                    ChangeColor cc = new ChangeColor("green",temp.getCid());

                    animationTotal.addComponent(cc);


                    //修改 赋值对象
                    if(root.parent != null){
                        //修改线的id start
                        addChangeLineId(changeList ,root.parent.getCid() , root.getCid() , root.parent.getCid() , temp.getCid() );
                    }

                    if (root.left != null){
                        addChangeLineId(changeList ,root.getCid() , root.left.getCid() , temp.getCid() , root.left.getCid() );
                    }

                    if(root.right != null) {
                        addChangeLineId(changeList ,root.getCid() , root.right.getCid() , temp.getCid() , root.right.getCid() );
                    }


                    //如果删除节点  等于 替换节点(右继节点) ,
//                    if(nodeRightEqualReplace){
//                        addChangeLineId(changeList ,node.getCid() , node.right.getCid() , node.right.getCid() , node.getCid() );
//                    }else {
//                        if(node.right != null) {
//                            addChangeLineId(changeList ,node.getCid() , node.right.getCid() , replace.getCid() , node.right.getCid() );
//                        }
//                        addChangeLineId(changeList ,replace.parent.getCid() , replace.getCid() , replace.parent.getCid() , node.getCid() );
//
//                    }


                    if(temp.parent != null) {
                        //修改线的id start
                        addChangeLineId(changeList ,temp.parent.getCid() , temp.getCid() , temp.parent.getCid() , root.getCid() );

                    }

                    if(temp.left != null ){
                        addChangeLineId(changeList ,temp.getCid() , temp.left.getCid() , root.getCid() , temp.left.getCid() );
                    }
                    if(temp.right != null){
                        addChangeLineId(changeList ,temp.getCid() , temp.right.getCid() , root.getCid() , temp.right.getCid() );
                    }

                    List<GraphComponent> list = new ArrayList<>();
                    //增加圆的动画,将后继结点替换到删除节点上,  动画部分
                    this.addCircleAndTextAnimation(list,root.getCid(), root.getNodeTextId(),temp.getWidth() , compute(temp.getLevel()));
                    //将删除节点与替换节点互换位置
                    this.addCircleAndTextAnimation(list,temp.getCid(), temp.getNodeTextId(),root.getWidth() , compute(root.getLevel()));
                    MultiMove multiMove = new MultiMove();
                    multiMove.setGcs(list);


//                    multiMove.setAd(this.getDesc(RBTreeStepConstant.SWITCH_DEL_REPLACE_NODE,replaceMap));
                    this.animationTotal.addComponent(multiMove);
                    this.animationTotal.addComponent(changeAttr);

                }


//                if (animationFlag){
//
//                    List<GraphComponent> list = new ArrayList<>();
//                    //增加圆的动画,将后继结点替换到删除节点上,  动画部分
//                    this.addCircleAndTextAnimation(list,root.getCid(), root.getNodeTextId(),temp.getWidth() , compute(temp.getLevel()));
//                    //将删除节点与替换节点互换位置
//                    this.addCircleAndTextAnimation(list,temp.getCid(), temp.getNodeTextId(),root.getWidth() , compute(root.getLevel()));
//                    MultiMove multiMove = new MultiMove();
//                    multiMove.setGcs(list);
//
//
////                    multiMove.setAd(this.getDesc(RBTreeStepConstant.SWITCH_DEL_REPLACE_NODE,replaceMap));
//                    this.animationTotal.addComponent(multiMove);
//
//                    int tLevel = targetNode.getLevel();
//                    int tWidth = targetNode.getWidth();
//                    int tBuffer = targetNode.getBuffer();
//
//                    int rLevel = replace.getLevel();
//                    int rWidth = replace.getWidth();
//                    int rBuffer = replace.getBuffer();
//
//
//
//                    //刷新map
//                    refreshMap(replace.getCid() , tLevel , tWidth , tBuffer);
//                    refreshMap(node.getCid() , rLevel , rWidth , rBuffer);
//                }

                //modify by ww
                if(root.getLeft() != null) {
                    root.getLeft().setParent(temp);
                }
                if (root.getRight() != null) {
                    root.getRight().setParent(temp);
                }
                if (root.getParent() !=null){
                    if(root.getParent().getLeft() != null && root.getParent().getLeft().getKey() == root.getKey() ) {
                        root.getParent().setLeft(temp);
                    }else if(root.getParent().getRight() != null && root.getParent().getRight().getKey() == root.getKey()) {
                        root.getParent().setRight(temp);
                    }
                }

                if (temp.getParent() != null) {

                    if(temp.getParent().getLeft() != null && temp.getParent().getLeft().getKey() == temp.getKey()){

                        temp.getParent().setLeft(root);
                    }else if (temp.getParent().getRight() != null && temp.getParent().getRight().getKey() == temp.getKey()){

                        temp.getParent().setRight(root);
                    }


                }

                AVLTreeNode tempRoot = temp.parent;


                temp.setParent(root.getParent());
                root.setParent(tempRoot);

                temp.setLeft(root.getLeft());
                temp.setRight(root.getRight());
                root.setLeft(null);
                root.setRight(null);


                // Copy the inorder successor's data to this node
//                root.setKey(temp.getKey());  //dddddddd

                temp.getKey();



//                root.setWidth(temp.getWidth());
//                root.setBuffer(temp.getBuffer());
//                root.setl

                // Delete the inorder successor
                root.setRight(deleteNode(temp.getRight(), root.getKey()));
                root = temp;
            }
        }

        // If the tree had only one node then return
        if (root == null){
            return root;
        }





        // STEP 2: UPDATE HEIGHT OF THE CURRENT NODE
        root.setHeight(max(height(root.getLeft()), height(root.getRight())) + 1);

        // STEP 3: GET THE BALANCE FACTOR OF THIS NODE (to check whether
        // this node became unbalanced)
        int balance = getBalance(root);

        // If this node becomes unbalanced, then there are 4 cases
        // Left Left Case
        if (balance > 1 && getBalance(root.getLeft()) >= 0)
            return rightRotate(root);

        // Left Right Case
        if (balance > 1 && getBalance(root.getLeft()) < 0)
        {
            root.setLeft(leftRotate(root.getLeft()));
            return rightRotate(root);
        }

        // Right Right Case
        if (balance < -1 && getBalance(root.getRight()) <= 0)
            return leftRotate(root);

        // Right Left Case
        if (balance < -1 && getBalance(root.getRight()) > 0)
        {
            root.setRight(rightRotate(root.getRight()));
            return leftRotate(root);
        }

        return root;
    }


    /***
     * 删除元素
     */
    private void removeCircle(String cid , String delPid ,String textId){


        List<String> destroy = new ArrayList<>();
        destroy.add(cid);

        destroy.add(textId);

        //删除节点已经被放到最末端 ,所以只需要删除
        destroy.add(delPid + "-" + cid);
        String[] c = destroy.toArray(new String[destroy.size()]);
        Destroy d = new Destroy(c);
//        d.setAd( this.getDesc(RBTreeStepConstant.DEL_NODE , replaceMap));
        this.animationTotal.addComponent(d);
    }

    /****
     *
     * @param x
     *
     *
    x.setRight(T2);

     */
    private void leftAnimation(AVLTreeNode  x){
        if (animationFlag){

            AVLTreeNode  y = x.getRight();

            AVLTreeNode T2 = y.getLeft();

            ChangeAttr changeAttr = new ChangeAttr();
            MultiMove multiMove = new MultiMove();

            List<GraphComponent> list = new ArrayList<>();

            multiMove.setGcs(list);

            animationTotal.addComponent(multiMove);
            animationTotal.addComponent(changeAttr);


            List<ChangeAttrDetail> changeList = new ArrayList<>();
            changeAttr.setList(changeList);

            //x的基础属性
            int xWidth = x.getWidth();
            int xLevel = x.getLevel();
            int xY = compute(xLevel);

            int yWidth = y.getWidth();
            int yLevel = y.getLevel();
            int yBuffer = y.getBuffer();
            int yY =  compute(yLevel);


            int xNewLevel = xLevel + 1;
            int xNewWidth = xWidth - yBuffer;
            int xNewBuffer = y.getBuffer();
            int xNewY = compute(xNewLevel);


            int yNewLevel = yLevel -1;
            int yNewWidth = xWidth ;
            int yNewY = compute(yNewLevel);



            String ycid = y.getCid();
            String xcid = x.getCid();



            //增加x圆的动画
            this.addCircleAndTextAnimation(list,x.getCid(), x.getNodeTextId(),xNewWidth , xNewY);


            //增加y圆的动画
            this.addCircleAndTextAnimation(list,y.getCid(), y.getNodeTextId(),xWidth , xY);



            //增加 x , y 线的动画
            this.addLineAnimation(list  , xcid ,ycid,  ycid,xcid,
                     xNewWidth,xNewY - r ,yNewWidth , yNewY + r );

            /***
             * 交换 x 的父级节点到x的线的属性值
             */
            if (x.getParent() !=null){

                addChangeLineId(changeList , x.getParent().getCid() , x.getCid() , x.getParent().getCid(),y.getCid());
            }

            // x 的右侧节点的左侧节点变成 x左侧节点的右侧节点
            if (T2 !=null) {

                int T2y = compute(T2.getLevel());
                int T2Width = xNewWidth + T2.getBuffer() ;

                //增加x圆的动画
                this.addCircleAndTextAnimation(list,T2.getCid(), T2.getNodeTextId(),T2Width , T2y);

                //增加 x , y 线的动画
                this.addLineAnimation(list  , y.getCid() ,T2.getCid(),  x.getCid(), T2.getCid(),
                        xNewWidth,xNewY + r ,T2Width , T2y - r );

                //x的左节点下的双子节点
                refreshLeftRotateLeftChild(list,T2 ,T2.getLevel() ,T2Width ,T2y ,new Double(T2.getBuffer()*rate).intValue() ) ;
            }

            if (x.getLeft()!=null) {

                AVLTreeNode avlLeftTreeNode = x.getLeft() ;

                int xLeftNewBuffer = new Double(avlLeftTreeNode.getBuffer()*rate).intValue() ;
                int xLeftNewLevel = avlLeftTreeNode.getLevel() + 1;
                int xLeftNewWidth = avlLeftTreeNode.getWidth()-xLeftNewBuffer ;
                int xLeftNewY = compute(xLeftNewLevel);

                //增加x圆的动画
                this.addCircleAndTextAnimation(list,avlLeftTreeNode.getCid(), avlLeftTreeNode.getNodeTextId(),xLeftNewWidth , xLeftNewY);


                //增加 x , y 线的动画
                this.addLineAnimation(list  , x.getCid() ,x.getLeft().getCid(),  null, null,
                        x.getLeft().getWidth(),compute(x.getLeft().getLevel()) + r ,xLeftNewWidth , xLeftNewY - r );

                //x的左节点下的双子节点
                refreshLeftRotateLeftChild(list,x.getLeft() ,xLeftNewLevel ,xLeftNewWidth ,xLeftNewY ,new Double(xLeftNewBuffer*rate).intValue()) ;
            }

            if (y.getRight()!=null){

//                refreshLeftRotateRightNode(y , list);


                AVLTreeNode domRY = y.getRight();
//

                //增加圆的动画
                this.addCircleAndTextAnimation(list,domRY.getCid(), domRY.getNodeTextId(),yWidth , yY);
                //增加线的动画
                this.addLineAnimation(list ,ycid , domRY.getCid() ,null,null,
                        yNewWidth , yNewY + r , yWidth,yY - r);

//
//                if(y.getRight().getLeft() != null){
//                    y.getRight().getLeft().setBuffer( y.getBuffer());
//                }
//                if (y.getRight().getRight() != null ) {
//                    y.getRight().getRight().setBuffer( y.getBuffer());
//
//                }

                refreshLeftRotateLeftChild(list, y.getRight() , y.getLevel() , y.getWidth() , compute(y.getLevel()) ,new Double(y.getBuffer()*rate).intValue());

//                refreshLeftRotateLeftChild(list, y.getRight());

//                if (y.getRight().getRight() != null) {
//
//                    AVLTreeNode domRYRY = y.getRight().getRight();
//
//                    int domRYRYNewY = compute(domRY.getLevel());
//
//                    //增加x圆的动画
//                    this.addCircleAndTextAnimation(list,domRYRY.getCid(), domRYRY.getNodeTextId(),domRY.getWidth() , domRYRYNewY);
//
//                    //增加线的动画
//                    this.addLineAnimation(list  , domRY.getCid() ,domRYRY.getCid() ,null,null,
//                             yWidth,yY + r , domRY.getWidth() , domRYRYNewY  - r);
//
//                }
                domRY.getRight();
            }
        }
    }


    /***
     *
     * 右旋
     *   AVLTreeNode x = y.getLeft();
     AVLTreeNode T2 = x.getRight();

     // Perform rotation
     x.setRight(y);
     y.setLeft(T2);

     y.setParent(x);
     if(T2!=null){
     T2.setParent(y);

     }

     -----------------------

     左旋
     AVLTreeNode y = x.getRight();
     AVLTreeNode T2 = y.getLeft();

     // Perform rotation
     y.setLeft(x);
     x.setRight(T2);

     //ww
     x.setParent(y);
     if (T2 != null){
     T2.setParent(x);

     }





     * 右旋
     * @param y
     */
    private void rightAnimation(AVLTreeNode y){
        if (animationFlag){

            AVLTreeNode  x = y.getLeft();

            AVLTreeNode T2 = x.getRight();

            ChangeAttr changeAttr = new ChangeAttr();
            MultiMove multiMove = new MultiMove();

            List<GraphComponent> list = new ArrayList<>();

            multiMove.setGcs(list);

            animationTotal.addComponent(multiMove);
            animationTotal.addComponent(changeAttr);


            List<ChangeAttrDetail> changeList = new ArrayList<>();
            changeAttr.setList(changeList);

            //x的基础属性
            int xWidth = x.getWidth();
            int xLevel = x.getLevel();
            int xBuffer = x.getBuffer();
            int xY = compute(xLevel);

            int yWidth = y.getWidth();
            int yLevel = y.getLevel();
            int yBuffer = y.getBuffer();
            int yY =  compute(yLevel);


            int xNewLevel = xLevel - 1;
            int xNewWidth = xWidth + xBuffer;
            int xNewBuffer = y.getBuffer();
            int xNewY = compute(xNewLevel);


            int yNewLevel = yLevel + 1;
            int yNewWidth = yWidth + xBuffer;
            int yNewY = compute(yNewLevel);



            String ycid = y.getCid();
            String xcid = x.getCid();



            //增加x圆的动画
            this.addCircleAndTextAnimation(list,x.getCid(), x.getNodeTextId(),xNewWidth , xNewY);


            //增加y圆的动画
            this.addCircleAndTextAnimation(list,y.getCid(), y.getNodeTextId(),yNewWidth , yNewY);



            //增加 x , y 线的动画
            this.addLineAnimation(list  ,ycid, xcid ,  xcid,ycid,
                    yNewWidth,yNewY - r ,xNewWidth , xNewY + r );

//            /***
//             * 交换 x 的父级节点到x的线的属性值
//             */
            if (x.getParent() !=null && y.getParent() != null){

                addChangeLineId(changeList , y.getParent().getCid() , y.getCid() , y.getParent().getCid(),x.getCid());
//                addChangeLineId(changeList , x.getParent().getCid() , x.getCid() , y.getParent().getCid(),x.getCid());
            }

            // x 的右侧节点的左侧节点变成 x左侧节点的右侧节点
            if (T2 !=null) {

                int T2y = compute(T2.getLevel());
                int T2Width = yNewWidth  - T2.getBuffer() ;

                //增加x圆的动画
                this.addCircleAndTextAnimation(list,T2.getCid(), T2.getNodeTextId(),T2Width , T2y);

                //增加 x , y 线的动画
                this.addLineAnimation(list  , x.getCid() ,T2.getCid(),   y.getCid(), T2.getCid(),
                        yNewWidth,yNewY + r ,T2Width , T2y - r );

                //x的左节点下的双子节点
                refreshLeftRotateLeftChild(list,T2 ,T2.getLevel() ,T2Width ,T2y ,new Double(T2.getBuffer()*rate).intValue() ) ;
            }

//
//            // x 的右侧节点的左侧节点变成 x左侧节点的右侧节点
//            if (T2 !=null) {
//
//                int T2y = compute(T2.getLevel());
//                int T2Width = xNewWidth + T2.getBuffer() ;
//
//                //增加x圆的动画
//                this.addCircleAndTextAnimation(list,T2.getCid(), T2.getNodeTextId(),T2Width , T2y);
//
//                //增加 x , y 线的动画
//                this.addLineAnimation(list  , y.getCid() ,T2.getCid(),  x.getCid(), T2.getCid(),
//                        xNewWidth,xNewY + r ,T2Width , T2y - r );
//
//                //x的左节点下的双子节点
//                refreshLeftRotateLeftChild(list,T2 ,T2.getLevel() ,T2Width ,T2y ,new Double(T2.getBuffer()*rate).intValue() ) ;
//            }
//
            if (x.getLeft()!=null) {

                AVLTreeNode avlRightTreeNode = x.getLeft() ;

                int xLeftNewBuffer = x.getBuffer() ;
                int xLeftNewLevel = x.getLevel();
                int xLeftNewWidth = x.getWidth() ;
                int xLeftNewY = compute(xLeftNewLevel);

                //增加x圆的动画
                this.addCircleAndTextAnimation(list,avlRightTreeNode.getCid(), avlRightTreeNode.getNodeTextId(),xLeftNewWidth , xLeftNewY);


                //增加 x , y 线的动画
                this.addLineAnimation(list  , x.getCid() ,x.getLeft().getCid(),  null, null,
                        xNewWidth,compute(xNewLevel) + r ,xLeftNewWidth , xLeftNewY - r );

                //x的左节点下的双子节点
                refreshLeftRotateLeftChild(list,x.getLeft() ,xLeftNewLevel ,xLeftNewWidth ,xLeftNewY ,new Double(xLeftNewBuffer*rate).intValue()) ;
            }

            if (y.getRight()!=null){



                AVLTreeNode domRY = y.getRight();


                int yRightNewLevel = y.getRight().getLevel() + 1 ;

                int yRightNewY = compute(yRightNewLevel);

                int xRightNewBuffer = new Double(y.getRight().getBuffer()*rate).intValue();
                int xRightNewWidth = y.getRight().getWidth() + xRightNewBuffer ;

//                int yNewRightWidth =
//

                //增加圆的动画
                this.addCircleAndTextAnimation(list,domRY.getCid(), domRY.getNodeTextId(),xRightNewWidth , yRightNewY);
                //增加线的动画
                this.addLineAnimation(list ,ycid , domRY.getCid() ,null,null,
                        yNewWidth , yNewY + r , xRightNewWidth,yRightNewY - r);



                refreshLeftRotateLeftChild(list, y.getRight() , yRightNewLevel , xRightNewWidth , yRightNewY ,new Double(xRightNewBuffer*rate).intValue());

            }


        }
    }

    private void refreshLeftRotateRightChild(List<GraphComponent> list , AVLTreeNode parentNode, int parentNewLevel ,
                                             int parentNewWidth,  int parentNewY){

//        if(parentNode.getLeft()!=null){
//
//            AVLTreeNode avlTreeNode = parentNode.getLeft();
//            int xNewLevel = parentNewLevel + 1;
//            int xNewBuffer = new Double(parentNode.left.getBuffer()*rate).intValue() ;
//            int xNewWidth = parentNode.left.getWidth() - xNewBuffer ;
//            int xNewY = compute(xNewLevel);
//            //重新刷新map里的x值
////            refreshMap(parentNode.left.getCid() , xNewLevel,xNewWidth,xNewBuffer);
//
//
//            //增加圆的动画
//            this.addCircleAndTextAnimation(list, avlTreeNode.getCid(), avlTreeNode.getNodeTextId(),xNewWidth , xNewY);
//            //增加线的动画
//            this.addLineAnimation(list ,parentNode.getCid() , avlTreeNode.getCid() ,null,null,
//                    parentNewWidth , parentNewY + r , xNewWidth,xNewY - r);
//
////            refreshLeftRotateChild(list  ,changeList, parentNode.left ,xNewLevel,
////                    xNewWidth , xNewBuffer ,xNewY);
//        }
//
//        if (parentNode.right != null) {
//
//            AVLTreeNode avlTreeNode = parentNode.getRight();
//
//            int xRightNewBuffer = new Double(parentNode.right.getBuffer()*rate).intValue() ;
//            int xRightNewLevel = parentNewLevel - 1 ;
//            int xNewWidth = parentNewWidth + xRightNewBuffer ;
//            int xNewY = compute(xRightNewLevel);
//
//            //增加圆的动画
//            this.addCircleAndTextAnimation(list, avlTreeNode.getCid(), avlTreeNode.getNodeTextId(),xNewWidth , xNewY);
//            //增加线的动画
//            this.addLineAnimation(list ,parentNode.getCid() , avlTreeNode.getCid() ,null,null,
//                    parentNewWidth , parentNewY + r , xNewWidth,xNewY - r);
//
//            //重新刷新map里的x值
////            refreshMap(parentNode.right.getCid() , xRightNewLevel,xNewWidth,xRightNewBuffer);
////            refreshLeftRotateChild(list , changeList , parentNode.right ,xRightNewLevel,
////                    xNewWidth , xRightNewBuffer ,xNewY);
//        }

    }



    private void refreshLeftRotateLeftChild(List<GraphComponent> list , AVLTreeNode parentNode, int parentNewLevel ,
                                        int parentNewWidth,  int parentNewY , int newBuffer){
        if(parentNode.getLeft()!=null){

            AVLTreeNode avlTreeNode = parentNode.getLeft();
            int xNewLevel = parentNewLevel + 1;
//            int xNewBuffer = new Double(parentNode.left.getBuffer()*rate).intValue() ;
            int xNewWidth = parentNewWidth - newBuffer ;
            int xNewY = compute(xNewLevel);
            //重新刷新map里的x值
//            refreshMap(parentNode.left.getCid() , xNewLevel,xNewWidth,xNewBuffer);


            //增加圆的动画
            this.addCircleAndTextAnimation(list, avlTreeNode.getCid(), avlTreeNode.getNodeTextId(),xNewWidth , xNewY);
            //增加线的动画
            this.addLineAnimation(list ,parentNode.getCid() , avlTreeNode.getCid() ,null,null,
                    parentNewWidth , parentNewY + r , xNewWidth,xNewY - r);

            refreshLeftRotateLeftChild(list  , parentNode.getLeft() ,xNewLevel,
                    xNewWidth , compute(xNewLevel) ,new Double(newBuffer*rate).intValue() );
        }

        if (parentNode.getRight() != null) {

            AVLTreeNode avlTreeNode = parentNode.getRight();

//            int xRightNewBuffer = new Double(parentNode.right.getBuffer()*rate).intValue() ;
            int xRightNewLevel = parentNewLevel + 1 ;
            int xNewWidth = parentNewWidth + newBuffer ;
            int xNewY = compute(xRightNewLevel);

            //增加圆的动画
            this.addCircleAndTextAnimation(list, avlTreeNode.getCid(), avlTreeNode.getNodeTextId(),xNewWidth , xNewY);
            //增加线的动画
            this.addLineAnimation(list ,parentNode.getCid() , avlTreeNode.getCid() ,null,null,
                    parentNewWidth , parentNewY + r , xNewWidth,xNewY - r);


            refreshLeftRotateLeftChild(list  , parentNode.getRight() ,xRightNewLevel,
                    xNewWidth , compute(xRightNewLevel) ,new Double(newBuffer*rate).intValue() );

            //重新刷新map里的x值
//            refreshMap(parentNode.right.getCid() , xRightNewLevel,xNewWidth,xRightNewBuffer);
//            refreshLeftRotateChild(list , changeList , parentNode.right ,xRightNewLevel,
//                    xNewWidth , xRightNewBuffer ,xNewY);
        }
    }





    public static void main(String[] args) {
        AVLTree tree = new AVLTree();

//        /* Constructing tree given in the above figure */
//        tree.root = tree.insert(tree.root, 10);
//        tree.root = tree.insert(tree.root, 20);
//        tree.root = tree.insert(tree.root, 30);
//        tree.root = tree.insert(tree.root, 40);
//        tree.root = tree.insert(tree.root, 50);
//        tree.root = tree.insert(tree.root, 25);
//
//        /* The constructed AVL Tree would be
//             30
//            /  \
//          20   40
//         /  \     \
//        10  25    50
//        */
//        System.out.println("Preorder traversal" +
//                " of constructed tree is : ");
//        tree.preOrder(tree.root);







        /* Constructing tree given in the above figure */
        tree.root = tree.insert(tree.root, 9 ,new ArrayNode(JUUID.getUUID() ,JUUID.getUUID() , JUUID.getUUID() , new Integer(9)));
        tree.root = tree.insert(tree.root, 5,new ArrayNode(JUUID.getUUID() ,JUUID.getUUID() , JUUID.getUUID() , new Integer(5)));
        tree.root = tree.insert(tree.root, 10 ,new ArrayNode(JUUID.getUUID() ,JUUID.getUUID() , JUUID.getUUID() , new Integer(10)));
        tree.root = tree.insert(tree.root, 0 ,new ArrayNode(JUUID.getUUID() ,JUUID.getUUID() , JUUID.getUUID() , new Integer(0)));
        tree.root = tree.insert(tree.root, 6 ,new ArrayNode(JUUID.getUUID() ,JUUID.getUUID() , JUUID.getUUID() , new Integer(6)));
        tree.root = tree.insert(tree.root, 11 ,new ArrayNode(JUUID.getUUID() ,JUUID.getUUID() , JUUID.getUUID() , new Integer(11)));
        tree.root = tree.insert(tree.root, -1,new ArrayNode(JUUID.getUUID() ,JUUID.getUUID() , JUUID.getUUID() , new Integer(-1)));
        tree.root = tree.insert(tree.root, 1,new ArrayNode(JUUID.getUUID() ,JUUID.getUUID() , JUUID.getUUID() , new Integer(1)));
        tree.root = tree.insert(tree.root, 2,new ArrayNode(JUUID.getUUID() ,JUUID.getUUID() , JUUID.getUUID() , new Integer(2)));

        /* The constructed AVL Tree would be
        9
        / \
        1 10
        / \ \
        0 5 11
        / / \
        -1 2 6
        */
        System.out.println("Preorder traversal of "+
                "constructed tree is : ");
        tree.preOrder(tree.root);

        tree.root = tree.deleteNode(tree.root, 10);

        /* The AVL Tree after deletion of 10
        1
        / \
        0 9
        /     / \
        -1 5 11
        / \
        2 6
        */
        System.out.println("");
        System.out.println("Preorder traversal after "+
                "deletion of 10 :");
        tree.preOrder(tree.root);

    }

    public AVLTreeNode getRoot() {
        return root;
    }

    public void setRoot(AVLTreeNode root) {
        this.root = root;
    }

    public AnimationTotal getAnimationTotal() {
        return animationTotal;
    }

    public void setAnimationTotal(AnimationTotal animationTotal) {
        this.animationTotal = animationTotal;
    }

    public boolean isAnimationFlag() {
        return animationFlag;
    }

    public void setAnimationFlag(boolean animationFlag) {
        this.animationFlag = animationFlag;
    }
}
