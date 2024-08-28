package com.mugen.inventory.entity.model.vo.response;

import com.mugen.inventory.entity.Salary;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalaryPageVo {
    private Long count;
    private List<Salary> salaryList;
}
