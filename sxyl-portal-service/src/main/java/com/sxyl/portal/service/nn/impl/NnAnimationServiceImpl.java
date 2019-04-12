package com.sxyl.portal.service.nn.impl;

import com.sxyl.portal.domain.d.*;
import com.sxyl.portal.domain.d.compute.*;
import com.sxyl.portal.domain.nn.dnn.DnnOutputNeuron;
import com.sxyl.portal.domain.nn.dnn.param.DnnConstructParam;
import com.sxyl.portal.service.nn.NnAnimationService;
import com.sxyl.portal.service.nn.NnCommonService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NnAnimationServiceImpl extends NnCommonService implements NnAnimationService {




    @Override
    public AnimationTotal getNnAnimation(List<String> inputIds, List<List<String>> hiddenIds, List<String> outputIds , DnnConstructParam dnnConstructParam) {

        AnimationTotal total = new AnimationTotal();


        //todo ,暂时写死
        List<String> firstHidden = hiddenIds.get(0);

        this.addAnimation(total, inputIds , firstHidden);

        this.addAnimation(total , firstHidden,outputIds);


        this.addError(total , dnnConstructParam , outputIds);

        this.addOutWeight(total ,dnnConstructParam,firstHidden , outputIds);


        this.addHiddenWeight(total , inputIds , firstHidden , outputIds);

        return total;
    }


    /***
     * 隐藏层 和输出层的计算过程
     * @param total
     * @param sid
     * @param tid
     */
    private void addAnimation(AnimationTotal total , List<String> sid,List<String> tid){

        Integer stepNo = 1;

        for (String t:tid){
            //公式拷贝,根据 目标元素的id ，获取目标元素的计算逻辑
            total.addComponent(new FormulaCopy(super.getSumInput(t) , super.getFormulaId()));
            List<String> sumIds = new ArrayList<>();
            //按照层与层之间的数量循环
            for (String s : sid){

                //输入层输入层文本的id
                String sMiddleTextId = super.getHiddenBottomOutId(s) ;
                //输入层中间文本的id拷贝
                String copyMiddleId = getCopyId(sMiddleTextId , stepNo);

                sumIds.add(copyMiddleId);

                //复制对象
                total.addComponent(new Copy(sMiddleTextId , copyMiddleId));
                stepNo++;
                //移动对象到权重的文本
                Move moveToWeight = new Move();
                moveToWeight.setId(copyMiddleId);
                moveToWeight.setTid(super.getNeuronToNeuronWeightId(s, t));
//                moveToWeight.setTid("MJMATHI-62");
                //buffer 设置为 -50
                moveToWeight.setBx(-50);
                total.addComponent(moveToWeight);
                stepNo++;



                //乘积的
                Multiply multiply = new Multiply();
                List<String> mids=new ArrayList<>();
                mids.add(copyMiddleId);
                mids.add(super.getNeuronToNeuronWeightValue(s,t));
                multiply.setIds(mids);
                multiply.setTid(copyMiddleId);
                total.addComponent(multiply);


                //移动到公式 乘积
                Move moveToFormula = new Move();
                moveToFormula.setId(copyMiddleId);
                moveToFormula.setTid(super.getSumNodeId(s,t));
                moveToFormula.setBx(30);
                total.addComponent(moveToFormula);

                //销毁公式中的对象
                total.addComponent(new Destroy(super.getSumNodeId(s,t)));

            }

            //求和所有数值
            Sum sum = new Sum();
            sum.setTid(super.getFormulaResultId(t));
            sum.setIds(sumIds);
            total.addComponent(sum);

            //销毁对象
            for (String id:sumIds){
                Destroy destroy = new Destroy();
                destroy.setId(id);
                total.addComponent(destroy);
            }

            //移动公式结果对象到
            Move moveToHiddenResult = new Move();
            moveToHiddenResult.setId(super.getFormulaResultId(t));
            moveToHiddenResult.setTid(super.getHiddenBottomNetId(t));
            total.addComponent(moveToHiddenResult);

            //将计算结果的值赋予 net
            total.addComponent(new ChangeContent(super.getHiddenBottomNetId(t) , super.getFormulaResultId(t) ));

            //销毁公式中计算结果对象
            total.addComponent(new Destroy(super.getFormulaResultId(t)));




            //将sigmoid公式拷贝到处理逻辑中
            total.addComponent(new FormulaCopy(super.getSigmoidId(t) , super.getFormulaId()));

            String copyBottomNetId = this.getCopyId(super.getHiddenBottomNetId(t) ,stepNo);

            Copy c = new Copy();
            c.setSid(super.getHiddenBottomNetId(t) );
            c.setTidEnd(copyBottomNetId);
            total.addComponent(c);

            //将 隐藏层 的net值 移动到公式中的节点
            total.addComponent(new Move(copyBottomNetId , super.getSigmoidNodeId(t)));

            //销毁公式中的节点
            total.addComponent(new Destroy(super.getSigmoidNodeId(t)));

            //进行sigmoid 计算
            total.addComponent(new Sigmoid(super.getFormulaResultId(t),copyBottomNetId));

            //将结算结果放入到
            total.addComponent(new Move(super.getFormulaResultId(t) , super.getHiddenBottomOutId(t)));

            //删除拷贝元素
            total.addComponent(new Destroy(copyBottomNetId));

            //变更内容,将公式计算结果替换到输出层的结果上
            total.addComponent(new ChangeContent( super.getHiddenBottomOutId(t) , super.getFormulaResultId(t)));



        }


    }


    /***
     * 增加损失函数
     * @param total
     * @param dnnConstructParam
     */
    private void addError(AnimationTotal total ,DnnConstructParam dnnConstructParam , List<String> tid){

        int stepNo = 0 ;

        List<DnnOutputNeuron> outputNeuronList = dnnConstructParam.getOutputLayer().getNeurons();

//        for (String t : tid){
        for (DnnOutputNeuron dnnOutputNeuron : outputNeuronList){
            String t = dnnOutputNeuron.getId();
            String errorId = super.getErrorTextId(t);
            //将error公式拷贝到处理逻辑中
            total.addComponent(new FormulaCopy(super.getFormulaSquaredErrorId(t) , super.getFormulaId()));

            //隐藏层的输出对象
//            String copyBottomNetId = this.getCopyId(super.getHiddenBottomOutId(t) ,stepNo);
            String copyBottomNetId = this.getCopyId(dnnOutputNeuron.getActivationValueTextId() ,stepNo);

//            total.addComponent(new Copy(super.getHiddenBottomOutId(t) , copyBottomNetId));
            total.addComponent(new Copy(dnnOutputNeuron.getActivationValueTextId() , copyBottomNetId));

            Move etNode =new Move(copyBottomNetId , super.getSquaredErrorTargetNodeId(t)) ;
            etNode.setBx(10);
            total.addComponent(etNode);

            total.addComponent(new Destroy(super.getSquaredErrorTargetNodeId(t)));

            //error 底部的id
            String copyBottomOutId = this.getCopyId(super.getHiddenBottomOutId(errorId) , stepNo);

            total.addComponent(new Copy(super.getHiddenBottomOutId(errorId) ,copyBottomOutId , true));


            Move eeNode = new Move(copyBottomOutId , super.getSquaredErrorErrorNodeId(t)) ;
            eeNode.setBx(10);
            total.addComponent(eeNode);

            total.addComponent(new Destroy(super.getSquaredErrorErrorNodeId(t)));

            total.addComponent(new SquaredError( super.getFormulaResultId(t) , copyBottomNetId , copyBottomOutId));
            //将结算结果放入到
//            total.addComponent(new Move(super.getFormulaResultId(t) , super.getHiddenBottomOutId(errorId)));
            total.addComponent(new Move(super.getFormulaResultId(t) , dnnOutputNeuron.getCostValueTextId()));
            //删除拷贝元素
            total.addComponent(new Destroy(copyBottomNetId));
            total.addComponent(new Destroy(copyBottomOutId));
            //变更内容,将公式计算结果替换到输出层的结果上
//            total.addComponent(new ChangeContent( super.getHiddenBottomOutId(errorId) , super.getFormulaResultId(t)));
            total.addComponent(new ChangeContent(dnnOutputNeuron.getCostValueTextId() , super.getFormulaResultId(t)));
            total.addComponent(new Destroy(super.getFormulaResultId(t)));
            stepNo++;

        }


    }


    /***
     * 更新输出层权重
     * @param total
     * @param sidList
     * @param tidList
     */
    private void addOutWeight(AnimationTotal total ,DnnConstructParam dnnConstructParam, List<String> sidList ,List<String> tidList){
        int stepNo =0 ;

        for (String tid : tidList){
            String errorId = super.getErrorTextId(tid);
            for (String sid : sidList){

                //将error公式拷贝到处理逻辑中
                total.addComponent(new FormulaCopy(super.getFormulaUpdateWeightId(tid,sid) , super.getFormulaId()));

                //隐藏层的输出对象
                String copyBottomTargetId = this.getCopyId(super.getHiddenBottomOutId(errorId) ,stepNo);
                total.addComponent(new Copy(super.getHiddenBottomOutId(errorId) , copyBottomTargetId));

                Move targetError = new Move(copyBottomTargetId , super.getOutWeightTargetNodeId(tid));
                targetError.setBx(10);
                total.addComponent(targetError);

                total.addComponent(new Destroy(super.getOutWeightTargetNodeId(tid))) ;

                String copyBottomOutId0 = this.getCopyId(super.getHiddenBottomOutId(tid) ,stepNo);
                stepNo++;
                String copyBottomOutId1 = this.getCopyId(super.getHiddenBottomOutId(tid) ,stepNo);
                stepNo++;
                String copyBottomOutId2 = this.getCopyId(super.getHiddenBottomOutId(tid) ,stepNo);


                total.addComponent(new Copy(super.getHiddenBottomOutId(tid) , copyBottomOutId0));
                total.addComponent(new Copy(super.getHiddenBottomOutId(tid) , copyBottomOutId1));
                total.addComponent(new Copy(super.getHiddenBottomOutId(tid) , copyBottomOutId2));


                Move outNode0 = new Move(copyBottomOutId0 , super.getOutWeightOutNodeId(tid,"0"));
                outNode0.setBx(20);
                total.addComponent(outNode0);
                //添加销毁
                total.addComponent(new Destroy(super.getOutWeightOutNodeId(tid,"0")));


                Move outNode1 = new Move(copyBottomOutId1 , super.getOutWeightOutNodeId(tid,"1"));
                outNode1.setBx(20);
                total.addComponent(outNode1);
                total.addComponent(new Destroy(super.getOutWeightOutNodeId(tid,"1")));

                Move outNode2 = new Move(copyBottomOutId2 , super.getOutWeightOutNodeId(tid,"2"));
                outNode2.setBx(20);
                total.addComponent(outNode2);
                total.addComponent(new Destroy(super.getOutWeightOutNodeId(tid,"2")));


                String hiddenCopyId = this.getCopyId(sid ,stepNo) ;
                Copy hiddenCopy =new Copy(super.getHiddenBottomOutId(sid) , hiddenCopyId) ;
                total.addComponent(hiddenCopy);

                Move hiddenNode1 = new Move(hiddenCopyId , super.getOutWeightOutHiddenNodeId(sid));
                hiddenNode1.setBx(30);
                total.addComponent(hiddenNode1);


                total.addComponent(new Destroy( super.getOutWeightOutHiddenNodeId(sid)));


                //计算
                ComputeOutWeight computeOutWeight = new ComputeOutWeight();
                computeOutWeight.setTid(super.getFormulaResultId(tid));
                computeOutWeight.setTargetId(copyBottomTargetId);
                computeOutWeight.setOuto(super.getHiddenBottomOutId(tid));
                computeOutWeight.setOuth(super.getHiddenBottomOutId(sid));
                total.addComponent(computeOutWeight);



                //将结算结果放入到
                total.addComponent(new Move(super.getFormulaResultId(tid) , super.getNeuronToNeuronWeightValue(sid , tid)));


                //变更内容,将公式计算结果替换到输出层的结果上
                total.addComponent(new ChangeContent(super.getNeuronToNeuronWeightValue(sid , tid) , super.getFormulaResultId(tid)));

                total.addComponent(new Destroy(super.getFormulaResultId(tid)));


                //销毁对象
                total.addComponent(new Destroy(copyBottomTargetId))
                        .addComponent(new Destroy(copyBottomOutId0))
                        .addComponent(new Destroy(copyBottomOutId1))
                        .addComponent(new Destroy(copyBottomOutId2))
                        .addComponent(new Destroy(hiddenCopyId))
                        ;

                stepNo++ ;

            }

        }

    }


    /***
     * 隐藏层权重
     * @param total
     * @param inputList
     * @param hiddenList
     */
    private void addHiddenWeight(AnimationTotal total , List<String> inputList ,List<String> hiddenList , List<String> targetList){
        int stepNo =0 ;
        for (int i = 0  ; i <inputList.size() ; i++) {
            String sid = inputList.get(i);

            for (int j = 0 ; j<hiddenList.size() ;j++) {
                String tid = hiddenList.get(j);
                //将error公式拷贝到处理逻辑中
                total.addComponent(new FormulaCopy(super.getFormulaUpdateWeightId(tid, sid), super.getFormulaId()));


                List<String> weight = new ArrayList<>();
                List<String> targetId = new ArrayList<>();
                List<String> outId = new ArrayList<>();

                List<String> copyId = new ArrayList<>();
                for (int k =0; k<targetList.size() ; k++) {


                        String errorId = super.getErrorTextId(targetList.get(k));
                        weight.add(super.getNeuronToNeuronWeightValue(tid , targetList.get(k)));

                        targetId.add(super.getHiddenBottomOutId(errorId));

                        outId.add(super.getHiddenBottomOutId(targetList.get(k)));



                        String copyWeightId = this.getCopyId(super.getNeuronToNeuronWeightValue(tid , targetList.get(k) ) , stepNo);
                        total.addComponent(new Copy(super.getNeuronToNeuronWeightValue(tid , targetList.get(k)) ,copyWeightId));
                        Move hiddenNode = new Move(copyWeightId , super.getOutputWeightNodeId(tid , targetList.get(k)));
                        hiddenNode.setBx(20);
                        total.addComponent(hiddenNode);
                        total.addComponent(new Destroy(super.getOutputWeightNodeId(tid , targetList.get(k))));
                        stepNo++;
                        copyId.add(copyWeightId);

                }


                String copyHiddenId0 = this.getCopyId(super.getHiddenBottomOutId(tid) , stepNo);
                total.addComponent(new Copy(super.getHiddenBottomOutId(tid) ,copyHiddenId0));
                Move hiddenNode = new Move(copyHiddenId0 , super.getOutWeightOutNodeId(tid , "0"));
                hiddenNode.setBx(20);
                total.addComponent(hiddenNode);
                total.addComponent(new Destroy( super.getOutWeightOutNodeId(tid , "0")));

                stepNo++ ;


                String copyHiddenId1 = this.getCopyId(super.getHiddenBottomOutId(tid) , stepNo);
                total.addComponent(new Copy(super.getHiddenBottomOutId(tid) ,copyHiddenId1));
                Move hiddenNode1 = new Move(copyHiddenId1, super.getOutWeightOutNodeId(tid , "1"));
                hiddenNode1.setBx(20);
                total.addComponent(hiddenNode1);
                total.addComponent(new Destroy( super.getOutWeightOutNodeId(tid , "1")));

                stepNo++ ;

                String copyInputId = this.getCopyId(super.getHiddenBottomOutId(sid) , stepNo);
                total.addComponent(new Copy(super.getHiddenBottomOutId(sid) ,copyInputId));
                Move inputNode = new Move(copyInputId , super.getOutWeightOutHiddenNodeId(sid));
                inputNode.setBx(20);
                total.addComponent(inputNode);
                total.addComponent(new Destroy(super.getOutWeightOutHiddenNodeId(sid)));


                stepNo++ ;


                ComputeHiddenWeight computeHiddenWeight = new ComputeHiddenWeight();
                computeHiddenWeight.setTid(super.getFormulaResultId(sid));
                computeHiddenWeight.setTargetId(targetId);
                computeHiddenWeight.setOutId(outId);
                computeHiddenWeight.setWeightId(weight);

                computeHiddenWeight.setOid(super.getHiddenBottomOutId(tid));
                computeHiddenWeight.setIv(super.getHiddenBottomOutId(sid));


                total.addComponent(computeHiddenWeight);


                //将结算结果放入到
                total.addComponent(new Move(super.getFormulaResultId(sid) , super.getNeuronToNeuronWeightValue(sid , tid)));


                //变更内容,将公式计算结果替换到输出层的结果上
                total.addComponent(new ChangeContent(super.getNeuronToNeuronWeightValue(sid , tid) , super.getFormulaResultId(sid)));

                total.addComponent(new Destroy(super.getFormulaResultId(sid)));

                for (String s :copyId){
                    total.addComponent(new Destroy(s));
                }

                total.addComponent(new Destroy(copyHiddenId0));
                total.addComponent(new Destroy(copyHiddenId1));
                total.addComponent(new Destroy(copyInputId));

//                if (j==0){
//                    return;
//                }


            }
        }
    }

    /****
     * 获取复制id的信息
     * @param sid
     * @return
     */
    private String getCopyId(String sid,Integer stepNo){
        String copyId = "c-"+stepNo+"-"+sid;
        return copyId;
    }

}
