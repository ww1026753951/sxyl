package com.sxyl.portal.service.jvm.pool.impl;

import com.sxyl.portal.domain.CommonMove;
import com.sxyl.portal.domain.constant.ColorEnum;
import com.sxyl.portal.domain.constant.ComponentCompositeEnum;
import com.sxyl.portal.domain.d.AnimationTotal;
import com.sxyl.portal.domain.d.Move;
import com.sxyl.portal.domain.graph.*;
import com.sxyl.portal.domain.jvm.CoreThreadPool;
import com.sxyl.portal.domain.jvm.MaxThreadPool;
import com.sxyl.portal.domain.jvm.QueuePool;
import com.sxyl.portal.domain.jvm.pool.PoolConstruct;
import com.sxyl.portal.domain.jvm.pool.PoolConstructData;
import com.sxyl.portal.service.jvm.pool.ThreadPoolService;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author: create by wei.wang
 * @version: v1.0
 * @description: com.sxyl.portal.domain.jvm.pool
 * @date:2020/9/14
 */
@Service
public class ThreadPoolServiceImpl implements ThreadPoolService {


    private final String CORE_COLOR= "gray";

    private final String QUEUE_COLOR= "green";

    private final String MAX_COLOR= "AliceBlue";

    private final int threadWidth = 20;

    private final int threadGeight = 20;

    private final int TEXT_BUFFER = 5;


    //池的x
    private final int poolX = 570;

    //池的y
    private final int poolY = 200;

    //队列的y
    private final int queueY = 70;

    //池的高度
    private final int poolHeight = 60 ;
    //池的宽度
    private final int poolWidth = 460;


    private final int corePoolHeight= 120;

    private final int corePoolWidth = 240 ;

    private final int maxHeight = 300 ;

    private final int maxPoolWidth = 320;

    private final int coreBuffer = 20;


    /***
     * 任务圆圈的 半径
     */
    private final int threadRadius = 30;

    //线程初始的 横坐标
    private final int threadInitX = 50;

    //线程初始的 纵坐标
    private final int threadInitY = 50;


    @Override
    public PoolConstruct getPoolConstruct() {

        PoolConstruct poolConstruct = new PoolConstruct();


        PoolConstructData poolConstructData = new PoolConstructData();

        //设置核心线程数对象
        poolConstructData.setCoreThreadPool(createCoreThreadPool());
        //设置
        poolConstructData.setMaxThreadPool(createMaxThreadPool());
        //设置队列数
        poolConstructData.setQueuePool(createQueuePool());

        poolConstruct.setPcd(poolConstructData);


        Group construct = getPoolGroup(poolConstruct);

        poolConstruct.setGroup(construct);




        return poolConstruct;
    }

    @Override
    public PoolConstruct createNewThread(PoolConstruct poolConstruct) {
        AnimationTotal animationTotal = new AnimationTotal();

        PoolConstructData poolConstructData = poolConstruct.getPcd();


        Group group = new Group();
        Circle newTask =   createNewTaskGraph();
        group.addChild(newTask);

        Rect newThread = createNewThreadGraph();

        group.addChild(newThread);


        poolConstruct.setGroup(group);

        //创建移动
        this.createMove(newTask.getId() , poolConstructData  , animationTotal);

        poolConstruct.setAt(animationTotal);



        //队列数量 + 1
        if (poolConstructData.getCoreThreadPoolCount() == poolConstructData.getCorePoolSize()
                && poolConstructData.getQueuePoolCount() < poolConstructData.getQueueSize() ){
            poolConstructData.setQueuePoolCount(poolConstructData.getQueuePoolCount() + 1);
        }

        //核心线程数量 +1
        if (poolConstructData.getCoreThreadPoolCount() < poolConstructData.getCorePoolSize()) {
            poolConstructData.setCoreThreadPoolCount(poolConstructData.getCoreThreadPoolCount() + 1  );
        }


        //最大线程池数量 +1
        if (poolConstructData.getCoreThreadPoolCount() == poolConstructData.getCorePoolSize()
                && poolConstructData.getQueuePoolCount() == poolConstructData.getQueueSize()
                &&poolConstructData.getMaxThreadPoolCount() < poolConstructData.getMaxPoolSize() ){

            poolConstructData.setMaxThreadPoolCount(poolConstructData.getMaxThreadPoolCount() + 1  );
        }

        return poolConstruct;
    }

