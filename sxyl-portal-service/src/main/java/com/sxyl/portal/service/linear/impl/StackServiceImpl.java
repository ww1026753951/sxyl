package com.sxyl.portal.service.linear.impl;

import com.sxyl.portal.domain.constant.ColorEnum;
import com.sxyl.portal.domain.constant.CommonConstant;
import com.sxyl.portal.domain.constant.LineTypeEnum;
import com.sxyl.portal.domain.d.AnimationTotal;
import com.sxyl.portal.domain.d.Destroy;
import com.sxyl.portal.domain.d.MultiMove;
import com.sxyl.portal.domain.d.MultiMoveDetail;
import com.sxyl.portal.domain.graph.*;
import com.sxyl.portal.domain.jvm.PoolCommonAttribute;
import com.sxyl.portal.domain.linear.StackConstruct;
import com.sxyl.portal.service.CommonService;
import com.sxyl.portal.service.linear.StackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: create by wei.wang
 * @version: v1.0
 * @description: com.sxyl.portal.service.linear.impl
 * @date:2020/11/22
 */
@Service
@Slf4j
public class StackServiceImpl extends CommonService implements StackService {


    private final String STACK_COLOR = "white";


    private final int STACK_WIDTH= 100;

    private final int STACK_HEIGHT=350;


    private final int STACK_DATA_WIDTH= 80;



    private final int STACK_DATA_HEIGHT=20;
    private final int STACK_DATA_WIDTH_BUFFER= 10;


    //栈背景初始的 x
    private final int x = 160;

    //栈背景初始的 y
    private final int y = 100;

    @Override
    public StackConstruct getPoolConstruct(StackConstruct stackConstruct) {

        if(stackConstruct == null) {
            stackConstruct = new StackConstruct();
        }

        PoolCommonAttribute poolCommonAttribute = createCoreThreadPool();

        stackConstruct.setStack(poolCommonAttribute);

        Group group = getPoolGroup(stackConstruct);

        stackConstruct.setGroup(group);

        return stackConstruct;
    }



    //获取背景布局颜色
    private Group getPoolGroup(StackConstruct stackConstruct) {
        PoolCommonAttribute stack = stackConstruct.getStack();
        Group all = new Group();
        Group stackGroup = new Group();
        Rect rectMax = new Rect(UUID.randomUUID().toString(), stack.getWidth(), stack.getHeight(),stack.getX() ,stack.getY(),STACK_COLOR);
        rectMax.setStroke(ColorEnum.WHITE.getHtmlCode());
        stackGroup.addChild(rectMax);
        Text text = new Text(UUID.randomUUID().toString() , stack.getX(),stack.getY()  ,"栈" );
        stackGroup.addChild(text);
        all.addChild(stackGroup);
        //左侧的线
        Line leftLine =new Line(LineTypeEnum.POSITION.getCode(),stack.getX(),stack.getY(),stack.getX(),stack.getY() + stack.getHeight());
        all.addChild(leftLine);


        Line bottomLine =new Line(LineTypeEnum.POSITION.getCode(),stack.getX(),stack.getY() + stack.getHeight(),stack.getX() + stack.getWidth(),stack.getY()+ stack.getHeight() );
        all.addChild(bottomLine);

        Line rightLine = new Line(LineTypeEnum.POSITION.getCode(),stack.getX() +  stack.getWidth(),stack.getY()  ,stack.getX() + stack.getWidth(),stack.getY()+ stack.getHeight() );

        all.addChild(rightLine);

        return all;

    }


    private PoolCommonAttribute createCoreThreadPool(){
        //核心线程对象
        PoolCommonAttribute stack = new PoolCommonAttribute();
        stack.setWidth(STACK_WIDTH );
        stack.setHeight(STACK_HEIGHT );
        stack.setY(y );
        stack.setX(x );
        return stack;
    }


