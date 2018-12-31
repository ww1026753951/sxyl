package com.sxyl.portal.service.nn;

import com.sxyl.portal.domain.constant.formula.FormulaConstant;
import org.apache.commons.lang.StringUtils;

import java.lang.annotation.Target;

public class NnCommonService {


    //输入层的文本前缀
    private final String INPUT_TEXT="X";

    //隐藏层 net 的文本前缀
    protected String NET = "∑";

    //输出层的 out 的文本前缀
    protected String OUT = "out";


    //误差项的 target 的文本前缀
    private String TARGET = "target";


    //误差项的 error 的文本前缀
    private String ERROR = "error";

    //权重的文本前缀
    private final String WEIGHT="W";



    //输入层的后缀
    protected String INPUT_ID_SUFFIX ="-inputId";

    //隐藏层 net 后缀
    protected String NET_ID = "-netId";

    //隐藏层 out 后缀
    protected String OUT_ID = "-outId";


    //文本的前缀
    protected String TEXT_NAME_ID ="t-";



    protected String BOTTOM ="bottom-";


    //-----------------------------公式篇
    private String FORMULA_PREFIX = "f-";
    private String sum="sum-";

    private String sigmoid="sigmoid-";

    private String squaredError="squared-error-";


    private String updateWeight="update-weight-";

    private String FORMULA_SUM_NET_NODE_TEXT = FORMULA_PREFIX+sum+"%s" + "%s"+"-node";

    private String FORMULA_RESULT ="result-" ;


    /***
     * 获得公式id
     * @return
     */
    protected String getFormulaId(){
        return "formula_id";
    }


    //common--------------------------------------------


    /***
     * 获取文本的id
     * @param id
     * @return
     */
    protected String getTextId(String id, String suffix){
        return TEXT_NAME_ID + id + suffix ;
    }


//    protected String getBottomTextId(String id){
//        return TEXT_NAME_ID + BOTTOM + id;
//    }


    protected String getDeltaOutputText(String value){

        return "δout" +value;
    }


    protected String getDeltaOutputNodeId(String id){

        return "delta"+"node" +id;
    }


    /****
     * 权重的nodeId
     * @param sid
     * @param tid
     * @return
     */
    protected String getOutputWeightNodeId(String sid , String tid){

        return WEIGHT +"node"+sid+"-"+tid;
    }


    //输入层------------------------------------------
    /***
     * 输入层的文本展示
     * @param value
     * @return
     */
    protected String getInputText(String value){
        return INPUT_TEXT + value;
    }



    //权重部分-------------------------------------------
    /****
     * 权重的文本展示
     * @param value
     * @return
     */
    protected String getWeightText(String value){
        return WEIGHT + value;
    }




    //隐藏层-------------------------------
    /*****
     * 获取隐藏层的net 文本
     * @param index ,下标数量
     * @return
     */
    protected String getHiddenNetText(String index){
        return "h_" + NET + index;
    }

    //输出层 ---------------------


    protected String getOutNetText(String index){
        return "o_" + NET + index;
    }


    protected String getOutOutText(String index){
        return "o_" + OUT  + index ;
    }

    /*****
     * 获取隐藏层的net 文本
     * @param index ,下标数量
     * @return
     */
    protected String getHiddenOutText(String index){
        return "h_" + OUT + index;
    }
    //误差项-----------------------------------

    /*****
     * 获取误差层，y实际值的文本
     * @param index ,下标数量
     * @return
     */
    protected String getTargetNetText(String index){
        return TARGET+index;
    }


    /****
     * 获取error 文本
     * @param index
     * @return
     */
    protected String getErrorText(String index){
        return ERROR +  index;
    }

    /***
     * 获取error id
     * @param id
     * @return
     */
    protected String getErrorTextId(String id){
        return ERROR +  id;
    }



    /*****
     * 获取隐藏层的sigmoid net 文本
     * @param index ,下标数量
     * @return
     */
    protected String getHiddenSigmoidText(String index){
        return OUT + index;
    }


    /*****
     * 获取隐藏层的net 文本 输入值 id值
     * @param hiddenId
     * @return
     */
    protected String getHiddenBottomNetId(String hiddenId){
        return TEXT_NAME_ID + BOTTOM + hiddenId + NET_ID;
    }

    /****
     * 获取隐藏层的out 文本 id值
     * @param outId
     * @return
     */
    protected String getHiddenOutId(String outId){
        return TEXT_NAME_ID + outId + OUT_ID ;
    }

    /****
     * 获取隐藏层 out 文本的 value id 值
     * @param outId
     * @return
     */
    protected String getHiddenBottomOutId(String outId){
        return TEXT_NAME_ID + BOTTOM + outId + OUT_ID ;

    }

    /*****
     * 获取神经单元到神经单元权重的id
     * @param sid
     * @param tid
     * @return
     */
    protected String getNeuronToNeuronWeightId(String sid,String tid){
        return TEXT_NAME_ID+sid+"-"+tid;
    }

    /*****
     * 获取神经单元到神经单元权重的值
     * @param sid
     * @param tid
     * @return
     */
    protected String getNeuronToNeuronWeightValue(String sid,String tid){
        return TEXT_NAME_ID+sid+"-"+tid+"-value";
    }


    //公式篇--------------------------------------------------------------------------------------

    /***
     * 获取求和信息
     * @param id
     * @return
     */
    protected String getSumInput(String id){
        return sum+id;
    }

    /****
     * sigmoid函数
     * @param id
     * @return
     */
    protected String getSigmoidId(String id){
        return sigmoid + id ;
    }

    /****
     * error函数
     * @param id
     * @return
     */
    protected String getFormulaSquaredErrorId(String id){
        return squaredError + id ;
    }

    /****
     * error函数
     * @param tid
     * @param sid
     * @return
     */
    protected String getFormulaUpdateWeightId(String tid , String sid){
        return updateWeight + tid + "-" + sid ;
    }

    /****
     * sigmoid函数
     * @param id
     * @return
     */
    protected String getSigmoidNodeId(String id){
        return sigmoid +"node" +id ;
    }

    //获取求和节点id
    protected String getSumNodeId(String s, String t){
        return String.format(FORMULA_SUM_NET_NODE_TEXT , s ,t);
    }

    /***
     * 获取公式结果id
     * @param id
     * @return
     */
    protected String getFormulaResultId(String id){
        return FORMULA_RESULT + id;
    }


    /***
     * 平方差target id
     * @param id
     * @return
     */
    protected String getSquaredErrorTargetNodeId(String id){
        return TARGET +"node" +id ;
    }

    /***
     * 平方差error id
     * @param id
     * @return
     */
    protected String getSquaredErrorErrorNodeId(String id){
        return ERROR +"node" +id ;
    }


    /****
     * 节点 target
     * @param id
     * @return
     */
    protected String getOutWeightTargetNodeId(String id){
        return TARGET +"node"+id;
    }


    /***
     * 节点out
     * @param id
     * @param stepNo
     * @return
     */
    protected String getOutWeightOutNodeId(String id ,String stepNo){
        return TARGET + "node"+ id + stepNo ;
    }

    protected String getOutWeightOutHiddenNodeId(String id){
        return OUT + "node" + id;
    }

}
