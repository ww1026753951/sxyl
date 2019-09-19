package com.sxyl.portal.domain.tree.rb;

import com.sxyl.portal.domain.constant.ChangeAttrEnum;
import com.sxyl.portal.domain.constant.RBTreeStepConstant;
import com.sxyl.portal.domain.d.*;
import com.sxyl.portal.domain.graph.Circle;
import com.sxyl.portal.domain.graph.GraphComponent;
import com.sxyl.portal.domain.graph.Line;
import com.sxyl.portal.domain.graph.Text;
import com.sxyl.portal.domain.sort.ArrayNode;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.sxyl.portal.domain.tree.rb.RBTreeColor.BLACK;
import static com.sxyl.portal.domain.tree.rb.RBTreeColor.RED;

/**
 * Java 语言: 红黑树
 *

 */

public class RBTree<T extends Comparable<T>> {


    // 圆的半径
    private final int r = 20;

    private RBTNode<T> mRoot;    // 根结点

    private boolean animationFlag = false;


    private AnimationTotal animationTotal = new AnimationTotal();


    private Map<String ,RBTNode> nodeMap = new HashMap<>();


    private final int defaultMt = 20 ;


    //rate
    double rate = 0.5;



    //层与层的高度
    int height = 80 ;

    /***
     * 执行步骤
     */
    private Map<String , String> stepExecute = new HashMap<>();


    private Map<String,String> replaceMap = new HashMap<>();


//    public class RBTNode<T extends Comparable<T>> {
//        boolean color;        // 颜色
//        T key;                // 关键字(键值)
//        RBTNode<T> left;    // 左孩子
//        RBTNode<T> right;    // 右孩子
//        RBTNode<T> parent;    // 父结点
//
//        public RBTNode(T key, boolean color, RBTNode<T> parent, RBTNode<T> left, RBTNode<T> right) {
//            this.key = key;
//            this.color = color;
//            this.parent = parent;
//            this.left = left;
//            this.right = right;
//        }
//
//        public T getKey() {
//            return key;
//        }
//
//        public String toString() {
//            return ""+key+(this.color==RED?"(R)":"B");
//        }
//    }

    public RBTree() {
        mRoot=null;
    }

    public RBTNode getRoot(){
        return mRoot;
    }

    private RBTNode<T> parentOf(RBTNode<T> node) {
        return node!=null ? node.parent : null;
    }
    private boolean colorOf(RBTNode<T> node) {
        return node!=null ? node.color : BLACK;
    }
    private boolean isRed(RBTNode<T> node) {
        return ((node!=null)&&(node.color==RED)) ? true : false;
    }
    private boolean isBlack(RBTNode<T> node) {
        return !isRed(node);
    }
    private void setBlack(RBTNode<T> node) {
        if (node!=null){

            node.color = BLACK;
            if(animationFlag){
                Map nodeMap = new HashMap();
                //将元素变为黑色
                ChangeColor changeColor = new ChangeColor("black",node.getCid());
                nodeMap.put(RBTreeStepConstant.NODE , node.getKey().toString());
                changeColor.setAd(this.getDesc(RBTreeStepConstant.REPLACE_NODE_COLOR , nodeMap ));
                animationTotal.addComponent(changeColor);
            }

        }
    }
    private void setRed(RBTNode<T> node) {
        if (node!=null){
            node.color = RED;
            if(animationFlag){
                //将元素变为红色
                ChangeColor changeColor = new ChangeColor("red",node.getCid()) ;

                Map nodeMap = new HashMap();
                nodeMap.put(RBTreeStepConstant.NODE , node.getKey().toString());
                changeColor.setAd(this.getDesc(RBTreeStepConstant.REPLACE_NODE_COLOR , nodeMap ));
                animationTotal.addComponent(changeColor);
            }
        }
    }
    private void setParent(RBTNode<T> node, RBTNode<T> parent) {
        if (node!=null)
            node.parent = parent;
    }
    private void setColor(RBTNode<T> node, boolean color) {
        if (node!=null){

            node.color = color;

            if(animationFlag){
                if(color == RED){
                    ChangeColor changeColor = new ChangeColor("red",node.getCid()) ;
                    Map nodeMap = new HashMap();
                    nodeMap.put(RBTreeStepConstant.NODE , node.getKey().toString());

                    changeColor.setAd(this.getDesc(RBTreeStepConstant.REPLACE_NODE_COLOR , nodeMap ));

                    animationTotal.addComponent(changeColor);
                }else {
                    ChangeColor changeColor = new ChangeColor("black",node.getCid());
                    Map nodeMap = new HashMap();
                    nodeMap.put(RBTreeStepConstant.NODE , node.getKey().toString());
                    changeColor.setAd(this.getDesc(RBTreeStepConstant.REPLACE_NODE_COLOR , nodeMap ));
                    animationTotal.addComponent(changeColor);
                }
            }
        }
    }

    /*
     * 前序遍历"红黑树"
     */
    private void preOrder(RBTNode<T> tree) {
        if(tree != null) {
            System.out.print(tree.key+" ");
            preOrder(tree.left);
            preOrder(tree.right);
        }
    }

    public void preOrder() {
        preOrder(mRoot);
    }

    /*
     * 中序遍历"红黑树"
     */
    private void inOrder(RBTNode<T> tree) {
        if(tree != null) {
            inOrder(tree.left);
            System.out.print(tree.key+" ");
            inOrder(tree.right);
        }
    }

    public void inOrder() {
        inOrder(mRoot);
    }


    /*
     * 后序遍历"红黑树"
     */
    private void postOrder(RBTNode<T> tree) {
        if(tree != null)
        {
            postOrder(tree.left);
            postOrder(tree.right);
            System.out.print(tree.key+" ");
        }
    }

    public void postOrder() {
        postOrder(mRoot);
    }


    /*
     * (递归实现)查找"红黑树x"中键值为key的节点
     */
    private RBTNode<T> search(RBTNode<T> x, T key) {
        if (x==null)
            return x;

        int cmp = key.compareTo(x.key);
        if (cmp < 0)
            return search(x.left, key);
        else if (cmp > 0)
            return search(x.right, key);
        else
            return x;
    }

    public RBTNode<T> search(T key) {
        return search(mRoot, key);
    }

    /*
     * (非递归实现)查找"红黑树x"中键值为key的节点
     */
    private RBTNode<T> iterativeSearch(RBTNode<T> x, T key) {
        while (x!=null) {
            int cmp = key.compareTo(x.key);

            if (cmp < 0)
                x = x.left;
            else if (cmp > 0)
                x = x.right;
            else
                return x;
        }

        return x;
    }

    public RBTNode<T> iterativeSearch(T key) {
        return iterativeSearch(mRoot, key);
    }

    /*
     * 查找最小结点：返回tree为根结点的红黑树的最小结点。
     */
    private RBTNode<T> minimum(RBTNode<T> tree) {
        if (tree == null)
            return null;

        while(tree.left != null)
            tree = tree.left;
        return tree;
    }

    public T minimum() {
        RBTNode<T> p = minimum(mRoot);
        if (p != null)
            return p.key;

        return null;
    }

    /*
     * 查找最大结点：返回tree为根结点的红黑树的最大结点。
     */
    private RBTNode<T> maximum(RBTNode<T> tree) {
        if (tree == null)
            return null;

        while(tree.right != null)
            tree = tree.right;
        return tree;
    }

    public T maximum() {
        RBTNode<T> p = maximum(mRoot);
        if (p != null)
            return p.key;

        return null;
    }

