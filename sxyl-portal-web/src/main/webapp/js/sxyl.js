SXYL={
    i:0,
    j:0,
    selectMinIndex:0,
    insertSortConfig:{},
    base:{},
    d3:{}
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


    // var rectRight = d3.select("#" + idB) ;
    // var rectLeftMatrix = SXYL.d3.getTranslation(rectLeft.attr("transform"));
    // var rectRightMatrix = SXYL.d3.getTranslation(rectRight.attr("transform"));
    // rectLeft.transition().duration(50).attr("transform", "translate("+rectRightMatrix[0]+","+rectLeftMatrix[1]+")");
    // rectRight.transition().duration(50).attr("transform", "translate("+rectLeftMatrix[0]+","+rectRightMatrix[1]+")");
}