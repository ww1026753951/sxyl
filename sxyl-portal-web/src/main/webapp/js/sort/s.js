//冒泡排序
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
//鸡尾酒排序
SXYL.SORT.CocktailShakerSort = function () {
    var x= SXYL.SORT.array ;
    var uuidArray =SXYL.SORT.uuidArray ;
    if(SXYL.i == undefined){
        SXYL.i = 0
    }
    if(SXYL.j == undefined){
        SXYL.j = 0
    }
    for (var i = SXYL.i ; i < x.length/2 ; i++) {
        if(SXYL.cocktailShakerSortConfig.swap){
            for(var j = SXYL.j ; j < x.length - 1 -i ; j++){
                console.log("compare index,i="+i+",j="+j+" , a = " + x[j] + ", b = " + x[j+1]);
                //变换颜色
                var leftColor =SXYL.DOM.changeElementColor(uuidArray[j], "yellow") ;
                var rightColor =SXYL.DOM.changeElementColor(uuidArray[j+1], "yellow") ;
                if(leftColor && rightColor){
                    return ;
                }
                if(x[j] > x[j+1]){//比较大小 , 说明需要换位置
                    var leftColor =SXYL.DOM.changeElementColor(uuidArray[j], "red") ;
                    var rightColor =SXYL.DOM.changeElementColor(uuidArray[j+1], "red") ;
                    //元素交换位置
                    SXYL.DOM.changeElementTransformX(uuidArray[j] , uuidArray[j+1]);
                    //uuid的数组进行交换
                    SXYL.base.changeArrayElement(uuidArray , j , j+1);
                    //数组的内容进行交换
                    SXYL.base.changeArrayElement(x,j , j+1);
                }else{//比较大小,说明不需要换位置
                    //颜色变换回来
                    var leftColor =SXYL.DOM.changeElementColor(uuidArray[j], "red") ;
                    var rightColor =SXYL.DOM.changeElementColor(uuidArray[j+1], "red") ;
                }
                if(j == x.length - 2 - i){//如果是最后一位
                    SXYL.DOM.changeElementColor(uuidArray[j+1], "green");
                    SXYL.j = SXYL.i + 1 ; //重置 i变量, 数据为 1,3,5,7等
                    SXYL.cocktailShakerSortConfig.swap = false ;
                }else{
                    SXYL.j++ ;
                }
                return
            }
        }
        if(!SXYL.cocktailShakerSortConfig.swap){
            if(SXYL.cocktailShakerSortConfig.swapJ == undefined){
                SXYL.cocktailShakerSortConfig.swapJ = x.length - 2 - i ;
            }
            for(var j = SXYL.cocktailShakerSortConfig.swapJ ; j > i  ; j--){
                console.log("compare index,i="+i+",j="+j+" , a = " + x[j] + ", b = " + x[j-1]);
                //变换颜色
                var leftColor =SXYL.DOM.changeElementColor(uuidArray[j], "yellow") ;
                var rightColor =SXYL.DOM.changeElementColor(uuidArray[j-1], "yellow") ;
                if(leftColor && rightColor){
                    return ;
                }
                var leftColor =SXYL.DOM.changeElementColor(uuidArray[j], "red") ;
                var rightColor =SXYL.DOM.changeElementColor(uuidArray[j-1], "red") ;
                if(x[j] < x[j-1]){//比较大小 , 说明需要换位置
                    //元素交换位置
                    SXYL.DOM.changeElementTransformX(uuidArray[j] , uuidArray[j-1]);
                    //uuid的数组进行交换
                    SXYL.base.changeArrayElement(uuidArray , j , j-1);
                    //数组的内容进行交换
                    SXYL.base.changeArrayElement(x,j , j-1);
                }
                if(j == SXYL.i + 1 ){
                    SXYL.DOM.changeElementColor(uuidArray[j-1], "green");
                    SXYL.cocktailShakerSortConfig.swap = true ;
                    SXYL.cocktailShakerSortConfig.swapJ = undefined ;
                    SXYL.i = SXYL.i + 1;
                }else{
                    SXYL.cocktailShakerSortConfig.swapJ -- ;
                }
                return;
            }
        }
    }
    SXYL.DOM.changeElementColor(uuidArray[SXYL.i], "green");
    console.log("end"+ SXYL.i);
    window.clearInterval(SXYL.execute_i);
}