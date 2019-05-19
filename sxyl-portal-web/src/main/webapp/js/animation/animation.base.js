SXYL.ANIMATION={

    //从接口中获取的全量执行步骤
    ALL_STEP:undefined,
    //公式执行步骤
    FORMULA_STEP:undefined,
    //备注执行步骤
    SEQUENCE_STEP:undefined,
    /****
     * 清除公式
     */
    cleanFormulaStep:function(){
        SXYL.ANIMATION.FORMULA_STEP = {};
    },
    /****
     * 清除描述序列
     */
    cleanSequenceStep:function(){
        SXYL.ANIMATION.SEQUENCE_STEP = {};
    },
    /***
     * 初始化公式执行步骤
     */
    initFormulaStep:function(o){
        if(o instanceof Array){
            for (var i=0;i<o.length;i++){
                SXYL.ANIMATION.initFormulaStep(o[i]);
            }
        }else if (o instanceof Object){
            if(o.cache){
                SXYL.ANIMATION.FORMULA_STEP[o.id]=o;
            }
        }
        if(o.child){
            SXYL.ANIMATION.initFormulaStep(o.child);
        }
    },
    /***
     * 初始化执行map
     * @param o
     */
    initExecutionSequence:function(o){
        if(!o){
            debugger;
        }
        for (var i=0;i<o.length;i++){
            SXYL.ANIMATION.SEQUENCE_STEP[o.stepCode]=o;
        }
    },

    AT_MAP:{
        //拷贝对象
        1:{f:SXYL.DOM.copyElement},
        //移动对象
        2:{f:SXYL.DOM.moveElement},
        //交换对象对象
        3:{f:SXYL.DOM.swapElement},
        //销毁对象
        4:{f:SXYL.DOM.destroyElement},
        //移动公式对象结果
        5:{f:SXYL.DOM.moveFormulaResult},

        //拷贝对象结果
        6:{f:SXYL.DOM.copyFormulaResult},

        //变色
        7:{f:SXYL.DOM.changeColor},

        8:{f:SXYL.DOM.show},

        //赋值对象内容
        10:{f:SXYL.DOM.changeElement},
        //刷新公式
        11:{f:SXYL.DOM.refreshFormula},
        //清空公式
        12:{f:SXYL.DOM.clearFormula},
        //累计乘积方法
        51:{f:SXYL.DOM.multiplyElement},
        //求和方法
        52:{f:SXYL.DOM.sumElement},
        //sigmoid方法
        61:{f:SXYL.DOM.sigmoidElement},
        //平方差
        62:{f:SXYL.DOM.squareErrorElement},
        71:{f:SXYL.DOM.computeOutWeight},
        //平方差
        72:{f:SXYL.DOM.computeHiddenWeight},


        //公式拷贝方法
        111:{f:SXYL.DOM.copyFormulaElement}
        // 4:{f:SXYL.GRAPH.C},
        // 5:{f:SXYL.GRAPH.L}
    },

    /****
     * 执行操作步骤
     */
    executeAnimation : function () {
        var executeStep = SXYL.ANIMATION.ALL_STEP.acs;

        for (var i = SXYL.step_no ; i< executeStep.length  ; i++){
            var stepObject = executeStep[i];
            var type = stepObject.at;

            if(stepObject.ad != undefined){
                $("#step-content").html(stepObject.ad);
            }

            var ob = SXYL.ANIMATION.AT_MAP[type];
            if(ob){
                ob.f(stepObject);
            }
            SXYL.step_no++;
            if(SXYL.step_no == executeStep.length){
                window.clearInterval(SXYL.execute_i);
            }
            return;
        }

    }

}