    /****
     * 增加移动动画
     * @param id
     * @param poolConstructData
     * @param animationTotal
     */
    private void createMove(String id ,PoolConstructData poolConstructData
            ,AnimationTotal animationTotal  ){

        String ad =null;
        //核心线程池对象
        CoreThreadPool coreThreadPool = poolConstructData.getCoreThreadPool();

        //队列数
        QueuePool queuePool = poolConstructData.getQueuePool();


        //最大线程池对象
        MaxThreadPool maxThreadPool = poolConstructData.getMaxThreadPool();

        int queueSize = poolConstructData.getQueuePoolCount();



        int initX1 =0;

        int initY1 =0;


        int initX2 =0;

        int initY2 =0;

//        int row = corePoolHeight / (threadRadius *2) ;

        int column = corePoolWidth / (threadRadius * 2);

        int columnMax =maxThreadPool.getWidth()/ (threadRadius * 2);

        /***
         * 如果核心线程池里的线程数少于总线程数量
         */
        if (poolConstructData.getCoreThreadPoolCount() < poolConstructData.getCorePoolSize()) {


            ad = "当前核心线程数少于核心线程总数量,所以新创建线程。";
            if (poolConstructData.getCoreColumnIndex() == column){
                //行数的下标数量加 1
                poolConstructData.setCoreRowIndex(poolConstructData.getCoreRowIndex() + 1);

                //如果为新的一行,则将 列号置为0
                poolConstructData.setCoreColumnIndex(0);
            }
            if  (poolConstructData.getCoreColumnIndex() < column) {

                CommonMove commonMove = createCommonMove(coreThreadPool.getX() , coreThreadPool.getY(),corePoolWidth,
                        poolConstructData.getCoreRowIndex(), poolConstructData.getCoreColumnIndex());

                //核心线程的方框 +  核心线程的高度
                initX1 = commonMove.getX1() ;
                initY1 = commonMove.getY1() ;
                initX2 = commonMove.getX2() ;
                initY2 = commonMove.getY2() ;

                poolConstructData.setCoreColumnIndex(poolConstructData.getCoreColumnIndex() + 1 );
            }
        }


        //如果核心线程数满了,执行队列 加操作
        if (poolConstructData.getCoreThreadPoolCount() == poolConstructData.getCorePoolSize()
                && queueSize < poolConstructData.getQueueSize()){

            ad = "核心线程数量已满,所以将任务放到执行队列中。";
            CommonMove commonMove = createCommonMove(queuePool.getX() , queuePool.getY() , queuePool.getWidth(),
                    0, queueSize );
            initX1 = commonMove.getX1() ;
            initY1 = commonMove.getY1() ;
            initX2 = commonMove.getX2() ;
            initY2 = commonMove.getY2() ;
        }


        //如果核心线程池和队列都满了， 则往最大线程池里放
        if(poolConstructData.getCoreThreadPoolCount() == poolConstructData.getCorePoolSize() &&
                queueSize == poolConstructData.getQueueSize()){

            ad = "核心线程数量已满,所以将任务放到执行队列中。";

            if (poolConstructData.getMaxColumnIndex() == columnMax){
                //行数的下标数量加 1
                poolConstructData.setMaxRowIndex(poolConstructData.getMaxRowIndex() + 1);

                //如果为新的一行,则将 列号置为0
                poolConstructData.setMaxColumnIndex(0);
            }

            if  (poolConstructData.getMaxColumnIndex() < columnMax) {
                CommonMove commonMove = createCommonMove(maxThreadPool.getX() , (maxThreadPool.getY() + coreThreadPool.getHeight() +coreBuffer ), maxThreadPool.getWidth(),
                        poolConstructData.getMaxRowIndex(), poolConstructData.getMaxColumnIndex() );
                initX1 = commonMove.getX1() ;
                initY1 = commonMove.getY1() ;
                initX2 = commonMove.getX2() ;
                initY2 = commonMove.getY2() ;

                poolConstructData.setMaxColumnIndex(poolConstructData.getMaxColumnIndex() + 1 );
            }



        }

//        if ()

        //移动的元素
        Move move = new Move();
        move.setId(id);
        move.setX(initX1);
        move.setY(initY1);
        move.setAd(ad);
        animationTotal.addComponent(move);

        //移动的元素
        Move move2 = new Move();
        move2.setId(id);
        move2.setX(initX2);
        move2.setY(initY2);

        animationTotal.addComponent(move2);
    }

