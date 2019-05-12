package com.sxyl.portal.domain.d;

import com.sxyl.portal.domain.constant.AnimationEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class RefreshFormula  extends AnimationComponent implements Serializable {

    private static final long serialVersionUID = 1L;

    /*****
     * 需要移动的元素的目标id
     */
    private String tid;



    @Override
    public void setAnimationType() {
        super.setAt(AnimationEnum.FRESH_FORMULA.getType());
    }
}