    /*
     * 找结点(x)的后继结点。即，查找"红黑树中数据值大于该结点"的"最小结点"。
     */
    public RBTNode<T> successor(RBTNode<T> x) {
        // 如果x存在右孩子，则"x的后继结点"为 "以其右孩子为根的子树的最小结点"。
        if (x.right != null)
            return minimum(x.right);

        // 如果x没有右孩子。则x有以下两种可能：
        // (01) x是"一个左孩子"，则"x的后继结点"为 "它的父结点"。
        // (02) x是"一个右孩子"，则查找"x的最低的父结点，并且该父结点要具有左孩子"，找到的这个"最低的父结点"就是"x的后继结点"。
        RBTNode<T> y = x.parent;
        while ((y!=null) && (x==y.right)) {
            x = y;
            y = y.parent;
        }

        return y;
    }

    /*
     * 找结点(x)的前驱结点。即，查找"红黑树中数据值小于该结点"的"最大结点"。
     */
    public RBTNode<T> predecessor(RBTNode<T> x) {
        // 如果x存在左孩子，则"x的前驱结点"为 "以其左孩子为根的子树的最大结点"。
        if (x.left != null)
            return maximum(x.left);

        // 如果x没有左孩子。则x有以下两种可能：
        // (01) x是"一个右孩子"，则"x的前驱结点"为 "它的父结点"。
        // (01) x是"一个左孩子"，则查找"x的最低的父结点，并且该父结点要具有右孩子"，找到的这个"最低的父结点"就是"x的前驱结点"。
        RBTNode<T> y = x.parent;
        while ((y!=null) && (x==y.left)) {
            x = y;
            y = y.parent;
        }

        return y;
    }

    /*
     * 对红黑树的节点(x)进行左旋转
     *
     * 左旋示意图(对节点x进行左旋)：
     *      px                              px
     *     /                               /
     *    x                               y
     *   /  \      --(左旋)-.           / \                #
     *  lx   y                          x  ry
     *     /   \                       /  \
     *    ly   ry                     lx  ly
     *
     *
     */
    private void leftRotate(RBTNode<T> x) {
        // 设置x的右孩子为y
        RBTNode<T> y = x.right;

        //左旋的动画
        leftAnimation(x , y) ;
        // 将 “y的左孩子” 设为 “x的右孩子”；
        // 如果y的左孩子非空，将 “x” 设为 “y的左孩子的父亲”
        x.right = y.left;
        if (y.left != null)
            y.left.parent = x;

        // 将 “x的父亲” 设为 “y的父亲”
        y.parent = x.parent;

        if (x.parent == null) {
            this.mRoot = y;            // 如果 “x的父亲” 是空节点，则将y设为根节点
        } else {
            if (x.parent.left == x)
                x.parent.left = y;    // 如果 x是它父节点的左孩子，则将y设为“x的父节点的左孩子”
            else
                x.parent.right = y;    // 如果 x是它父节点的左孩子，则将y设为“x的父节点的左孩子”
        }

        // 将 “x” 设为 “y的左孩子”
        y.left = x;
        // 将 “x的父节点” 设为 “y”
        x.parent = y;
    }

    /*
     * 对红黑树的节点(y)进行右旋转
     *
     * 右旋示意图(对节点y进行左旋)：
     *            py                               py
     *           /                                /
     *          y                                x
     *         /  \      --(右旋)-.            /  \                     #
     *        x   ry                           lx   y
     *       / \                                   / \                   #
     *      lx  rx                                rx  ry
     *
     */
    private void rightRotate(RBTNode<T> y) {
        // 设置x是当前节点的左孩子。
        RBTNode<T> x = y.left;


        //设置动画
        this.rightAnimation(x , y);

        // 将 “x的右孩子” 设为 “y的左孩子”；
        // 如果"x的右孩子"不为空的话，将 “y” 设为 “x的右孩子的父亲”
        y.left = x.right;
        if (x.right != null){
            x.right.parent = y;
        }

        // 将 “y的父亲” 设为 “x的父亲”
        x.parent = y.parent;

        if (y.parent == null) {
            // 如果 “y的父亲” 是空节点，则将x设为根节点
            this.mRoot = x;
        } else {
            if (y == y.parent.right){
                // 如果 y是它父节点的右孩子，则将x设为“y的父节点的右孩子”
                y.parent.right = x;
            }
            else{
                // (y是它父节点的左孩子) 将x设为“x的父节点的左孩子”
                y.parent.left = x;
            }
        }

        // 将 “y” 设为 “x的右孩子”
        x.right = y;

        // 将 “y的父节点” 设为 “x”
        y.parent = x;
    }


