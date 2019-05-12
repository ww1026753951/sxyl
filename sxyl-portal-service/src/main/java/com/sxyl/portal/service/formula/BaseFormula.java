package com.sxyl.portal.service.formula;

import com.sxyl.portal.domain.graph.MathFormula;
import org.apache.commons.lang.StringUtils;

public class BaseFormula {

    String EQUALS = "=";

    String EQUALS_REPLACE = "????";

    /***
     * 替换元素的起始位置
     */
    String REPLACE_START = "!re_s!" ;

    /****
     * 替换元素的截止位置
     */
    String REPLACE_END = "!re_e!" ;

    protected StringBuffer getStartEnd(){
        return new StringBuffer("$$") ;
    }

    /***
     * 获取公式等号左侧信息信息
     * @param f
     * @param sup
     * @param sub
     * @return
     */
    protected StringBuffer getF(String f,String sup,String sub){
        return getCommonText(getStartEnd() , f , sup , sub).append("=") ;
    }
    /***
     * 获取公式等号左侧信息信息
     * @param f
     * @param sup
     * @param sub
     * @return
     */
    protected StringBuffer getFLine(String f,String sup,String sub){
        return getCommonText(getStartEnd() , f , sup , sub).append("&=") ;
    }
    /***
     * 获取公式等号左侧信息信息
     * @param f
     * @param sup
     * @param sub
     * @return
     */
    protected StringBuffer getNodeText(String f,String sup,String sub){
        return getCommonText(new StringBuffer() , f , sup , sub) ;
    }

    private StringBuffer getCommonText(StringBuffer stringBuffer ,String f,String sup,String sub){
        stringBuffer = stringBuffer.append(f);
        if(StringUtils.isNotBlank(sup)){
            stringBuffer.append("^").append(addBrace(sup));
        }
        if(StringUtils.isNotBlank(sub)){
            stringBuffer.append("_").append(addBrace(sub));
        }
        return stringBuffer ;
    }
    /***
     * 获取公式结果的公共方法
     * @return
     */
    protected StringBuffer getResult(){
        StringBuffer stringBuffer = new StringBuffer(EQUALS) ;
        stringBuffer.append(REPLACE_START).append(EQUALS_REPLACE).append(REPLACE_END);
        stringBuffer.append(getStartEnd());
        return stringBuffer ;
    }

    /***
     * 获取公式变量的方法
     *
     * !re_s!x}!re_e!
     * @return
     */
    protected StringBuffer getVariable(String variable){
        StringBuffer stringBuffer = new StringBuffer(REPLACE_START) ;
        stringBuffer.append("\\color{red}{").append(variable).append("}").append(REPLACE_END);
        return stringBuffer ;
    }

    /***
     * 获取公式变量的方法
     *
     * !re_s!x}!re_e!
     * @return
     */
    protected StringBuffer getVariable(StringBuffer sb){
        StringBuffer stringBuffer = new StringBuffer(REPLACE_START) ;
        stringBuffer.append("\\color{red}{").append(sb).append("}").append(REPLACE_END);
        return stringBuffer ;
    }

    /***
     * 替换公式占位符
     * @param rfc
     * @return
     */
    protected String replaceFormula(String rfc){
        String fText = rfc.replaceAll(REPLACE_START,"");
        fText = fText.replaceAll(REPLACE_END,"");
        return fText ;
    }

    /***
     * 增加括号
     * @param sb
     * @return
     */
    protected StringBuffer addBrace(String sb){
        return new StringBuffer("{").append(sb).append("}");
    }


    /***
     * 组装对象
     * @param formulaTemplate
     * @return
     */
    protected MathFormula wrapMathFormula(String formulaTemplate){
        MathFormula mathFormula = new MathFormula();
        mathFormula.setRfc(formulaTemplate);
        mathFormula.setFc(replaceFormula(formulaTemplate));
        return mathFormula;

    }


}
