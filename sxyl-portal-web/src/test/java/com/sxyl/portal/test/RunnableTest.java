package com.sxyl.portal.test;

/**
 * @author: create by wei.wang
 * @version: v1.0
 * @description: com.sxyl.portal.test
 * @date:2019/7/10
 */

public class RunnableTest implements Runnable {

    private String taskName;

    public RunnableTest(final String taskName) {
        this.taskName = taskName;
    }

    @Override
    public void run() {
        System.out.println("Inside "+taskName);
//        throw new RuntimeException("RuntimeException from inside " + taskName);
    }
}
