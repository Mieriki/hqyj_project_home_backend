package com.mugen.inventory.entity.model.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeePageVo {
    Long count;
    List<EmployeeVo> employeeList;
}
