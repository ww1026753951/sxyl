package com.sxyl.portal.domain.nn.dnn.param;

import lombok.Data;

import java.io.Serializable;

@Data
public class DnnParam  implements Serializable {

    private static final long serialVersionUID = 1L;



    //用户可控参数先作为唯一变量
    //输入文本的前缀
    private String inputText = "X";


}