    /*
    * 对红黑树的节点(x)进行左旋转
    *
    * 左旋示意图(对节点x进行左旋)：
    *      px                              px
    *     /                               /
    *    x                               y
    *   /  \      --(左旋)-.           / \                #
    *  lx   y                          x  ry
    *     /   \                       /  \
    *    ly   ry                     lx  ly
    *
    *
    */
    private void leftAnimation(RBTNode<T> x , RBTNode<T> y){
        if (animationFlag){
            ChangeAttr changeAttr = new ChangeAttr();

            List<ChangeAttrDetail> changeList = new ArrayList<>();
            changeAttr.setList(changeList);

            MultiMove multiMove = new MultiMove();
            List<GraphComponent> list = new ArrayList<>();

            multiMove.setGcs(list);

            replaceMap.put(RBTreeStepConstant.X_NODE , x.getKey().toString());
            replaceMap.put(RBTreeStepConstant.Y_NODE , y.getKey().toString());

            multiMove.setAd(this.getDesc(RBTreeStepConstant.LEFT_ROTATE, replaceMap));
            animationTotal.addComponent(multiMove);
            animationTotal.addComponent(changeAttr);

            RBTNode domX = nodeMap.get(x.getCid());
            RBTNode domY = nodeMap.get(y.getCid());

            //x的基础属性
            String xcid = domX.getCid();
            int xLevel = domX.getLevel();
            int xWidth = domX.getWidth();
            int xBuffer = domX.getBuffer();

            //y的基础属性
            String ycid = domY.getCid();
            int yLevel = domY.getLevel();
            int yWidth = domY.getWidth();
            int yBuffer = domY.getBuffer();


            int xNewLevel = xLevel + 1;
            int xNewWidth = xWidth - yBuffer;
            int xNewBuffer = domY.getBuffer();
            int xNewY = compute(xNewLevel);


            int yNewLevel = yLevel - 1;
            int yNewWidth = yWidth -  yBuffer;
            int yNewBuffer = domX.getBuffer();
            int yNewY = compute(yNewLevel) ;

            //lx 的三个属性
            int lxNewLevel = 0 ; int lxNewWidth = 0 ; int lxNewBuffer = 0 ;int lxNewY = 0 ;
            //ly 的三个属性
            int lyLevel = 0 ; int lyWidth = 0 ; int lyBuffer = 0 ;
            //ry 的三个属性
            int ryLevel = 0 ; int ryWidth = 0 ; int ryBuffer = 0 ;

            //x的移动

            //增加x圆的动画
            this.addCircleAndTextAnimation(list,domX.getCid(), domX.getNodeTextId(),xNewWidth , xNewY);

            //增加y圆的动画
            this.addCircleAndTextAnimation(list,domY.getCid(), domY.getNodeTextId(),yNewWidth , yNewY);

            //增加 x , y 线的动画
            this.addLineAnimation(list ,changeList,xcid , ycid ,ycid , xcid,
                    xNewWidth , xNewY - r , yNewWidth,yNewY + r);

            if(x.getParent()!=null){
                addChangeLineId(changeList , x.getParent().getCid() , x.getCid() , x.getParent().getCid(),y.getCid());
            }else {
                //r如果没有父节点,则说明x为根节点， 则交换x y的位置
                addChangeLineId(changeList , x.getCid() , y.getCid() ,y.getCid() , x.getCid());

            }

            //可能为新节点， 所以不能用当前元素的 x y 计算 ,改用 y的值计算
            if(y.right != null){

                RBTNode domRY = nodeMap.get(y.right.getCid());
                int ryX = domY.getWidth();
                int ryY = (domY.getLevel())*height + defaultMt;
                int ryNewLevel = domRY.getLevel() - 1;
                int ryNewBuffer = domY.getBuffer();

                //增加圆的动画
                this.addCircleAndTextAnimation(list,domRY.getCid(), domRY.getNodeTextId(),ryX , ryY);
                //增加线的动画
                this.addLineAnimation(list,changeList ,ycid , domRY.getCid() ,null,null,
                        yNewWidth , yNewY + r , ryX,ryY - r);
                //右侧子节点的处理
                refreshYRightRotateChild(list ,changeList, domRY , domY.getLevel() ,ryX , ryNewBuffer , ryY) ;
                //重新刷新map里的x值
                refreshMap(y.right.getCid() , ryNewLevel,ryX,ryNewBuffer);
            }


            //不知道是不是为新节点
            if(y.left != null){
                RBTNode domLY = nodeMap.get(y.left.getCid());
                //用计算后的 x 宽度当buffer用
                int lyX = xNewWidth + domLY.getBuffer();
                int lyY = (domLY.getLevel())*height + defaultMt;
                //增加圆的动画
                this.addCircleAndTextAnimation(list,domLY.getCid(), domLY.getNodeTextId(),lyX , lyY);
                //增加线的动画
                //y的父节点变为 x的父节点 ,此处比较重要，  因为父节点变换了
                this.addLineAnimation(list ,changeList,ycid , domLY.getCid() , xcid , domLY.getCid() ,
                        xNewWidth, xNewY + r , lyX,lyY - r);

                //
                refreshLeftRotateYLChild(list , changeList , domLY ,domLY.getLevel() ,lyX , domLY.getBuffer(),lyY);
                //重新刷新map里的x值
                refreshMap(y.left.getCid() , domLY.getLevel(),lyX,domLY.getBuffer());

            }

            //如果 x有左节点,则递归处理
            if(x.left != null){
                RBTNode domLX = nodeMap.get(x.left.getCid());
                lxNewBuffer = new Double(domLX.getBuffer()*rate).intValue() ;
                lxNewLevel = domLX.getLevel() + 1;
                lxNewWidth = domLX.getWidth() - lxNewBuffer ;
                lxNewY = compute(lxNewLevel);


                //增加圆的动画
                this.addCircleAndTextAnimation(list,domLX.getCid(), domLX.getNodeTextId(),lxNewWidth , lxNewY);
                //增加线的动画
                this.addLineAnimation(list,changeList ,xcid , domLX.getCid() ,null,null,
                        xNewWidth, xNewY + r , lxNewWidth,lxNewY - r);

                //左侧子节点的处理
                refreshLeftRotateChild(list , changeList , domLX ,lxNewLevel , lxNewWidth,lxNewBuffer,lxNewY );
                //重新刷新map里的x值
                refreshMap(x.left.getCid() , lxNewLevel,lxNewWidth,lxNewBuffer);

            }
            //重新刷新map里的x值
            refreshMap(x.getCid() , xNewLevel,xNewWidth,xNewBuffer);
            //重新刷新map里的y值
            refreshMap(y.getCid(),yNewLevel,yNewWidth,yNewBuffer);

        }
    }

    /***
     * 刷新子节点
     * @param parentNode
     * @param parentNewLevel
     * @param parentNewWidth
     * @param parentNewBuffer
     */
    private void refreshLeftRotateChild(List<GraphComponent> list ,List<ChangeAttrDetail> changeList, RBTNode parentNode, int parentNewLevel ,
                                        int parentNewWidth, int parentNewBuffer , int parentNewY){
        if(parentNode.left!=null){
            int xNewBuffer = new Double(parentNode.left.getBuffer()*rate).intValue() ;
            int xNewLevel = parentNewLevel + 1;
            int xNewWidth = parentNode.left.getWidth() - xNewBuffer ;
            int xNewY = compute(xNewLevel);
            //重新刷新map里的x值
            refreshMap(parentNode.left.getCid() , xNewLevel,xNewWidth,xNewBuffer);


            //增加圆的动画
            this.addCircleAndTextAnimation(list, parentNode.left.getCid(), parentNode.left.getNodeTextId(),xNewWidth , xNewY);
            //增加线的动画
            this.addLineAnimation(list,changeList ,parentNode.getCid() , parentNode.left.getCid() ,null,null,
                    parentNewWidth , parentNewY + r , xNewWidth,xNewY - r);

            refreshLeftRotateChild(list  ,changeList, parentNode.left ,xNewLevel,
                    xNewWidth , xNewBuffer ,xNewY);
        }

        if (parentNode.right != null) {
            int xRightNewBuffer = new Double(parentNode.right.getBuffer()*rate).intValue() ;
            int xRightNewLevel = parentNewLevel + 1 ;
            int xNewWidth = parentNewWidth + xRightNewBuffer ;
            int xNewY = compute(xRightNewLevel);

            //增加圆的动画
            this.addCircleAndTextAnimation(list, parentNode.right.getCid(), parentNode.right.getNodeTextId(),xNewWidth , xNewY);
            //增加线的动画
            this.addLineAnimation(list,changeList ,parentNode.getCid() , parentNode.right.getCid() ,null,null,
                    parentNewWidth , parentNewY + r , xNewWidth,xNewY - r);

            //重新刷新map里的x值
            refreshMap(parentNode.right.getCid() , xRightNewLevel,xNewWidth,xRightNewBuffer);
            refreshLeftRotateChild(list , changeList , parentNode.right ,xRightNewLevel,
                    xNewWidth , xRightNewBuffer ,xNewY);
        }
    }


