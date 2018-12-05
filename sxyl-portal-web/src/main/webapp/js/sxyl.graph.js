SXYL.GRAPH = {
    x:0,
    y:0,
    addX:false,//x累加的标志位
    addY:false,//y累加的标志位
    xStack:null,
    yStack:null,

    //临时的x坐标,现在主要用于线上的文字
    currentX:undefined,
    //临时的y坐标,现在主要用于线上的文字
    currentY:undefined,
    /****
     * 初始化位置对象
     */
    initPosition:function(){
        SXYL.GRAPH.x=0;
        SXYL.GRAPH.y=0;
        SXYL.GRAPH.xStack = new Array();
        SXYL.GRAPH.yStack = new Array() ;
    },
    /****
     * 初始化背景
     * @constructor
     */
    InitContext:function (type,domId) {
        //画布大小
        var width =  $('#' + domId).width(), height = 650;
        // 在body里添加一个SVG画布
        d3.select("#" + domId)
            .append("svg")
            .attr("id","sortAlg")
            .attr("width",width)
            .attr("height",height);
    },
    /****
     * 获取当前背景画布
     * @returns {*}
     */
    getContext:function () {
        return d3.select("#sortAlg");
    },
    /****
     * 画矩形
     * {
     *     marginLeft:居左间距
     *     id:domId
     *     value:矩形的值
     * }
     */
    drawRect:function (o) {
        //初始化左间距,默认为100
        if(!o.marginLeft){
            o.marginLeft = 100;
        }else {
            o.marginLeft = o.marginLeft + SXYL.DOM.defaultSpaceX ;
        }
        //矩形的值
        var v = (parseInt(o.value))*5;
        var tarG = SXYL.GRAPH.getContext().append("g")
            .attr("id", o.id)
            .attr("transform", "translate("+o.marginLeft+","+(300 - v)+")");
        tarG.append("rect")
            .attr("width", 40)
            .attr("height", v)
            .style("fill","red");
        var y = v - 5 ;
        tarG.append("text")
            .attr("x", 13)
            .attr("y", y)
            .text(o.value);
        return {left:o.marginLeft}
    },

    /****
     * 画园
     * @param o
     * @param o.c 对象中园的对象内容
     * @param o.c.r 圆的半径
     *
     * @returns {{left: (number|*)}}
     */
    drawCircle:function (o) {
        //初始化左间距,默认为100

        //矩形的值
        var tarG = SXYL.GRAPH.getContext().append("g")
            .attr("id", o.id);

        tarG.append("circle")
            .attr("id", o.c.id)
            .attr("cx", o.c.x)//横坐标
            .attr("cy", o.c.y)//纵坐标
            .attr("r", o.c.r)//半径
            .attr("stroke", "black")//半径
            .style("fill",o.c.fill?o.c.fill:"white");


        //文本的位置需要调整，现在不是圆的中心位置
        tarG.append("text")
            .attr("x", o.c.x)
            .attr("y", o.c.y)
            .text(o.showText);

        return {left:o.c.x, top:o.c.y, right:o.c.x + o.c.r, bottom:o.c.y + o.c.r}
    },

    /***
     * draw svg  g标签
     * @param parent
     * @returns {*|void}
     */
    G:function(parent,o){
        //矩形的值
        var tarG = parent.append("g")
            .attr("id", o.id);
        return {ob:tarG};
    },

    /***
     * draw svg  t标签
     * @param parent
     * @returns {*|void}
     */
    T:function(parent,o){
        var x = o.x;
        var y = o.y;
        if(SXYL.GRAPH.currentX){
            x = SXYL.GRAPH.currentX;
        }
        if(SXYL.GRAPH.currentY){
            y = SXYL.GRAPH.currentY;
        }
        //文本的值
        var ob = parent.append("text")
            .attr("x", x)
            .attr("y", y)
            .attr("style",o.sts)
            .text(o.st); //o.st
        return {ob:ob};
    },


    /***
     * draw svg  line标签
     * @param parent
     * @returns {*|void}
     */
    L:function(parent,o){
        //
        // var sourceX = ((tx-sx)*sr/r2).toFixed(2);
        // var sourceY = ((ty-sy)*sr/r2).toFixed(2);
        // var targetX = ((sx-tx)*tr/r2).toFixed(2);
        // var targetY = ((sy-ty)*tr/r2).toFixed(2);

        var tarG;
        if(parseInt(o.lt)==1){
            var x1 = parseInt(o.x) + parseInt(o.x1);
            var x2 = parseInt(o.x) + parseInt(o.x2);
            var y1 = parseInt(o.y) + parseInt(o.y1);
            var y2 = parseInt(o.y) + parseInt(o.y2);
            tarG = parent.append("line").attr("x1",x1).attr("y1",y1).attr("id",o.id)
                .attr("x2",x2).attr("y2",y2).attr("style","stroke:rgb(99,99,99);");
        }else {
            //different value
            var dv = different(o.sid,o.tid,o.lpt);

            //矩形的值
            // var tarG = parent.append("line").attr("x1",parseFloat(sx)+parseFloat(sourceX)).attr("y1",parseFloat(sy)+parseFloat(sourceY))
            //     .attr("x2", parseFloat(tx)+parseFloat(targetX)).attr("y2",parseFloat(ty)+parseFloat(targetY)).attr("style","stroke:rgb(99,99,99);");

            tarG = parent.append("line").attr("x1",dv.sx).attr("y1",dv.sy).attr("id",o.id)
                .attr("x2",dv.tx).attr("y2",dv.ty).attr("style","stroke:rgb(99,99,99);");
        }


        function different(sid,tid,type) {
            var sourceOb = d3.select("#" + o.sid);
            var sx = sourceOb.attr("cx");
            var sy = sourceOb.attr("cy");
            var sr = sourceOb.attr("r")

            var targetOb = d3.select("#" + o.tid);
            var tx = targetOb.attr("cx");
            var ty = targetOb.attr("cy");
            var tr = targetOb.attr("r");

            var r2= Math.sqrt(Math.pow((tx-sx),2) + Math.pow((ty -sy),2)).toFixed(2);

            sx = getDifferent(sx,tx,type[0],sr,r2);
            sy = getDifferent(sy,ty,type[1],sr,r2);
            tx = getDifferent(tx,sx,type[2],tr,r2);
            ty = getDifferent(ty,sy,type[3],tr,r2);
            return {sx:sx,sy:sy,tx:tx,ty:ty}

            /****
             * 获取差值
             * @param s 当前元素的坐标
             * @param t 远端元素的坐标
             * @param type 位移类型
             * @param r 当前元素的半径
             * @param r2 俩个元素之间的半径
             * @return dsx: different source x
             * @return dsy: different source y
             * @return dtx: different target x
             * @return dty: different target y
             */
            function getDifferent(s,t,type,r,r2) {

                if(type == SXYL.GRAPH.LINE_POSITION_MAP.cc){
                    return s;
                }
                //如果类型为半径扣减类型
                if(type == SXYL.GRAPH.LINE_POSITION_MAP.r){
                    if(s>t){
                        return parseFloat(s)-parseFloat(r);
                    }else{
                        return parseFloat(s)+parseFloat(r);
                    }
                }
                //如果类型为三角函数类型,则进行三角函数规则处理
                if(type == SXYL.GRAPH.LINE_POSITION_MAP.v){
                    //取俩个圆心间的距离
                    var d = ((t-s)*r/r2).toFixed(2);
                    return d;
                }
            }
        }
        return {ob:tarG};
    },

    /***
     * draw svg  circle 标签
     * @param parent
     * @returns {*|void}
     */
    C:function(parent,o){
        //圆形
        var ob = parent.append("circle")
            .attr("id", o.id)
            .attr("cx", o.x)//横坐标
            .attr("cy", o.y)//纵坐标
            .attr("r", o.r)//半径
            .attr("stroke", o.s?o.s:"black")//半径
            .style("fill",o.f?o.f:"white");

        // var ttt = parent.append("text")
        //     .attr("x", o.x)
        //     .attr("y", o.y).attr("style","text-anchor: middle")
        //     .text(o.st);
        //
        // parent.append("text")
        //     .attr("x", parseInt(o.x) - parseInt(o.r))
        //     .attr("y", o.y).attr("style","text-anchor: end")
        //     .text("W1=0"); //o.st



        return {
            ob:ob,
            left:o.x, top:o.y,
            right:o.x + (o.r*2), bottom:o.y + (o.r*2)
        }
    },

    /****
     * 根据组建创建对象
     * @param o
     */
    drawByComponent:function (parent ,o) {


        //左边距
        SXYL.GRAPH.x = SXYL.GRAPH.x + parseInt(o.ml?o.ml:0) ;
        //右边距
        SXYL.GRAPH.y = SXYL.GRAPH.y + parseInt(o.mt?o.mt:0);

        var graph = SXYL.GRAPH.GRAPH_MAP[o.ct];

        var x = o.x?o.x:0;
        var y = o.y?o.y:0;
        x = parseInt(x) + parseInt(SXYL.GRAPH.x);
        y = parseInt(y) + parseInt(SXYL.GRAPH.y);
        $.extend(o,{x:x,y:y});

        //调用方法,创建图形
        var obParent = graph.f(parent , o);

        var ob = obParent.ob;

        //如果是分组的类型,并且有分组类型 , 设置 x或y是否添加的标志位
        if (o.ct== SXYL.GRAPH.GRAPH_TYPE.GROUP && o.compose){
            SXYL.GRAPH.addX = SXYL.GRAPH.POSITION_MAP[o.compose].addX;
            SXYL.GRAPH.addY = SXYL.GRAPH.POSITION_MAP[o.compose].addY;
        }


        // 文本的处理,在xy变化之前
        if(o.currentComponent){
            for (var j = 0;j < o.currentComponent.length;j++){
                debugger;

                var childOb = o.currentComponent[j];

                //如父元素为line ,则重新计算 x,y的值
                if(o.ct == parseInt(SXYL.GRAPH.GRAPH_TYPE.LINE)){
                    SXYL.GRAPH.computePosition(o,childOb);
                }

                SXYL.GRAPH.drawByComponent(parent , childOb);
            }
        }

        if(obParent.right && SXYL.GRAPH.addX){
            SXYL.GRAPH.x = obParent.right;
        }
        if(obParent.bottom && SXYL.GRAPH.addY){
            SXYL.GRAPH.y = obParent.bottom;
        }
        if (o.ct== SXYL.GRAPH.GRAPH_TYPE.GROUP && o.compose){
            //位置的栈赋值
            SXYL.GRAPH.xStack.push(SXYL.GRAPH.x);
            SXYL.GRAPH.yStack.push(SXYL.GRAPH.y);
        }

        if(o.child){
            for (var i = 0;i<o.child.length;i++){
                var childOb = o.child[i];
                SXYL.GRAPH.drawByComponent(ob , childOb);
            }
        }

        if(o.ct == SXYL.GRAPH.GRAPH_TYPE.GROUP ){
            if(SXYL.GRAPH.addX){
                SXYL.GRAPH.x = SXYL.GRAPH.xStack.pop();
            }
            if(SXYL.GRAPH.addY){
                SXYL.GRAPH.y = SXYL.GRAPH.yStack.pop();
            }
        }
        return ob ;
    },
    /***
     * 计算位置,现在主要是  线上的文字,
     * 计算后的数值放入到 x, y 栈里面。
     * @param lineObject
     */
    computePosition:function (lineObject,textObject) {
        var ratio = textObject.ratio;
        if(!ratio){
            ratio = 2 ;
        }
        debugger
        var lineId = lineObject.id;
        var l = d3.select("#"+lineId) ;
        var x1 = l.attr("x1");
        var x2 = l.attr("x2");
        var y1 = l.attr("y1");
        var y2 = l.attr("y2");

        var x_different = parseInt(x2) - parseInt(x1);
        var y_different = parseInt(y2) - parseInt(y1);

        //暂定除以 2 ,放在线的中间位置
        var x_different = parseInt(x_different/ratio);
        var y_different = parseInt(y_different/ratio);

        SXYL.GRAPH.currentX=parseInt(x1) + parseInt(x_different)  ;
        SXYL.GRAPH.currentY=parseInt(y1) + parseInt(y_different);


        // SXYL.GRAPH.xStack.push

    }


}
/****
 * 组件类型的type
 */
SXYL.GRAPH.GRAPH_TYPE = {
    GROUP:1,
    CIRCLE:4,
    LINE:5
}

/****
 * 方法map
 */
SXYL.GRAPH.GRAPH_MAP = {
    1:{f:SXYL.GRAPH.G},
    2:{f:SXYL.GRAPH.T},
    4:{f:SXYL.GRAPH.C},
    5:{f:SXYL.GRAPH.L}
}

/****
 * position Map
 */
SXYL.GRAPH.POSITION_MAP = {
    1:{addX:true , addY:false},
    2:{addX:false, addY:true}
}

/****
 * line position Map
 */
SXYL.GRAPH.LINE_POSITION_MAP = {
    cc:1,
    r:2,
    t:3
}