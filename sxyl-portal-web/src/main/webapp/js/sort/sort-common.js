SXYL.SORT = {

    stepHtml:"<p id='{stepCode}'>{stepDesc}</p>" ,
    getStep:function (url) {
        $.ajax({
            type: 'POST',
            url: url,
            success: function (d) {
                if(d instanceof Array){
                    for (var i=0;i<d.length;i++){
                        var ob = d[i];
                        $("#step-content").append(SXYL.SORT.stepHtml.format(ob));
                    }
                }
            }
        });
    },

    executeStep:function(code){
        $("#step-content").children().css("background-color","")
        $("#" + code).css("background-color","green")
    },
    /***
     * 获取速度,默认600,
     * @returns {number}
     */
    getSpeed:function () {
        debugger ;

        var speed = 600;

        var a = $("input[name='speedRadioOptions']:checked").val();

        if(a && !isNaN(a) ){
            speed = speed / a;
        }
        return speed;

    }

}