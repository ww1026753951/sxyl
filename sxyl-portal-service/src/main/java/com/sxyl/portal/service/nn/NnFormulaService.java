package com.sxyl.portal.service.nn;

import com.sxyl.portal.domain.formula.MathFormulaTemplate;
import com.sxyl.portal.domain.graph.Group;

import java.util.List;

public interface NnFormulaService {


    /****
     *
     * 总公式
     * @return
     */
    List<MathFormulaTemplate> getDnnFormulaTotal();


    /****
     * 获取dnn具体的执行步骤公式
     * @param inputIds
     * @param hiddenIds
     * @param outputIds
     * @return
     */
    Group getDnnFormulaDetailSVG(List<String> inputIds, List<List<String>> hiddenIds, List<String> outputIds);
}
