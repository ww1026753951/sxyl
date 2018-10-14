SXYL.SORT = {
    array:[],//排序数组的全局变量
    uuidArray:[],//排序数组对应的dom唯一标识id
    stepHtml:"<p id='{stepCode}' group='{group}' t='{stepDesc}'>{stepDesc}</p>" ,
    getStep:function (url) {
        $.ajax({
            type: 'POST',
            url: url,
            success: function (d) {
                if(d instanceof Array){
                    for (var i=0;i<d.length;i++){
                        var ob = d[i];
                        $("#"+ob.groupCode).append(SXYL.SORT.stepHtml.format(ob));
                    }
                }
            }
        });
    },

    executeStep:function(code,replace,color){
    // ,groupCode,group
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
                targetText = targetText.replace(replace[i][0] , replace[i][1])
            }
            $("#" + code).text(targetText);
        }
    },
    /***
     * 获取速度,默认600,
     * @returns {number}
     */
    getSpeed:function () {
        var speed = 600;
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
    changeSpeed:function (f) {
        var runType = $("#run-pause").attr("run-type");
        if(runType==1){
            clearInterval(SXYL.execute_i);
            SXYL.execute_i = setInterval(f, SXYL.SORT.getSpeed());
        }
    },

}