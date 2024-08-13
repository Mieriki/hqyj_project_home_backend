package com.mugen.inventory.entity.model.vo.request;

import lombok.Data;

@Data
public class PurchaseQueryPageVo {
    String purchaseNumber;
    String supplier;
    Integer state;
    Integer currentPage;
    Integer pageSize;
}
