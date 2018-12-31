//var str = "js实现用{two}自符串替换占位符{two} {three}  {one} ".format({one: "I",two: "LOVE",three: "YOU"});
//var str2 = "js实现用{1}自符串替换占位符{1} {2}  {0} ".format("I","LOVE","YOU");
String.prototype.format = function() {
    if(arguments.length == 0){
        return this;
    }
    var param = arguments[0];
    var s = this;
    if(typeof(param) == 'object') {
        for(var key in param){
            s = s.replace(new RegExp("\\{" + key + "\\}", "g"), param[key]);
        }
        return s;
    } else {
        for(var i = 0; i < arguments.length; i++){
            s = s.replace(new RegExp("\\{" + i + "\\}", "g"), arguments[i]);
        }
        return s;
    }
}


SXYL={
    //全局执行速度
    speed:200,
    //执行的顺序号
    step_no:0,
    //全局执行次数,  SXYL.execute_i = setInterval(f, speed);
    execute_i: undefined ,
    //全局执行的函数
    // execute_f: undefined ,
    i:undefined,
    j:undefined,
    selectMinIndex:0,
    insertSortConfig:{
        index:0,//目标等待排序的下标
        indexContent:false,//插入排序配置项,等待排序元素内容数组 的下标
        idIndexContent:false, //插入排序配置项,等待排序元素id数组 的下标
        // indexContent:false,//插入排序配置项,等待排序元素内容数组 的内容
        // idIndexContent:false, //插入排序配置项,等待排序元素id数组 的内容

        indexFlag:false //等待排序元素的下标位置
    },
    quickSortConfig:{
        partitionIndex : undefined, //
        partitionFinishFlag : false,// default false， 默认为false，意识是未完成,需要进行比较找位置 ,  true为比较结束,由比较循环结束后置换为true
        recursiveStack:[] //快速排序递归的栈结构
    },
    //归并排序的全局变量
    mergeSortConfig:{
        // recursiveStack:[] , //归并排序的递归栈结构,里面存储三个元素,数组左下标,数组右下标,分割后的数组内容
        recursiveGroupStack: [], //归并排序的分组 结构 , leftStart, leftEnd , rightStart , rightEnd, leftArray, rightArray
        recursiveMergeResult: undefined, //归并排序的合并时的处理
        recursiveMergeUuidResult: undefined, //归并排序的合并时的处理
        recursiveMergeLeftIndex:undefined , //左侧下标
        recursiveMergeRightIndex:undefined,  //右侧下标
        recursiveMergeWidth:undefined  //合并元素的x,y信息 , ,不用每次都计算
    },

    //鸡尾酒排序的全局变量信息
    cocktailShakerSortConfig:{
        swap: true, //正向或者逆向的标志位, true为正向处理,false为逆向处理
        swapJ: undefined  //合并元素的x,y信息 , ,不用每次都计算
    },

    base:{},
    // d3:{},
    DOM:{
        defaultY: 240 , //默认y轴坐标
        defaultSpaceX: 50//默认y轴坐标
    }
}

SXYL.base.randomFrom = function (lowerValue,upperValue){
    return Math.floor(Math.random() * (upperValue - lowerValue + 1) + lowerValue);
}

/***
 * 生成uuid
 * @returns {string}
 */
SXYL.base.uuid = function () {
    var s = [];
    var hexDigits = "abcdefghijklmnop";//"qrst"
    for (var i = 0; i < 36; i++) {
        s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
    }
    s[14] = "4";
    s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);
    s[8] = s[13] = s[18] = s[23] = "-";
    var uuid = s.join("");
    return uuid;
}
/***
 * 交换数组内俩个下标的元素
 * @returns {string}
 */
SXYL.base.changeArrayElement = function (array,indexA,indexB) {
    var tmpElement = array[indexA];
    array[indexA] = array[indexB];
    array[indexB] = tmpElement;
}

SXYL.base.changeChildrenArray = function (array , child,start,end) {
    var left = array.slice(0,start) ;
    var right = array.slice(end+1,array.length);
    return left.concat(child).concat(right) ;
}


/****
 * d3的一个作废方法， 获取 transform属性的  宽和高
 * @param transform
 * @returns {*[]}
 */
