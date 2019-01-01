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
    // 用户操作异常, 这里可以对 1 或 2 等错误码进行单独处理, 也可以 result.code > 0 来粗粒度的处理, 根据业务而定.
    else if (result.code === 1) {
        showError(result.message);
    }
    // 系统异常, 这里可以对 -1 或 -2 等错误码进行单独处理, 也可以 result.code > 0 来粗粒度的处理, 根据业务而定.
    else if (result.code === -1) {
        showError(result.message);
    }
    // 如果进行细粒度的状态码判断, 那么就应该重点注意这里没出现过的状态码. 这个判断仅建议在开发阶段保留用来发现未定义的状态码.
    else {
        showError("出现未定义的状态码:" + result.code);
    }
}