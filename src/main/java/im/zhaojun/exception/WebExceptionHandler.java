package im.zhaojun.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@ControllerAdvice
public class WebExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(WebExceptionHandler.class);


    @ExceptionHandler
    public String testError(ArithmeticException e, HttpServletRequest request) {
        log.error("出现了除零异常", e);
        request.setAttribute("javax.servlet.error.status_code", 500);
        request.setAttribute("code", 66);
        request.setAttribute("message", "出现了除零异常");
        return "forward:/error";
    }

    @ExceptionHandler
    public String methodArgumentNotValid(BindException e) {
        log.error("参数校验失败", e);
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        StringBuilder errorMessage = new StringBuilder();
        for (int i = 0; i < allErrors.size(); i++) {
            ObjectError error = allErrors.get(i);
            errorMessage.append(error.getDefaultMessage());
            if (i != allErrors.size() - 1) {
                errorMessage.append(", ");
            }
        }
        // do something
        return generateErrorInfo(1, errorMessage.toString(), HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler
    public String ageDeleteException(AgeDeleteException e) {
        log.error("删除小于 18 岁的用户: ", e);
        return generateErrorInfo(-1, "不允许删除小于 18 岁的用户!");
    }

    @ExceptionHandler
    public String unknownException(Exception e) {
        log.error("发生了未知异常: ", e);
        return generateErrorInfo(-99, "系统故障, 请稍后再试!");
    }


    /**
     * 生成错误信息, 放到 request 域中.
     * @param code          错误码
     * @param message       错误信息
     * @param httpStatus    HTTP 状态码
     * @return              SpringBoot 默认提供的 /error Controller 处理器
     */
    private String generateErrorInfo(int code, String message, int httpStatus) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        request.setAttribute("code", code);
        request.setAttribute("message", message);
        request.setAttribute("javax.servlet.error.status_code", httpStatus);
        return "forward:/error";
    }

    private String generateErrorInfo(int code, String message) {
        return generateErrorInfo(code, message, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}
