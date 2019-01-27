/**
 * 显示错误信息
 * @param message：错误信息
 */
function showError(message) {
    layer.msg(message, {icon: 2});
}

/**
 * 处理 ajax 请求结果
 * @param result：ajax 返回的结果
 * @param fn：成功的处理函数 ( 传入data: fn(result.data) )
 */
function handlerResult(result, fn) {
    // 成功执行操作，失败提示原因
    if (result.code === 0) {
        fn(result.data);
    }
    // 这里可以根据不同的状态码做不同的业务,  如提示框不同, 或某个状态码标识未登录, 跳转到登录页面
    // else if (result.code === 1) {
    //     showError(result.message);
    // }
    else {
        showError(result.message);
    }
}

/**
 * jQuery 全局异常处理, 处理 document 中的所有 ajax 的 error 事件.
 */
$(document).ajaxError(function(event, response){
    console.log("错误响应状态码: ",response.status);
    console.log("错误响应结果: ",response.responseJSON);
    showError(response.responseJSON.message);
});