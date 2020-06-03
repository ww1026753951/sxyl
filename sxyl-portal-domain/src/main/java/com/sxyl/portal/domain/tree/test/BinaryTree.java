package com.sxyl.portal.domain.tree.test;

/**
 * @author: create by wei.wang
 * @version: v1.0
 * @description: com.sxyl.portal.domain.tree.test
 * @date:2020/4/7
 */
@Deprecated
public class BinaryTree<T extends Comparable> {

    private static class BinaryNode<T> {
        T element;
        BinaryNode<T> left;
        BinaryNode<T> right;

        BinaryNode(T element) {
            this(element, null, null);
        }

        BinaryNode(T element, BinaryNode<T> left, BinaryNode<T> right) {
            this.element = element;
            this.left = left;
            this.right = right;
        }
    }

    private BinaryNode<T> root;

    public BinaryTree() {
    }

    public BinaryTree (BinaryNode<T> root) {
        this.root = root;
    }

    public BinaryNode<T> getRoot() {
        return root;
    }

    /**
     * 清空二叉树
     */
    public void makeEmpty() {
        root = null;
    }

    /**
     * 检查二叉树是否为空
     * @return boolean
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * 查看二叉树中是否含有元素x
     * @param x 待查元素
     * @return boolean
     */
    public boolean contains(T x) {
        return contains(x, root);
    }

    /**
     * 查看根为root的二叉树中是否含有元素x
     * @param x 待查元素
     * @param root 二叉树根
     * @return boolean
     */
    private boolean contains(T x, BinaryNode<T> root) {
        if (x == null) return false;

        int compareResult;

        while ((root != null) && ((compareResult = x.compareTo(root.element)) != 0)) {
            if (compareResult < 0) {
                root = root.left;
            } else {
                root = root.right;
            }
        }

        return null != root;
    }

    /**
     * 寻找此二叉树中的最小值
     * @return T
     */
    public T findMin() {
        if (isEmpty()) return null;
        return findMin(root).element;
    }

    /**
     * 寻找子二叉树中的最小值
     * @param root 子二叉树根
     * @return BinaryNode<T>
     */
    public BinaryNode<T> findMin(BinaryNode<T> root) {
        if (root == null) return null;

        while ( root.left != null ) {
            root = root.left;
        }

        return root;
    }

    /**
     * 寻找此二叉树中的最大值
     * @return T
     */
    public T findMax() {
        if (isEmpty()) return null;
        return findMax(root).element;
    }

    /**
     * 寻找子二叉树中的最大值
     * @param root 子二叉树根
     * @return BinaryNode<T>
     */
    public BinaryNode<T> findMax(BinaryNode<T> root) {
        if (root == null) return null;

        while ( root.right != null ) {
            root = root.right;
        }

        return root;
    }

    /**
     * 插入x到树
     * @param x 插入值
     */
    public void insert(T x) {
        root = insert(x, root);
    }

    /**
     * 插入x到子树root
     * @param x 插入值
     * @param root 子树根
     * @return 新子树根
     */
    public BinaryNode<T> insert(T x, BinaryNode<T> root) {
        // 空树
        if (root == null) return new BinaryNode<>(x);

        // 树非空，找到插入位置
        int compareResult;
        BinaryNode<T> index = root;     // 寻找插入位置索引

        // 寻找到插入位置就插入
        while ( (compareResult = x.compareTo(index.element)) != 0 ) {   // 当前索引位置值不等于插入值
            if (compareResult < 0 ) {
                if (index.left == null) {       // 寻找到插入位置
                    index.left = new BinaryNode<>(x);
                    break;
                }
                else index = index.left;        // 未寻找到插入位置，继续寻找
            } else if (index.right == null){    // 寻找到插入位置
                index.right = new BinaryNode<>(x);
                break;
            } else {                            // 未寻找到插入位置，继续寻找
                index = index.right;
            }
        }

        return root;
    }

