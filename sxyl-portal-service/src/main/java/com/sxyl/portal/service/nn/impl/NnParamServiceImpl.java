package com.sxyl.portal.service.nn.impl;

import com.sxyl.portal.domain.constant.CommonConstant;
import com.sxyl.portal.domain.constant.NeuronTypeEnum;
import com.sxyl.portal.domain.nn.dnn.*;
import com.sxyl.portal.domain.nn.dnn.param.DnnConstructParam;
import com.sxyl.portal.domain.nn.dnn.param.DnnParam;
import com.sxyl.portal.service.nn.NnCommonService;
import com.sxyl.portal.service.nn.NnParamService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NnParamServiceImpl extends NnCommonService implements NnParamService {

    //输入层中文描述
    private final String INPUT_LAYER_NAME = "输入层";
    //输入层别名
    private final String INPUT_LAYER_ALIAS = "inputLayer";
    //输入层单元别名
    private final String INPUT_ID_ALIAS = "inputId";


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
        dnnConstructParam.setDnnInputLayer(createDnnInputLayer(dnnParam,inputNum));

        //隐藏层对象
        dnnConstructParam.setDnnHiddenLayerList(createDnnHiddenLayer(dnnParam,hiddenNum));

        //输出层对象
        dnnConstructParam.setOutputLayer(createDnnOutLayer(dnnParam , outputNum));

        return dnnConstructParam;
    }

    /****
     * 获取输入层结构
     * @param inputNum
     * @return
     */
    private DnnInputLayer createDnnInputLayer(DnnParam dnnParam ,int inputNum){
        DnnInputLayer dnnInputLayer = new DnnInputLayer();
        dnnInputLayer.setLayerName(INPUT_LAYER_NAME);
        List<DnnInputNeuron> neurons = new ArrayList<>();
        DnnInputNeuron dnnInputNeuron ;
        for (int i = 0 ; i < inputNum ; i++){
            dnnInputNeuron = new DnnInputNeuron();
            //神经元类型和基础数据
            dnnInputNeuron.setNeuronType(NeuronTypeEnum.NEURON.getCode());
            dnnInputNeuron.setIndex(i+1);
            dnnInputNeuron.setId(INPUT_LAYER_ALIAS + CommonConstant.HYPHEN + new Integer(i));
            //输入框文本类型
            dnnInputNeuron.setText(dnnParam.getInputText() + dnnInputNeuron.getIndex());
            dnnInputNeuron.setTextId(super.TEXT_NAME_ID + dnnInputNeuron.getId() + CommonConstant.HYPHEN + INPUT_ID_ALIAS );
            dnnInputNeuron.setValueText(dnnInputNeuron.getIndex().toString());
            dnnInputNeuron.setValueTextId( TEXT_NAME_ID + BOTTOM + dnnInputNeuron.getId() + OUT_ID );
            neurons.add(dnnInputNeuron);
        }
        dnnInputLayer.setNeurons(neurons);
        return dnnInputLayer ;
    }

    /****
     * 获取隐藏层结构
     * @param hiddenNum
     * @return
     */
    private List<DnnHiddenLayer> createDnnHiddenLayer(DnnParam dnnParam , int[] hiddenNum){
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
                dnnHiddenNeuron.setNeuronType(NeuronTypeEnum.NEURON.getCode());
                dnnHiddenNeuron.setIndex(index);
                dnnHiddenNeuron.setId(id);
                //求和的部分
                dnnHiddenNeuron.setSumTextId(  TEXT_NAME_ID + id + super.NET_ID );
                dnnHiddenNeuron.setSumText("h_" + NET + index);
                dnnHiddenNeuron.setSumValueTextId(TEXT_NAME_ID + BOTTOM + id + super.NET_ID );
                dnnHiddenNeuron.setSumValueText("n"+ index);
                //激活函数的部分
                dnnHiddenNeuron.setActivationTextId(TEXT_NAME_ID + id + super.OUT_ID);
                dnnHiddenNeuron.setActivationText("h_" + OUT + index);
                dnnHiddenNeuron.setActivationValueTextId(TEXT_NAME_ID + BOTTOM + id + OUT_ID );
                dnnHiddenNeuron.setActivationValueText("o"+index);
                neurons.add(dnnHiddenNeuron);
            }
            dnnHiddenLayer.setNeurons(neurons);
            dnnHiddenLayers.add(dnnHiddenLayer);
        }
        return dnnHiddenLayers ;
    }


    /****
     * 创建输出层
     * @param dnnParam
     * @param outputNum
     * @return
     */
    private DnnOutputLayer createDnnOutLayer(DnnParam dnnParam ,int outputNum){
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
            dnnOutputNeuron.setNeuronType(NeuronTypeEnum.NEURON.getCode());
            dnnOutputNeuron.setIndex(index);
            dnnOutputNeuron.setId(id);
            //输入框文本类型


            //求和的部分
            dnnOutputNeuron.setSumTextId(  TEXT_NAME_ID + id + super.NET_ID );
            dnnOutputNeuron.setSumText("o_" + NET + index);
            dnnOutputNeuron.setSumValueTextId(TEXT_NAME_ID + BOTTOM + id + super.NET_ID );
            dnnOutputNeuron.setSumValueText("n"+ index);
            //激活函数的部分
            dnnOutputNeuron.setActivationTextId(TEXT_NAME_ID + id + super.OUT_ID);
            dnnOutputNeuron.setActivationText("h_" + OUT + index);
            dnnOutputNeuron.setActivationValueTextId(TEXT_NAME_ID + BOTTOM + id + OUT_ID );
            dnnOutputNeuron.setActivationValueText("o"+index);


            neurons.add(dnnOutputNeuron);
        }
        dnnOutputLayer.setNeurons(neurons);
        return dnnOutputLayer ;
    }
}