package com.mugen.inventory.controller.exception;


import com.mugen.inventory.utils.RestBean;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLSyntaxErrorException;

@Slf4j
@RestControllerAdvice
public class RuntimeController {
    @ExceptionHandler(RuntimeException.class)
    public RestBean<Void> handleRuntimeException(RuntimeException e) {
        log.warn("Resolved [{}: {}]", e.getClass().getName(), e.getMessage());
        return RestBean.failure(400,  e.getMessage());
    }
    @ExceptionHandler(SQLSyntaxErrorException.class)
    public RestBean<Void> handleSQLSyntaxErrorException(SQLSyntaxErrorException e) {
        log.warn("Resolved [{}: {}]", e.getClass().getName(), e.getMessage());
        return RestBean.failure(400, "对象构建失败");
    }
}