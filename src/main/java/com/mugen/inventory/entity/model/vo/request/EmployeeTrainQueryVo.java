package com.mugen.inventory.entity.model.vo.request;

import lombok.Data;

@Data
public class EmployeeTrainQueryVo {
    private String eName;
    private String trainContent;
    private int currentPage;
    private int pageSize;
}
