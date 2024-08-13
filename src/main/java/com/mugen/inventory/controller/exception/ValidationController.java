package com.mugen.inventory.controller.exception;

import com.mugen.inventory.utils.RestBean;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 用于接口参数校验处理的控制器
 */
@Slf4j
@RestControllerAdvice
public class ValidationController {

    /**
     * 与SpringBoot保持一致，校验不通过打印警告信息，而不是直接抛出异常
     * @param e 验证异常
     * @return 校验结果
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public RestBean<Void> handleConstraintViolationException(ConstraintViolationException e) {
        log.warn("Resolved [{}: {}]", e.getClass().getName(), e.getMessage());
        return RestBean.failure("请求参数有误");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RestBean<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.warn("Resolved [{}: {}]", e.getClass().getName(), e.getMessage());
        return RestBean.failure("请求参数有误");
    }
}
