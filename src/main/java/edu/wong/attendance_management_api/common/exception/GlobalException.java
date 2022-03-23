package edu.wong.attendance_management_api.common.exception;

import edu.wong.attendance_management_api.common.lang.ResponseFormat;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalException {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = ShiroException.class)
    public ResponseFormat handler(ShiroException e) {
        log.error("未授权异常", e);
        return ResponseFormat.operate(401, e.getMessage(), null);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseFormat handler(MethodArgumentNotValidException e) {
        log.error("实体校验异常", e);
        BindingResult bindingResult = e.getBindingResult();
        ObjectError objectError = bindingResult.getAllErrors().stream().findFirst().get();
        return ResponseFormat.operate(401, objectError.getDefaultMessage(), null);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseFormat handler(IllegalArgumentException e) {
        log.error("Assert时异常", e);
        return ResponseFormat.fail(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = UnauthorizedException.class)
    public ResponseFormat handler(UnauthorizedException e) {
        log.error("权限异常", e);
        return ResponseFormat.fail("权限异常:" + e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = RuntimeException.class)
    public ResponseFormat handler(RuntimeException e) {
        log.error("运行时异常", e);
        return ResponseFormat.fail(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = Exception.class)
    public ResponseFormat handler(Exception e) {
        log.error("异常", e);
        return ResponseFormat.fail(e.getMessage());
    }

}
