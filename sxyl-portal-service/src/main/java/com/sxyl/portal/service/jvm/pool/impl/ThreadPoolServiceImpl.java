package com.sxyl.portal.service.jvm.pool.impl;

import com.sxyl.portal.domain.CommonMove;
import com.sxyl.portal.domain.constant.ColorEnum;
import com.sxyl.portal.domain.constant.ComponentCompositeEnum;
import com.sxyl.portal.domain.constant.DisplayEnum;
import com.sxyl.portal.domain.d.*;
import com.sxyl.portal.domain.graph.*;
import com.sxyl.portal.domain.jvm.*;
import com.sxyl.portal.domain.jvm.pool.MaxThreadBo;
import com.sxyl.portal.domain.jvm.pool.PoolConstruct;
import com.sxyl.portal.domain.jvm.pool.PoolConstructData;
import com.sxyl.portal.service.jvm.pool.ThreadPoolService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

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

    private final String REJECT_COLOR= "blue";

    private final int threadWidth = 20;

    private final int threadGeight = 20;

    //方框上文案的buffer
    private final int TEXT_BUFFER = -5;


    //池的x
    private final int poolX = 550;

    //池的y
    private final int poolY = 180;

    //队列的y
    private final int queueY = 70;

    //池的高度
    private final int poolHeight = 60 ;
    //池的宽度
    private final int poolWidth = 460;



    //拒绝策略的高度
    private final int rejectHeight = 80 ;
    //拒绝策略的宽
    private final int rejectWidth = 140;

    //拒绝策略的高度
    private final int rejectX = 120 ;
    //拒绝策略的宽
    private final int rejectY = 400;



    private final int corePoolHeight= 120;

    private final int corePoolWidth = 240 ;

    private final int maxHeight = 320 ;

    private final int maxPoolWidth = 320;

    //核心线程模块 距离 最大线程模块的 间距
    private final int coreBuffer = 20;


    /***
     * 任务圆圈的 半径
     */
    private final int threadRadius = 30;

    //线程初始的 横坐标
    private final int threadInitX = 50;

    //线程初始的 纵坐标
    private final int threadInitY = 50;

    //---------------
    //任务中模块的文案 buffer
    private final int TASK_TEXT_X_BUFFER = -18;
    private final int TASK_TEXT_Y_BUFFER = 4;

    //-------------
    //活跃线程的 宽和高
    private final int THREAD_WIDTH = 60 ;

    //活跃线程和  上面的 buffer
    private final int ACTIVE_CORE_BUFFER = 20;


    //活跃线程之间 的列间距 buffer
    private final int ACTIVE_COLUMN_BUFFER = 10;


    //活跃线程之间 的 行间距 buffer
    private final int ACTIVE_ROW_BUFFER = 20;


    private final String[] colorArray = new String[]{"rgb(0,0,0)","rgb(90,90,90)","rgb(100,100,100)","rgb(110,110,110)","rgb(120,120,120)","rgb(130,130,130)","rgb(140,140,140)","rgb(150,150,150)","rgb(160,160,160)","rgb(170,170,170)","rgb(180,180,180)","rgb(190,190,190)","rgb(200,200,200)","rgb(210,210,210)","rgb(220,220,220)","rgb(255,255,255)"};

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
        //设置拒绝策略的方框
        poolConstructData.setReject(createReject());

        poolConstruct.setPcd(poolConstructData);


        Group construct = getPoolGroup(poolConstruct);

        poolConstruct.setGroup(construct);




        return poolConstruct;
    }

    @Override
    public PoolConstruct createNewThread(PoolConstruct poolConstruct) {
        AnimationTotal animationTotal = new AnimationTotal();

        PoolConstructData poolConstructData = poolConstruct.getPcd();


        //是否用现有线程
        boolean useThread = false;
//        Integer threadIndex = null;
        Object[] threadObj=null ;

        //是否用现有线程，且线程已隐藏.然后重激活的标志位
        boolean reActive = false;

        //是否是最大线程池的标志位
        Boolean max = null;


        //如果有现有线程
        if(! CollectionUtils.isEmpty(poolConstructData.getThreadMap())){
            for(int i =0;i<poolConstructData.getThreadMap().size(); i++){
                Object[] objects = poolConstructData.getThreadMap().get(i);
                //如果当前线程没有任务 则使用现有线程
                if ( (Boolean)objects[3] == false  ) {

                    max = (Boolean)objects[6];
                    threadObj = objects ;

                    //如果是核心线程,则用老线程
                    if(!max){
                        useThread = true;
                    }
                    //如果是最大线程数
                    if(max){
                        //如果是 显示的,则需要用先有线程, 如果是 隐藏的,则需要重新激活,重新激活需要和是否用队列一起判断
                        if (checkShow(objects)) {
                            useThread = true;

                            //如果队列数量相等,则重新激活最大线程池中的线程
                        }else if(  poolConstructData.getQueuePoolCount() == poolConstructData.getQueueSize()) {
                            reActive = true;
                        }
                    }

                    break;
                }

            }
        }


        //任务 +1

        poolConstructData.setTaskCount(poolConstructData.getTaskCount() + 1  );


        Group group = new Group();
        Rect newThread = null;
        Text newThreadText = null;
        String threadId = null;
        if (poolConstructData.getCoreThreadPoolCount() < poolConstructData.getCorePoolSize()

                || (poolConstructData.getCoreThreadPoolCount() == poolConstructData.getCorePoolSize() &&
                 poolConstructData.getMaxThreadPoolCount() < poolConstructData.getMaxPoolSize() &&
                poolConstructData.getQueuePoolCount() == poolConstructData.getQueueSize())) {

            //如果不是用老线程并且不是激活老线程
            if(!useThread && !reActive){
                String threadIdGraph = UUID.randomUUID().toString();
                //创建线程部分
                newThread = createNewThreadGraph(threadIdGraph);
                newThreadText = createNewThreadText(threadIdGraph);
                group.addChild(newThreadText);
                group.addChild(newThread);
                threadId = newThread.getId();
            }

        }

        //创建任务部分
        Circle newTask =   createNewTaskGraph(poolConstructData.getTaskCount());
        Text newTaskText = createNewTaskTextGraph(poolConstructData.getTaskCount() );
        group.addChild(newTask);
        group.addChild(newTaskText);


        //维护map,key 为任务id , value 为 线程id
        if(StringUtils.isNotEmpty(threadId)){
            poolConstruct.getPcd().getMap().put(poolConstructData.getTaskCount() , threadId);
        }

        //设置分组设置对象
        poolConstruct.setGroup(group);

        //创建移动
        CommonMoveBo commonMoveBo = this.createMove(newTask  ,newTaskText ,newThread ,newThreadText , poolConstructData  , animationTotal ,useThread ,threadObj ,reActive);
//
        poolConstruct.setAt(animationTotal);



        //队列数量的逻辑
        if (poolConstructData.getCoreThreadPoolCount() == poolConstructData.getCorePoolSize()
                && poolConstructData.getQueuePoolCount() < poolConstructData.getQueueSize() ){




            if(useThread){

                poolConstructData.setMaxThreadPoolCount(poolConstructData.getMaxThreadPoolCount() + 1  );
                poolConstruct.getPcd().getMaxTaskNo().add(poolConstructData.getTaskCount());

                //将此线程的任务设置为 true
//                poolConstruct.getPcd().getThreadMap().get(threadIndex)[3]=true;
                threadObj[3] = true;

                //将新任务的线程id 赋值 任务和线程的映射关系
                poolConstruct.getPcd().getMap().put(poolConstructData.getTaskCount() , (String)threadObj[0]);

            }else {

                //队列数量+1
                poolConstructData.setQueuePoolCount(poolConstructData.getQueuePoolCount() + 1);
                //维护队列的list
                poolConstruct.getPcd().getQueueList().add(poolConstructData.getTaskCount());
            }




        }

        //核心线程数量 +1
        else if (poolConstructData.getCoreThreadPoolCount() < poolConstructData.getCorePoolSize()) {
            poolConstructData.setCoreThreadPoolCount(poolConstructData.getCoreThreadPoolCount() + 1  );
            poolConstruct.getPcd().getCoreTaskNo().add(poolConstructData.getTaskCount());


            //如果不用老线程,则创建新线程的 key value
            if(useThread){
                //将此线程的任务设置为 true
//                poolConstruct.getPcd().getThreadMap().get(threadIndex)[3]=true;
                threadObj[3] = true;
                //将新任务的线程id 赋值 任务和线程的映射关系
                poolConstruct.getPcd().getMap().put(poolConstructData.getTaskCount() , (String)threadObj[0]);
            }else {
                poolConstruct.getPcd().getThreadMap().add(new MaxThreadBo(newThread.getId() , null, false ,true , commonMoveBo.getInitX2() , commonMoveBo.getInitY2() , false).createObject() );

            }

        }


        //最大线程池数量 +1
        else if (poolConstructData.getCoreThreadPoolCount() == poolConstructData.getCorePoolSize()
                && poolConstructData.getQueuePoolCount() == poolConstructData.getQueueSize()
                &&poolConstructData.getMaxThreadPoolCount() < poolConstructData.getMaxPoolSize() ){

            poolConstructData.setMaxThreadPoolCount(poolConstructData.getMaxThreadPoolCount() + 1  );
            poolConstruct.getPcd().getMaxTaskNo().add(poolConstructData.getTaskCount());

            //如果是重新激活
            if(reActive){
                threadObj[3] = true;
                //将新任务的线程id 赋值 任务和线程的映射关系
                poolConstruct.getPcd().getMap().put(poolConstructData.getTaskCount() , (String)threadObj[0]);
            }else {
                //维护线程  array的 list
                poolConstruct.getPcd().getThreadMap().add(new MaxThreadBo(newThread.getId() , null, false ,true , commonMoveBo.getInitX2() , commonMoveBo.getInitY2() , true ).createObject() );

            }

        }

        return poolConstruct;
    }

    @Override
    public PoolConstruct executeNewTask(PoolConstruct poolConstruct , Integer taskNo) {
        //次任务是否  是最大线程的 flag
        boolean maxPool = false;
        //队列是否为空的 flag
        boolean queueEmpty = false;
        //此任务号对应的线程id
        String threadId = poolConstruct.getPcd().getMap().get(taskNo);
        AnimationTotal animationTotal = new AnimationTotal();

        if( (!poolConstruct.getPcd().getMaxTaskNo().contains(taskNo))
                && (!poolConstruct.getPcd().getCoreTaskNo().contains(taskNo))
                ){
            poolConstruct.setErrorMsg("此任务编号不在线程中，请输入任务编号为的任务");
            return poolConstruct;
        }

        if(poolConstruct.getPcd().getMaxTaskNo().contains(taskNo)){
            maxPool = true ;
        }

        List<Integer> queueList = poolConstruct.getPcd().getQueueList();

        String cid = "task-"+taskNo;
        String textId = "task-text-"+taskNo;
        //任务ids
        String[] ids = new String[]{cid , textId};

        ChangeContent changeContent = new ChangeContent(cid, null,"任务执行");
        changeContent.setAd("任务将被执行。");
        animationTotal.addComponent(changeContent);



        //隐藏任务
        Hide hide = new Hide();
        hide.setIds(ids);
        animationTotal.addComponent(hide);


        if(maxPool){
            poolConstruct.getPcd().getMaxTaskNo().remove(taskNo);
        }else {
            poolConstruct.getPcd().getCoreTaskNo().remove(taskNo);
        }

        //如果队列里的元素为空,则将标志位  置换为 true ,
        // 如果不为空, 则将线程池中的任务标志为结束,并且再判断如果
        if (CollectionUtils.isEmpty(queueList)){
            queueEmpty = true;

            if(maxPool){
                poolConstruct.getPcd().setMaxThreadPoolCount(poolConstruct.getPcd().getMaxThreadPoolCount() - 1  );

            }else {
                poolConstruct.getPcd().setCoreThreadPoolCount(poolConstruct.getPcd().getCoreThreadPoolCount() - 1  );

            }

        }else {
            Integer queueCid = queueList.get(0);

            //置顶
            Top top = new Top();
            String[] topIds  = new String[]{"task-" + queueCid ,"task-text-" +queueCid};
            top.setIds(topIds);
            animationTotal.addComponent(top);
            //移动模块1
            Swap swap = new Swap();
            String[] sid = new String[]{cid,textId};
            String[] tid = new String[]{"task-" + queueCid,"task-text-" +queueCid};
            swap.setSid(sid);
            swap.setTid(tid);
            swap.setAd("线程池中的任务被执行,由于阻塞队列中有等待任务，所以将任务放入到线程中的空闲线程中执行。");
            animationTotal.addComponent(swap);
            //删除队列中的第一个元素
            poolConstruct.getPcd().getQueueList().remove(0);
            poolConstruct.getPcd().setQueuePoolCount(poolConstruct.getPcd().getQueuePoolCount() -1);

            if(! CollectionUtils.isEmpty(poolConstruct.getPcd().getQueueList())){
                for ( Integer s: poolConstruct.getPcd().getQueueList()){
                    Swap swapList = new Swap();
                    String[] sidList  = new String[]{cid,textId};
                    String[] tidList  = new String[]{"task-" + s,"task-text-" +s};
                    swapList.setSid(sidList);
                    swapList.setTid(tidList);
                    animationTotal.addComponent(swapList);
                }


            }
            //维护map ,将key 和值放入到 map中
            poolConstruct.getPcd().getMap().put(queueCid ,threadId );

            //将队列里的任务数量放入到最大线程中,否则将任务数量放入到核心线程中
            if(maxPool){
                poolConstruct.getPcd().getMaxTaskNo().add(queueCid);

            }else {
                poolConstruct.getPcd().getCoreTaskNo().add(queueCid);
            }


        }

        //销毁对象
        Destroy destroy = new Destroy();
        destroy.setIds(ids);
        animationTotal.addComponent(destroy);
        poolConstruct.setAt(animationTotal);


        //线程销毁逻辑
        if( queueEmpty){
            //如果队列里的元素为空,则将线程开始变色，
            if(CollectionUtils.isEmpty(poolConstruct.getPcd().getQueueList())) {
//                if(maxPool) {

                    //按照线程池循环

                    //将线程所属的任务置成false,表示此线程没有任务
                    for (Object[] maxThreadBo : poolConstruct.getPcd().getThreadMap()) {
                        String dataThreadId = (String)maxThreadBo[0];
                        if (threadId.equals(dataThreadId)){
                            maxThreadBo[3] = false;
                        }


                    }


//                }
            }
        }


        //是否跳出死循环的map ,key 为线程id ,
        Map<String,Boolean> returnMap = new HashMap<>();
        int breakCount=0;

        //如果是最大线程池中的线程,则进行变色处理
        if(maxPool){
            for (;;){
                ChangeColor cc= new ChangeColor();
                List<String> colorIds=new ArrayList<>();
                List<String> colors=new ArrayList<>();
                for (Object[] objects:poolConstruct.getPcd().getThreadMap() ){
                    //如果此线程不含任务,则执行变色处理
                    if (! (Boolean)objects[3]){
                        String threadColor=(String)objects[1];
                        //去掉多余空格
                        threadColor= threadColor.replaceAll(" ","");
                        //如果已经是最后一个颜色,则说明已隐藏
                        if (threadColor.equals(colorArray[colorArray.length-1])){
                            objects[1] = colorArray[colorArray.length-1];
                            continue;
                        }


                        for (int i = 1 ; i < colorArray.length ; i ++){
                            String s = colorArray[i];
                            String lastColor = colorArray[i-1];
                            if(lastColor.equals(threadColor.trim())){
                                colorIds.add((String)objects[0]);
                                colors.add(s);
                                objects[1] = colorArray[i];
                                break;
                            }
                        }
                    }
                }
                if (colorIds.size() == colors.size() && colorIds.size()>0){

                    String[] aidsArray = colorIds.toArray(new String[colorIds.size()]);
                    String[] colorArray = colors.toArray(new String[colors.size()]);
                    cc.setIds(aidsArray);
                    cc.setColors(colorArray);

                    cc.setAd("由于当前线程没有任务执行，所以过一定时间后，线程将被销毁。");
                    animationTotal.addComponent(cc);
                }

                //看如果线程颜色置换为最后一个,则将其置换为隐藏
                for (Object[] objects:poolConstruct.getPcd().getThreadMap() ){

                    if((Boolean)objects[3]){
                        returnMap.put((String)objects[0] ,true);
                    }else {
                        if (colorArray[colorArray.length-1].equals((String)objects[1])){

                            returnMap.put((String)objects[0] ,true);
                            Hide hideThread = new Hide();
                            String[] threadIds = new String[]{(String)objects[0],this.getThreadText((String)objects[0])};
                            hideThread.setIds(threadIds);
                            hideThread.setAd("由于当前线程处在非核心线程池中，并且是没有执行的任务。所以线程将被jvm回收。");
                            animationTotal.addComponent(hideThread);
                        }else {
                            returnMap.put((String)objects[0] ,false);
                        }
                    }
                }



                if (returnMap.size() == poolConstruct.getPcd().getThreadMap().size()){
                    boolean returnFlag = true;

                    for (Object[] ob: poolConstruct.getPcd().getThreadMap()){
                        Boolean threadMapColor = returnMap.get((String)ob[0]);

                        returnFlag =returnFlag && threadMapColor;
                    }
                    if (returnFlag){
                        break;
                    }

                }
            }
        }


        //将任务号所对应的线程删除调
        poolConstruct.getPcd().getMap().remove(taskNo);


        return poolConstruct;
    }

    /****
     * 增加移动动画
     * @param circle
     * @param taskText
     * @param poolConstructData
     * @param animationTotal
     */
    private CommonMoveBo createMove(Circle circle,Text taskText  ,Rect rect, Text threadText,
                            PoolConstructData poolConstructData
            ,AnimationTotal animationTotal , boolean useOldThread ,Object[] threadObj , boolean reActive ){

        String ad =null;
        //核心线程池对象
        CoreThreadPool coreThreadPool = poolConstructData.getCoreThreadPool();
        //队列数
        QueuePool queuePool = poolConstructData.getQueuePool();
        //最大线程池对象
        MaxThreadPool maxThreadPool = poolConstructData.getMaxThreadPool();
        //拒绝策略
        PoolCommonAttribute poolCommonAttribute = poolConstructData.getReject();

        int queueSize = poolConstructData.getQueuePoolCount();



        int initX1 =0 ;
        int initY1 =0 ;
        int initX2 =0 ;
        int initY2 =0 ;


        int initX3 = 0  ;
        int initY3 = 0  ;

//        int row = corePoolHeight / (threadRadius *2) ;

        //每个核心线程数量放置的 对象数量
        int column = corePoolWidth / (THREAD_WIDTH + ACTIVE_COLUMN_BUFFER );

        //每个最大线程放置的数量
        int columnMax =maxThreadPool.getWidth()/ (THREAD_WIDTH+ ACTIVE_COLUMN_BUFFER);
        CommonMove commonMove = null ;

        boolean createThreadFlag = false;
        boolean rejectFlag = false;

        //如果用现有线程
        if(useOldThread ){

            ad = "由于当前线程池中有空闲线程。所以将等待任务放入到空闲线程执行。";

//            ad = "核心线程数量已满,所以将任务放到执行队列中。";
//            commonMove = createCircleMove(queuePool.getX() , queuePool.getY() , queuePool.getWidth(),
//                    0, queueSize );
            commonMove = createCircleMove(queuePool.getX() , queuePool.getY() , queuePool.getWidth(),
                    0, queueSize );


            initX3 = (Integer) threadObj[4];
            initY3 = (Integer) threadObj[5];

        }else {
            /***
             * 如果核心线程池里的线程数少于总线程数量
             */
            if (poolConstructData.getCoreThreadPoolCount() < poolConstructData.getCorePoolSize()) {

                createThreadFlag =true;

                ad = "当前核心线程池中有空闲线程,所以将执行任务放入到核心线程池中的线程执行。";
                if (poolConstructData.getCoreColumnIndex() == column){
                    //行数的下标数量加 1
                    poolConstructData.setCoreRowIndex(poolConstructData.getCoreRowIndex() + 1);

                    //如果为新的一行,则将 列号置为0
                    poolConstructData.setCoreColumnIndex(0);
                }
                if  (poolConstructData.getCoreColumnIndex() < column) {

                    commonMove = createCommonMove(coreThreadPool.getX() , coreThreadPool.getY(),corePoolWidth,
                            poolConstructData.getCoreRowIndex(), poolConstructData.getCoreColumnIndex());

                    //核心线程的方框 +  核心线程的高度


                    poolConstructData.setCoreColumnIndex(poolConstructData.getCoreColumnIndex() + 1 );
                }
            }


            //如果核心线程数满了,执行队列 加操作
            if (poolConstructData.getCoreThreadPoolCount() == poolConstructData.getCorePoolSize()
                    && queueSize < poolConstructData.getQueueSize()){

                ad = "核心线程数量已满,所以将任务放到执行队列中。";
                commonMove = createCircleMove(queuePool.getX() , queuePool.getY() , queuePool.getWidth(),
                        0, queueSize );
            }


            //如果核心线程池和队列都满了， 则往最大线程池里放,并且不是重新激活的逻辑
            if(!reActive && poolConstructData.getCoreThreadPoolCount() == poolConstructData.getCorePoolSize() &&
                    queueSize == poolConstructData.getQueueSize() &&
                    poolConstructData.getMaxThreadPoolCount() < poolConstructData.getMaxPoolSize()){
                createThreadFlag = true ;
                ad = "因为核心线程数量已满，队列中已满。所以将任务放到线程池中活跃线程执行。";
                if (poolConstructData.getMaxColumnIndex() == columnMax){
                    //行数的下标数量加 1
                    poolConstructData.setMaxRowIndex(poolConstructData.getMaxRowIndex() + 1);

                    //如果为新的一行,则将 列号置为0
                    poolConstructData.setMaxColumnIndex(0);
                }
                if  (poolConstructData.getMaxColumnIndex() < columnMax) {
                    commonMove = createCommonMove(maxThreadPool.getX() , (maxThreadPool.getY() + coreThreadPool.getHeight() +coreBuffer ), maxThreadPool.getWidth(),
                            poolConstructData.getMaxRowIndex(), poolConstructData.getMaxColumnIndex() );
                    poolConstructData.setMaxColumnIndex(poolConstructData.getMaxColumnIndex() + 1 );
                }

            }
            if(poolConstructData.getCoreThreadPoolCount() == poolConstructData.getCorePoolSize() &&
                    queueSize == poolConstructData.getQueueSize() &&
                    poolConstructData.getMaxThreadPoolCount() == poolConstructData.getMaxPoolSize()){

                ad = "因为核心线程数量已满，队列中已满，线程池中已满。所以将任务执行拒绝策略。";

                rejectFlag = true;

                commonMove = createCircleMove(poolCommonAttribute.getX() , poolCommonAttribute.getY(),rejectWidth,
                        0, 0);

            }

            //如果是重新激活,则将任务放在待激活线程中
            if (reActive){
                commonMove = createCircleMove(queuePool.getX() , queuePool.getY() , queuePool.getWidth(),
                        0, queueSize );

                commonMove.setX2((Integer) threadObj[4]+  threadRadius);
                commonMove.setY2((Integer) threadObj[5]+ACTIVE_CORE_BUFFER +threadRadius);

            }

        }

        //重新将线程激活
        if(reActive){

            String[] idd = new String[]{(String)threadObj[0]};
            ChangeColor cc = new ChangeColor();
            cc.setIds(idd);
            cc.setTotalColor(colorArray[0]);
            cc.setAd("由于核心线程池已满,队列已满,所以新建线程,然后将新任务放入到线程中去执行");
            animationTotal.addComponent(cc);

            //将线程展示出来
            animationTotal.addComponent(new Show((String)threadObj[0] , this.getThreadText((String)threadObj[0])));

        }



        //现在只有在 最大线程的重新激活中会涉及次业务。
        if(commonMove!=null){

            initX1 = commonMove.getX1() ;
            initY1 = commonMove.getY1() ;
            initX2 = commonMove.getX2() ;
            initY2 = commonMove.getY2() ;
        }

//        if ()


        //因为线程中，任务外面还套着一层 线程方框， 所以要加buffer
        int taskBufferX = 0;
        int taskBufferY=0;

        //需要创建新线程
        if(createThreadFlag) {

            //移动模块1
            MultiMove multiMoveThread1 = new MultiMove();
            List<MultiMoveDetail> details = new ArrayList<>();
            details.add(new MultiMoveDetail(rect.getId() , initX1,initY1  +  ACTIVE_CORE_BUFFER));
            details.add(new MultiMoveDetail(threadText.getId() , initX1,initY1+ TEXT_BUFFER +  ACTIVE_CORE_BUFFER ));
            multiMoveThread1.setDetails(details);
            multiMoveThread1.setAd("线程池中没有可用的活跃线程,所以先创建活跃线程。");
            animationTotal.addComponent(multiMoveThread1);

            //移动模块2
            MultiMove multiMoveThread2 = new MultiMove();
            List<MultiMoveDetail> details2 = new ArrayList<>();
            details2.add(new MultiMoveDetail(rect.getId() , initX2 ,initY2  +  ACTIVE_CORE_BUFFER));
            details2.add(new MultiMoveDetail(threadText.getId() , initX2  ,initY2 +TEXT_BUFFER+  ACTIVE_CORE_BUFFER ));
            multiMoveThread2.setDetails(details2);
            multiMoveThread2.setAd("线程池中没有可用的活跃线程,所以先创建活跃线程。");
            animationTotal.addComponent(multiMoveThread2);


            taskBufferX = taskBufferX +  threadRadius;
            taskBufferY = taskBufferY + +threadRadius +ACTIVE_CORE_BUFFER;



        }







        //将任务的圆圈显示出来
        animationTotal.addComponent(new Show(circle.getId(),taskText.getId()));

        //移动模块1
        MultiMove multiMove1 = new MultiMove();
        List<GraphComponent> multiMoveList1 = new ArrayList<>();
        Circle moveCircle = new Circle(circle.getId() , initX1  + taskBufferX, initY1 + taskBufferY);
        Text moveText = new Text(taskText.getId() , initX1 +  TASK_TEXT_X_BUFFER + taskBufferX, initY1 +TASK_TEXT_Y_BUFFER + taskBufferY);
        multiMoveList1.add(moveCircle);
        multiMoveList1.add(moveText);
        multiMove1.setGcs(multiMoveList1);
        multiMove1.setAd(ad);
        animationTotal.addComponent(multiMove1);

        //移动模块2
        MultiMove multiMove2 = new MultiMove();
        List<GraphComponent> multiMoveList2 = new ArrayList<>();
        Circle moveCircle2 = new Circle(circle.getId() , initX2 +taskBufferX , initY2 + taskBufferY);
        Text moveText2 = new Text(taskText.getId() , initX2 +  TASK_TEXT_X_BUFFER+ taskBufferX , initY2 +TASK_TEXT_Y_BUFFER + taskBufferY);
        multiMoveList2.add(moveCircle2);
        multiMoveList2.add(moveText2);
        multiMove2.setGcs(multiMoveList2);
        multiMove2.setAd(ad);
        animationTotal.addComponent(multiMove2);

        if(initX3 > 0 && initY3 >0){
            String[] idd = new String[]{(String)threadObj[0]};
            ChangeColor cc = new ChangeColor();
            cc.setIds(idd);
            cc.setTotalColor(colorArray[0]);
            animationTotal.addComponent(cc);


            taskBufferX = taskBufferX +  threadRadius;
            taskBufferY = taskBufferY + +threadRadius +ACTIVE_CORE_BUFFER;

            //移动模块3
            MultiMove multiMove3 = new MultiMove();
            List<GraphComponent> multiMoveList3 = new ArrayList<>();
            Circle moveCircle3 = new Circle(circle.getId() , initX3 +taskBufferX , initY3 + taskBufferY);
            Text moveText3 = new Text(taskText.getId() , initX3 +  TASK_TEXT_X_BUFFER+ taskBufferX , initY3 +TASK_TEXT_Y_BUFFER + taskBufferY);
            multiMoveList3.add(moveCircle3);
            multiMoveList3.add(moveText3);
            multiMove3.setGcs(multiMoveList3);
            multiMove3.setAd(ad);
            animationTotal.addComponent(multiMove3);

        }


        if (rejectFlag) {
            ChangeContent changeContent = new ChangeContent(taskText.getId(), null,"抛异常");
            changeContent.setAd("由于队列和线程池都已满，所以执行拒绝策略。");
            animationTotal.addComponent(changeContent);

            Destroy destroy = new Destroy();
            String[] ids = new String[2];
            ids[0] = circle.getId();
            ids[1] = taskText.getId();
            destroy.setIds(ids);

            destroy.setAd("执行拒绝策略，抛出异常，任务结束。");
            animationTotal.addComponent(destroy);
        }



        CommonMoveBo commonMoveBo = new CommonMoveBo();


        commonMoveBo.setInitX1(initX1);


        commonMoveBo.setInitY1(initY1);

        commonMoveBo.setInitX2(initX2);


        commonMoveBo.setInitY2(initY2);

        commonMoveBo.setInitX3(initX3);


        commonMoveBo.setInitY3(initY3);

        return commonMoveBo;
    }

    //创建圆的移动
    private CommonMove createCircleMove (int backX , int backY , int backWidth , int rowIndex , int columnIndex){
        CommonMove commonMove = new CommonMove();

        int x1 = threadInitX ;
        //核心线程的方框 +  核心线程的高度
        int y1 = backY + ( rowIndex * threadRadius*2) + threadRadius  ;
        int x2 = backX + backWidth - (columnIndex * threadRadius*2) - threadRadius  - ( (columnIndex+1) *ACTIVE_COLUMN_BUFFER);
        int y2 =  y1 ;

        commonMove.setX1(x1);
        commonMove.setY1(y1);
        commonMove.setX2(x2);
        commonMove.setY2(y2);

        return commonMove;

    }
    //创建方框的移动
    private CommonMove createCommonMove (int backX , int backY , int backWidth , int rowIndex , int columnIndex){
        CommonMove commonMove = new CommonMove();

        int x1 = threadInitX ;
        //核心线程的方框 +  核心线程的高度
        int y1 = backY + ( rowIndex * THREAD_WIDTH)  + ( rowIndex * ACTIVE_ROW_BUFFER )  ;

        int x2 = backX + backWidth - (columnIndex * THREAD_WIDTH) - THREAD_WIDTH - ( (columnIndex+1) *ACTIVE_COLUMN_BUFFER);
        int y2 =  y1 ;

        commonMove.setX1(x1);
        commonMove.setY1(y1);
        commonMove.setX2(x2);
        commonMove.setY2(y2);

        return commonMove;

    }


