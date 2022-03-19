package edu.wong.attendance_management_api.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionUtil {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseUtil handler(ShiroException e) {
        log.error("运行时异常", e);
        return ResponseUtil.operate(401, e.getMessage(), null);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseUtil handler(RuntimeException e) {
        log.error("运行时异常", e);
        return ResponseUtil.fail(e.getMessage());
    }

}
