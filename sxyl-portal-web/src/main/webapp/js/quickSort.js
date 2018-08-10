function quickSort() {
    var result = quickSortStart(x , 0 , x.length -1)
    window.clearInterval(int);
    /***
     * 数组，左侧下标，右侧下标
     * @param arr
     * @param left
     * @param right
     * @return {*}
     */
    function quickSortStart(arr, left, right){
        var len = arr.length, pivot, partitionIndex;
        debugger
        if(left < right){
            pivot = right;
            //分割位置
            partitionIndex = partition(arr, pivot, left, right);

            //sort left and right

            quickSortStart(arr, left, partitionIndex - 1);
            quickSortStart(arr, partitionIndex + 1, right);
        }
        return arr;
    }

    function partition(arr, pivot, left, right){
        var pivotValue = arr[pivot], partitionIndex = left;

        for(var i = left; i < right; i++){
            if(arr[i] < pivotValue){
                swap(arr, i, partitionIndex);
                partitionIndex++;
            }
        }
        swap(arr, right, partitionIndex);
        return partitionIndex;
    }
    function swap(arr, i, j){
        var temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}