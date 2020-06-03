package com.sxyl.portal.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: create by wei.wang
 * @version: v1.0
 * @description: com.sxyl.portal.service
 * @date:2020/2/19
 */
public class TempleErYeTemplate extends Temple {


    ExecutorService pool = Executors.newFixedThreadPool(5);
    @Override
    public int A() {

        System.out.print("二爷 A");
        return 0;
    }

    @Override
    public int B() {

        pool.execute(() ->{

            System.out.print("二爷 B");
        });

        return 0;
    }
}
