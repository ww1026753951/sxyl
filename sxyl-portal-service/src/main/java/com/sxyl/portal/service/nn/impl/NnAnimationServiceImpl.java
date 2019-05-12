package com.sxyl.portal.service.nn.impl;

import com.sxyl.portal.common.JUUID;
import com.sxyl.portal.domain.d.*;
import com.sxyl.portal.domain.d.compute.*;
import com.sxyl.portal.domain.formula.dnn.HiddenWeightContent;
import com.sxyl.portal.domain.graph.Group;
import com.sxyl.portal.domain.graph.MathFormula;
import com.sxyl.portal.domain.nn.NnWeight;
import com.sxyl.portal.domain.nn.dnn.*;
import com.sxyl.portal.domain.nn.dnn.param.DnnConstructParam;
import com.sxyl.portal.service.nn.NnAnimationService;
import com.sxyl.portal.service.nn.NnCommonService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class NnAnimationServiceImpl extends NnCommonService implements NnAnimationService {




    @Override
    public AnimationTotal getNnAnimation(List<String> inputIds, List<List<String>> hiddenIds, List<String> outputIds , DnnConstructParam dnnConstructParam) {

        AnimationTotal total = new AnimationTotal();


        //todo ,暂时写死
        List<String> firstHidden = hiddenIds.get(0);

        this.addAnimation(total, inputIds , firstHidden);

        this.addAnimation(total , firstHidden,outputIds);


        this.addError(total , dnnConstructParam );

        this.addOutWeight(total ,dnnConstructParam);


        this.addHiddenWeight(total , inputIds , firstHidden , outputIds , dnnConstructParam);

        return total;
    }


    /***
     * 隐藏层 和输出层的计算过程
     * @param total
     * @param sid
     * @param tid
     */
    private void addAnimation(AnimationTotal total , List<String> sid,List<String> tid){


        for (String t:tid){

            //公式拷贝,根据 目标元素的id ，获取目标元素的计算逻辑
            total.addComponent(new FormulaCopy(super.getSumInput(t) , super.getFormulaId()));
            List<String> sumIds = new ArrayList<>();
            //按照层与层之间的数量循环
            for (String s : sid){

                //输入层输入层文本的id
                String sMiddleTextId = super.getHiddenBottomOutId(s) ;
                //输入层中间文本的id拷贝
                String copyMiddleId = JUUID.getUUID();
//                String copyMiddleId = getCopyId(sMiddleTextId , stepNo);

                sumIds.add(copyMiddleId);

                //复制对象
                total.addComponent(new Copy(sMiddleTextId , copyMiddleId));
                //移动对象到权重的文本
                Move moveToWeight =  new Move(copyMiddleId , super.getNeuronToNeuronWeightId(s, t));
                //buffer 设置为 -50
                moveToWeight.setBx(-50);
                total.addComponent(moveToWeight);



                //乘积的
                Multiply multiply = new Multiply();
                List<String> mids=new ArrayList<>();
                mids.add(copyMiddleId);
                mids.add(super.getNeuronToNeuronWeightValue(s,t));
                multiply.setIds(mids);
                multiply.setTid(copyMiddleId);
                total.addComponent(multiply);

                //新版本公式,移动
                total.addComponent(new Move(copyMiddleId , super.getFormulaObjectId()));

                //刷新公式
                total.addComponent(new RefreshFormula(copyMiddleId ));

                //销毁公式中的对象
                total.addComponent(new Destroy(super.getSumNodeId(s,t)));
            }

            //求和所有数值
            Sum sum = new Sum();
            sum.setTid(super.getFormulaObjectId());
            sum.setIds(sumIds);
            total.addComponent(sum);

            //销毁对象
            for (String id:sumIds){
                Destroy destroy = new Destroy();
                destroy.setId(id);
                total.addComponent(destroy);
            }


            String sumResultUuid = JUUID.getUUID();
            total.addComponent(new CopyFormulaResult(super.getFormulaObjectId() , sumResultUuid));
            //移动公式结果对象到
            total.addComponent(new Move(sumResultUuid , super.getHiddenBottomNetId(t)));

            //将计算结果的值赋予 net
            total.addComponent(new ChangeContent(super.getHiddenBottomNetId(t) , sumResultUuid ));

            //销毁公式中计算结果对象
            total.addComponent(new Destroy(sumResultUuid));

            //清空对象
            total.addComponent(new ClearFormula(super.getFormulaObjectId()));


            //将sigmoid公式拷贝到处理逻辑中
            total.addComponent(new FormulaCopy(super.getSigmoidId(t) , super.getFormulaId()));

            String copyBottomNetId = JUUID.getUUID();
            total.addComponent(new Copy(super.getHiddenBottomNetId(t) , copyBottomNetId));

            //将 隐藏层 的net值 移动到公式中的节点

            //新版本公式,移动
            total.addComponent(new Move(copyBottomNetId , super.getFormulaObjectId()));

            //新版本公式
            total.addComponent(new RefreshFormula(copyBottomNetId ));

            //计算新版本公式， 计算sigmoid
            total.addComponent(new Sigmoid(super.getFormulaObjectId(),copyBottomNetId));


            total.addComponent(new Destroy(copyBottomNetId));

            //新版本，迁移公式

            //复制结果对象
            String resultUuid = JUUID.getUUID();
            total.addComponent(new CopyFormulaResult(super.getFormulaObjectId() , resultUuid));
            total.addComponent(new Move(resultUuid, super.getHiddenBottomOutId(t))) ;

            total.addComponent(new ChangeContent(super.getHiddenBottomOutId(t)  , resultUuid));

            total.addComponent(new Destroy(resultUuid));

            //清空对象
            total.addComponent(new ClearFormula(super.getFormulaObjectId()));


        }
    }


    /***
     * 增加损失函数
     * @param total
     * @param dnnConstructParam
     */
    private void addError(AnimationTotal total ,DnnConstructParam dnnConstructParam ){
        List<DnnOutputNeuron> outputNeuronList = dnnConstructParam.getOutputLayer().getNeurons();
        for (DnnOutputNeuron dnnOutputNeuron : outputNeuronList){
            //将error公式拷贝到处理逻辑中
            total.addComponent(new FormulaCopy(super.getFormulaSquaredErrorId(dnnOutputNeuron.getId()) , super.getFormulaId()));

            //将对象移动到公式 ,并且刷新公式
            String copyBottomNetId = copyObjectAndMove(total ,dnnOutputNeuron.getActivationValueTextId() );
            //预测值得逻辑

            //实际值的对象移动到公式，并且刷新公式
            String copyBottomOutId = copyObjectAndMove(total ,dnnOutputNeuron.getActualValueTextId() );
//            total.addComponent(new Copy( dnnOutputNeuron.getActualValueTextId(),copyBottomOutId ));
//            total.addComponent(new Move(copyBottomOutId , super.getFormulaObjectId() )) ;
//            total.addComponent(new RefreshFormula(copyBottomOutId ));

            total.addComponent(new SquaredError( super.getFormulaObjectId() , copyBottomNetId , copyBottomOutId));



            //复制结果对象
            String resultUuid = JUUID.getUUID();
            total.addComponent(new CopyFormulaResult(super.getFormulaObjectId() , resultUuid));
            total.addComponent(new Move(resultUuid, dnnOutputNeuron.getCostValueTextId())) ;
            total.addComponent(new ChangeContent(dnnOutputNeuron.getCostValueTextId()  , resultUuid));

            //清空对象
            total.addComponent(new ClearFormula(super.getFormulaObjectId()));

            total.addComponent(new Destroy(copyBottomNetId));
            total.addComponent(new Destroy(copyBottomOutId));
            total.addComponent(new Destroy(resultUuid));
        }
    }


    /***
     * 更新输出层权重
     * @param total
     */
    private void addOutWeight(AnimationTotal total ,DnnConstructParam dnnConstructParam){

        for (DnnOutputNeuron dnnOutputNeuron : dnnConstructParam.getOutputLayer().getNeurons()) {
            String tid = dnnOutputNeuron.getId();
            for (NnWeight nnWeight : dnnOutputNeuron.getNeuronWeight()) {

                total.addComponent(new FormulaCopy(super.getFormulaUpdateWeightId(tid,nnWeight.getSid()) , super.getFormulaId()));

                //权重值
                String wid = copyObjectAndMove(total , nnWeight.getValueId()) ;
                //实际值
                String yAct = copyObjectAndMove(total , dnnOutputNeuron.getActualValueTextId()) ;
                // hidden A0
                String oa0 = copyObjectAndMove(total , dnnOutputNeuron.getActivationValueTextId());
                // hidden A1
                String oa1 = copyObjectAndMove(total , dnnOutputNeuron.getActivationValueTextId());
                // hidden A2
                String oa2 = copyObjectAndMove(total , dnnOutputNeuron.getActivationValueTextId());
                // out A
                DnnHiddenNeuron dnnHiddenNeuron = dnnConstructParam.getHiddenNeuronMap().get(nnWeight.getSid()) ;
                String ha = copyObjectAndMove(total , dnnHiddenNeuron.getActivationValueTextId());
                //计算结果
                total.addComponent(new ComputeOutWeight(super.getFormulaObjectId() ,wid,yAct ,oa0 ,ha));
                String resultId = copyFormulaResult(total ,nnWeight.getValueId() );
                //销毁对象
                total.addComponent(new Destroy(wid,yAct,oa0,oa1,oa2,ha,resultId));

            }
        }


//        for (String tid : tidList){
//            String errorId = super.getErrorTextId(tid);
//            for (String sid : sidList){
//
//                //将error公式拷贝到处理逻辑中
//                total.addComponent(new FormulaCopy(super.getFormulaUpdateWeightId(tid,sid) , super.getFormulaId()));
//
//                //隐藏层的输出对象
//                String copyBottomTargetId = this.getCopyId(super.getHiddenBottomOutId(errorId) ,stepNo);
//                total.addComponent(new Copy(super.getHiddenBottomOutId(errorId) , copyBottomTargetId));
//
//                Move targetError = new Move(copyBottomTargetId , super.getOutWeightTargetNodeId(tid));
//                targetError.setBx(10);
//                total.addComponent(targetError);
//
//                total.addComponent(new Destroy(super.getOutWeightTargetNodeId(tid))) ;
//
//                String copyBottomOutId0 = this.getCopyId(super.getHiddenBottomOutId(tid) ,stepNo);
//                stepNo++;
//                String copyBottomOutId1 = this.getCopyId(super.getHiddenBottomOutId(tid) ,stepNo);
//                stepNo++;
//                String copyBottomOutId2 = this.getCopyId(super.getHiddenBottomOutId(tid) ,stepNo);
//
//
//                total.addComponent(new Copy(super.getHiddenBottomOutId(tid) , copyBottomOutId0));
//                total.addComponent(new Copy(super.getHiddenBottomOutId(tid) , copyBottomOutId1));
//                total.addComponent(new Copy(super.getHiddenBottomOutId(tid) , copyBottomOutId2));
//
//
//                Move outNode0 = new Move(copyBottomOutId0 , super.getOutWeightOutNodeId(tid,"0"));
//                outNode0.setBx(20);
//                total.addComponent(outNode0);
//                //添加销毁
//                total.addComponent(new Destroy(super.getOutWeightOutNodeId(tid,"0")));
//
//
//                Move outNode1 = new Move(copyBottomOutId1 , super.getOutWeightOutNodeId(tid,"1"));
//                outNode1.setBx(20);
//                total.addComponent(outNode1);
//                total.addComponent(new Destroy(super.getOutWeightOutNodeId(tid,"1")));
//
//                Move outNode2 = new Move(copyBottomOutId2 , super.getOutWeightOutNodeId(tid,"2"));
//                outNode2.setBx(20);
//                total.addComponent(outNode2);
//                total.addComponent(new Destroy(super.getOutWeightOutNodeId(tid,"2")));
//
//
//                String hiddenCopyId = this.getCopyId(sid ,stepNo) ;
//                Copy hiddenCopy =new Copy(super.getHiddenBottomOutId(sid) , hiddenCopyId) ;
//                total.addComponent(hiddenCopy);
//
//                Move hiddenNode1 = new Move(hiddenCopyId , super.getOutWeightOutHiddenNodeId(sid));
//                hiddenNode1.setBx(30);
//                total.addComponent(hiddenNode1);
//
//
//                total.addComponent(new Destroy( super.getOutWeightOutHiddenNodeId(sid)));
//
//
//                //计算
//                ComputeOutWeight computeOutWeight = new ComputeOutWeight();
//                computeOutWeight.setTid(super.getFormulaResultId(tid));
////                computeOutWeight.setTargetId(copyBottomTargetId);
//                computeOutWeight.setOuto(super.getHiddenBottomOutId(tid));
//                computeOutWeight.setOuth(super.getHiddenBottomOutId(sid));
//                total.addComponent(computeOutWeight);
//
//
//
//                //将结算结果放入到
//                total.addComponent(new Move(super.getFormulaResultId(tid) , super.getNeuronToNeuronWeightValue(sid , tid)));
//
//
//                //变更内容,将公式计算结果替换到输出层的结果上
//                total.addComponent(new ChangeContent(super.getNeuronToNeuronWeightValue(sid , tid) , super.getFormulaResultId(tid)));
//
//                total.addComponent(new Destroy(super.getFormulaResultId(tid)));
//
//
//                //销毁对象
//                total.addComponent(new Destroy(copyBottomTargetId))
//                        .addComponent(new Destroy(copyBottomOutId0))
//                        .addComponent(new Destroy(copyBottomOutId1))
//                        .addComponent(new Destroy(copyBottomOutId2))
//                        .addComponent(new Destroy(hiddenCopyId))
//                        ;
//
//                stepNo++ ;
//
//            }
//
//        }

    }


    /***
     * 隐藏层权重
     * @param total
     * @param inputList
     * @param hiddenList
     */
    private void addHiddenWeight(AnimationTotal total , List<String> inputList ,List<String> hiddenList , List<String> targetList,DnnConstructParam dnnConstructParam){
        List<DnnHiddenLayer> hiddenLayers = dnnConstructParam.getDnnHiddenLayerList();
        DnnOutputLayer dnnOutputLayer = dnnConstructParam.getOutputLayer() ;
        for ( int i = 0 ; i < hiddenLayers.size() ; i++){
            DnnHiddenLayer dnnHiddenLayer = hiddenLayers.get(i);
            //隐藏层神经单元循环
            for (DnnHiddenNeuron dnnHiddenNeuron : dnnHiddenLayer.getNeurons()){
                for (NnWeight hnnWeight : dnnHiddenNeuron.getNeuronWeight()){
                    String sid = hnnWeight.getSid();
                    String tid = hnnWeight.getTid();
                    ArrayList<String> destory = new ArrayList<>();
                    List<String> weight = new ArrayList<>();
                    List<String> targetId = new ArrayList<>();
                    List<String> outId = new ArrayList<>();
                    total.addComponent(new FormulaCopy(super.getFormulaUpdateWeightId(tid, sid) , super.getFormulaId()));
                    DnnInputNeuron dnnInputNeuron = dnnConstructParam.getInputNeuronMap().get(sid);

                    //权重值
                    String wid = copyObjectAndMove(total , hnnWeight.getValueId()) ;
                    destory.add(wid);

                    //按照输出层进行循环
                    for (DnnOutputNeuron dnnOutputNeuron :dnnOutputLayer.getNeurons()){
                        for (NnWeight nnWeight :dnnOutputNeuron.getNeuronWeight()){
                            //如果输出层的 id 和隐藏层的id相同,则说明是能匹配的权重值
                            if(nnWeight.getSid().equals(tid)){

                                String hiddenId = copyObjectAndMove(total , dnnOutputNeuron.getActualValueTextId());
                                String out1 = copyObjectAndMove(total , dnnOutputNeuron.getActivationValueTextId());
                                String out2 = copyObjectAndMove(total , dnnOutputNeuron.getActivationValueTextId());
                                String out3 = copyObjectAndMove(total , dnnOutputNeuron.getActivationValueTextId());
                                String hiddenWeight = copyObjectAndMove(total , nnWeight.getValueId());
                                destory.add(hiddenId);
                                destory.add(out1);
                                destory.add(out2);
                                destory.add(out3);
                                destory.add(hiddenWeight);
                                weight.add(nnWeight.getValueId());
                                targetId.add(dnnOutputNeuron.getActualValueTextId());
                                outId.add(dnnOutputNeuron.getActivationValueTextId());

                            }
                        }
                    }


                    String hiddenOutId = copyObjectAndMove(total , dnnHiddenNeuron.getActivationValueTextId());
                    destory.add(hiddenOutId);
                    String hiddenOutId2 = copyObjectAndMove(total , dnnHiddenNeuron.getActivationValueTextId());
                    destory.add(hiddenOutId2);
                    String ivid = copyObjectAndMove(total , dnnInputNeuron.getValueTextId());
                    destory.add(ivid);

                    ComputeHiddenWeight computeHiddenWeight = new ComputeHiddenWeight();
                    computeHiddenWeight.setTid(super.getFormulaObjectId());
                    computeHiddenWeight.setTargetId(targetId);
                    computeHiddenWeight.setOutId(outId);
                    computeHiddenWeight.setWeightId(weight);
                    computeHiddenWeight.setOid(dnnHiddenNeuron.getActivationValueTextId());
                    computeHiddenWeight.setIv(dnnInputNeuron.getValueTextId());
                    total.addComponent(computeHiddenWeight);


                    String resultId = copyFormulaResult(total ,hnnWeight.getValueId() );
                    destory.add(resultId);

                    //销毁对象
                    total.addComponent(new Destroy(destory.toArray(new String[destory.size()])));
                }
            }
        }

//        int stepNo =0 ;
//        for (int i = 0  ; i <inputList.size() ; i++) {
//            String sid = inputList.get(i);
//
//            for (int j = 0 ; j<hiddenList.size() ;j++) {
//                String tid = hiddenList.get(j);
//                //将error公式拷贝到处理逻辑中
//                total.addComponent(new FormulaCopy(super.getFormulaUpdateWeightId(tid, sid), super.getFormulaId()));
//
//
//                List<String> weight = new ArrayList<>();
//                List<String> targetId = new ArrayList<>();
//                List<String> outId = new ArrayList<>();
//
//                List<String> copyId = new ArrayList<>();
//                for (int k =0; k<targetList.size() ; k++) {
//
//
//                        String errorId = super.getErrorTextId(targetList.get(k));
//                        weight.add(super.getNeuronToNeuronWeightValue(tid , targetList.get(k)));
//
//                        targetId.add(super.getHiddenBottomOutId(errorId));
//
//                        outId.add(super.getHiddenBottomOutId(targetList.get(k)));
//
//
//
//                        String copyWeightId = this.getCopyId(super.getNeuronToNeuronWeightValue(tid , targetList.get(k) ) , stepNo);
//                        total.addComponent(new Copy(super.getNeuronToNeuronWeightValue(tid , targetList.get(k)) ,copyWeightId));
//                        Move hiddenNode = new Move(copyWeightId , super.getOutputWeightNodeId(tid , targetList.get(k)));
//                        hiddenNode.setBx(20);
//                        total.addComponent(hiddenNode);
//                        total.addComponent(new Destroy(super.getOutputWeightNodeId(tid , targetList.get(k))));
//                        stepNo++;
//                        copyId.add(copyWeightId);
//
//                }
//
//
//                String copyHiddenId0 = this.getCopyId(super.getHiddenBottomOutId(tid) , stepNo);
//                total.addComponent(new Copy(super.getHiddenBottomOutId(tid) ,copyHiddenId0));
//                Move hiddenNode = new Move(copyHiddenId0 , super.getOutWeightOutNodeId(tid , "0"));
//                hiddenNode.setBx(20);
//                total.addComponent(hiddenNode);
//                total.addComponent(new Destroy( super.getOutWeightOutNodeId(tid , "0")));
//
//                stepNo++ ;
//
//
//                String copyHiddenId1 = this.getCopyId(super.getHiddenBottomOutId(tid) , stepNo);
//                total.addComponent(new Copy(super.getHiddenBottomOutId(tid) ,copyHiddenId1));
//                Move hiddenNode1 = new Move(copyHiddenId1, super.getOutWeightOutNodeId(tid , "1"));
//                hiddenNode1.setBx(20);
//                total.addComponent(hiddenNode1);
//                total.addComponent(new Destroy( super.getOutWeightOutNodeId(tid , "1")));
//
//                stepNo++ ;
//
//                String copyInputId = this.getCopyId(super.getHiddenBottomOutId(sid) , stepNo);
//                total.addComponent(new Copy(super.getHiddenBottomOutId(sid) ,copyInputId));
//                Move inputNode = new Move(copyInputId , super.getOutWeightOutHiddenNodeId(sid));
//                inputNode.setBx(20);
//                total.addComponent(inputNode);
//                total.addComponent(new Destroy(super.getOutWeightOutHiddenNodeId(sid)));
//
//
//                stepNo++ ;
//
//
//                ComputeHiddenWeight computeHiddenWeight = new ComputeHiddenWeight();
//                computeHiddenWeight.setTid(super.getFormulaResultId(sid));
//                computeHiddenWeight.setTargetId(targetId);
//                computeHiddenWeight.setOutId(outId);
//                computeHiddenWeight.setWeightId(weight);
//
//                computeHiddenWeight.setOid(super.getHiddenBottomOutId(tid));
//                computeHiddenWeight.setIv(super.getHiddenBottomOutId(sid));
//
//
//                total.addComponent(computeHiddenWeight);
//
//
//                //将结算结果放入到
//                total.addComponent(new Move(super.getFormulaResultId(sid) , super.getNeuronToNeuronWeightValue(sid , tid)));
//
//
//                //变更内容,将公式计算结果替换到输出层的结果上
//                total.addComponent(new ChangeContent(super.getNeuronToNeuronWeightValue(sid , tid) , super.getFormulaResultId(sid)));
//
//                total.addComponent(new Destroy(super.getFormulaResultId(sid)));
//
//                for (String s :copyId){
//                    total.addComponent(new Destroy(s));
//                }
//
//                total.addComponent(new Destroy(copyHiddenId0));
//                total.addComponent(new Destroy(copyHiddenId1));
//                total.addComponent(new Destroy(copyInputId));
//
//
//
//            }
//        }
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
