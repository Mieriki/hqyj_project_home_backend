package com.mugen.inventory.utils;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mugen.inventory.utils.constant.HttpMessageConstant;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 用于封装返回给前端的Restful接口数据
 * @param <T> 返回的数据类型
 */
public record RestBean<T>(int code, T data, String message) implements HttpMessageConstant {
    // 状态码与信息映射表
    private static final Map<Integer, String> codeMessageMap = new HashMap<>();
    /**
     * 静态代码块，初始化codeMessageMap
     * 该Map用于根据状态码获取对应的信息
     */
    static {
        // 使用反射获取 HttpMessageConstant 接口中定义的常量并存储到 Map 中
        Field[] fields = HttpMessageConstant.class.getDeclaredFields();
        for (Field field : fields) {
            if (field.getType() == int.class) {
                try {
                    int code = field.getInt(null);
                    String message = (String) HttpMessageConstant.class.getField(field.getName() + "_MESSAGE").get(null);
                    codeMessageMap.put(code, message);
                } catch (IllegalAccessException | NoSuchFieldException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 线程局部变量，用于线程安全的ObjectMapper实例
     */
    private static final ThreadLocal<ObjectMapper> objectMapperThreadLocal = ThreadLocal.withInitial(() -> new ObjectMapper()
            .setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL)
    );

    /**
     * 在必要时允许根据code重新构造RestBean
     * @param code 状态码
     * @return 新的RestBean
     */
    public RestBean<T> code(int code) {
        return new RestBean<>(code, data, message);
    }

    /**
     * 在必要时允许根据data重新构造RestBean
     * @param data 数据
     * @return 新的RestBean
     */
    public RestBean<T> data(T data) {
        return new RestBean<>(code, data, message);
    }

    /**
     * 在必要时允许根据message重新构造RestBean
     * @param message 信息
     * @return 新的RestBean
     */
    public RestBean<T> message(String message) {
        return new RestBean<>(code, data, message);
    }

    /**
     * 获取data数据
     * @return data数据
     */
    public T getData() {
        return data;
    }

    /**
     * 成功的RestBean
     * @param <T> 数据类型
     * @return 成功的RestBean
     */
    public static <T> RestBean<T> success() {
        return new RestBean<>(OK, null, OK_MESSAGE);
    }

    /**
     * 成功的RestBean
     * @param <T> 数据类型
     * @param code 状态码
     * @return 成功的RestBean
     */
    public static <T> RestBean<T> success(int code) {
        return new RestBean<>(code, null, codeMessageMap.get(code));
    }


    /**
     * 成功的RestBean
     * @param <T> 数据类型
     * @param data 数据
     * @return 成功的RestBean
     */
    public static <T> RestBean<T> success(T data) {
        return new RestBean<>(OK, data, OK_MESSAGE);
    }

    /**
     * 成功的RestBean
     * @param <T> 数据类型
     * @param message 信息
     * @return 成功的RestBean
     */
    public static <T> RestBean<T> success(String message) {
        return new RestBean<>(OK, null, message);
    }


    /**
     * 成功的RestBean
     * @param <T> 数据类型
     * @param message 信息
     * @return 成功的RestBean
     */
    public static <T> RestBean<T> success(int code, String message) {
        return new RestBean<>(code, null, message);
    }

    /**
     * 成功的RestBean
     * @param <T> 数据类型
     * @param data 数据
     * @return 成功的RestBean
     */
    public static <T> RestBean<T> success(int code, T data) {
        return new RestBean<>(code, data, codeMessageMap.get(code));
    }

    /**
     * 成功的RestBean
     * @param <T> 数据类型
     * @param data 数据
     * @param message 信息
     * @return 成功的RestBean
     */
    public static <T> RestBean<T> success(T data, String message) {
        return new RestBean<>(OK, data, message);
    }

    /**
     * 成功的RestBean
     * @param <T> 数据类型
     * @param data 数据
     * @param message 信息
     * @return 成功的RestBean
     */
    public static <T> RestBean<T> success(int code, T data, String message) {
        return new RestBean<>(code, data, message);
    }

    /**
     * 失败的RestBean
     * @param <T> 数据类型
     * @return 失败的RestBean
     */
    public static <T> RestBean<T> failure() {
        return new RestBean<>(BAD_REQUEST, null, BAD_REQUEST_MESSAGE);
    }

    /**
     * 失败的RestBean
     * @param <T> 数据类型
     * @param code 状态码
     * @return 失败的RestBean
     */
    public static <T> RestBean<T> failure(int code) {
        return new RestBean<>(code, null, codeMessageMap.get(code));
    }

    /**
     * 失败的RestBean
     * @param <T> 数据类型
     * @param message 信息
     * @return 失败的RestBean
     */
    public static <T> RestBean<T> failure(String message) {
        return new RestBean<>(BAD_REQUEST, null, message);
    }

    /**
     * 失败的RestBean
     * @param <T> 数据类型
     * @param code 状态码
     * @param message 信息
     * @return 失败的RestBean
     */
    public static <T> RestBean<T> failure(int code, String message) {
        return new RestBean<>(code, null, message);
    }

    /**
     * 抽象的RestBean构造器方法
     * @param action Supplier，用于从外部获取message信息
     * @param seccuessCode 成功状态码
     * @param successMessage 成功信息
     * @param failureCode 失败状态码
     * @return RestBean
     * @param <T> 数据类型
     */
    public static <T> RestBean<Void> messageHandle(Supplier<String> action, int seccuessCode, String successMessage, int failureCode) {
        String message = action.get();
        return message == null ? RestBean.success(seccuessCode, null, successMessage) : RestBean.failure(failureCode, message);
    }

    /**
     * RestBean构造器方法，用于处理vo对象，并返回RestBean
     * @param vo vo对象
     * @param function vo的处理方法
     * @return RestBean
     * @param <T> 数据类型
     */
    public static <T> RestBean<Void> messageHandle(T vo, Function<T, String> function) {
        return messageHandle(() -> function.apply(vo), OK, OK_MESSAGE, BAD_REQUEST);
    }

    /**
     * RestBean构造器方法，用于处理vo对象，并返回RestBean
     * @param vo vo对象
     * @param function vo的处理方法
     * @param seccuessCode 成功状态码
     * @return RestBean
     * @param <T> 数据类型
     */
    public static <T> RestBean<Void> messageHandle(T vo, Function<T, String> function, int seccuessCode) {
        return messageHandle(() -> function.apply(vo), seccuessCode, codeMessageMap.get(seccuessCode), BAD_REQUEST);
    }

    /**
     * RestBean构造器方法，用于处理vo对象，并返回RestBean
     * @param vo vo对象
     * @param function vo的处理方法
     * @param successMessage 成功信息
     * @return RestBean
     * @param <T> 数据类型
     */
    public static <T> RestBean<Void> messageHandle(T vo, Function<T, String> function, String successMessage) {
        return messageHandle(() -> function.apply(vo), OK, successMessage, BAD_REQUEST);
    }

    /**
     * RestBean构造器方法，用于处理vo对象，并返回RestBean
     * @param vo vo对象
     * @param function vo的处理方法
     * @param seccuessCode 成功状态码
     * @param successMessage 成功信息
     * @return RestBean
     * @param <T> 数据类型
     */
    public static <T> RestBean<Void> messageHandle(T vo, Function<T, String> function, int seccuessCode, String successMessage) {
        return messageHandle(() -> function.apply(vo), seccuessCode, successMessage, BAD_REQUEST);
    }

    /**
     * RestBean构造器方法，用于处理vo对象，并返回RestBean
     * @param vo vo对象
     * @param function vo的处理方法
     * @param seccuessCode 成功状态码
     * @param failureCode 失败状态码
     * @return RestBean
     * @param <T> 数据类型
     */
    public static <T> RestBean<Void> messageHandle(T vo, Function<T, String> function, int seccuessCode, int failureCode) {
        return messageHandle(() -> function.apply(vo), seccuessCode, codeMessageMap.get(seccuessCode), failureCode);
    }

    /**
     * RestBean构造器方法，用于处理vo对象，并返回RestBean
     * @param vo vo对象
     * @param function vo的处理方法
     * @param seccuessCode 成功状态码
     * @param successMessage 成功信息
     * @param failureCode 失败状态码
     * @return RestBean
     * @param <T> 数据类型
     */
    public static <T> RestBean<Void> messageHandle(T vo, Function<T, String> function, int seccuessCode, String successMessage, int failureCode) {
        return messageHandle(() -> function.apply(vo), seccuessCode, successMessage, failureCode);
    }

    /**
     * 将RestBean转换为json字符串
     * @return json字符串
     */
    @SneakyThrows
    public String asJsonString() {
        ObjectMapper objectMapper = objectMapperThreadLocal.get();
        try {
            return objectMapper.writeValueAsString(this);
        } finally {
            objectMapperThreadLocal.remove();
        }
    }
}
