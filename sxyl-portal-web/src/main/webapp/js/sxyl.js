SXYL={
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
        recursiveStack:[] , //归并排序的递归栈结构,里面存储三个元素,数组左下标,数组右下标,分割后的数组内容
        recursiveGroupStack:[] //归并排序的分组 结构
    },
    base:{},
    d3:{
        defaultY: 240//默认y轴坐标
    }
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


//
// SXYL.d3.createDom = function (transform) {
//     // Create a dummy g for calculation purposes only. This will never
//     // be appended to the DOM and will be discarded once this function
//     // returns.
//     var g = document.createElementNS("http://www.w3.org/2000/svg", "g");
//
//     // Set the transform attribute to the provided string value.
//     g.setAttributeNS(null, "transform", transform);
//
//     // consolidate the SVGTransformList containing all transformations
//     // to a single SVGTransform of type SVG_TRANSFORM_MATRIX and get
//     // its SVGMatrix.
//     var matrix = g.transform.baseVal.consolidate().matrix;
//
//     // As per definition values e and f are the ones for the translation.
//     return [matrix.e, matrix.f];
// }

/****
 * d3的一个作废方法， 获取 transform属性的  宽和高
 * @param transform
 * @returns {*[]}
 */
SXYL.d3.getTranslation = function (transform) {
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
SXYL.d3.changeElementTransformX = function (idA , idB) {
    var rectLeft = d3.select("#" + idA) ;
    var rectRight = d3.select("#" + idB) ;
    var rectLeftMatrix = SXYL.d3.getTranslation(rectLeft.attr("transform"));
    var rectRightMatrix = SXYL.d3.getTranslation(rectRight.attr("transform"));
    rectLeft.transition().duration(50).attr("transform", "translate("+rectRightMatrix[0]+","+rectLeftMatrix[1]+")");
    rectRight.transition().duration(50).attr("transform", "translate("+rectLeftMatrix[0]+","+rectRightMatrix[1]+")");
}



/***
 * 变更颜色
 * @param idA 元素id
 * @param color 颜色
 * @return true 替换成功   false 替换失败,组件为原有颜色
 */
SXYL.d3.changeElementColor = function (id , color) {
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
 * 变更颜色
 * @param idA 元素id
 * @param color 颜色
 * @return true 替换成功   false 替换失败,组件为原有颜色
 */
SXYL.d3.changeElementY = function (id , y) {
    var rectTarget = d3.select("#" + id) ;
    var rectTargetMatrix = SXYL.d3.getTranslation(rectTarget.attr("transform"));
    debugger
    if(!y){
        y = SXYL.d3.defaultY ;
    }
    y = parseInt(rectTargetMatrix[1]) + y;
    rectTarget.transition().duration(50).attr("transform", "translate("+rectTargetMatrix[0]+","+y+")");
}