    private CommonMove createCommonMove (int backX , int backY , int backWidth , int rowIndex , int columnIndex){
        CommonMove commonMove = new CommonMove();

        int x1 = threadInitX ;
        //核心线程的方框 +  核心线程的高度
        int y1 = backY + ( rowIndex * threadRadius*2) + threadRadius  ;
        int x2 = backX + backWidth - (columnIndex * threadRadius*2) - threadRadius;
        int y2 =  y1 ;

        commonMove.setX1(x1);
        commonMove.setY1(y1);
        commonMove.setX2(x2);
        commonMove.setY2(y2);

        return commonMove;

    }


    private Group getPoolGroup(PoolConstruct poolConstruct) {

        CoreThreadPool coreThreadPool = poolConstruct.getPcd().getCoreThreadPool();

        MaxThreadPool maxThreadPool = poolConstruct.getPcd().getMaxThreadPool();

        QueuePool queuePool = poolConstruct.getPcd().getQueuePool();

        Group all = new Group();

        Group max = new Group();
        Rect rectMax = new Rect(UUID.randomUUID().toString(), maxThreadPool.getWidth(), maxThreadPool.getHeight(),maxThreadPool.getX() ,maxThreadPool.getY(),MAX_COLOR);
        max.addChild(rectMax);

        Text text = new Text(UUID.randomUUID().toString() , maxThreadPool.getX(),maxThreadPool.getY() -TEXT_BUFFER,"最大线程池");
        max.addChild(text);


        all.addChild(max);
        all.addChild(text);


        Group core = new Group();
        core.addChild(new Rect(UUID.randomUUID().toString(), coreThreadPool.getWidth(), coreThreadPool.getHeight(),coreThreadPool.getX() ,coreThreadPool.getY(),CORE_COLOR));

        core.addChild(new Text(UUID.randomUUID().toString() , coreThreadPool.getX(),coreThreadPool.getY() - TEXT_BUFFER,"核心线程池"));


        all.addChild(core);


        Group queue = new Group();
        Rect queueRect = new Rect(UUID.randomUUID().toString(), queuePool.getWidth(), queuePool.getHeight(),queuePool.getX() ,queuePool.getY(),QUEUE_COLOR);
        queue.addChild(queueRect);

        queue.addChild(new Text(UUID.randomUUID().toString() , queuePool.getX(),queuePool.getY() - TEXT_BUFFER,"工作队列"));

        all.addChild(queue);




        all.setCompose(ComponentCompositeEnum.HORIZONTAL.getType());

        return all;

    }


    /****
     * 创建核心线程的对象
     * @return
     */
    private Circle createNewTaskGraph(){

        Circle task = new Circle("task"+UUID.randomUUID().toString(), threadRadius, ColorEnum.RED.getHtmlCode()) ;

        task.setX(threadInitX);
        task.setY(threadInitY);

        return task ;
    }

    private Rect createNewThreadGraph(){
        Rect thread = new Rect("thread"+UUID.randomUUID().toString() , 20,20,300,300,"white" ) ;

        return thread;

    }


    private CoreThreadPool createCoreThreadPool(){
        //核心线程对象
        CoreThreadPool coreThreadPool = new CoreThreadPool();
        coreThreadPool.setWidth(corePoolWidth );
        coreThreadPool.setHeight(corePoolHeight );
        coreThreadPool.setX(poolX + coreBuffer);
        coreThreadPool.setY(poolY + coreBuffer);
        return coreThreadPool;
    }


    /***
     * 创建最大线程数
     * @return
     */
    private MaxThreadPool createMaxThreadPool(){
        //最大线程数
        MaxThreadPool maxThreadPool = new MaxThreadPool();
        maxThreadPool.setWidth(maxPoolWidth);
        maxThreadPool.setHeight(maxHeight);
        maxThreadPool.setX(poolX);
        maxThreadPool.setY(poolY);

        return maxThreadPool;
    }

    /***
     * 创建队列数
     * @return
     */
    private QueuePool createQueuePool(){
        //队列数
        QueuePool queuePool = new QueuePool();
        queuePool.setWidth(poolWidth);
        queuePool.setHeight(poolHeight);
        queuePool.setX(poolX);
        queuePool.setY(queueY);

        return queuePool;

    }
}
