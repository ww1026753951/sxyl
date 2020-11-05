package com.sxyl.portal.test;

import java.util.concurrent.*;

/**
 * @author: create by wei.wang
 * @version: v1.0
 * @description: com.sxyl.portal.test
 * @date:2019/7/10
 */
public class PoolTest {


    public static void main(String[] args) {
//        ExecutorService pool = Executors.newFixedThreadPool(5);

        ExecutorService pool = new ThreadPoolExecutor(1,3 , 10000,TimeUnit.HOURS
                , new ArrayBlockingQueue<Runnable>(1) );
        /**
         * execute(Runnable x) 没有返回值。可以执行任务，但无法判断任务是否成功完成。
         */
//        pool.execute(new RunnableTest("Task1"));


        pool.execute(new RunnableTest("Task1"));

        /**
         * submit(Runnable x) 返回一个future。可以用这个future来判断任务是否成功完成。请看下面：
         */
        pool.execute(new RunnableTest("Task2"));



        pool.execute(new RunnableTest("Task3"));

        pool.execute(new RunnableTest("Task4"));



        try {

            Thread.sleep(9000);
        }catch (Exception e){

        }

//        pool.execute(new RunnableTest("Task5"));
//
//        pool.execute(new RunnableTest("Task6"));
//
//
//        pool.execute(new RunnableTest("Task7"));


        for (int i=0; i<=10; i++){

            try {

                Thread.sleep(9);
            }catch (Exception e){

            }
            pool.execute(new RunnableTest("Task1"+i));
        }



//        try {
//            if(future.get()==null){//如果Future's get返回null，任务完成
//                System.out.println("任务完成");
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            //否则我们可以看看任务失败的原因是什么
//            System.out.println(e.getCause().getMessage());
//        }

    }

}

