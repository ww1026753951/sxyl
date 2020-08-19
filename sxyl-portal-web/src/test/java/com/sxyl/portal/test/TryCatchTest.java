package com.sxyl.portal.test;

/**
 * @author: create by wei.wang
 * @version: v1.0
 * @description: com.sxyl.portal.test
 * @date:2020/6/18
 */
public class TryCatchTest {


    public static void main(String[] args) {


        TryCatchTest tryCatchTest = new TryCatchTest();

        tryCatchTest.testTry();

    }

    private String testTry(){

        try {

            testExecute();


            return testReturn();

        }catch (Exception e){

        }finally {
            System.out.println("finally");
        }

        return "b";

    }

    private String testReturn(){
        System.out.println("return");
        return "a";
    }


    private void testExecute(){
        System.out.println("execute");
    }
}