    /***
     * 刷新子节点 ,  y 的 right 的节点的子节点刷新
     * @param parentNode
     * @param parentNewLevel
     * @param parentNewWidth
     * @param parentNewBuffer
     */
    private void refreshYRightRotateChild(List<GraphComponent> list ,List<ChangeAttrDetail> changeList  , RBTNode parentNode, int parentNewLevel , int parentNewWidth,
                                        int parentNewBuffer , int parentNewY){
        if(parentNode.left!=null){
            int yNewBuffer = new Double(parentNode.getBuffer()*rate).intValue() ;
            int yNewLevel = parentNewLevel + 1;
            //此处扣减用老的buffer
            int yNewWidth = parentNewWidth - parentNode.getBuffer() ;
            int yNewY = compute(yNewLevel);
            //重新刷新map里的x值
            refreshMap(parentNode.left.getCid() , yNewLevel,yNewWidth,yNewBuffer);
            //增加圆的动画
            this.addCircleAndTextAnimation(list,parentNode.left.getCid(), parentNode.left.getNodeTextId(),yNewWidth , yNewY );
            //增加线的动画
            this.addLineAnimation(list ,changeList,parentNode.getCid() , parentNode.left.getCid() ,null,null,
                    parentNewWidth , parentNewY + r , yNewWidth,yNewY - r);
            refreshYRightRotateChild(list ,changeList, parentNode.left ,yNewLevel,
                    yNewWidth , yNewBuffer ,yNewY);
        }

        if (parentNode.right != null) {
            int xRightNewBuffer =  new Double(parentNode.getBuffer()*rate).intValue() ; ;
            int xRightNewLevel = parentNewLevel + 1 ;
            int xNewWidth = parentNewWidth + parentNode.getBuffer() ;
            int xNewY = compute(xRightNewLevel);
            //重新刷新map里的x值
            refreshMap(parentNode.right.getCid() , xRightNewLevel,xNewWidth,xRightNewBuffer);

            //增加圆的动画
            this.addCircleAndTextAnimation(list,parentNode.right.getCid(), parentNode.right.getNodeTextId(),xNewWidth , xNewY );
            //增加线的动画
            this.addLineAnimation(list,changeList,parentNode.getCid() , parentNode.right.getCid() , null,null,
                    parentNewWidth , parentNewY + r,xNewWidth,xNewY - r);

//            Circle yryCircle = new Circle();
//            yryCircle.setId(parentNode.right.getCid());
//            yryCircle.setX(xNewWidth);
//            yryCircle.setY(xNewY);
//            list.add(yryCircle);
//
//            Text yryText = new Text();
//            yryText.setId(parentNode.right.getNodeTextId());
//            yryText.setX(xNewWidth);
//            yryText.setY(xNewY);
//            list.add(yryText);

//            Line yryLine = new Line();
//            yryLine.setId(parentNode.getCid()+"-" + parentNode.right.getCid());
//            yryLine.setX1(parentNewWidth);
//            yryLine.setY1(parentNewY + r);
//            yryLine.setX2(xNewWidth);
//            yryLine.setY2(xNewY - r);
//            list.add(yryLine);

            refreshYRightRotateChild(list ,changeList, parentNode.right ,xRightNewLevel,
                    xNewWidth , xRightNewBuffer ,xNewY);
        }
    }


    private void refreshLeftRotateYLChild(List<GraphComponent> list ,List<ChangeAttrDetail> changeList, RBTNode parentNode, int parentNewLevel ,
                                          int parentNewWidth, int parentNewBuffer , int parentNewY){

        if(parentNode.left != null){
            RBTNode left = parentNode.left;
            int yLeftNewBuffer =  left.getBuffer() ; ;
            int yLeftNewLevel = left.getLevel() ;
            int yLeftNewWidth = parentNewWidth - yLeftNewBuffer ;
            int yLeftNewY = compute(yLeftNewLevel);

            //重新刷新map里的x值
            refreshMap(parentNode.left.getCid() , yLeftNewLevel,yLeftNewWidth,yLeftNewBuffer);

            //增加圆的动画
            this.addCircleAndTextAnimation(list,parentNode.left.getCid(), parentNode.left.getNodeTextId(),yLeftNewWidth , yLeftNewY );

            //增加线的动画
            this.addLineAnimation(list,changeList,parentNode.getCid() , parentNode.left.getCid() , null,null,
                    parentNewWidth , parentNewY + r,yLeftNewWidth,yLeftNewY - r);

            //刷新y左节点的子节点
            this.refreshLeftRotateYLChild(list , changeList, left , yLeftNewLevel , yLeftNewWidth , yLeftNewBuffer , yLeftNewY) ;
        }


        if (parentNode.right != null){

            RBTNode right = parentNode.right;

            int yRightNewBuffer =  right.getBuffer() ; ;
            int yRightNewLevel = right.getLevel() ;
            int yRightNewWidth = parentNewWidth + yRightNewBuffer ;
            int yRightNewY = compute(yRightNewLevel);

            //重新刷新map里的x值
            refreshMap(parentNode.right.getCid() , yRightNewLevel,yRightNewWidth,yRightNewBuffer);


            //增加圆的动画
            this.addCircleAndTextAnimation(list,parentNode.right.getCid(), parentNode.right.getNodeTextId(),yRightNewWidth , yRightNewY );

            //增加线的动画
            this.addLineAnimation(list,changeList,parentNode.getCid() , parentNode.right.getCid() , null,null,
                    parentNewWidth , parentNewY + r,yRightNewWidth,yRightNewY - r);

            //刷新y右节点的子节点
            this.refreshLeftRotateYLChild(list , changeList, right , yRightNewLevel , yRightNewWidth , yRightNewBuffer , yRightNewY) ;

        }

    }


