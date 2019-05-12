package com.sxyl.portal.domain.d;

import com.sxyl.portal.domain.constant.AnimationEnum;

import java.io.Serializable;

public class MoveFormulaResult extends AnimationComponent implements Serializable {

    private static final long serialVersionUID = 1L;

    /***
     * 公式id
     */
    private String formulaId;

    /***
     * 目标id
     */
    private String tid;


    public MoveFormulaResult(String formulaId, String tid) {
        this.formulaId = formulaId;
        this.tid = tid;
    }

    public String getFormulaId() {
        return formulaId;
    }

    public void setFormulaId(String formulaId) {
        this.formulaId = formulaId;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    @Override
    public void setAnimationType() {
        super.setAt(AnimationEnum.MOVE_FORMULA_RESULT.getType());
    }
}
