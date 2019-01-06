package com.sxyl.portal.domain.nn.dnn.param;

import lombok.Data;

import java.io.Serializable;

/****
 * 用户设置超参数
 */
@Data
public class DnnParam  implements Serializable {

    private static final long serialVersionUID = 1L;

    /****
     * 输入文本的前缀
     */
    private String inputText = "x";

    /***
     * 求和的文字展示
     */
    private String sumText = "z";

    /****
     * 激活函数的文字展示
     */
    private String activationText = "a";


}
