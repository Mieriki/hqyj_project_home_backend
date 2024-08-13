package com.mugen.inventory.entity.model.vo.request;

import lombok.Data;

@Data
public class SaleQueryPageVo {
    String saleNumber;
    String customer;
    Integer state;
    Integer currentPage;
    Integer pageSize;
}
