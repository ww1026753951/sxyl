package com.sxyl.portal.service.nn.impl;

import com.sxyl.portal.domain.constant.CommonConstant;
import com.sxyl.portal.domain.constant.NeuronTypeEnum;
import com.sxyl.portal.domain.nn.NnWeight;
import com.sxyl.portal.domain.nn.dnn.*;
import com.sxyl.portal.domain.nn.dnn.param.DnnConstructParam;
import com.sxyl.portal.domain.nn.dnn.param.DnnParam;
import com.sxyl.portal.service.nn.NnCommonService;
import com.sxyl.portal.service.nn.NnParamService;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NnParamServiceImpl extends NnCommonService implements NnParamService {

    //输入层中文描述
    private final String INPUT_LAYER_NAME = "输入层";
    //输入层别名
    private final String INPUT_LAYER_ALIAS = "inputLayer";
    //输入层单元别名
    private final String INPUT_ID_ALIAS = "inputId";

    //增加别名id
    private final String BIAS_ID_ALIAS = "biasId";

    //隐藏层中文描述
    private final String HIDDEN_LAYER_NAME = "隐藏层";
    //隐藏层别名
    private final String HIDDEN_LAYER_ALIAS = "hiddenLayer";
    //隐藏层单元别名
    private final String HIDDEN_ID_ALIAS = "hiddenId";


    //输出层中文描述
    private final String OUT_LAYER_NAME = "输出层";
    //输出层别名
    private final String OUT_LAYER_ALIAS = "outputLayer";
    //输出层单元别名
    private final String OUT_ID_ALIAS = "outId";





    @Override
    public DnnConstructParam createDnnParam(DnnParam dnnParam , int inputNum, int[] hiddenNum, int outputNum) {
        DnnConstructParam dnnConstructParam = new DnnConstructParam();

        //输入层对象
        createDnnInputLayer(dnnConstructParam,dnnParam,inputNum);

        //隐藏层对象
        dnnConstructParam.setDnnHiddenLayerList(createDnnHiddenLayer(dnnParam ,dnnConstructParam , hiddenNum));

        //输出层对象
        dnnConstructParam.setOutputLayer(createDnnOutLayer(dnnParam , dnnConstructParam.getDnnHiddenLayerList().get(dnnConstructParam.getDnnHiddenLayerList().size()-1) , outputNum));

        return dnnConstructParam;
    }

    /****
     * 获取输入层结构
     * @param inputNum
     * @return
     */
    private void createDnnInputLayer(DnnConstructParam dnnConstructParam ,DnnParam dnnParam ,int inputNum){
        Map<String , DnnInputNeuron> inputNeuronMap = new HashMap<>();
        DnnInputLayer dnnInputLayer = new DnnInputLayer();
        dnnInputLayer.setLayerName(INPUT_LAYER_NAME);
        List<DnnInputNeuron> neurons = new ArrayList<>();
        DnnInputNeuron dnnInputNeuron ;

        for (int i = 0 ; i < inputNum ; i++){
            dnnInputNeuron = new DnnInputNeuron();
            //神经元类型和基础数据
            dnnInputNeuron.setNeuronType(NeuronTypeEnum.INPUT_NEURON.getCode());
            dnnInputNeuron.setIndex(i+1);
            dnnInputNeuron.setId(INPUT_LAYER_ALIAS + CommonConstant.HYPHEN + new Integer(i));
            //输入框文本类型
            dnnInputNeuron.setText(dnnParam.getInputText());
            dnnInputNeuron.setTextId(super.TEXT_NAME_ID + dnnInputNeuron.getId() + CommonConstant.HYPHEN + INPUT_ID_ALIAS );
            dnnInputNeuron.setValueText(dnnInputNeuron.getIndex().toString());
            dnnInputNeuron.setValueTextId(TEXT_NAME_ID + BOTTOM + dnnInputNeuron.getId() + OUT_ID );
            neurons.add(dnnInputNeuron);
            inputNeuronMap.put(dnnInputNeuron.getId() , dnnInputNeuron);
        }
        //增加偏置量层

        DnnBiasNeuron dnnBiasNeuron = new DnnBiasNeuron();
        //神经元类型和基础数据
        dnnBiasNeuron.setNeuronType(NeuronTypeEnum.BIAS.getCode());
        dnnBiasNeuron.setIndex(0);
        dnnBiasNeuron.setId(INPUT_LAYER_ALIAS + CommonConstant.HYPHEN + new Integer(inputNum));
        //输入框文本类型
        dnnBiasNeuron.setText(dnnParam.getBiasText() + dnnBiasNeuron.getIndex());
        dnnBiasNeuron.setTextId(super.TEXT_NAME_ID + dnnBiasNeuron.getId() + CommonConstant.HYPHEN  +BIAS_ID_ALIAS );
        dnnBiasNeuron.setValueText(dnnBiasNeuron.getIndex().toString());
        dnnBiasNeuron.setValueTextId(TEXT_NAME_ID + BOTTOM + dnnBiasNeuron.getId()  +BIAS_ID_ALIAS);
        dnnInputLayer.setDnnBiasNeuron(dnnBiasNeuron);
        dnnInputLayer.setNeurons(neurons);

        //设置输入层
        dnnConstructParam.setDnnInputLayer(dnnInputLayer);
        //设置输入层map
        dnnConstructParam.setInputNeuronMap(inputNeuronMap);
        return  ;
    }

    /****
     * 获取隐藏层结构
     * @param hiddenNum
     * @return
     */
    private List<DnnHiddenLayer> createDnnHiddenLayer(DnnParam dnnParam ,DnnConstructParam dnnConstructParam, int[] hiddenNum){

        Map<String , DnnHiddenNeuron> inputNeuronMap = new HashMap<>();
        List<DnnHiddenLayer> dnnHiddenLayers = new ArrayList<DnnHiddenLayer>();
        //id 为神经单元id ,index为神经单元下标
        String id ; Integer index;
        for (int i=0 ; i < hiddenNum.length ; i++){
            DnnHiddenLayer dnnHiddenLayer = new DnnHiddenLayer();
            dnnHiddenLayer.setLayerName(HIDDEN_LAYER_NAME);
            List<DnnHiddenNeuron> neurons = new ArrayList<>();
            DnnHiddenNeuron dnnHiddenNeuron ;
            for (int j = 0 ; j < hiddenNum[i] ; j++){
                dnnHiddenNeuron = new DnnHiddenNeuron();
                id = HIDDEN_LAYER_ALIAS+ new Integer(i) + CommonConstant.HYPHEN + new Integer(j);
                index = j + 1;
                //神经元类型和基础数据
                dnnHiddenNeuron.setNeuronType(NeuronTypeEnum.HIDDEN_NEURON.getCode());
                dnnHiddenNeuron.setIndex(index);
                dnnHiddenNeuron.setId(id);
                //求和的部分
                dnnHiddenNeuron.setSumTextId(  TEXT_NAME_ID + id + super.NET_ID );
//                dnnHiddenNeuron.setSumText("h_" + NET + index);

                dnnHiddenNeuron.setSumText(dnnParam.getSumText());
                dnnHiddenNeuron.setSumValueTextId(TEXT_NAME_ID + BOTTOM + id + super.NET_ID );
                dnnHiddenNeuron.setSumValueText("n"+ index);
                //激活函数的部分
                dnnHiddenNeuron.setActivationTextId(TEXT_NAME_ID + id + super.OUT_ID);
//                dnnHiddenNeuron.setActivationText("h_" + OUT + index);
                dnnHiddenNeuron.setActivationText(dnnParam.getActivationText()) ;

                dnnHiddenNeuron.setActivationValueTextId(TEXT_NAME_ID + BOTTOM + id + OUT_ID );
                dnnHiddenNeuron.setActivationValueText("o"+index);
                //创建权重
                dnnHiddenNeuron.setNeuronWeight(this.createFirstHiddenWeight(dnnConstructParam.getDnnInputLayer() , dnnHiddenNeuron));
                neurons.add(dnnHiddenNeuron);
                //设置神经元map
                inputNeuronMap.put(dnnHiddenNeuron.getId() , dnnHiddenNeuron);
            }

            //偏置量
            DnnBiasNeuron dnnBiasNeuron = new DnnBiasNeuron();
            //神经元类型和基础数据
            dnnBiasNeuron.setNeuronType(NeuronTypeEnum.BIAS.getCode());
            dnnBiasNeuron.setIndex(0);
            dnnBiasNeuron.setId(HIDDEN_LAYER_ALIAS + CommonConstant.HYPHEN + new Integer(hiddenNum[i]));
            //输入框文本类型
            dnnBiasNeuron.setText(dnnParam.getActivationText() + dnnBiasNeuron.getIndex());
            dnnBiasNeuron.setTextId(super.TEXT_NAME_ID + dnnBiasNeuron.getId() + CommonConstant.HYPHEN  + BIAS_ID_ALIAS );
            dnnBiasNeuron.setValueText("1");
            dnnBiasNeuron.setValueTextId(TEXT_NAME_ID + BOTTOM + dnnBiasNeuron.getId() + BIAS_ID_ALIAS );
            dnnHiddenLayer.setDnnBiasNeuron(dnnBiasNeuron);

            dnnHiddenLayer.setNeurons(neurons);
//            if(i==0){
//                dnnHiddenLayer.set
//            }
            dnnHiddenLayers.add(dnnHiddenLayer);
        }


        //设置输入层map
        dnnConstructParam.setHiddenNeuronMap(inputNeuronMap);
        return dnnHiddenLayers ;
    }


    /**
     * 创建第一个隐藏层的权重list
     * @return
     */
    private List<NnWeight> createFirstHiddenWeight(DnnInputLayer dnnInputLayer,DnnHiddenNeuron dnnHiddenNeuron){
        List<NnWeight> list = new ArrayList<>();
        for (DnnInputNeuron dnnInputNeuron : dnnInputLayer.getNeurons()){
            list.add( this.createNnWeight(
                    dnnInputNeuron.getId(),dnnHiddenNeuron.getId(),
                    dnnInputNeuron.getIndex() ,dnnHiddenNeuron.getIndex()));
        }
        return list ;
    }


    /***
     * 创建神经元权重
     * @param sid
     * @param tid
     * @param sourceIndex
     * @param targetIndex
     * @return
     */
    private NnWeight createNnWeight(String sid , String tid  , Integer sourceIndex, Integer targetIndex){
        NnWeight nnWeight = new NnWeight();
        //权重的文本
        nnWeight.setId(super.getNeuronToNeuronWeightId(sid,tid));
        //权重的文本展示, 最早用的是后缀,但是后期用的子dom  tspan ,所以主文本上不需要有任何后缀
        nnWeight.setText(super.getWeightText(""));
        //权重的值
        nnWeight.setValueId(super.getNeuronToNeuronWeightValue(sid,tid));
        nnWeight.setValueText(new DecimalFormat("#0.00").format(Math.random()));
        nnWeight.setSid(sid);
        nnWeight.setTid(tid);

        nnWeight.setSourceIndex(sourceIndex);
        nnWeight.setTargetIndex(targetIndex);
        return nnWeight ;
    }


    /****
     * 创建输出层
     * @param dnnParam
     * @param outputNum
     * @return
     */
    private DnnOutputLayer createDnnOutLayer(DnnParam dnnParam  ,  DnnHiddenLayer dnnHiddenLayer ,int outputNum){
        DnnOutputLayer dnnOutputLayer = new DnnOutputLayer();
        //id 为神经单元id ,index为神经单元下标
        String id ; Integer index;
        dnnOutputLayer.setLayerName(OUT_LAYER_NAME);
        List<DnnOutputNeuron> neurons = new ArrayList<>();
        DnnOutputNeuron dnnOutputNeuron ;
        for (int i = 0 ; i < outputNum ; i++){
            dnnOutputNeuron = new DnnOutputNeuron();

            index = i+1;
            id = OUT_LAYER_ALIAS+ CommonConstant.HYPHEN + new Integer(i);

            //神经元类型和基础数据
            dnnOutputNeuron.setNeuronType(NeuronTypeEnum.OUTPUT_NEURON.getCode());
            dnnOutputNeuron.setIndex(index);
            dnnOutputNeuron.setId(id);
            //输入框文本类型


            //求和的部分
            dnnOutputNeuron.setSumTextId(  TEXT_NAME_ID + id + super.NET_ID );
//            dnnOutputNeuron.setSumText("o_" + NET + index);
            dnnOutputNeuron.setSumText(dnnParam.getSumText());
            dnnOutputNeuron.setSumValueTextId(TEXT_NAME_ID + BOTTOM + id + super.NET_ID );
            dnnOutputNeuron.setSumValueText("n"+ index);
            //激活函数的部分
            dnnOutputNeuron.setActivationTextId(TEXT_NAME_ID + id + super.OUT_ID);
//            dnnOutputNeuron.setActivationText("h_" + OUT + index);
            dnnOutputNeuron.setActivationText(dnnParam.getActivationText());
            dnnOutputNeuron.setActivationValueTextId(TEXT_NAME_ID + BOTTOM + id + OUT_ID );
            dnnOutputNeuron.setActivationValueText("o"+index);


//            errorId , super.NET_ID)
            dnnOutputNeuron.setActualTextId(TEXT_NAME_ID + getErrorTextId(id) + super.NET_ID);
            dnnOutputNeuron.setActualText("y=" + index);

            dnnOutputNeuron.setCostValueTextId(TEXT_NAME_ID + BOTTOM + getErrorTextId(id) + OUT_ID );
            dnnOutputNeuron.setCostValueText("error"+index+"=???");

            dnnOutputNeuron.setNeuronWeight(createOutHiddenWeight(dnnHiddenLayer , dnnOutputNeuron));

            neurons.add(dnnOutputNeuron);
        }
        dnnOutputLayer.setNeurons(neurons);
        return dnnOutputLayer ;
    }


    /***
     * 创建输出层的权重list
     * @param dnnHiddenLayer
     * @param dnnOutputNeuron
     * @return
     */
    private List<NnWeight> createOutHiddenWeight(DnnHiddenLayer dnnHiddenLayer , DnnOutputNeuron dnnOutputNeuron){
        List<NnWeight> list = new ArrayList<>();
        for (DnnHiddenNeuron dnnHiddenNeuron : dnnHiddenLayer.getNeurons()){
            list.add( this.createNnWeight(
                    dnnHiddenNeuron.getId(),dnnOutputNeuron.getId(),
                    dnnHiddenNeuron.getIndex() ,dnnOutputNeuron.getIndex()));
        }

        return list ;
    }
}
