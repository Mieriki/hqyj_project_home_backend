package com.mugen.inventory.annotation;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mugen.inventory.entity.Admin;
import com.mugen.inventory.entity.Syslog;
import com.mugen.inventory.service.AdminService;
import com.mugen.inventory.service.SyslogService;
import com.mugen.inventory.utils.JwtUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;

@Log4j2
@Aspect
@Component
@Order(-190) // 控制多个切面的执行顺序，数字越小优先级越高
public class LoggerAspect {
    @Resource
    SyslogService syslogService;

    @Resource
    AdminService adminService;

    @Resource
    JwtUtils utils;

    @Pointcut("@annotation(com.mugen.inventory.annotation.LoggerPermission)")
    private void logger() {

    }

    @Around("logger()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        // 记录方法执行开始时间
        long startTime = System.currentTimeMillis();

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        String authorization = request.getHeader("Authorization");
        DecodedJWT jwt = utils.resolveJwt(authorization);

        if (jwt == null) {
            throw new RuntimeException("非法请求");
        }


        Method method = ((MethodSignature) proceedingJoinPoint.getSignature()).getMethod();
        LoggerPermission loggerPermission = method.getAnnotation(LoggerPermission.class);
        String operation = loggerPermission.operation();
        // 继续执行目标方法
        Object result = proceedingJoinPoint.proceed();

        // 记录方法执行结束时间
        long endTime = System.currentTimeMillis();
        Admin admin = adminService.getOne(new QueryWrapper<Admin>().eq("id",utils.getId(jwt)));
        syslogService.save(Syslog.builder()
                .operation(operation)
                .method(method.getDeclaringClass().getName() + "." + method.getName())
                .params(Arrays.toString(proceedingJoinPoint.getArgs()))
                .userName(admin.getUserName())
                .time((int) (endTime - startTime))
                .createTime(new Date())
                .ip(request.getRemoteAddr())
                .build()
        );
        return result;
    }
}
