package com.mugen.inventory.entity.model.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeTrainPageVo {
    Long count;
    List<EmployeeTrainVo> employeeTrainList;
}
