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
    // console.log("end");
    window.clearInterval(SXYL.execute_i);
}
//鸡尾酒排序
SXYL.SORT.CocktailShakerSort = function () {
    if(SXYL.i == undefined){
        SXYL.i = 0
    }
    if(SXYL.j == undefined){
        SXYL.j = 0
    }
    for (var i = SXYL.i ; i < SXYL.SORT.array.length/2 ; i++) {
        if(SXYL.cocktailShakerSortConfig.swap){
            // SXYL.SORT.executeStep("background1",[["{SIZE}",SXYL.SORT.array.length - 1],["{INDEX}",i]] , "silver");

            for(var j = SXYL.j ; j < SXYL.SORT.array.length - 1 -i ; j++){
                // console.log("compare index,i="+i+",j="+j+" , a = " + SXYL.SORT.array[j] + ", b = " + SXYL.SORT.array[j+1]);
                //变换颜色
                var leftColor =SXYL.DOM.changeElementColor(SXYL.SORT.uuidArray[j], "yellow") ;
                var rightColor =SXYL.DOM.changeElementColor(SXYL.SORT.uuidArray[j+1], "yellow") ;
                if(leftColor && rightColor){
                    return ;
                }
                if(SXYL.SORT.array[j] > SXYL.SORT.array[j+1]){//比较大小 , 说明需要换位置
                    var leftColor =SXYL.DOM.changeElementColor(SXYL.SORT.uuidArray[j], "red") ;
                    var rightColor =SXYL.DOM.changeElementColor(SXYL.SORT.uuidArray[j+1], "red") ;
                    //元素交换位置
                    SXYL.DOM.changeElementTransformX(SXYL.SORT.uuidArray[j] , SXYL.SORT.uuidArray[j+1]);
                    //uuid的数组进行交换
                    SXYL.base.changeArrayElement(SXYL.SORT.uuidArray , j , j+1);
                    //数组的内容进行交换
                    SXYL.base.changeArrayElement(SXYL.SORT.array,j , j+1);
                }else{//比较大小,说明不需要换位置
                    //颜色变换回来
                    var leftColor =SXYL.DOM.changeElementColor(SXYL.SORT.uuidArray[j], "red") ;
                    var rightColor =SXYL.DOM.changeElementColor(SXYL.SORT.uuidArray[j+1], "red") ;
                }
                if(j == SXYL.SORT.array.length - 2 - i){//如果是最后一位
                    SXYL.DOM.changeElementColor(SXYL.SORT.uuidArray[j+1], "green");
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
                SXYL.cocktailShakerSortConfig.swapJ = SXYL.SORT.array.length - 2 - i ;
            }
            for(var j = SXYL.cocktailShakerSortConfig.swapJ ; j > i  ; j--){
                // console.log("compare index,i="+i+",j="+j+" , a = " + SXYL.SORT.array[j] + ", b = " + SXYL.SORT.array[j-1]);
                //变换颜色
                var leftColor =SXYL.DOM.changeElementColor(SXYL.SORT.uuidArray[j], "yellow") ;
                var rightColor =SXYL.DOM.changeElementColor(SXYL.SORT.uuidArray[j-1], "yellow") ;
                if(leftColor && rightColor){
                    return ;
                }
                var leftColor =SXYL.DOM.changeElementColor(SXYL.SORT.uuidArray[j], "red") ;
                var rightColor =SXYL.DOM.changeElementColor(SXYL.SORT.uuidArray[j-1], "red") ;
                if(SXYL.SORT.array[j] < SXYL.SORT.array[j-1]){//比较大小 , 说明需要换位置
                    //元素交换位置
                    SXYL.DOM.changeElementTransformX(SXYL.SORT.uuidArray[j] , SXYL.SORT.uuidArray[j-1]);
                    //uuid的数组进行交换
                    SXYL.base.changeArrayElement(SXYL.SORT.uuidArray , j , j-1);
                    //数组的内容进行交换
                    SXYL.base.changeArrayElement(SXYL.SORT.array,j , j-1);
                }
                if(j == SXYL.i + 1 ){
                    SXYL.DOM.changeElementColor(SXYL.SORT.uuidArray[j-1], "green");
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
    SXYL.DOM.changeElementColor(SXYL.SORT.uuidArray[SXYL.i], "green");
    // console.log("end"+ SXYL.i);
    window.clearInterval(SXYL.execute_i);
}

//选择排序
SXYL.SORT.SelectionSort = function(){
    var  minIndex ;
    var indexI = SXYL.i;
    if(indexI == undefined){
        indexI = 0 ;
        SXYL.i = 0 ;
    }
    for(var i = indexI ; i < SXYL.SORT.array.length-1; i++){
        if(SXYL.selectMinIndex ==0){
            minIndex = i;//记录每次循环的第一个数为该次循环的最小值索引
        }else{
            minIndex = SXYL.selectMinIndex;
        }
        if(SXYL.j == undefined){
            var minColor =SXYL.DOM.changeElementColor(SXYL.SORT.uuidArray[i], "blue") ;
            if(minColor){
                return ;
            }
        }
        var indexJ = 0 ;
        if(SXYL.j == undefined){
            indexJ = i+1;
            SXYL.j = indexJ;
        }else{
            indexJ = SXYL.j ;
        }
        //循环剩余的数组
        for(var j = indexJ ; j < SXYL.SORT.array.length ; j++){
            // console.log("compare start j=" + j +",minIndex = " + minIndex+", left =" + SXYL.SORT.array[j] + ",right="+SXYL.SORT.array[minIndex])
            var compareColor =SXYL.DOM.changeElementColor(SXYL.SORT.uuidArray[j], "yellow") ;
            if(compareColor){
                return ;
            }
            if(SXYL.SORT.array[j]<SXYL.SORT.array[minIndex]){
                var betweenMinColor =SXYL.DOM.changeElementColor(SXYL.SORT.uuidArray[minIndex], "red") ;
                var afterMinColor =SXYL.DOM.changeElementColor(SXYL.SORT.uuidArray[j], "blue") ;
                if(betweenMinColor && afterMinColor){
                    // minIndex = j;//找到每次循环到的最小值，
                    SXYL.selectMinIndex = j ;
                    SXYL.j++ ;
                    return ;
                }
            }else{
                //将比较的颜色切换回来
                var compareColor =SXYL.DOM.changeElementColor(SXYL.SORT.uuidArray[j], "red") ;
                if(compareColor){
                    SXYL.j++ ;
                    return ;
                }
            }
        }
        // console.log("compare end , minIndex = " + minIndex + ",minContent=" + SXYL.SORT.array[minIndex]);
        if(!(i == minIndex)){

            SXYL.DOM.changeElementColor(SXYL.SORT.uuidArray[minIndex], "green");
            //交换位置
            SXYL.DOM.changeElementTransformX(SXYL.SORT.uuidArray[i] , SXYL.SORT.uuidArray[minIndex]);
            //uuid的数组进行交换
            SXYL.base.changeArrayElement(SXYL.SORT.uuidArray , minIndex , i);
            //数组的内容进行交换
            SXYL.base.changeArrayElement(SXYL.SORT.array,minIndex , i);

            SXYL.j = undefined ;
            SXYL.i++;
            SXYL.selectMinIndex =0 ;
            return ;
        }else{
            SXYL.DOM.changeElementColor(SXYL.SORT.uuidArray[minIndex], "green");
            SXYL.j = undefined ;
            SXYL.i++;
            SXYL.selectMinIndex =0 ;
            return ;
        }
    }
    SXYL.DOM.changeElementColor(SXYL.SORT.uuidArray[SXYL.SORT.array.length-1], "green");
    window.clearInterval(SXYL.execute_i);




}

/****
 * 插入排序
 * @constructor
 */
SXYL.SORT.InsertionSort=function(){
    /**
     * 初始化第一个柱子为绿色
     * *
     **/
    if(SXYL.i == undefined){
        SXYL.DOM.changeElementColor(SXYL.SORT.uuidArray[0], "green") ;
    }

    // console.log("sort start , loop array start:x="+SXYL.SORT.array);
    var iIndex = SXYL.i ,jIndex = SXYL.j;
    if(iIndex == undefined){
        SXYL.i = 1; // 默认从1开始
        iIndex = 1; // 默认从1 开始
    }
    if(jIndex == undefined) {
        jIndex = 0;
    }
    if(jIndex == SXYL.SORT.array.length){
        jIndex = iIndex - 1 ;
        SXYL.j = iIndex - 1;
    }

    // var changeFlag = false ;
    for(var i = iIndex;i<SXYL.SORT.array.length;i++){
        if(!(SXYL.insertSortConfig.indexContent)){
            SXYL.insertSortConfig.index = i ;
            SXYL.insertSortConfig.indexContent = SXYL.SORT.array[i];
        }
        if(!(SXYL.insertSortConfig.idIndexContent)){
            SXYL.insertSortConfig.idIndexContent = SXYL.SORT.uuidArray[i];
        }
        if( SXYL.insertSortConfig.indexFlag == -1){
            SXYL.insertSortConfig.indexFlag = iIndex -1 ;
        }
        // console.log("选出循环的待排序元素, i = " + i  + ", content = " + SXYL.insertSortConfig.indexContent);
        /***
         * 目标标签挪y坐标
         * **/
        // console.log("SXYL.insertSortConfig.index = "+ SXYL.insertSortConfig.index);
        var targetColor =SXYL.DOM.changeElementColor(SXYL.insertSortConfig.idIndexContent, "blue") ;
        if(targetColor){
            var rectTarget = d3.select("#" + SXYL.insertSortConfig.idIndexContent) ;
            var rectTargetMatrix = SXYL.DOM.getTranslation(rectTarget.attr("transform"));
            var y = parseInt(rectTargetMatrix[1]) + 240;
            rectTarget.transition().duration(50).attr("transform", "translate("+rectTargetMatrix[0]+","+y+")");
            return;
        }

        // console.log("根据已排序好的数组进行循环,start");

        for(var j = jIndex ;j >= 0;j--){
            // console.log("compare start ,比较等待排序和已排好的元素进行比较, targetI = " + i +",targetContent="+ SXYL.insertSortConfig.indexContent
            //     + ",已排序下标j="+j+",已排序内容,content="+SXYL.SORT.array[j]);
            if(SXYL.SORT.array[j] > SXYL.insertSortConfig.indexContent){
                ///向右移动40个像素
                var rectLeft = d3.select("#" + SXYL.SORT.uuidArray[j]) ;
                var rectLeftMatrix = SXYL.DOM.getTranslation(rectLeft.attr("transform"));
                var leftx = parseInt(rectLeftMatrix[0]) + SXYL.DOM.defaultSpaceX;
                rectLeft.transition().duration(50).attr("transform", "translate("+ leftx +","+rectLeftMatrix[1]+")");
                //移动结束
                SXYL.SORT.uuidArray[j+1] = SXYL.SORT.uuidArray[j];
                SXYL.SORT.array[j+1]=SXYL.SORT.array[j];
                // console.log("compare end,need swap , 需要目标的下标=" + j + ",移动过后下标="+ (j+1));
                SXYL.j--;
                SXYL.insertSortConfig.indexFlag = j;
                return ;
            }else {
                SXYL.insertSortConfig.indexFlag = j +1;
                SXYL.j =-1;
                return ;
            }
        }
        // console.log("根据已排序好的数组进行循环,end , SXYL.insertSortConfig.indexFlag = " + SXYL.insertSortConfig.indexFlag+", iIndex = " +iIndex );

        if(SXYL.insertSortConfig.indexFlag >= 0 ){
            // console.log("插入目标元素,changeFlag=true，targetElement="
            //     + SXYL.insertSortConfig.indexContent + ",位置是="+(SXYL.insertSortConfig.indexFlag));
            //上次循环的值
            SXYL.SORT.uuidArray[SXYL.insertSortConfig.indexFlag] = SXYL.insertSortConfig.idIndexContent;
            SXYL.SORT.array[SXYL.insertSortConfig.indexFlag] = SXYL.insertSortConfig.indexContent;

            //目标向左移动40个像素,
            var rectLeft = d3.select("#" + SXYL.insertSortConfig.idIndexContent) ;
            var rectLeftMatrix = SXYL.DOM.getTranslation(rectLeft.attr("transform"));
            var leftx = parseInt(rectLeftMatrix[0]) - (SXYL.insertSortConfig.index - SXYL.insertSortConfig.indexFlag)*SXYL.DOM.defaultSpaceX;
            var y = parseInt(rectLeftMatrix[1]) - 240;
            rectLeft.transition().duration(50).attr("transform", "translate("+ leftx +","+ y +")");
            SXYL.DOM.changeElementColor(SXYL.insertSortConfig.idIndexContent, "green");

            SXYL.insertSortConfig.idIndexContent = false;
            SXYL.insertSortConfig.indexContent = false;
            SXYL.insertSortConfig.indexFlag = -1 ;
            SXYL.i++;
            SXYL.j = SXYL.SORT.array.length;//j的值通过x的长度进行处理
            return ;
        }else {
            // console.log("插入目标元素，changeFlag=false，targetElement="
            //     + SXYL.insertSortConfig.indexContent + " ,不需要变化,下标=" + SXYL.insertSortConfig.indexFlag);

            SXYL.SORT.uuidArray[SXYL.insertSortConfig.indexFlag] = SXYL.insertSortConfig.idIndexContent;
            SXYL.SORT.array[SXYL.insertSortConfig.indexFlag] = SXYL.insertSortConfig.indexContent;

            SXYL.insertSortConfig.idIndexContent = false;
            SXYL.insertSortConfig.indexContent = false;
            SXYL.insertSortConfig.indexFlag = -1 ;
            SXYL.i++;
            SXYL.j = SXYL.SORT.array.length;//j的值通过x的长度进行处理
            return;
        }
    }
    // console.log("sort end,SXYL.SORT.array="+SXYL.SORT.array);
    window.clearInterval(SXYL.execute_i);
}

/****
 * 快速排序
 * @constructor
 */
SXYL.SORT.QuickSort=function(){
    var x = SXYL.SORT.array;
    var uuidArray = SXYL.SORT.uuidArray;
    var stack = getStackTop();
    var left , right ;
    if(stack == undefined){
        left = 0 ;
        right = x.length -1;
    }else {
        left = stack[0];
        right = stack[1];
    }
    var result = quickSortStart(x , left , right);
    if(result == undefined){
        //靠递归栈来判断是否都处理完成
        if(SXYL.quickSortConfig.recursiveStack.length == 0){
            window.clearInterval(SXYL.execute_i);
        }
        return ;
    }
    /***
     * 数组，左侧下标，右侧下标
     * @param arr
     * @param left
     * @param right
     * @return {*}
     */
    function quickSortStart(arr, left, right){
        //步骤a
        putStack(left , right) ;
        var pivot, partitionIndex;
        if(left < right){
            pivot = right;
            //分割位置
            partitionIndex = partition( pivot, left, right);
            //通过返回标志位判断是否为子方法结束
            if(partitionIndex == undefined){
                return;
            }else{
                SXYL.quickSortConfig.partitionIndex = undefined ;//方法出来后将 全局的位置标识 置为undified
            }
            //可能有错误，有点忘记了, 将步骤a的压栈操作进行出栈
            popStack();
            //先放置右侧再放置左侧
            putStack(partitionIndex + 1, right);
            putStack(left , partitionIndex - 1);
            return ;
            //sort left and right
            // quickSortStart(arr, left, partitionIndex - 1);
            // quickSortStart(arr, partitionIndex + 1, right);
        }else{
            var compareColor = SXYL.DOM.changeElementColor(uuidArray[left], "green") ;
            if(compareColor){
                return ;
            }
        }
        //方法结束后将递归栈的第一个内容弹出。
        popStack();
        return ;
    }

    function partition( pivot, left, right){
        if(SXYL.quickSortConfig.partitionFinishFlag){
            SXYL.quickSortConfig.partitionFinishFlag = false ;
            return SXYL.quickSortConfig.partitionIndex;
        }
        var pivotValue = x[pivot];

        //将比较元素置换成为蓝色
        if(SXYL.DOM.changeElementColor(uuidArray[pivot], "blue")){
            return ;
        }

        //todo 注:这里有坑,后期有时间优化,建议统一用undified做标识
        var iIndex = SXYL.i;
        if(iIndex == undefined){
            iIndex = left;
            SXYL.i = left;
        }
        if(SXYL.quickSortConfig.partitionIndex == undefined){
            SXYL.quickSortConfig.partitionIndex = left;
        }

        for(var i = iIndex; i < right; i++){

            //将比较的颜色置换为黄色
            if(SXYL.DOM.changeElementColor(uuidArray[i], "yellow")){
                return ;
            }
            if(x[i] < pivotValue){
                //变换颜色,将颜色换成紫色,后期考虑换成别的颜色
                SXYL.DOM.changeElementColor(uuidArray[i], "purple");

                //比较数组内容进行交换
                SXYL.base.changeArrayElement(x, i, SXYL.quickSortConfig.partitionIndex);
                //元素交换位置
                SXYL.DOM.changeElementTransformX(uuidArray[i] , uuidArray[SXYL.quickSortConfig.partitionIndex]);
                //uuid的数组进行交换
                SXYL.base.changeArrayElement(uuidArray, i, SXYL.quickSortConfig.partitionIndex);
                SXYL.quickSortConfig.partitionIndex++;
                SXYL.i++;
                return ;
            }else {
                SXYL.i++;
                //将比较的颜色置换回紫色
                SXYL.DOM.changeElementColor(uuidArray[i], "purple")
                return;
            }
        }
        SXYL.i = undefined ;//循环后将循环标志位置成undified
        var finishColor = SXYL.DOM.changeElementColor(uuidArray[right], "green") ;
        if(finishColor){
            //比较数组内容进行交换
            SXYL.base.changeArrayElement(x, right, SXYL.quickSortConfig.partitionIndex);
            SXYL.DOM.changeElementTransformX(uuidArray[right], uuidArray[SXYL.quickSortConfig.partitionIndex]);
            //uuid的数组进行交换
            SXYL.base.changeArrayElement(uuidArray, right, SXYL.quickSortConfig.partitionIndex);
            // return SXYL.quickSortConfig.partitionIndex;
            resetColor();
        }else {
            console("compare error ,pivot="+pivot + " , left = " + left+",right = " + right);
        }
        //将获取位置的标志位置换成为true
        SXYL.quickSortConfig.partitionFinishFlag = true;
        return ;
    }



    /***
     * 压栈处理
     * @param left
     * @param right
     */
    function putStack(left,right) {
        var topStack = getStackTop();
        if(topStack == undefined) {
            var stack=new Array();
            stack.push(left) ;
            stack.push(right) ;
            SXYL.quickSortConfig.recursiveStack.push(stack);
            return ;
        }
        if(topStack[0] != left || topStack[1] != right){
            var stack=new Array();
            stack.push(left) ;
            stack.push(right) ;
            SXYL.quickSortConfig.recursiveStack.push(stack);
            return ;
        }
    }


    function getStackTop() {
        if(SXYL.quickSortConfig.recursiveStack.length>0) {
            var topStack = SXYL.quickSortConfig.recursiveStack[SXYL.quickSortConfig.recursiveStack.length - 1];
            return topStack ;
        }
        return ;
    }

    function popStack() {
        if(SXYL.quickSortConfig.recursiveStack.length>0) {
            SXYL.quickSortConfig.recursiveStack.pop();
        }
        return ;
    }

    function resetColor(){
        var array = SXYL.quickSortConfig.recursiveStack;
        for (var i = 0 ; i <array.length ; i++){
            var stack = SXYL.quickSortConfig.recursiveStack[i];
            for (var j = stack[0] ; j < stack[1] - stack[0] ; j++){
                //靠绿色判断是否已经排好序,后期可以优化
                if(SXYL.DOM.changeElementColor(uuidArray[j], "green")){
                    SXYL.DOM.changeElementColor(uuidArray[j], "red");
                }
            }
        }
    }
}
//归并排序
SXYL.SORT.MergeSort=function(){
//从堆栈数组里获取数据,如果为undified 则将数组赋值为全局变量
    if( SXYL.mergeSortConfig.recursiveGroupStack.length == 0 ) {
        //将数组的各个元素打散
        var result = mergeSortArray(0 , SXYL.SORT.array.length, SXYL.SORT.array) ;
    }
    //循环栈内各个元素 , 进行合并操作
    for (var i = 0 ;  i< SXYL.mergeSortConfig.recursiveGroupStack.length ; i++) {
        var r = merge(SXYL.mergeSortConfig.recursiveGroupStack[i]) ;
        if(r == undefined){
            return ;
        }else{
            SXYL.mergeSortConfig.recursiveGroupStack = SXYL.mergeSortConfig.recursiveGroupStack.slice(  1 ,SXYL.mergeSortConfig.recursiveGroupStack.length );
            if( SXYL.mergeSortConfig.recursiveGroupStack.length == 0 ){//下面合并时会将各个元素剔除
                window.clearInterval(SXYL.execute_i);
            }

            return ;
        }
    }




    function mergeSortArray(leftIndex , rightIndex , arr){
        var len = arr.length;
        if(len <2){//说明只剩下一个元素
            return arr;
        }

        var mid = Math.ceil(len/2);//截取数组的下标记
        // var mid = Math.floor(len/2);//截取数组的下标记
        var left = arr.slice(0, mid ) ;//左侧下标截取后的数组
        var right =arr.slice(mid, len );//右侧下表截取后的数组


        var leftArray = mergeSortArray(leftIndex , leftIndex + mid - 1 , left);
        var rightArray = mergeSortArray(leftIndex + mid ,  leftIndex + len - 1 , right);

        //赋值堆栈数组
        var groupArray = new Array() ;
        groupArray.push(leftIndex) ;
        groupArray.push(leftIndex + mid - 1) ;
        groupArray.push(leftIndex + mid) ;
        groupArray.push(leftIndex + len - 1 ) ;
        groupArray.push(left) ;
        groupArray.push(right) ;
        SXYL.mergeSortConfig.recursiveGroupStack.push(groupArray);
    }

    /***
     * 合并数组
     * @param stackContent
     * @return {number}
     */
    function merge(stackContent){
        var leftStart = stackContent[0]  , leftEnd = stackContent[1] , rightStart = stackContent[2]
            , rightEnd =stackContent[3]   ; //, left = stackContent[4] , right =stackContent[5]
        var lLen = leftEnd - leftStart + 1;//左侧数组长度
        var rLen = rightEnd - rightStart + 1;//右侧数组长度

        if(SXYL.mergeSortConfig.recursiveMergeResult == undefined) {//合并的数组结果初始化
            SXYL.mergeSortConfig.recursiveMergeResult = [] ;
        }
        if(SXYL.mergeSortConfig.recursiveMergeUuidResult == undefined) {//合并的数组结果初始化
            SXYL.mergeSortConfig.recursiveMergeUuidResult = [] ;
        }

        if(SXYL.mergeSortConfig.recursiveMergeLeftIndex == undefined) {//左侧数据全局下标初始化
            SXYL.mergeSortConfig.recursiveMergeLeftIndex = 0 ;
        }
        if(SXYL.mergeSortConfig.recursiveMergeRightIndex == undefined) {//右侧数据全局下标初始化
            SXYL.mergeSortConfig.recursiveMergeRightIndex = 0 ;
        }


        //如果为undified,则说明为每个小数组的第一个次合并.将本次需要处理的宽和高放入一个数组中
        if(SXYL.mergeSortConfig.recursiveMergeWidth == undefined){
            SXYL.mergeSortConfig.recursiveMergeWidth = [];
            //放入方式为从后到前,方便计算
            for (var j = rightEnd ; j>= leftStart  ; j--){
                var o =SXYL.DOM.getDomXY(SXYL.SORT.uuidArray[j]);
                o.my = SXYL.DOM.defaultY;
                o.mx = 0 ;
                SXYL.mergeSortConfig.recursiveMergeWidth.push(o);
            }
        }


        while(SXYL.mergeSortConfig.recursiveMergeLeftIndex < lLen && SXYL.mergeSortConfig.recursiveMergeRightIndex < rLen){
            if(SXYL.SORT.array[SXYL.mergeSortConfig.recursiveMergeLeftIndex + leftStart] < SXYL.SORT.array[SXYL.mergeSortConfig.recursiveMergeRightIndex  + rightStart]){
                SXYL.mergeSortConfig.recursiveMergeResult.push(SXYL.SORT.array[SXYL.mergeSortConfig.recursiveMergeLeftIndex + leftStart]);
                SXYL.mergeSortConfig.recursiveMergeUuidResult.push(SXYL.SORT.uuidArray[SXYL.mergeSortConfig.recursiveMergeLeftIndex + leftStart]);


                var moveFlag = mergeObjectMove(SXYL.mergeSortConfig.recursiveMergeLeftIndex + leftStart);
                if(moveFlag){
                    SXYL.mergeSortConfig.recursiveMergeLeftIndex++;
                    return ;
                }
            }
            else{
                SXYL.mergeSortConfig.recursiveMergeResult.push(SXYL.SORT.array[SXYL.mergeSortConfig.recursiveMergeRightIndex + rightStart]);
                SXYL.mergeSortConfig.recursiveMergeUuidResult.push(SXYL.SORT.uuidArray[SXYL.mergeSortConfig.recursiveMergeRightIndex + rightStart]);
                //将比较后的值,放入到下方位置
                var moveFlag = mergeObjectMove(SXYL.mergeSortConfig.recursiveMergeRightIndex + rightStart);
                if(moveFlag){
                    SXYL.mergeSortConfig.recursiveMergeRightIndex++;
                    return ;
                }
            }
        }
        //如果左侧还有剩余元素
        if(SXYL.mergeSortConfig.recursiveMergeLeftIndex < lLen){
            SXYL.mergeSortConfig.recursiveMergeResult.push(SXYL.SORT.array[SXYL.mergeSortConfig.recursiveMergeLeftIndex + leftStart]);
            SXYL.mergeSortConfig.recursiveMergeUuidResult.push(SXYL.SORT.uuidArray[SXYL.mergeSortConfig.recursiveMergeLeftIndex + leftStart]);
            //将比较后的值,放入到下方位置
            var moveFlag = mergeObjectMove(SXYL.mergeSortConfig.recursiveMergeLeftIndex + leftStart);
            if(moveFlag){
                SXYL.mergeSortConfig.recursiveMergeLeftIndex++;
                return ;
            }
        }

        //如果右侧还有剩余元素
        if(SXYL.mergeSortConfig.recursiveMergeRightIndex < rLen){
            SXYL.mergeSortConfig.recursiveMergeResult.push(SXYL.SORT.array[SXYL.mergeSortConfig.recursiveMergeRightIndex + rightStart]);
            SXYL.mergeSortConfig.recursiveMergeUuidResult.push(SXYL.SORT.uuidArray[SXYL.mergeSortConfig.recursiveMergeRightIndex + rightStart]);

            var moveFlag = mergeObjectMove(SXYL.mergeSortConfig.recursiveMergeRightIndex + rightStart);
            if(moveFlag){
                SXYL.mergeSortConfig.recursiveMergeRightIndex++;
                return ;
            }

        }


        //执行到这步骤 ,说明 结果数组结果集和  分隔的结果集数量相等
        if(SXYL.mergeSortConfig.recursiveMergeResult.length == rightEnd - leftStart +1){
            for (var i =0 ; i < SXYL.mergeSortConfig.recursiveMergeUuidResult.length ; i++){
                if(SXYL.mergeSortConfig.recursiveMergeUuidResult.length == SXYL.SORT.uuidArray.length){
                    var targetColor =SXYL.DOM.changeElementColor(SXYL.mergeSortConfig.recursiveMergeUuidResult[i], "green") ;
                }else {
                    var targetColor =SXYL.DOM.changeElementColor(SXYL.mergeSortConfig.recursiveMergeUuidResult[i], "red") ;
                }
                SXYL.DOM.changeElementY(SXYL.mergeSortConfig.recursiveMergeUuidResult[i] , -SXYL.DOM.defaultY  );//todo ,y值使用 目标位置的y值即可
            }
            SXYL.SORT.array = SXYL.base.changeChildrenArray(SXYL.SORT.array , SXYL.mergeSortConfig.recursiveMergeResult , leftStart , rightEnd) ;
            SXYL.SORT.uuidArray = SXYL.base.changeChildrenArray(SXYL.SORT.uuidArray , SXYL.mergeSortConfig.recursiveMergeUuidResult , leftStart , rightEnd) ;

            SXYL.mergeSortConfig.recursiveMergeResult = undefined ;
            SXYL.mergeSortConfig.recursiveMergeUuidResult = undefined ;
            SXYL.mergeSortConfig.recursiveMergeLeftIndex = undefined ;
            SXYL.mergeSortConfig.recursiveMergeRightIndex = undefined ;
            SXYL.mergeSortConfig.recursiveMergeWidth = undefined ;
            return 1 ;
        }
        // console.log("error, 不可能会到这一步骤")
        return  ;
    }


    function mergeObjectMove(index) {
        var targetColor =SXYL.DOM.changeElementColor(SXYL.SORT.uuidArray[index], "blue") ;
        //获取当前merger分组的第一个元素
        var target = SXYL.mergeSortConfig.recursiveMergeWidth.pop();
        var y = SXYL.DOM.getDomXY(SXYL.SORT.uuidArray[index]).y;
        target.y = y;
        if(targetColor){
            SXYL.DOM.changeElementXYByObject(SXYL.SORT.uuidArray[index] ,target );//todo ,y值使用 目标位置的y值即可
            return true ;
        }
        return false;
    }



}

//排序函数映射,后期通过后台映射
SXYL.SORT.F_MAP={
    1:SXYL.SORT.BubbleSort,//冒泡排序
    2:SXYL.SORT.CocktailShakerSort,//鸡尾酒排序
    3:SXYL.SORT.SelectionSort,//选择排序
    4:SXYL.SORT.InsertionSort,//插入排序
    5:SXYL.SORT.QuickSort,//快速排序
    6:SXYL.SORT.MergeSort//归并排序
}