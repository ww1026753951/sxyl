package com.sxyl.portal.domain.jvm.pool;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: create by wei.wang
 * @version: v1.0
 * @description: com.sxyl.portal.domain.jvm.pool
 * @date:2020/10/8
 */
@Data
@AllArgsConstructor
public class MaxThreadBo implements Serializable {

    private static final long serialVersionUID = 1L;


    //线程id
    private String threadId;


    /****
     * 视图层的rgb 前端传入
     */
    private String viewRgb;


    /****
     * 是否已隐藏,前端传入.
     */
    private boolean display;

    //当前线程是否有任务
    private boolean hasTask;

    //线程的x的值
    private int x;

    //线程的y值
    private int y;

    private boolean max;


    public Object[] createObject(){
        Object[] result = new Object[7];
        result[0] = this.threadId;
        result[1] = this.viewRgb;
        result[2] = this.display;
        result[3] = this.hasTask;
        result[4] = this.x;
        result[5] = this.y;
        result[6] = this.max;
        return result;
    }
}
