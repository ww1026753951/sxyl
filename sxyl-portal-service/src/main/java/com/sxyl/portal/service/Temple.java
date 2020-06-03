package com.sxyl.portal.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: create by wei.wang
 * @version: v1.0
 * @description: com.sxyl.portal.service
 * @date:2020/2/19
 */
public abstract class Temple {



    ExecutorService pool = Executors.newFixedThreadPool(5);

    public abstract int A();

    public abstract int B();

    public int templeMethod(){
        this.A();
//        this.B();
        pool.execute(()->{
            this.B();
        });

        return 1;
    }

}
