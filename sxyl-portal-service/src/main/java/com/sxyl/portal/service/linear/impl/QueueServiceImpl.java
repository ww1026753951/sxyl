package com.sxyl.portal.service.linear.impl;

import com.sxyl.portal.domain.constant.ColorEnum;
import com.sxyl.portal.domain.constant.LineTypeEnum;
import com.sxyl.portal.domain.d.*;
import com.sxyl.portal.domain.graph.Group;
import com.sxyl.portal.domain.graph.Line;
import com.sxyl.portal.domain.graph.Rect;
import com.sxyl.portal.domain.graph.Text;
import com.sxyl.portal.domain.jvm.PoolCommonAttribute;
import com.sxyl.portal.domain.linear.QueueConstruct;
import com.sxyl.portal.service.linear.QueueService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author: create by wei.wang
 * @version: v1.0
 * @description: com.sxyl.portal.service.linear.impl
 * @date:2020/12/4
 */
@Service
public class QueueServiceImpl implements QueueService {


    private final int STACK_WIDTH= 450;

    private final int STACK_HEIGHT=100;

//
    private final int STACK_DATA_WIDTH= 30;
//
//
//
    private final int STACK_DATA_HEIGHT=80;

    private final int STACK_DATA_HEIGHT_BUFFER= 10;


    //栈背景初始的 x
    private final int x = 200;

    //栈背景初始的 y
    private final int y = 150;





    @Override
    public QueueConstruct getConstruct(QueueConstruct queueConstruct) {

        if(queueConstruct == null) {
            queueConstruct = new QueueConstruct();
        }

        int queueSize = STACK_WIDTH/(STACK_DATA_WIDTH + STACK_DATA_HEIGHT_BUFFER);

        PoolCommonAttribute poolCommonAttribute = createCoreThreadPool();

        queueConstruct.setQueue(poolCommonAttribute);

        Group group = getPoolGroup(queueConstruct);

        queueConstruct.setGroup(group);
        queueConstruct.setQueueSize(queueSize);

        return queueConstruct;
    }


    private PoolCommonAttribute createCoreThreadPool(){
        //核心线程对象
        PoolCommonAttribute stack = new PoolCommonAttribute();
        stack.setHeight(STACK_HEIGHT );
        stack.setWidth(STACK_WIDTH );
        stack.setY(y );
        stack.setX(x );
        return stack;
    }


    //获取背景布局颜色
    private Group getPoolGroup(QueueConstruct queueConstruct) {
        PoolCommonAttribute stack = queueConstruct.getQueue();
        Group all = new Group();
        Group stackGroup = new Group();
        Rect rectMax = new Rect(UUID.randomUUID().toString(), stack.getWidth(), stack.getHeight(),stack.getX() ,stack.getY(),ColorEnum.WHITE.getHtmlCode());
        rectMax.setStroke(ColorEnum.WHITE.getHtmlCode());
        stackGroup.addChild(rectMax);
        Text text = new Text(UUID.randomUUID().toString() , stack.getX(),stack.getY()  ,"队列" );
        stackGroup.addChild(text);
        all.addChild(stackGroup);
        //左侧的线

        Line bottomLine =new Line(LineTypeEnum.POSITION.getCode(),stack.getX(),stack.getY() + stack.getHeight(),stack.getX() + stack.getWidth(),stack.getY()+ stack.getHeight() );
        all.addChild(bottomLine);


        Line topLine =new Line(LineTypeEnum.POSITION.getCode(),stack.getX(),stack.getY() ,stack.getX() + stack.getWidth(),stack.getY());
        all.addChild(topLine);


        return all;

    }