    /*
    * 对红黑树的节点(y)进行右旋转 ,设置动画
    *
    * 右旋示意图(对节点y进行左旋)：
    *            py                               py
    *           /                                /
    *          y                                x
    *         /  \      --(右旋)-.            /  \                     #
    *        x   ry                           lx   y
    *       / \                                   / \                   #
    *      lx  rx                                rx  ry
    *
    */
    private void rightAnimation(RBTNode<T> x , RBTNode<T> y){
        if (animationFlag){

            ChangeAttr changeAttr = new ChangeAttr();
            MultiMove multiMove = new MultiMove();
            List<GraphComponent> list = new ArrayList<>();

            multiMove.setGcs(list);


            replaceMap.put(RBTreeStepConstant.X_NODE , x.getKey().toString());
            replaceMap.put(RBTreeStepConstant.Y_NODE , y.getKey().toString());

            multiMove.setAd(this.getDesc(RBTreeStepConstant.RIGHT_ROTATE, replaceMap));
//            multiMove.setAd("以x为支点进行右旋");

            animationTotal.addComponent(multiMove);


            List<ChangeAttrDetail> changeList = new ArrayList<>();
            changeAttr.setList(changeList);
            animationTotal.addComponent(changeAttr);

            RBTNode domX = nodeMap.get(x.getCid());
            RBTNode domY = nodeMap.get(y.getCid());




            String xcid = domX.getCid();
            //x的基础属性
            int xLevel = domX.getLevel();
            int xWidth = domX.getWidth();
            int xBuffer = domX.getBuffer();

            String ycid = domY.getCid();
            //y的基础属性
            int yLevel = domY.getLevel();
            int yWidth = domY.getWidth();
            int yBuffer = domY.getBuffer();


            int xNewLevel = xLevel - 1;
            int xNewWidth = domY.getWidth();
            int xNewBuffer = domY.getBuffer();
            int xNewY = compute(xNewLevel) ;


            int yNewLevel = yLevel + 1;
            int yNewWidth = domY.getWidth()+ domX.getBuffer();
            int yNewBuffer = domX.getBuffer();
            int yNewY = compute(yNewLevel);




            //增加x圆的动画
            this.addCircleAndTextAnimation(list,domX.getCid(), domX.getNodeTextId(),xNewWidth , xNewY);

            //增加y圆的动画
            this.addCircleAndTextAnimation(list,domY.getCid(), domY.getNodeTextId(),yNewWidth , yNewY);

            //增加 x , y 线的动画
            this.addLineAnimation(list ,changeList,ycid , xcid ,xcid , ycid,
                    yNewWidth , yNewY - r , xNewWidth,xNewY + r);

            if(y.getParent()!=null){
                addChangeLineId(changeList , y.getParent().getCid() , y.getCid() , y.getParent().getCid(),x.getCid());
            }else {
                //r如果没有父节点,则说明x为根节点， 则交换x y的位置
                addChangeLineId(changeList , y.getCid() , x.getCid() ,x.getCid() , y.getCid());

            }




            //lx
            if(x.left != null){



                RBTNode domLX = nodeMap.get(x.left.getCid());
                int lxX = domX.getWidth();
                int lxY = (domX.getLevel())*height + defaultMt;
                int lxNewLevel = domLX.getLevel() - 1 ;
                int lxNewBuffer = domX.getBuffer();

                //增加圆的动画
                this.addCircleAndTextAnimation(list,domLX.getCid(), domLX.getNodeTextId(),lxX , lxY);
                //增加线的动画
                this.addLineAnimation(list,changeList ,xcid , domLX.getCid() ,null,null,
                        xNewWidth , xNewY + r , lxX,lxY - r);
                //右侧子节点的处理
                refreshYRightRotateChild(list ,changeList, domLX ,  domX.getLevel() ,lxX , lxNewBuffer , lxY) ;

                //重新刷新map里的x值
                refreshMap(x.left.getCid() , lxNewLevel,lxX,lxNewBuffer);
            }

            if(y.right != null){
                RBTNode domRY = nodeMap.get(y.right.getCid());
                int ryNewBuffer = new Double(domRY.getBuffer()*rate).intValue() ;
                int ryNewLevel = domRY.getLevel() + 1;
                int ryNewWidth = domRY.getWidth() + ryNewBuffer ;
                int ryNewY = compute(ryNewLevel);


                //增加圆的动画
                this.addCircleAndTextAnimation(list,domRY.getCid(), domRY.getNodeTextId(),ryNewWidth , ryNewY);
                //增加线的动画
                this.addLineAnimation(list,changeList ,ycid , domRY.getCid() ,null,null,
                        yNewWidth, yNewY + r , ryNewWidth,ryNewY - r);

                //右侧子节点的处理
                refreshLeftRotateChild(list , changeList , domRY ,ryNewLevel , ryNewWidth,ryNewBuffer,ryNewY );
                //重新刷新map里的x值
                refreshMap(y.right.getCid() , ryNewLevel,ryNewWidth,ryNewBuffer);
            }


            //不知道是不是为新节点
            if(x.right != null){
                RBTNode domRX = nodeMap.get(x.right.getCid());
                //用计算后的 x 宽度当buffer用
                int rxX = yNewWidth - domRX.getBuffer();
                int rxY = (domRX.getLevel())*height + defaultMt;
                //增加圆的动画
                this.addCircleAndTextAnimation(list,domRX.getCid(), domRX.getNodeTextId(),rxX , rxY);
                //增加线的动画
                //y的父节点变为 x的父节点 ,此处比较重要，  因为父节点变换了
                this.addLineAnimation(list ,changeList,xcid , domRX.getCid() , ycid , domRX.getCid() ,
                        yNewWidth, yNewY + r , rxX,rxY - r);

                //
                refreshLeftRotateYLChild(list , changeList , domRX ,domRX.getLevel() ,rxX , domRX.getBuffer(),rxY);
                //重新刷新map里的x值
                refreshMap(x.right.getCid() , domRX.getLevel(),rxX,domRX.getBuffer());

            }


            //重新刷新map里的x值
            refreshMap(x.getCid() , xNewLevel,xNewWidth,xNewBuffer);
            //重新刷新map里的y值
            refreshMap(y.getCid(),yNewLevel,yNewWidth,yNewBuffer);
        }
    }
    /*
     * 红黑树插入修正函数
     *
     * 在向红黑树中插入节点之后(失去平衡)，再调用该函数；
     * 目的是将它重新塑造成一颗红黑树。
     *
     * 参数说明：
     *     node 插入的结点        // 对应《算法导论》中的z
     */
    private void insertFixUp(RBTNode<T> node) {
        RBTNode<T> parent, gparent;
        // 若“父节点存在，并且父节点的颜色是红色”
        while (((parent = parentOf(node))!=null) && isRed(parent)) {
            gparent = parentOf(parent);

            //若“父节点”是“祖父节点的左孩子”
            if (parent == gparent.left) {
                // Case 1条件：叔叔节点是红色
                RBTNode<T> uncle = gparent.right;
                if ((uncle!=null) && isRed(uncle)) {
                    setBlack(parent);
                    setRed(gparent);
                    setBlack(uncle);
                    node = gparent;
                    continue;
                }

                // Case 2条件：叔叔是黑色，且当前节点是右孩子
                if (parent.right == node) {
                    RBTNode<T> tmp;
                    leftRotate(parent);
                    tmp = parent;
                    parent = node;
                    node = tmp;
                }

                // Case 3条件：叔叔是黑色，且当前节点是左孩子。
                setBlack(parent);
                setRed(gparent);
                rightRotate(gparent);
            } else {    //若“z的父节点”是“z的祖父节点的右孩子”
                // Case 1条件：叔叔节点是红色
                RBTNode<T> uncle = gparent.left;
                if ((uncle!=null) && isRed(uncle)) {
                    setBlack(parent);
                    setRed(gparent);
                    setBlack(uncle);
                    node = gparent;
                    continue;
                }

                // Case 2条件：叔叔是黑色，且当前节点是左孩子
                if (parent.left == node) {
                    RBTNode<T> tmp;
                    rightRotate(parent);
                    tmp = parent;
                    parent = node;
                    node = tmp;
                }

                // Case 3条件：叔叔是黑色，且当前节点是右孩子。
                setBlack(parent);
                setRed(gparent);
                leftRotate(gparent);
            }
        }
        // 将根节点设为黑色
        setBlack(this.mRoot);
    }

    /***
     * 寻找插入节点
     * @param node
     * @return
     */
    public RBTNode findInsertNode(RBTNode<T> node){
        int cmp;
        RBTNode<T> y = null;
        RBTNode<T> x = this.mRoot;

        // 1. 将红黑树当作一颗二叉查找树，将节点添加到二叉查找树中。
        // 通过x 为null的判断    。根据入参的值和节点比较， 然后将x 设置为子节点的值， 如果子节点为null， 则跳出判断比较， 说明node值应该放在此位置
        while (x != null) {
            y = x;
            cmp = node.key.compareTo(x.key);
            if (cmp < 0)
                x = x.left;
            else
                x = x.right;
        }

        return y;
    }

    /*
     * 将结点插入到红黑树中
     *
     * 参数说明：
     *     node 插入的结点        // 对应《算法导论》中的node
     */
    private void insert(RBTNode<T> node) {
        int cmp;
        //
        RBTNode<T> y = null;
        RBTNode<T> x = this.mRoot;

        // 1. 将红黑树当作一颗二叉查找树，将节点添加到二叉查找树中。
        // 通过x 为null的判断    。根据入参的值和节点比较， 然后将x 设置为子节点的值， 如果子节点为null， 则跳出判断比较， 说明node值应该放在此位置
        while (x != null) {
            y = x;
            cmp = node.key.compareTo(x.key);
            if (cmp < 0)
                x = x.left;
            else
                x = x.right;
        }

        //将节点的父节点设置为 y ,即上面最后查询的末级节点
        node.parent = y;
        if (y!=null) {
            //判断，如果小于父节点则放入到左节点， 如果大于父节点则放入到右侧节点
            cmp = node.key.compareTo(y.key);
            if (cmp < 0){
                y.left = node;
            }
            else{
                y.right = node;
            }
        } else {
            this.mRoot = node;
        }




        // 2. 设置节点的颜色为红色
        node.color = RED;

        // 3. 将它重新修正为一颗二叉查找树
        insertFixUp(node);
    }

