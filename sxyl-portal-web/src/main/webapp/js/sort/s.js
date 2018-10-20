SXYL.SORT.BubbleSort = function () {
    if(SXYL.i == undefined){
        SXYL.i = 0
    }
    for (var i = SXYL.i ; i < SXYL.SORT.array.length ; i++) {
        if(SXYL.j == undefined) {
            SXYL.j = 0 ;
        }
        SXYL.SORT.executeStep("background1",[["{SIZE}",SXYL.SORT.array.length - 1],["{INDEX}",i]] , "silver");
        for(var j = SXYL.j ; j < SXYL.SORT.array.length - 1 -i ; j++){
            // console.log("compare index,i="+i+",j="+j+" , a = " + x[j] + ", b = " + x[j+1]);
            SXYL.SORT.executeStep("background2",[["{SIZE}",SXYL.SORT.array.length - 2 -i],["{INDEX}",j]] , "silver");
            //变换颜色
            var leftColor =SXYL.DOM.changeElementColor(SXYL.SORT.uuidArray[j], "yellow") ;
            var rightColor =SXYL.DOM.changeElementColor(SXYL.SORT.uuidArray[j+1], "yellow") ;
            if(leftColor && rightColor){
                //执行步骤变色
                SXYL.SORT.executeStep("A1");
                return ;
            }
            var leftColor =SXYL.DOM.changeElementColor(SXYL.SORT.uuidArray[j], "red") ;
            var rightColor =SXYL.DOM.changeElementColor(SXYL.SORT.uuidArray[j+1], "red") ;
            //比较大小 , 说明需要换位置
            if(SXYL.SORT.array[j] > SXYL.SORT.array[j+1]){
                //元素交换位置
                SXYL.DOM.changeElementTransformX(SXYL.SORT.uuidArray[j] , SXYL.SORT.uuidArray[j+1]);
                //uuid的数组进行交换
                SXYL.base.changeArrayElement(SXYL.SORT.uuidArray , j , j+1);
                //数组的内容进行交换
                SXYL.base.changeArrayElement(SXYL.SORT.array ,j , j+1);
                //执行步骤变色
                SXYL.SORT.executeStep("A2");
            }else{//比较大小,说明不需要换位置
                //执行步骤变色
                SXYL.SORT.executeStep("A3");
            }
            if(j == SXYL.SORT.array.length - 2 - i){//如果是最后一位
                SXYL.DOM.changeElementColor(SXYL.SORT.uuidArray[j+1], "green");
                SXYL.j = undefined;
                SXYL.i++;
                //执行步骤变色
                SXYL.SORT.executeStep("A4");
            }else{
                SXYL.j++ ;
            }
            return
        }
        if(i == SXYL.SORT.array.length -1){
            //需要判断是升序还是降序? 可放在嵌套循环里， 但是做的判断更多一些
            SXYL.DOM.changeElementColor(SXYL.SORT.uuidArray[0], "green");
            //执行步骤变色
            SXYL.SORT.executeStep("A-END");
        }
    }
    console.log("end");
    window.clearInterval(SXYL.execute_i);
}