SXYL.DOM.getTranslation = function (transform) {
    // Create a dummy g for calculation purposes only. This will never
    // be appended to the DOM and will be discarded once this function
    // returns.
    var g = document.createElementNS("http://www.w3.org/2000/svg", "g");

    // Set the transform attribute to the provided string value.
    g.setAttributeNS(null, "transform", transform);

    // consolidate the SVGTransformList containing all transformations
    // to a single SVGTransform of type SVG_TRANSFORM_MATRIX and get
    // its SVGMatrix.
    var matrix = g.transform.baseVal.consolidate().matrix;

    // As per definition values e and f are the ones for the translation.
    return [matrix.e, matrix.f];
}


/***
 * 交换横坐标x的位置
 * @param idA 元素id
 * @param idB 元素id
 */
SXYL.DOM.changeElementTransformX = function (idA , idB) {
    var rectLeft = d3.select("#" + idA) ;
    var rectRight = d3.select("#" + idB) ;
    var rectLeftMatrix = SXYL.DOM.getTranslation(rectLeft.attr("transform"));
    var rectRightMatrix = SXYL.DOM.getTranslation(rectRight.attr("transform"));
    rectLeft.transition().duration(50).attr("transform", "translate("+rectRightMatrix[0]+","+rectLeftMatrix[1]+")");
    rectRight.transition().duration(50).attr("transform", "translate("+rectLeftMatrix[0]+","+rectRightMatrix[1]+")");
}



/***
 * 变更颜色
 * @param idA 元素id
 * @param color 颜色
 * @return true 替换成功   false 替换失败,组件为原有颜色
 */
SXYL.DOM.changeElementColor = function (id , color) {
    var element = d3.select("#" + id).select("rect") ;
    var elementColor = element.style("fill") ;

    if(elementColor === color){
        return false ;
    }else{
        element.style("fill",color);
        return true;
    }
}



/***
 * 变更元素的y值
 * @param idA 元素id
 * @param color 颜色
 * @return true 替换成功   false 替换失败,组件为原有颜色
 */
SXYL.DOM.changeElementY = function (id , y) {
    var rectTarget = d3.select("#" + id) ;
    var rectTargetMatrix = SXYL.DOM.getTranslation(rectTarget.attr("transform"));
    if(!y){
        y = SXYL.DOM.defaultY ;
    }
    y = parseInt(rectTargetMatrix[1]) + y;
    rectTarget.transition().duration(50).attr("transform", "translate("+rectTargetMatrix[0]+","+y+")");
}

/***
 *
 * @param id
 * @param x
 * @param y
 */
SXYL.DOM.changeElementXY = function (id , x , y) {
    var rectTarget = d3.select("#" + id) ;
    rectTarget.transition().duration(50).attr("transform", "translate("+x+","+y+")");
}

/***
 *
 * @param id
 * @param o ,   x = o.x  , y = o.y  modifiedX = o.mx, modifiedY = o.my
 */
SXYL.DOM.changeElementXYByObject = function (id , o) {
    var rectTarget = d3.select("#" + id) ;
    var mx = o.mx;
    var my = o.my;
    if(!mx){mx = 0 ;}
    if(!my){my = 0 ;}
    var x = parseInt(mx) + parseInt(o.x);
    var y = parseInt(my) + parseInt(o.y);
    rectTarget.transition().duration(50).attr("transform", "translate("+x+","+y+")");
}



/*****
 * 获取元素的  xy值
 * @param id
 * @return {{x: number, y: number}}
 */
SXYL.DOM.getDomXY = function (id) {
    var rectTarget = d3.select("#" + id) ;
    var rectTargetMatrix = SXYL.DOM.getTranslation(rectTarget.attr("transform"));
    return {x:parseInt(rectTargetMatrix[0]) , y:parseInt(rectTargetMatrix[1])};
}

/*****
 * 移动元素 ,对应 move对象
 * @param o
 * @param o.id 元素id
 */
SXYL.DOM.moveElement = function(o){
    var sid = d3.select("#"+o.id ) ;
    var xyDom = getXY(o.tid);

    var x = parseInt(xyDom.x) + parseInt(o.bx?o.bx:0);
    var y = parseInt(xyDom.y) + parseInt(o.by?o.by:0);

    sid.transition().duration(SXYL.speed)
        .attr("x",x).attr("y",y);


    /****
     * 获取元素的 x,y
     * @param tid
     * @param xb
     * @param yb
     * @return {{x: (*|void), y: (*|void)}}
     */
    function getXY(tid) {
        var x = 0 ;
        var y = 0 ;
        if(tid){
            var tidOb = d3.select("#"+tid ) ;
            x = tidOb.attr("x");
            y = tidOb.attr("y");
        }

        return {x:x,y:y}
    }
}

