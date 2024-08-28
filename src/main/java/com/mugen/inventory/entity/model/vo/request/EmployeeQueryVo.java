package com.mugen.inventory.entity.model.vo.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class EmployeeQueryVo {
    private String name;
    private Integer posId;
    private List<Integer> departmentIdList;
    private Integer currentPage;
    private Integer pageSize;
}