    /*
     * 新建结点(key)，并将其插入到红黑树中
     *
     * 参数说明：
     *     key 插入结点的键值
     */
    public void insert(T key) {
        RBTNode<T> node=new RBTNode<T>(key,BLACK,null,null,null);

        // 如果新建结点失败，则返回。
        if (node != null)
            insert(node);
    }

    public void insert(ArrayNode arrayNode , RBTNode<T> node) {
        this.getNodeMap().put(arrayNode.getCid() , node);

        // 如果新建结点失败，则返回。
        if (node != null){
            insert(node);
        }
    }

    public void insert(ArrayNode arrayNode , T key) {
        RBTNode<T> node=new RBTNode<T>(key ,BLACK,null,null,null);
        node.setCid(arrayNode.getCid());
        node.setNodeTextId(arrayNode.getValueTextId());

        this.getNodeMap().put(arrayNode.getCid() , node);

        // 如果新建结点失败，则返回。
        if (node != null){
            insert(node);
        }
    }
    /*
     * 红黑树删除修正函数
     *
     * 在从红黑树中删除插入节点之后(红黑树失去平衡)，再调用该函数；
     * 目的是将它重新塑造成一颗红黑树。
     *
     * 参数说明：
     *     node 待修正的节点
     */
    private void removeFixUp(RBTNode<T> node, RBTNode<T> parent) {
        RBTNode<T> other;

        while ((node==null || isBlack(node)) && (node != this.mRoot)) {
            if (parent.left == node) {
                other = parent.right;
                if (isRed(other)) {
                    // Case 1: x的兄弟w是红色的  
                    setBlack(other);
                    setRed(parent);
                    leftRotate(parent);
                    other = parent.right;
                }

                if ((other.left==null || isBlack(other.left)) &&
                        (other.right==null || isBlack(other.right))) {
                    // Case 2: x的兄弟w是黑色，且w的俩个孩子也都是黑色的  
                    setRed(other);
                    node = parent;
                    parent = parentOf(node);
                } else {

                    if (other.right==null || isBlack(other.right)) {
                        // Case 3: x的兄弟w是黑色的，并且w的左孩子是红色，右孩子为黑色。  
                        setBlack(other.left);
                        setRed(other);
                        rightRotate(other);
                        other = parent.right;
                    }
                    // Case 4: x的兄弟w是黑色的；并且w的右孩子是红色的，左孩子任意颜色。
                    setColor(other, colorOf(parent));
                    setBlack(parent);
                    setBlack(other.right);
                    leftRotate(parent);
                    node = this.mRoot;
                    break;
                }
            } else {

                other = parent.left;
                if (isRed(other)) {
                    // Case 1: x的兄弟w是红色的  
                    setBlack(other);
                    setRed(parent);
                    rightRotate(parent);
                    other = parent.left;
                }

                if ((other.left==null || isBlack(other.left)) &&
                        (other.right==null || isBlack(other.right))) {
                    // Case 2: x的兄弟w是黑色，且w的俩个孩子也都是黑色的  
                    setRed(other);
                    node = parent;
                    parent = parentOf(node);
                } else {

                    if (other.left==null || isBlack(other.left)) {
                        // Case 3: x的兄弟w是黑色的，并且w的左孩子是红色，右孩子为黑色。  
                        setBlack(other.right);
                        setRed(other);
                        leftRotate(other);
                        other = parent.left;
                    }

                    // Case 4: x的兄弟w是黑色的；并且w的右孩子是红色的，左孩子任意颜色。
                    setColor(other, colorOf(parent));
                    setBlack(parent);
                    setBlack(other.left);
                    rightRotate(parent);
                    node = this.mRoot;
                    break;
                }
            }
        }

        if (node!=null){
            setBlack(node);

        }
    }