/*****
 *
 * @param o
 */
SXYL.DOM.copyElement = function (o) {
   var ob = document.getElementById(o.sid);
   var target = ob.cloneNode(true);

   if(o.cdf){
       target.innerHTML = d3.select("#"+o.sid).datum();
   }
   target.id = o.tidEnd;
   ob.parentElement.appendChild(target);
}



/*****
 * 复制对象，从公式对象中
 * @param o
 */
SXYL.DOM.copyFormulaElement = function (o) {

    var ob = SXYL.ANIMATION.FORMULA_STEP[o.sid] ;
    var parent = d3.select("#"+o.tid);
    $("#"+o.tid).empty();

    SXYL.GRAPH.initPosition();
    SXYL.GRAPH.drawByComponent(parent , ob);
}



/*****
 * 乘积对象
 * @param o
 */
SXYL.DOM.multiplyElement = function (o) {
    var r = 1;
    for (var i=0;i<o.ids.length;i++){
        r = parseFloat(r) * parseFloat($("#"+o.ids[i]).text()).toFixed(2);
    }
    $("#"+o.tid).text(parseFloat(r).toFixed(2));
}


/*****
 * 求和对象
 * @param o
 */
SXYL.DOM.sumElement = function (o) {
    var r = 0;
    for (var i=0;i<o.ids.length;i++){
        r = parseFloat(r) + parseFloat($("#"+o.ids[i]).text());
    }
    $("#"+o.tid).text(parseFloat(r).toFixed(2));
}


/*****
 * sigmoid计算
 * @param o
 */
SXYL.DOM.sigmoidElement = function (o) {
    var value = -parseFloat($("#"+o.cid).text());
    var eValue = parseFloat(Math.exp(value).toFixed(2));
    eValue = (parseFloat(1)/(parseFloat(1)+eValue)).toFixed(2);

    $("#"+o.tid).text(eValue);
}


/***
 * 计算输出值权重
 * @param o
 */
SXYL.DOM.computeOutWeight = function(o){
    var target_o = o.targetId;
    var target_o_v = parseFloat($("#"+target_o).text());
    var outo = o.outo;
    var outo_v = parseFloat($("#"+outo).text());
    var outh  = o.outh;
    var outh_v = parseFloat($("#"+outh).text());
    var result = ((outo_v - target_o_v)*outo_v*(1-outo_v)*outh_v);
    $("#"+o.tid).text(parseFloat(result).toFixed(2));
}




/***
 * 计算输入值权重
 *
 *
 *
 private String tid;

 private List<String> targetId;

 private List<String> outId;

 private List<String> weightId;

 private String oid ;

 private String iv;
 *
 *
 * @param o
 */
SXYL.DOM.computeHiddenWeight = function(o){
    var target_List = o.targetId;
    var weight_List = o.weightId;
    var out_List = o.outId ;
    var error =0;
    var error_sum=0;
    for(var i =0 ; i< target_List.length ; i++){

        var target_o_v = parseFloat($("#"+target_List[i]).text());
        var outo_v = parseFloat($("#"+out_List[i]).text());
        var weight = parseFloat($("#"+weight_List[i]).text());

        error = ((outo_v - target_o_v)*outo_v*(1- outo_v)).toFixed(2) ;
        error = (parseFloat(error) * parseFloat(weight)).toFixed(2);
        error_sum = parseFloat(error_sum) + parseFloat(error);
    }

    var outh  = parseFloat($("#"+o.oid).text());
    var iv = parseFloat($("#"+o.iv).text());
    var result = (error_sum*(outh*(1-outh))*iv);
    $("#"+o.tid).text(parseFloat(result).toFixed(2));
}


/*****
 * 平方差
 * @param o
 */
SXYL.DOM.squareErrorElement = function (o) {
    var targetValue =parseFloat($("#"+o.targetId).text());
    var outputValue =parseFloat($("#"+o.outputId).text());
    var sub = Math.pow(parseFloat(targetValue - outputValue) ,2).toFixed(2);
    $("#"+o.tid).text(parseFloat(sub/2).toFixed(2));
}


/*****
 * 销毁对象
 * @param o
 */
SXYL.DOM.destroyElement = function (o) {
    $("#"+o.id).remove();
}



/*****
 * 交换对象内容 ,暂时赋值 text
 * @param o
 */
SXYL.DOM.changeElement = function (o) {
    $("#"+o.sid).text($("#"+o.tid).text());
}
