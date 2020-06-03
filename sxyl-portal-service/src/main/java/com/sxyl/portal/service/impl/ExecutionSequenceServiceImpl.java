package com.sxyl.portal.service.impl;

import com.sxyl.portal.domain.ExecutionSequence;
import com.sxyl.portal.domain.constant.AlgorithmTypeEnum;
import com.sxyl.portal.domain.constant.BinaryTreeStepConstant;
import com.sxyl.portal.domain.constant.RBTreeStepConstant;
import com.sxyl.portal.domain.nn.dnn.param.DnnConstructParam;
import com.sxyl.portal.domain.nn.dnn.result.DnnConstruct;
import com.sxyl.portal.service.ExecutionSequenceService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ExecutionSequenceServiceImpl implements ExecutionSequenceService {


    /***
     * 红黑树的步骤说明
     */
    Map<String , String> rbTreeSequence = new ConcurrentHashMap<String , String>();



    /***
     * 二叉树的步骤说明
     */
    Map<String , String> binarySequence = new ConcurrentHashMap<String , String>();

    Map<String , List<ExecutionSequence>> stepSequence = new HashMap<String, List<ExecutionSequence>>(){{
        put("1", getBubbleStep());
        put("2", getCocktailShakerSort());
    }};

    @Override
    public List<ExecutionSequence> queryExecutionSequence(String type) {
        if(type==null){
            type="1";
        }
        List result = stepSequence.get(type);
        if (result==null){
            return new ArrayList<ExecutionSequence>();
        }
        return result;
    }

    /****
     * 获取冒泡算法执行步骤
     * @return
     */
    private List<ExecutionSequence> getBubbleStep(){
        List<ExecutionSequence> bubbleStep = new ArrayList<ExecutionSequence>(){{
            add(new ExecutionSequence("step-content","background1","background1","根据数组长度循环,0-&gt;{SIZE},当前下标{INDEX}"));
            add(new ExecutionSequence("step-content","background2","background2","根据数组长度循环,0-&gt;{SIZE},当前下标{INDEX}"));
            add(new ExecutionSequence("step-content","a","A1","1-相邻的俩个元素比较大小。"));
            add(new ExecutionSequence("step-content","a","A2","2-如果前一个元素比后一个元素大,交换位置,执行下次比较。"));
            add(new ExecutionSequence("step-content","a","A3","3-如果前一个元素比后一个元素小。直接执行下次循环"));
            add(new ExecutionSequence("step-content","a","A4","4-元素排序完成"));
            add(new ExecutionSequence("step-content","a","A-END","5-数组排序完毕"));
            //伪代码
            add(new ExecutionSequence("pseudo-code","a","A1","for(int i=0;i&lt;array.length;i++){"));
            add(new ExecutionSequence("pseudo-code","a","A2","&nbsp;&nbsp;&nbsp;&nbsp;for(int j=0;j&lt;array.length;j++){"));
            add(new ExecutionSequence("pseudo-code","a","A3","&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;if(array[i]&gt;array[i+1]){"));
            add(new ExecutionSequence("pseudo-code","a","A4","&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;swap(array[i],array[i+1])"));
            add(new ExecutionSequence("pseudo-code","","","&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}"));
            add(new ExecutionSequence("pseudo-code","","","&nbsp;&nbsp;&nbsp;&nbsp;}"));
            add(new ExecutionSequence("pseudo-code","","","}"));
        }};
        return bubbleStep;
    }

    /****
     * 获取鸡尾酒算法执行步骤
     * @return
     */
    private List<ExecutionSequence> getCocktailShakerSort(){
        List<ExecutionSequence> bubbleStep = new ArrayList<ExecutionSequence>(){{
            add(new ExecutionSequence("step-content","background1","background1","根据数组长度循环,0-&gt;{SIZE},当前下标{INDEX}"));
            add(new ExecutionSequence("step-content","background2","background2","根据数组长度循环,{SIZE}-&gt;0,当前下标{INDEX}"));
            add(new ExecutionSequence("step-content","a","A1","1-相邻的俩个元素比较大小。"));
            add(new ExecutionSequence("step-content","a","A2","2-如果前一个元素比后一个元素大,交换位置,执行下次比较。"));
            add(new ExecutionSequence("step-content","a","A3","3-如果前一个元素比后一个元素小。直接执行下次循环"));
            add(new ExecutionSequence("step-content","a","A4","4-元素排序完成"));
            add(new ExecutionSequence("step-content","a","A-END","5-数组排序完毕"));
            //伪代码 Pseudo code
            add(new ExecutionSequence("pseudo-code","a","A1","for(int i=0;i&lt;array.length;i++){"));
            add(new ExecutionSequence("pseudo-code","a","A2","&nbsp;&nbsp;&nbsp;&nbsp;for(int j=0;j&lt;array.length;j++){"));
            add(new ExecutionSequence("pseudo-code","a","A3","&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;if(array[i]&gt;array[i+1]){"));
            add(new ExecutionSequence("pseudo-code","a","A4","&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;swap(array[i],array[i+1])"));
            add(new ExecutionSequence("pseudo-code","","","&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}"));
            add(new ExecutionSequence("pseudo-code","","","&nbsp;&nbsp;&nbsp;&nbsp;}"));
            add(new ExecutionSequence("pseudo-code","","","}"));
        }};
        return bubbleStep;
    }


    /****
     * 全连接神经网络的执行步骤
     */
    public List<ExecutionSequence> queryDnnExecutionSequence(DnnConstructParam dnnConstructParam){
        List<ExecutionSequence> list = new ArrayList<>();



        return list ;
    }

    @Override
    public ExecutionSequence getExecutionSequenceByTypeAndCode(String type, String code, Map<String, String> replace) {

        ExecutionSequence executionSequence = new ExecutionSequence();

        Map<String,String> executeMap = getRbTreeSequenceMap();

        String step =executeMap.get(type+code);


        for (Map.Entry<String, String> entry : replace.entrySet()) {

            step.replaceAll(entry.getKey() , entry.getValue());
        }

        executionSequence.setStepDesc(step);
        return executionSequence;
    }

    @Override
    public Map<String, String> getExecutionSequenceByType(int type) {

        if(type == AlgorithmTypeEnum.BINARY.getType()){
            return getBinarySequenceMap();
        }else if (type == AlgorithmTypeEnum.RB_TREE.getType()){

            return getRbTreeSequenceMap();
        }
        return null;

    }

    private Map<String,String> getRbTreeSequenceMap(){
        if (rbTreeSequence.size() ==0){
            //找寻删除节点步骤
            rbTreeSequence.put( RBTreeStepConstant.REPLACE_NODE_COLOR , "将节点{NODE}修改颜色");
            //找寻删除节点步骤
            rbTreeSequence.put( RBTreeStepConstant.FIND_DEL_NODE , "搜索到删除节点 {DEL-NODE},并将其标注为删除节点。");
            //交换删除和替换节点
            rbTreeSequence.put( RBTreeStepConstant.SWITCH_DEL_REPLACE_NODE , "将删除节点{DEL-NODE}和替换节点{REPLACE-NODE}交换位置。");
            //交换删除和替换节点
            rbTreeSequence.put( RBTreeStepConstant.SWITCH_DEL_CHILD_NODE , "将删除节点{DEL-NODE}和替换节点的子节点{CHILD-NODE}交换位置。");
            //交换删除和替换节点
            rbTreeSequence.put( RBTreeStepConstant.SWITCH_DEL_AND_RIGHT_CHILD_NODE , "将删除节点{DEL-NODE}和替换节点的右子节点{CHILD-NODE}交换位置。");
            //交换删除和替换节点
            rbTreeSequence.put( RBTreeStepConstant.DEL_NODE , "将删除节点{DEL-NODE}从红黑树中移出。");
            //交换删除和替换节点
            rbTreeSequence.put( RBTreeStepConstant.FIND_REPLACE_NODE , "删除节点为{DEL-NODE},寻找删除节点的后继结点{REPLACE-NODE},将后继节点标志为绿色。");
            //右旋操作
            rbTreeSequence.put( RBTreeStepConstant.RIGHT_ROTATE , "以{X-NODE}为x节点,以{Y-NODE}为y节点进行右旋操作。");
            //左旋操作
            rbTreeSequence.put( RBTreeStepConstant.LEFT_ROTATE , "以{X-NODE}为x节点,以{Y-NODE}为y节点进行左旋操作。");
        }
        return rbTreeSequence;
    }

    private Map<String,String> getBinarySequenceMap(){
        if (binarySequence.size() == 0 ){
            //找寻左侧节点的位置步骤
            binarySequence.put( BinaryTreeStepConstant.REPLACE_FIND_LEFT_NODE_COLOR , "将比较节点{NODE}修改为灰色，比较新节点{NEW-NODE}与{NODE}的大小，因为插入节点比节点小，则需要再比较节点的左侧节点。");
            //找寻左侧节点的位置步骤
            binarySequence.put( BinaryTreeStepConstant.REPLACE_FIND_RIGHT_NODE_COLOR, "将比较节点{NODE}修改为灰色，比较新节点{NEW-NODE}与{NODE}的大小，因为插入节点比节点大，则需要再比较节点的右侧节点。");

        }
        return binarySequence;
    }
}