    @Override
    public StackConstruct pushStack(StackConstruct stackConstruct) {
        AnimationTotal animationTotal = new AnimationTotal();
        Group group = new Group();
        String uuid ="task-" + UUID.randomUUID().toString();
        //创建任务部分
        Rect newTask = createNewTaskGraph(uuid);
        group.addChild(newTask);

//        PoolCommonAttribute poolCommonAttribute = stackConstruct.getStack();


        //将栈的数据放入到对象中
        stackConstruct.getStringStack().add(uuid);

        //移动模块1
        MultiMove multiMoveThread1 = new MultiMove();
        List<MultiMoveDetail> details = new ArrayList<>();
        details.add(new MultiMoveDetail(uuid , x + STACK_DATA_WIDTH_BUFFER,y));
//        details.add(new MultiMoveDetail(threadText.getId() , initX1,initY1+ TEXT_BUFFER +  ACTIVE_CORE_BUFFER ));
        multiMoveThread1.setDetails(details);
        multiMoveThread1.setAd("将新创建的数据放入栈底。");
        animationTotal.addComponent(multiMoveThread1);
//
//        //移动模块2
        MultiMove multiMoveThread2 = new MultiMove();
        List<MultiMoveDetail> details2 = new ArrayList<>();
        details2.add(new MultiMoveDetail(uuid, x + STACK_DATA_WIDTH_BUFFER ,STACK_HEIGHT + y  -  (stackConstruct.getStringStack().size()) *(STACK_DATA_HEIGHT+STACK_DATA_WIDTH_BUFFER)  ));
//        details2.add(new MultiMoveDetail(threadText.getId() , initX2  ,initY2 +TEXT_BUFFER+  ACTIVE_CORE_BUFFER ));
        multiMoveThread2.setDetails(details2);
        multiMoveThread2.setAd("将新创建的数据放入栈底。");
        animationTotal.addComponent(multiMoveThread2);

        stackConstruct.setGroup(group);

        stackConstruct.setAt(animationTotal);


        return stackConstruct;
    }

    @Override
    public StackConstruct popStack(StackConstruct stackConstruct) {



        if (stackConstruct.getStringStack().size() ==0){

            stackConstruct.setErrorMsg("栈中没有任何数据,请先入栈。");
            return stackConstruct;
        }

        String uuid = stackConstruct.getStringStack().pop();
        AnimationTotal animationTotal = new AnimationTotal();




        //移动模块1
        MultiMove multiMoveThread1 = new MultiMove();
        List<MultiMoveDetail> details = new ArrayList<>();
        details.add(new MultiMoveDetail(uuid , x + STACK_DATA_WIDTH_BUFFER,y));
//        details.add(new MultiMoveDetail(threadText.getId() , initX1,initY1+ TEXT_BUFFER +  ACTIVE_CORE_BUFFER ));
        multiMoveThread1.setDetails(details);
        multiMoveThread1.setAd("将栈顶的数据移出栈。");
        animationTotal.addComponent(multiMoveThread1);



//        //移动模块2
        MultiMove multiMoveThread2 = new MultiMove();
        List<MultiMoveDetail> details2 = new ArrayList<>();
        details2.add(new MultiMoveDetail(uuid, x * 10 ,y));
//        details2.add(new MultiMoveDetail(threadText.getId() , initX2  ,initY2 +TEXT_BUFFER+  ACTIVE_CORE_BUFFER ));
        multiMoveThread2.setDetails(details2);
        multiMoveThread2.setAd("将栈顶的数据移出栈。");
        animationTotal.addComponent(multiMoveThread2);

        Destroy destroy = new Destroy();
        String[] ids = new String[2];
        ids[0] = uuid;
//        ids[1] = taskText.getId();
        destroy.setIds(ids);


        destroy.setAd("删除数据");
        animationTotal.addComponent(destroy);

        stackConstruct.setAt(animationTotal);


        return stackConstruct;
    }



    /****
     * 创建任务的圆的对象
     * @return
     */
    private Rect createNewTaskGraph(String uuid){



        Rect data = new Rect(uuid,STACK_DATA_WIDTH , STACK_DATA_HEIGHT ,  0 , 0, ColorEnum.GRAY.getHtmlCode()) ;
//        task.setX(CommonConstant.INIT_X);
//        task.setY(CommonConstant.INIT_Y);
//        task.setH(DisplayEnum.NONE.getContent());
        return data ;
    }



    public static void main(String[] args){

        AtomicInteger count = new AtomicInteger();
        int corePoolSize = 1;
        int maximumPoolSize = 3;
        long keepAliveTime = 10;
        TimeUnit unit = TimeUnit.SECONDS;
        BlockingQueue<Runnable> workQueue = new LinkedBlockingDeque<>(1);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        RejectedExecutionHandler handler = new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {/*什么也不做
*/}
        };
        ThreadPoolExecutor pool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
        for (int i = 0; i < 5; i++) {
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    count.addAndGet(1);
                }
            });
            System.out.printf("i=%d,activeCount=%d\n",i,pool.getActiveCount());
        }
        try {
            Thread.sleep(1000*5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.print(count.toString());


    }

}
