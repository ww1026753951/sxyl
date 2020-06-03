package com.sxyl.portal.domain.tree;

import com.sxyl.portal.domain.constant.LeafTypeEnum;
import com.sxyl.portal.domain.d.ChangeAttrDetail;
import com.sxyl.portal.domain.graph.Circle;
import com.sxyl.portal.domain.graph.GraphComponent;
import com.sxyl.portal.domain.graph.Line;
import com.sxyl.portal.domain.graph.Text;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: create by wei.wang
 * @version: v1.0
 * @description: com.sxyl.portal.domain.tree
 * @date:2020/4/12
 */
public class BaseTree {


    protected final int defaultMt = 20 ;


    //rate
    protected double rate = 0.5;



    //层与层的高度
    protected int height = 80 ;



    // 圆的半径
    protected final int r = 20;


    int buffer = 500;



    /***
     * 执行步骤
     */
    protected Map<String , String> stepExecute = new HashMap<>();

    /***
     * 增加圆和文本的动画
     * @param list
     * @param cid
     * @param textId
     * @param x
     * @param y
     */
    protected void addCircleAndTextAnimation(List<GraphComponent> list , String cid , String textId , Integer x , Integer y ){
        Circle lxCircle = new Circle();
        lxCircle.setId(cid);
        lxCircle.setY(y);
        lxCircle.setX(x);
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
    protected void addLineAnimation(List<GraphComponent> list,
                                  String parentId,String childId,String newParentId,String newChildId,
                                  Integer x1 , Integer y1 , Integer x2,Integer y2){
        Line xlxLine = new Line();
        xlxLine.setId(parentId + "-" + childId);
        xlxLine.setX1(x1);
        xlxLine.setX2(x2);
        xlxLine.setY1(y1);
        xlxLine.setY2(y2);

        //当  线更换父子关系时， 赋值新的值
        if(StringUtils.isNotBlank(newParentId) && StringUtils.isNotBlank(newChildId)){
            xlxLine.setTid(newParentId + "-" + newChildId);
        }
        list.add(xlxLine);
    }


    protected int compute(int level){
//        return (level - 1)*height + defaultMt ;
        return level*height + defaultMt ;
    }


    /***
     * 测试宽度
     * @param currentType
     * @param parentType
     * @param parentWidth
     * @param parentBuffer
     * @return
     */
    protected int computeWidth(int currentType , int parentType ,int parentWidth ,int parentBuffer){




        //如果节点为左节点，并且父节点为左节点
        if (currentType == LeafTypeEnum.LEFT.getType() ){
            return parentWidth - parentBuffer;
        }
        if (currentType== LeafTypeEnum.RIGHT.getType() ){
            return parentWidth + parentBuffer;
        }

        return parentWidth;

//        if(left){
//            return parentWidth - buffer;
//        }else {
//            return parentWidth + buffer;
//
//        }
    }


    /***
     * 修改线的id

     */
    protected void addChangeLineId(List<ChangeAttrDetail> changeList ,
                                 String parentId,String childId,String newParentId,String newChildId){
        String id = parentId + "-" + childId;

        ChangeAttrDetail changeAttrDetail = new ChangeAttrDetail(id);

        changeAttrDetail.addValue("id" , newParentId + "-" + newChildId);


        changeList.add(changeAttrDetail);
    }




    /***
     * 获取描述信息
     * @param code, 描述的值
     * @param replace
     * @return
     */
    protected String getDesc(String code , Map<String,String> replace){

        Map<String , String> stepMap = this.getStepExecute();
        if (stepMap == null){
            return null;
        }
        String step= this.getStepExecute().get(code);
        if (StringUtils.isEmpty(step)){
            return null;
        }
        for (Map.Entry<String, String> entry : replace.entrySet()) {
            if (StringUtils.isNotEmpty(step)){
                step = step.replace(entry.getKey() , entry.getValue());
            }
        }
        return step ;
    }

    public Map<String, String> getStepExecute() {
        return stepExecute;
    }

    public void setStepExecute(Map<String, String> stepExecute) {
        this.stepExecute = stepExecute;
    }
}