    /*
     * 删除结点(node)，并返回被删除的结点
     *
     * 参数说明：
     *     node 删除的结点
     *
     *
     *
     *
     *        List<ChangeAttrDetail> changeList = new ArrayList<>();
            changeAttr.setList(changeList);

            MultiMove multiMove = new MultiMove();
            List<GraphComponent> list = new ArrayList<>();

            multiMove.setGcs(list);


            animationTotal.addComponent(multiMove);
     */
    private void remove(RBTNode<T> node) {
        RBTNode<T> child, parent;
        boolean color;

        //删除节点移动位置后的 父id
        String delPid  ;

        //属性变更
        ChangeAttr changeAttr = new ChangeAttr();
        List<ChangeAttrDetail> changeList = new ArrayList<>();
        if (animationFlag){

            changeAttr.setDs(true);
            replaceMap.put(RBTreeStepConstant.PLACEHOLDER_DEL_NODE , node.getKey().toString()) ;
            changeAttr.setList(changeList);
            animationTotal.addComponent(changeAttr);




            //删除标记虚化
            ChangeAttr delNodeChangeAttr = new ChangeAttr();
            List<ChangeAttrDetail> delNodeSignList = new ArrayList<>();
//            String delNodeText = "搜索到删除节点%s,并将其标注为删除节点。";
            //虚化 删除样式
            ChangeAttrDetail cad = new ChangeAttrDetail(node.getCid());
            cad.addValue("style" , "opacity:0.1");
            cad.setCt(ChangeAttrEnum.ADD.getType());
            delNodeSignList.add(cad);
            //虚化 删除样式
            ChangeAttrDetail textCad = new ChangeAttrDetail(node.getNodeTextId());
            textCad.addValue("style" , "opacity:0.1");
            textCad.setCt(ChangeAttrEnum.ADD.getType());
            delNodeSignList.add(cad);
            delNodeChangeAttr.setAd(this.getDesc(RBTreeStepConstant.FIND_DEL_NODE , replaceMap));
            delNodeChangeAttr.setList(delNodeSignList);
            animationTotal.addComponent(delNodeChangeAttr);

        }


        // 被删除节点的"左右孩子都不为空"的情况。
        if ( (node.left!=null) && (node.right!=null) ) {
            // 被删节点的后继节点。(称为"取代节点")
            // 用它来取代"被删节点"的位置，然后再将"被删节点"去掉。
            RBTNode<T> replace = node;

            // 获取后继节点
            replace = replace.right;
            while (replace.left != null){
                replace = replace.left;
            }

            //赋值map 替换节点
            if(animationFlag){
                replaceMap.put(RBTreeStepConstant.PLACEHOLDER_REPLACE_NODE , replace.getKey().toString()) ;
            }

            delPid = replace.parent.getCid() ;

            //删除节点的右子节点 等于后继结点
            boolean nodeRightEqualReplace = node.right == replace?true:false;
            if(nodeRightEqualReplace){
                delPid = replace.getCid();
            }
            if (animationFlag){
                //将后继结点设置为替换颜色
                ChangeColor cc = new ChangeColor("green",replace.getCid());

                cc.setAd(this.getDesc(RBTreeStepConstant.FIND_REPLACE_NODE , replaceMap));

                animationTotal.addComponent(cc);

                if(node.parent != null){
                    //修改线的id start
                    addChangeLineId(changeList ,node.parent.getCid() , node.getCid() , node.parent.getCid() , replace.getCid() );
                }

                if (node.left != null){
                    addChangeLineId(changeList ,node.getCid() , node.left.getCid() , replace.getCid() , node.left.getCid() );
                }

                //如果删除节点  等于 替换节点(右继节点) ,
                if(nodeRightEqualReplace){
                    addChangeLineId(changeList ,node.getCid() , node.right.getCid() , node.right.getCid() , node.getCid() );
                }else {
                    if(node.right != null) {
                        addChangeLineId(changeList ,node.getCid() , node.right.getCid() , replace.getCid() , node.right.getCid() );
                    }
                    addChangeLineId(changeList ,replace.parent.getCid() , replace.getCid() , replace.parent.getCid() , node.getCid() );

                }

                if(replace.left != null ){
                    addChangeLineId(changeList ,replace.getCid() , replace.left.getCid() , node.getCid() , replace.left.getCid() );
                }
                if(replace.right != null){
                    addChangeLineId(changeList ,replace.getCid() , replace.right.getCid() , node.getCid() , replace.right.getCid() );
                }
                //修改线的id end
            }





            RBTNode targetNode ;
            // "node节点"不是根节点(只有根节点不存在父节点)
            //将父节点的  子节点引用修改为 替换节点
            if (parentOf(node)!=null) {
                if (parentOf(node).left == node){
                    targetNode =parentOf(node).left;
                    parentOf(node).left = replace;
                }
                else{
                    targetNode =parentOf(node).right;
                    parentOf(node).right = replace;
                }
            } else {
                targetNode = this.mRoot;
                // "node节点"是根节点，更新根节点。
                this.mRoot = replace;

            }


            if (animationFlag){

                List<GraphComponent> list = new ArrayList<>();
                //增加圆的动画,将后继结点替换到删除节点上,  动画部分
                this.addCircleAndTextAnimation(list,replace.getCid(), replace.getNodeTextId(),node.getWidth() , compute(node.getLevel()));
                //将删除节点与替换节点互换位置
                this.addCircleAndTextAnimation(list,node.getCid(), node.getNodeTextId(),replace.getWidth() , compute(replace.getLevel()));
                MultiMove multiMove = new MultiMove();
                multiMove.setGcs(list);


                multiMove.setAd(this.getDesc(RBTreeStepConstant.SWITCH_DEL_REPLACE_NODE,replaceMap));
                this.animationTotal.addComponent(multiMove);

                int tLevel = targetNode.getLevel();
                int tWidth = targetNode.getWidth();
                int tBuffer = targetNode.getBuffer();

                int rLevel = replace.getLevel();
                int rWidth = replace.getWidth();
                int rBuffer = replace.getBuffer();



                //刷新map
                refreshMap(replace.getCid() , tLevel , tWidth , tBuffer);
                refreshMap(node.getCid() , rLevel , rWidth , rBuffer);
            }





            // child是"取代节点"的右孩子，也是需要"调整的节点"。
            // "取代节点"肯定不存在左孩子！因为它是一个后继节点。
            child = replace.right;
            //赋值替换节点的右侧节点
            if(animationFlag){
                if(child!=null){
                    replaceMap.put(RBTreeStepConstant.PLACEHOLDER_CHILD_NODE , child.getKey().toString());
                }
            }
            parent = parentOf(replace);//parent 为替换节点的父节点
            // 保存"取代节点"的颜色
            color = colorOf(replace);



            if(child !=null){

                delPid = child.getCid() ;
                if (animationFlag){
                    List<GraphComponent> listChild = new ArrayList<>();
                    //如果替换节点有右子节点,则将右子节点和删除节点再次替换位置
                    //增加圆的动画,将后继结点替换到删除节点上,  动画部分
                    this.addCircleAndTextAnimation(listChild,child.getCid(), child.getNodeTextId(),node.getWidth() , compute(node.getLevel()));
                    //将删除节点与替换节点互换位置
                    this.addCircleAndTextAnimation(listChild,node.getCid(), node.getNodeTextId(),child.getWidth() , compute(child.getLevel()));
                    MultiMove multiMove = new MultiMove();
                    multiMove.setGcs(listChild);


                    multiMove.setAd(this.getDesc(RBTreeStepConstant.SWITCH_DEL_CHILD_NODE , replaceMap));
                    this.animationTotal.addComponent(multiMove);

                    int nLevel = node.getLevel();
                    int nWidth = node.getWidth();
                    int nBuffer = node.getBuffer();

                    int cLevel = child.getLevel();
                    int cWidth = child.getWidth();
                    int cBuffer = child.getBuffer();


                    //todo  node 的parent 不是最新的  todo  修改项目
                    addChangeLineId(changeList ,replace.getParent().getCid() , node.getCid() , replace.getParent().getCid() , child.getCid() );
                    addChangeLineId(changeList ,node.getCid() , child.getCid() , child.getCid() , node.getCid() );

                    if(replace == node.right){
                        addChangeLineId(changeList ,replace.getCid() , node.getCid() , replace.getCid() , child.getCid() );
                    }
                    //刷新map
                    refreshMap(child.getCid() , nLevel  , nWidth , nBuffer);
                    refreshMap(node.getCid() , cLevel , cWidth , cBuffer);
                }
            }

            // "被删除节点"是"它的后继节点的父节点"
            if (parent == node) {
                parent = replace;
            } else {
                // child不为空 , 如果替换节点的右侧节点不为空
                if (child!=null){
                    setParent(child, parent);
                }
                parent.left = child;//替换节点的右节点设置为 替换节点的父节点的左节点
                replace.right = node.right;
                setParent(node.right, replace);
            }

            replace.parent = node.parent;//将替换节点的父节点设置为删除节点的父节点

            replace.color = node.color;
            replace.left = node.left;
            node.left.parent = replace;


            if (animationFlag){
                ChangeColor replaceColor = new ChangeColor(replace.color == BLACK?"black":"red",replace.getCid()) ;
                Map<String,String> m = new HashMap<>();
                m.put(RBTreeStepConstant.NODE , replace.getKey().toString());
                replaceColor.setAd(this.getDesc(RBTreeStepConstant.REPLACE_NODE_COLOR, m));
                animationTotal.addComponent(replaceColor);


                ChangeColor nodeColor = new ChangeColor(node.color == BLACK?"black":"red",node.getCid());

                m.put(RBTreeStepConstant.NODE , node.getKey().toString());
                nodeColor.setAd(this.getDesc(RBTreeStepConstant.REPLACE_NODE_COLOR, m));
                animationTotal.addComponent(nodeColor);
            }


            if (color == BLACK){
                removeFixUp(child, parent);
            }


            if (animationFlag){
                //删除元素
                removeCircle(node.getCid() ,  delPid);
            }

            node = null;
            return ;
        }

        if (node.left !=null) {
            child = node.left;
        } else {
            child = node.right;
        }


        if(child == null){
            delPid = node.getParent().getCid();
        }else {

            delPid = child.getCid();
        }


        parent = node.parent;
        // 保存"取代节点"的颜色
        color = node.color;

        if (child!=null){
            child.parent = parent;
        }

        // "node节点"不是根节点
        if (parent!=null) {
            if (parent.left == node){
                parent.left = child;
            }
            else{
                parent.right = child;
            }
        } else {
            this.mRoot = child;
        }


        //交换对象
        if (animationFlag){
            if(child != null) {
                List<GraphComponent> list = new ArrayList<>();
                int nLevel = node.getLevel();
                int nWidth = node.getWidth();
                int nBuffer = node.getBuffer();

                int cLevel = child.getLevel();
                int cWidth = child.getWidth();
                int cBuffer = child.getBuffer();

                //增加圆的动画,将后继结点替换到删除节点上,  动画部分
                this.addCircleAndTextAnimation(list,child.getCid(), child.getNodeTextId(),node.getWidth() , compute(node.getLevel()));
                //将删除节点与替换节点互换位置
                this.addCircleAndTextAnimation(list,node.getCid(), node.getNodeTextId(),child.getWidth() , compute(child.getLevel()));
                MultiMove multiMove = new MultiMove();
                multiMove.setGcs(list);

                multiMove.setAd(this.getDesc(  RBTreeStepConstant.SWITCH_DEL_AND_RIGHT_CHILD_NODE , replaceMap));
                this.animationTotal.addComponent(multiMove);

                addChangeLineId(changeList ,node.parent.getCid() , node.getCid() , node.parent.getCid() , child.getCid() );

                addChangeLineId(changeList , node.getCid() ,child.getCid()  , child.getCid() , node.getCid() );

                //刷新map
                refreshMap(child.getCid() , nLevel  , nWidth , nBuffer);
                refreshMap(node.getCid() , cLevel , cWidth , cBuffer);

                ChangeColor childNode = new ChangeColor(child.color == BLACK?"black":"red",child.getCid()) ;
                Map<String,String> m = new HashMap<>();
                m.put(RBTreeStepConstant.NODE , child.getKey().toString());
                childNode.setAd(this.getDesc(RBTreeStepConstant.NODE , m));
                animationTotal.addComponent(childNode);


                ChangeColor n = new ChangeColor(node.color == BLACK?"black":"red",node.getCid());
                m.put(RBTreeStepConstant.NODE , node.getKey().toString());
                childNode.setAd(this.getDesc(RBTreeStepConstant.NODE , m));
                animationTotal.addComponent(n);
            }


        }


        if (color == BLACK){
            removeFixUp(child, parent);
        }


        if (animationFlag){
            //删除元素
            removeCircle(node.getCid() ,  delPid);
        }
        node = null;
    }

