package com.sxyl.portal.service.nn.impl;

import com.sxyl.portal.domain.d.AnimationTotal;
import com.sxyl.portal.domain.graph.Group;
import com.sxyl.portal.domain.nn.dnn.param.DnnConstructParam;
import com.sxyl.portal.domain.nn.dnn.result.DnnConstruct;
import com.sxyl.portal.service.nn.NnAnimationService;
import com.sxyl.portal.service.nn.NnConstructService;
import com.sxyl.portal.service.nn.NnFormulaService;
import com.sxyl.portal.service.nn.NnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NnServiceImpl implements NnService {


    @Autowired
    private NnConstructService nnConstructService;

    @Autowired
    private NnAnimationService nnAnimationService;

    @Autowired
    private NnFormulaService nnFormulaService;


    @Override
    public DnnConstruct getDnnConstruct(int inputNum, int[] hiddenNum, int outputNum , DnnConstructParam dnnConstructParam) {
        DnnConstruct result = new DnnConstruct();
        //输入层的ids
        List<String> inputIds = new ArrayList<>();
        //隐藏层的ids
        List<List<String>> hiddenIds = new ArrayList<>();
        //输出层的ids
        List<String> outIds = new ArrayList<>();

        //输入层 拼接id
        for (int i =0;i<inputNum;i++){
            inputIds.add("inputLayer-"+i);
        }
        //隐藏层 拼接id
        for (int i=0;i<hiddenNum.length;i++){
            List<String> hiddenId = new ArrayList<>();
            for (int j =0;j<hiddenNum[i];j++){
                hiddenId.add("hiddenLayer"+i+"-"+j);
            }
            hiddenIds.add(hiddenId);
        }
        //输出层 拼接id
        for (int i=0;i<outputNum;i++){
            outIds.add("outputLayer-"+i);
        }


        // dnn结构
        Group group =nnConstructService.getDnnConstruct(inputIds,hiddenIds,outIds,  dnnConstructParam);
        result.setGroup(group);

        //dnn动画
        AnimationTotal animationTotal =nnAnimationService.getNnAnimation(inputIds,hiddenIds,outIds);
        result.setAt(animationTotal);

        //dnn 公式部分
        result.setMfs(nnFormulaService.getDnnFormulaTotal());

        // dnn 公式部分
        Group fa =nnFormulaService.getDnnFormulaDetailSVG(inputIds,hiddenIds,outIds);
        result.setFa(fa);


        return result;
    }


}