    @Override
    public QueueConstruct push(QueueConstruct queueConstruct) {
        if (queueConstruct.getStringQueue().size() >= queueConstruct.getQueueSize()){
            queueConstruct.setErrorMsg("当前队列最大数量为"+queueConstruct.getQueueSize()+"。已超过最大值。");
            return queueConstruct;
        }
        AnimationTotal animationTotal = new AnimationTotal();
        Group group = new Group();
        String uuid ="task-" + UUID.randomUUID().toString();
        //创建任务部分

        Rect newTask = new Rect(uuid,STACK_DATA_WIDTH , STACK_DATA_HEIGHT ,  0 , 0, ColorEnum.GRAY.getHtmlCode()) ;
        group.addChild(newTask);

//        PoolCommonAttribute poolCommonAttribute = stackConstruct.getStack();


        //将栈的数据放入到对象中
        queueConstruct.getStringQueue().add(uuid);

        //移动模块1
        MultiMove multiMoveThread1 = new MultiMove();
        List<MultiMoveDetail> details = new ArrayList<>();
        details.add(new MultiMoveDetail(uuid , x ,y+ STACK_DATA_HEIGHT_BUFFER));
//        details.add(new MultiMoveDetail(threadText.getId() , initX1,initY1+ TEXT_BUFFER +  ACTIVE_CORE_BUFFER ));
        multiMoveThread1.setDetails(details);
        multiMoveThread1.setAd("将新创建的数据放入队列。");
        animationTotal.addComponent(multiMoveThread1);
//
//        //移动模块2
        MultiMove multiMoveThread2 = new MultiMove();
        List<MultiMoveDetail> details2 = new ArrayList<>();
        details2.add(new MultiMoveDetail(uuid, x + STACK_WIDTH -  (queueConstruct.getStringQueue().size()) *(STACK_DATA_WIDTH+STACK_DATA_HEIGHT_BUFFER)  ,STACK_DATA_HEIGHT_BUFFER + y  ));
//        details2.add(new MultiMoveDetail(threadText.getId() , initX2  ,initY2 +TEXT_BUFFER+  ACTIVE_CORE_BUFFER ));
        multiMoveThread2.setDetails(details2);
        multiMoveThread2.setAd("将新创建的数据放入队列。");
        animationTotal.addComponent(multiMoveThread2);

        queueConstruct.setGroup(group);

        queueConstruct.setAt(animationTotal);
        return queueConstruct;
    }

    @Override
    public QueueConstruct pop(QueueConstruct queueConstruct) {
        if (queueConstruct.getStringQueue().size() ==0){
            queueConstruct.setErrorMsg("队列没有任何数据,请先入栈。");
            return queueConstruct;
        }



        String uuid = queueConstruct.getStringQueue().poll();
        AnimationTotal animationTotal = new AnimationTotal();


        String targetUuid = "task" + UUID.randomUUID().toString();
        animationTotal.addComponent(new Copy(uuid ,targetUuid) );



        Hide hideThread = new Hide();
        String[] threadIds = new String[]{uuid};
        hideThread.setIds(threadIds);
        hideThread.setAd("将队列里的数据删除。");
        animationTotal.addComponent(hideThread);



        MultiMove multiMoveThread1 = new MultiMove();
        List<MultiMoveDetail> details = new ArrayList<>();
        details.add(new MultiMoveDetail(targetUuid , x * 10 ,y));
        multiMoveThread1.setDetails(details);
        multiMoveThread1.setAd("将队列的数据移出队列。");
        animationTotal.addComponent(multiMoveThread1);








        if(! CollectionUtils.isEmpty(queueConstruct.getStringQueue())){
            for ( String s: queueConstruct.getStringQueue()){
                Swap swapList = new Swap();
                String[] sidList  = new String[]{uuid};
                String[] tidList  = new String[]{s};
                swapList.setSid(sidList);
                swapList.setTid(tidList);
                animationTotal.addComponent(swapList);
            }


        }


        //移动模块1




//        //移动模块2
//        MultiMove multiMoveThread2 = new MultiMove();
//        List<MultiMoveDetail> details2 = new ArrayList<>();
//        details2.add(new MultiMoveDetail(uuid, x * 10 ,y));
////        details2.add(new MultiMoveDetail(threadText.getId() , initX2  ,initY2 +TEXT_BUFFER+  ACTIVE_CORE_BUFFER ));
//        multiMoveThread2.setDetails(details2);
//        multiMoveThread2.setAd("将队列的数据移出队列。");
//        animationTotal.addComponent(multiMoveThread2);

        Destroy destroy = new Destroy();
        String[] ids = new String[2];
        ids[0] = uuid;
//        ids[1] = taskText.getId();
        destroy.setIds(ids);


        destroy.setAd("将队列里的数据删除。");
        animationTotal.addComponent(destroy);





        queueConstruct.setAt(animationTotal);


        return queueConstruct;


    }
}