    /**
     * 将二叉树转换成链表，使用先序遍历
     * @param temp 二叉树根
     */
    public void flatten(BinaryNode temp) {
        if (temp != null) {
            BinaryNode tempLeft = temp.left;
            BinaryNode tempRight = temp.right;

            if (tempLeft == null) {         // 左子树为空，不需要进行左右指针变换
                flatten(tempRight);
                return;
            }

            flatten(tempLeft);      // 先序遍历
            flatten(tempRight);

            // 把左子树加到右边，再把右子树加到左子树后面
            temp.left = null;         // 设置左子树为空
            temp.right = tempLeft;    // 把左子树连到右边

            while (tempLeft.right != null) {       // 找到左子树的叶节点
                tempLeft = tempLeft.right;
            }

            tempLeft.right = tempRight;   // 把右子树加到左子树的叶子节点上
        }
    }

    /**
     * 先序遍历，中左右
     * @param temp 树根
     */
    public void preOrder(BinaryNode temp) {
        if (temp != null) {
            System.out.println(temp.element);     // 先访问父节点
            preOrder(temp.left);
            preOrder(temp.right);
        }
    }

    /**
     * 中序遍历，左中右
     * @param temp 树根
     */
    public void inOrder(BinaryNode temp) {
        if (temp != null) {
            inOrder(temp.left);
            System.out.println(temp.element);
            inOrder(temp.right);
        }
    }

    /**
     * 后序遍历，左右中
     * @param temp 树根
     */
    public void postOrder(BinaryNode temp) {
        if (temp != null) {
            postOrder(temp.left);
            postOrder(temp.right);
            System.out.println(temp.element);
        }
    }

    /**
     * 按照顺序打印树
     */
    public void printTree() {
        if (isEmpty()) System.out.println("Empty Tree!");
        else printTree(root);
    }

    /**
     * 按照顺序打印树
     * @param temp 树根
     */
    public void printTree(BinaryNode temp) {
        if (temp != null) {
            printTree(temp.left);
            System.out.println(temp.element);
            printTree(temp.right);
        }
    }

    /**
     * 计算树高
     * @param temp 树根
     * @return 树高
     */
    public int height(BinaryNode temp) {
        if (temp == null) return -1;
        else return 1 + Math.max(height(temp.right) ,height(temp.right));
    }

    /**
     * 在树中删除一个元素
     * @param x 待删除元素
     * @param t 树根
     * @return 新的树根
     */
    public BinaryNode<T> remove(T x, BinaryNode<T> t) {
        if (t == null) return t;

        int compareResult = x.compareTo(t.element);
        if (compareResult < 0) {
            t.left = remove(x, t.left);
        } else if (compareResult > 0) {
            t.right = remove(x, t.right);
        } else if (t.right != null && t.left != null) { // 找到此节点，有2个子节点
            t.element = findMin(t.right).element;       // 找到右子树上的最小节点，与当前节点对调
            t.right = remove(t.element, t.right);       // 在右子树上删除最小节点
        } else {
            t = (t.left != null) ? t.left : t.right;    // 找到此节点，子节点数少于2,
        }

        return t;
    }

    /**
     * 根据前序遍历和中序遍历重建二叉树
     * @param pre 前序遍历序列
     * @param startPre 前序遍历序列头
     * @param endPre   前序遍历序列尾
     * @param in    中序遍历序列
     * @param startIn   中序遍历序列头
     * @param endIn     中序遍历序列尾
     * @return 二叉树
     */
    public static BinaryNode reconstructBinaryTree(int[] pre, int startPre, int endPre, int[] in, int startIn ,int endIn) {
        if(startPre > endPre || startIn > endIn) return null;

        BinaryNode root = new BinaryNode(pre[startPre]);

        for(int i = startIn; i <= endIn; i++) {
            if (in[i] == pre[startPre]) {   // 找到中序遍历的根节点，则左树节点数为i - startIn
                root.left = reconstructBinaryTree(pre, startPre + 1, startPre + i - startIn,
                        in, startIn, i - 1);    // 找出左子树的前序和中序遍历，构建左子树
                root.right = reconstructBinaryTree(pre, i - startIn + startPre + 1, endPre,
                        in, i + 1, endIn);      // 找出右子树的前序和中序遍历，构建右子树
            }
        }
        return root;
    }
}