    /*
     * 删除结点(z)，并返回被删除的结点
     *
     * 参数说明：
     *     tree 红黑树的根结点
     *     z 删除的结点
     */
    public RBTNode remove(T key) {
        RBTNode<T> node;

        if ((node = search(mRoot, key)) != null){
            //获取cid
            remove(node);
        }
        return node ;
    }

    /*
     * 销毁红黑树
     */
    private void destroy(RBTNode<T> tree) {
        if (tree==null)
            return ;

        if (tree.left != null)
            destroy(tree.left);
        if (tree.right != null)
            destroy(tree.right);

        tree=null;
    }

    public void clear() {
        destroy(mRoot);
        mRoot = null;
    }

    /*
     * 打印"红黑树"
     *
     * key        -- 节点的键值
     * direction  --  0，表示该节点是根节点;
     *               -1，表示该节点是它的父结点的左孩子;
     *                1，表示该节点是它的父结点的右孩子。
     */
    private void print(RBTNode<T> tree, T key, int direction) {

        if(tree != null) {

            if(direction==0)    // tree是根节点
                System.out.printf("%2d(B) is root\n", tree.key);
            else                // tree是分支节点
                System.out.printf("%2d(%s) is %2d's %6s child\n", tree.key, isRed(tree)?"R":"B", key, direction==1?"right" : "left");

            print(tree.left, tree.key, -1);
            print(tree.right,tree.key,  1);
        }
    }

    public void print() {
        if (mRoot != null)
            print(mRoot, mRoot.key, 0);
    }

    public boolean getAnimationFlag() {
        return animationFlag;
    }

    public void setAnimationFlag(boolean animationFlag) {
        this.animationFlag = animationFlag;
    }

    public AnimationTotal getAnimationTotal() {
        return animationTotal;
    }

    public void setAnimationTotal(AnimationTotal animationTotal) {
        this.animationTotal = animationTotal;
    }

    public Map<String, RBTNode> getNodeMap() {
        return nodeMap;
    }

    public void setNodeMap(Map<String, RBTNode> nodeMap) {
        this.nodeMap = nodeMap;
    }

    private int compute(int level){
//        return (level - 1)*height + defaultMt ;
        return level*height + defaultMt ;
    }

    private void refreshMap(String cid , Integer level , Integer width,Integer buffer){
        RBTNode rbtNode = this.getNodeMap().get(cid);
        if(rbtNode != null){
            rbtNode.setWidth(width);
            rbtNode.setLevel(level);
            rbtNode.setBuffer(buffer);
            this.getNodeMap().put(cid , rbtNode);
        }
    }


    /***
     * 增加圆和文本的动画
     * @param list
     * @param cid
     * @param textId
     * @param x
     * @param y
     */
    private void addCircleAndTextAnimation(List<GraphComponent> list , String cid , String textId , Integer x , Integer y ){
        Circle lxCircle = new Circle();
        lxCircle.setId(cid);
        lxCircle.setX(x);
        lxCircle.setY(y);
        list.add(lxCircle);
        Text lxText = new Text();
        lxText.setId(textId);
        lxText.setX(x);
        lxText.setY(y);
        list.add(lxText);
    }

    /***
     * 增加线的动画
     * @param list
     * @param newParentId , 如果线更换父子关系, ,则赋值新的  父节点id
     * @param newChildId  , 如果线更换父子关系, ,则赋值新的  父节点id
     */
    private void addLineAnimation(List<GraphComponent> list, List<ChangeAttrDetail> changeList ,
                                  String parentId,String childId,String newParentId,String newChildId,
                                  Integer x1 , Integer y1 , Integer x2,Integer y2){
        Line xlxLine = new Line();
        xlxLine.setId(parentId + "-" + childId);
        xlxLine.setX1(x1);
        xlxLine.setY1( y1);
        xlxLine.setX2(x2);
        xlxLine.setY2(y2);

        //当  线更换父子关系时， 赋值新的值
        if(StringUtils.isNotBlank(newParentId) && StringUtils.isNotBlank(newChildId)){
            xlxLine.setTid(newParentId + "-" + newChildId);
        }
        list.add(xlxLine);
    }


    /***
     * 修改线的id

     */
    private void addChangeLineId(List<ChangeAttrDetail> changeList ,
                                  String parentId,String childId,String newParentId,String newChildId){
        String id = parentId + "-" + childId;

        ChangeAttrDetail changeAttrDetail = new ChangeAttrDetail(id);

        changeAttrDetail.addValue("id" , newParentId + "-" + newChildId);


        changeList.add(changeAttrDetail);
    }

    /***
     * 删除元素
     */
    private void removeCircle(String cid , String delPid){

        RBTNode rbtNode = getNodeMap().get(cid);

        List<String> destroy = new ArrayList<>();
        destroy.add(cid);

        destroy.add(rbtNode.getNodeTextId());

        //删除节点已经被放到最末端 ,所以只需要删除
        destroy.add(delPid + "-" + cid);
        String[] c = destroy.toArray(new String[destroy.size()]);
        Destroy d = new Destroy(c);
        d.setAd( this.getDesc(RBTreeStepConstant.DEL_NODE , replaceMap));
        this.animationTotal.addComponent(d);
    }



    public Map<String, String> getStepExecute() {
        return stepExecute;
    }

    public void setStepExecute(Map<String, String> stepExecute) {
        this.stepExecute = stepExecute;
    }


    /***
     * 获取描述信息
     * @param code
     * @param replace
     * @return
     */
    private String getDesc(String code , Map<String,String> replace){

        String step =getStepExecute().get(code);

        for (Map.Entry<String, String> entry : replace.entrySet()) {

            step = step.replace(entry.getKey() , entry.getValue());
        }
        return step ;
    }
}