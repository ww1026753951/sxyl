SXYL.GRAPH = {
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
    }



}