//    private CommonMove createRejectMove (int backX , int backY , int backWidth , int rowIndex , int columnIndex){
//        CommonMove commonMove = new CommonMove();
//
//        int x1 = threadInitX ;
//        //核心线程的方框 +  核心线程的高度
//        int y1 = backY + ( rowIndex * THREAD_WIDTH)  + ( rowIndex * ACTIVE_ROW_BUFFER )  ;
//
//        int x2 = backX + backWidth - (columnIndex * THREAD_WIDTH) - THREAD_WIDTH - ( (columnIndex+1) *ACTIVE_COLUMN_BUFFER);
//        int y2 =  y1 ;
//
//        commonMove.setX1(x1);
//        commonMove.setY1(y1);
//        commonMove.setX2(x2);
//        commonMove.setY2(y2);
//
//        return commonMove;
//
//    }

    private Group getPoolGroup(PoolConstruct poolConstruct) {

        CoreThreadPool coreThreadPool = poolConstruct.getPcd().getCoreThreadPool();

        MaxThreadPool maxThreadPool = poolConstruct.getPcd().getMaxThreadPool();

        QueuePool queuePool = poolConstruct.getPcd().getQueuePool();

        PoolCommonAttribute poolCommonAttribute = poolConstruct.getPcd().getReject();

        Group all = new Group();

        Group max = new Group();
        Rect rectMax = new Rect(UUID.randomUUID().toString(), maxThreadPool.getWidth(), maxThreadPool.getHeight(),maxThreadPool.getX() ,maxThreadPool.getY(),MAX_COLOR);
        max.addChild(rectMax);

        Text text = new Text(UUID.randomUUID().toString() , maxThreadPool.getX(),maxThreadPool.getY()  + TEXT_BUFFER,"线程池——容量为" + (poolConstruct.getPcd().getMaxPoolSize()+ poolConstruct.getPcd().getCorePoolSize() ) );
        max.addChild(text);


        all.addChild(max);
        all.addChild(text);


        Group core = new Group();
        core.addChild(new Rect(UUID.randomUUID().toString(), coreThreadPool.getWidth(), coreThreadPool.getHeight(),coreThreadPool.getX() ,coreThreadPool.getY(),CORE_COLOR));

        core.addChild(new Text(UUID.randomUUID().toString() , coreThreadPool.getX(),coreThreadPool.getY() + TEXT_BUFFER,"核心线程池——容量为" + poolConstruct.getPcd().getCorePoolSize()));


        all.addChild(core);


        Group queue = new Group();
        Rect queueRect = new Rect(UUID.randomUUID().toString(), queuePool.getWidth(), queuePool.getHeight(),queuePool.getX() ,queuePool.getY(),QUEUE_COLOR);
        queue.addChild(queueRect);

        queue.addChild(new Text(UUID.randomUUID().toString() , queuePool.getX(),queuePool.getY() + TEXT_BUFFER,"工作队列——队列容量为" + poolConstruct.getPcd().getQueueSize()));

        all.addChild(queue);





        Group reject = new Group();
        Rect rejectRect = new Rect(UUID.randomUUID().toString(), poolCommonAttribute.getWidth(), poolCommonAttribute.getHeight(),poolCommonAttribute.getX() ,poolCommonAttribute.getY(),REJECT_COLOR);
        reject.addChild(rejectRect);

        reject.addChild(new Text(UUID.randomUUID().toString() , poolCommonAttribute.getX(),poolCommonAttribute.getY() + TEXT_BUFFER,"拒绝策略(AbortPolicy)"));

        all.addChild(reject);


        all.setCompose(ComponentCompositeEnum.HORIZONTAL.getType());

        return all;

    }


    /****
     * 创建任务的圆的对象
     * @return
     */
    private Circle createNewTaskGraph(int taskIndex){

        Circle task = new Circle("task-"+taskIndex, threadRadius, ColorEnum.RED.getHtmlCode()) ;
        task.setX(threadInitX);
        task.setY(threadInitY);
        task.setH(DisplayEnum.NONE.getContent());
        return task ;
    }

    /***
     * 创建任务的文本
     * @return
     */
    private Text createNewTaskTextGraph(int taskIndex){
        Text text = new Text("task-text-"+taskIndex , threadInitX +TASK_TEXT_X_BUFFER  , threadInitY + TASK_TEXT_Y_BUFFER,"任务" + taskIndex);
        text.setH(DisplayEnum.NONE.getContent());
        return text;
    }

    /***
     * 创建线程的对象
     * @return
     */
    private Rect createNewThreadGraph(String threadIdGraph){
        Rect thread = new Rect("thread-"+ threadIdGraph, THREAD_WIDTH,THREAD_WIDTH,threadInitX,threadInitX,"black" ) ;
        return thread;
    }

    private Text createNewThreadText(String threadIdGraph){
        Text text = new Text("thread-text-"+ threadIdGraph, threadInitX   , threadInitY  + TEXT_BUFFER,"活跃线程");

        return text;

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


    /***
     * 创建拒绝策略
     * @return
     */
    private PoolCommonAttribute createReject(){
        //队列数
        PoolCommonAttribute rejectPool = new PoolCommonAttribute();
        rejectPool.setWidth(rejectWidth);
        rejectPool.setHeight(rejectHeight);
        rejectPool.setX(rejectX);
        rejectPool.setY(rejectY);
        return rejectPool;
    }


    //检查线程是否为 隐藏
    private boolean checkShow(Object[] threadObj){

        String rgb = (String)threadObj[1];

        rgb = rgb.replace(" ","");
        if (colorArray[colorArray.length-1].equals(rgb)){
            return false;
        }
        return true;
    }

    /***
     * 根据线程id获取线程文本id
     * @param threadId
     * @return
     */
    private String getThreadText(String threadId){
        if(StringUtils.isNotEmpty(threadId)){
            return threadId.replace("thread","thread-text");
        }
        return null;
    }

}
