package com.sxyl.portal.domain.formula;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MathFormulaTemplate  implements Serializable {

    private static final long serialVersionUID = 1L;


    /****
     * id
     */
    private String id;


    /**
     * 公式内容
     */
    private String fc;
}
