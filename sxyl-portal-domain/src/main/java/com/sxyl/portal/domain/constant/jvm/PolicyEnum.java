package com.sxyl.portal.domain.constant.jvm;

/**
 * @author: create by wei.wang
 * @version: v1.0
 * @description: com.sxyl.portal.domain.constant
 * @date:2020/11/7
 */
public enum  PolicyEnum {



    //抛异常
    ABORT_POLICY(1,"抛异常","AbortPolicy"),
    //丢弃任务
    DISCARD_POLICY(2,"丢弃任务","DiscardPolicy"),
    //丢弃最老的任务
    DISCARD_OLDEST_POLICY(3,"丢弃最老的任务","DiscardOldestPolicy"),
    //放回圆线程执行
    CALLER_RUNS_POLICY(4,"放回原线程执行","CallerRunsPolicy");

    private final int type;
    private final String desc;
    private final String showText;

    private PolicyEnum(int type , String desc , String showText) {
        this.type = type;
        this.desc = desc;
        this.showText = showText;
    }

    /**
     *codeOf
     */
    public static PolicyEnum codeOf(int code) {
        for (PolicyEnum e : PolicyEnum.values()) {
            if (code== e.getType() ) {
                return e;
            }
        }
        return null;
    }

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public String getShowText() {
        return showText;
    }
}
