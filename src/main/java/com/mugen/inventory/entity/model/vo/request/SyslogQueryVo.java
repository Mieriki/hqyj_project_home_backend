package com.mugen.inventory.entity.model.vo.request;

import lombok.Data;

@Data
public class SyslogQueryVo {
    private String name;

    private String operation;

    private int currentPage;

    private int pageSize;
}
