/****
 * 排序面板用的js,初始化事件
 * **/
$(document).ready(function(){
    //获取执行步骤
    SXYL.SORT.getStep('/sort/bubbleStep');
    //初始化画布
    SXYL.GRAPH.InitContext(1,"graph-content");
    //初始化排序数据
    SXYL.SORT.sortInit({dataType:'array',data:getData()});
    //播放的事件
    $('#run').bind('click', function () {
        SXYL.SORT.run(SXYL.SORT.switchSortFunction(1), SXYL.SORT.getSpeed())
    });
    //重置的事件
    $('#reset').bind('click', function () {
        SXYL.SORT.sortInit();
    });
    //确定的事件
    $('#enterInit').bind('click', function () {
        SXYL.SORT.sortInit({dataType:'array',data:getData()});
    });
    //速度变化的事件
    $('input[type=radio][name=speedRadioOptions]').change(function() {
        SXYL.SORT.changeSpeed(
            SXYL.SORT.switchSortFunction(1)
        );
    });

    //菜单的事件
    $('#menu-list a').bind('click' , function() {
        var type = $(this).attr('type') ;
        SXYL.SORT.sortInit({dataType:'array',data:getData()});


        debugger
        $(this).parent().parent().find('.active').removeClass('active');

        $(this).addClass('active');
        //后期用map格式
        if(type == 1){
            SXYL.SORT.F = SXYL.SORT.BubbleSort;
        }else  {
            SXYL.SORT.F = SXYL.SORT.CocktailShakerSort;
        }



        // SXYL.SORT.changeSpeed(
        //     SXYL.SORT.switchSortFunction(1)
        // );
    });

    /****
     * 获取用户数据的文本数据
     * @returns {undefined}
     */
    function getData() {
        //获取数据
        var content_array = undefined ;
        var arrayContent = $("#array-content").val()
        if(arrayContent) {
            content_array = arrayContent.split(",");
        }
        return content_array ;
    }
});