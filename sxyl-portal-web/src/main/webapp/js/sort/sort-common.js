SXYL.SORT = {
    //排序数组的全局变量
    array:[],
    //排序数组对应的dom唯一标识id
    uuidArray:[],
    //当前执行的 function
    F:undefined ,
    stepHtml:"<p id='{stepCode}' group='{group}' t='{stepDesc}'>{stepDesc}</p>" ,
    getStep:function (url) {
        $.ajax({
            type: 'POST',
            url: url,
            success: function (d) {
                // $("#"+ob.groupCode)
                var clearFlag={};
                $("#step-content").empty();
                $("#pseudo-code").empty();
                if(d instanceof Array){
                    for (var i=0;i<d.length;i++){
                        var ob = d[i];
                        if(clearFlag[ob.groupCode] != true ){
                            $("#"+ob.groupCode).empty();
                            clearFlag[ob.groupCode]=true;
                        }
                        $("#"+ob.groupCode).append(SXYL.SORT.stepHtml.format(ob));
                    }
                }
            }
        });
    },
    //执行步骤的js
    executeStep:function(code,replace,color){
        var codeOb = $("#"+code);
        var group = codeOb.attr("group");
        $("#"+code).parent().children("p[group='"+group+"']").css("background-color","");
        if(color){
            $("#" + code).css("background-color",color);
        }else {
            $("#" + code).css("background-color","green");
        }

        /***
         * 文本替换
         * */
        if(replace){
            var targetText = $("#" + code).attr("t");
            for (var i = 0 ; i< replace.length ; i++){
                targetText = targetText.replace(replace[i][0] , replace[i][1]);
            }
            $("#" + code).text(targetText);
        }
    },
    /***
     * 获取速度,默认600,
     * @returns {number}
     */
    getSpeed:function (s) {
        var speed = 600;
        if(s){
            speed = s;
        }
        var a = $("input[name='speedRadioOptions']:checked").val();
        if(a && !isNaN(a) ){
            speed = speed / a;
        }
        return speed;
    },

    //runType  0:开始  1:暂停  2:继续
    run:function (f,speed) {
        var runType = $("#run-pause").attr("run-type");
        if(runType==0){
            $("#run-pause").attr("run-type" , 1) ;
            $("#run-pause").text("暂停") ;
            $("#play-path").attr("d","M0 0v6h2v-6h-2zm4 0v6h2v-6h-2z");
            SXYL.execute_i = setInterval(f, speed);
        }else {
            SXYL.SORT.changeToStart();
        }
    },
    //runType  0:开始  1:暂停  2:继续
    changeToStart:function () {
        $("#run-pause").attr("run-type" , 0) ;
        $("#run-pause").text("播放") ;
        $("#play-path").attr("d","M0 0v6l6-3-6-3z");
        clearInterval(SXYL.execute_i);
    },
    changeSpeed:function (f,s) {
        var runType = $("#run-pause").attr("run-type");
        if(runType==1){
            clearInterval(SXYL.execute_i);
            SXYL.execute_i = setInterval(f, SXYL.SORT.getSpeed(s));
        }
    },

    sortInit:function f(o) {
        //参数初始化
        parameterInit();
        //按钮变为初始化
        SXYL.SORT.changeToStart();
        //清除画布中的元素
        d3.select("#sortAlg").selectAll("g").remove();
        //生产数据
        if(o && o.data){
            if(o.dataType==='array'){
                for (var i =0 ; i<o.data.length; i++ ){
                    if(!isNaN(o.data[i])){
                        SXYL.SORT.array.push(parseInt(o.data[i])) ;
                        SXYL.SORT.uuidArray.push(SXYL.base.uuid()) ;
                    }else{
                        SXYL.SORT.array = new Array();
                        SXYL.SORT.uuidArray = new Array();
                        break;
                    }
                }
            }
        }
        //暂定默认10数组,后续优化
        var arrayLength = 10;
        //初始化数据 ,默认为数组初始化
        if(SXYL.SORT.array.length == 0 && SXYL.SORT.uuidArray.length == 0 ){
            for (var i =0 ; i<arrayLength; i++ ){
                SXYL.SORT.array.push(SXYL.base.randomFrom(3,40)) ;
                SXYL.SORT.uuidArray.push(SXYL.base.uuid()) ;
            }
        }

        //生产矩形条
        var left = undefined;
        for (var i=0 ;i< SXYL.SORT.array.length ; i++) {
            left = SXYL.GRAPH.drawRect({ml:left , value:SXYL.SORT.array[i], id:SXYL.SORT.uuidArray[i]}).left;
        }

        function parameterInit(){
            //数组初始化
            SXYL.SORT.array = new Array();
            SXYL.SORT.uuidArray = new Array() ;
            //冒泡排序等变量初始化
            SXYL.i = undefined ;
            SXYL.j = undefined ;
            SXYL.selectMinIndex=0;
            SXYL.insertSortConfig.index = 0 ;
            SXYL.insertSortConfig.indexContent = false ;
            SXYL.insertSortConfig.idIndexContent = false ;
            SXYL.insertSortConfig.indexFlag = false ;
            SXYL.quickSortConfig.partitionIndex = undefined ;
            SXYL.quickSortConfig.partitionFinishFlag = false ;
            SXYL.quickSortConfig.recursiveStack = [] ;
            SXYL.mergeSortConfig.recursiveGroupStack=[];
            SXYL.mergeSortConfig.recursiveMergeResult=undefined;
            SXYL.mergeSortConfig.recursiveMergeUuidResult=undefined;
            SXYL.mergeSortConfig.recursiveMergeLeftIndex=undefined;
            SXYL.mergeSortConfig.recursiveMergeRightIndex=undefined;
            SXYL.mergeSortConfig.recursiveMergeWidth=undefined;
            //鸡尾酒排序的变量初始化
            SXYL.cocktailShakerSortConfig.swapJ=undefined;
            SXYL.cocktailShakerSortConfig.swap=true;
        }
    },
    //切换function
    switchSortFunction:function () {
        if (SXYL.SORT.F == undefined){
            return SXYL.SORT.BubbleSort;
        }
        return SXYL.SORT.F;
    }
}