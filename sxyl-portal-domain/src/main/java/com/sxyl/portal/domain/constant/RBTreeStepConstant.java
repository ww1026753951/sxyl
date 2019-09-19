package com.sxyl.portal.domain.constant;

/**
 * @author: create by wei.wang
 * @version: v1.0
 * @description: com.sxyl.portal.domain.constant
 * @date:2019/8/21
 */
public class RBTreeStepConstant {



    public final static String TYPE = "rbtree" ;


    /***
     * 删除节点替换颜色
     */
    public final static String REPLACE_NODE_COLOR = "replace-node-color" ;

    /***
     * 找寻删除节点
     */
    public final static String FIND_DEL_NODE = "find-del-node" ;


    /***
     * 找寻替换节点
     */
    public final static String FIND_REPLACE_NODE = "find-replace-node" ;


    /***
     * 交换删除和替换节点
     */
    public final static String SWITCH_DEL_REPLACE_NODE = "switch-del-replace-node" ;



    /***
     * 交换删除和替换节点
     */
    public final static String SWITCH_DEL_CHILD_NODE = "switch-del-child-node" ;



    /***
     * 交换删除和右子替换节点
     */
    public final static String SWITCH_DEL_AND_RIGHT_CHILD_NODE = "switch-del-child-right-node" ;



    /***
     * 右旋的处理
     */
    public final static String RIGHT_ROTATE = "right-rotate" ;


    /***
     * 右旋的处理
     */
    public final static String LEFT_ROTATE = "left-rotate" ;


    /***
     * 删除节点
     */
    public final static String DEL_NODE = "del-node" ;






    /***
     * 删除节点
     */
    public final static String PLACEHOLDER_DEL_NODE = "{DEL-NODE}";


    /***
     * 替换节点
     */
    public final static String PLACEHOLDER_REPLACE_NODE = "{REPLACE-NODE}";


    /***
     * 替换节点的右子节点
     */
    public final static String PLACEHOLDER_CHILD_NODE = "{CHILD-NODE}";


    /***
     * 替换节点的右子节点
     */
    public final static String NODE = "{NODE}";


    /***
     * x节点
     */
    public final static String X_NODE = "{X-NODE}";


    /***
     * y节点
     */
    public final static String Y_NODE = "{Y-NODE}";
}
