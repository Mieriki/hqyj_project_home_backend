package com.mugen.inventory.entity.model.vo.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpLeaveStartVo {
    private Integer id;
    private String name;
    private Integer day;
    private String reason;
    private String roleName;

    /**
     * 线程局部变量，用于线程安全的ObjectMapper实例
     */
    private static final ThreadLocal<ObjectMapper> objectMapperThreadLocal = ThreadLocal.withInitial(() -> new ObjectMapper()
            .setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL)
    );

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
