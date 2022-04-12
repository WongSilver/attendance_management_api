package edu.wong.attendance_management_api.common.exception;

import edu.wong.attendance_management_api.common.lang.ResponseFormat;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(value = ShiroException.class)
    public ResponseFormat handler(ShiroException e) {
        log.error("未授权异常", e);
        return ResponseFormat.operate(401, e.getMessage(), null);
    }

    @ExceptionHandler(value = ExpiredCredentialsException.class)
    public ResponseFormat handler(ExpiredCredentialsException e) {
        log.error("token已失效，请重新登录 ", e);
        return ResponseFormat.operate(999, e.getMessage(), null);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseFormat handler(MethodArgumentNotValidException e) {
        log.error("实体校验异常", e);
        BindingResult bindingResult = e.getBindingResult();
        ObjectError objectError = bindingResult.getAllErrors().stream().findFirst().get();
        return ResponseFormat.operate(401, objectError.getDefaultMessage(), null);
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseFormat handler(IllegalArgumentException e) {
        log.error("Assert时异常", e);
        return ResponseFormat.fail(e.getMessage());
    }

    @ExceptionHandler(value = UnauthorizedException.class)
    public ResponseFormat handler(UnauthorizedException e) {
        log.error("权限异常", e);
        return ResponseFormat.operate(233, e.getMessage(), null);
    }

    @ExceptionHandler(value = LockedAccountException.class)
    public ResponseFormat handler(LockedAccountException e) {
        log.error("用户被锁定，请联系管理员", e);
        return ResponseFormat.fail(e.getMessage());
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseFormat handler(RuntimeException e) {
        log.error("运行时异常", e);
        return ResponseFormat.fail(e.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseFormat handler(Exception e) {
        log.error("异常", e);
        return ResponseFormat.fail(e.getMessage());
    }

}
