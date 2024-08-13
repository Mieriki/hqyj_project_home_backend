package com.mugen.inventory.entity.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class CartSaleDto {
    // 主键id
    private Integer id;

    // 实付金额
    private Double amountPaid;

    // 应付金额
    private Double amountPayable;

    // 进货日期
    private Date saleDate;

    // 备注
    private String remarks;

    // 交易状态 1 已付  2 未付
    private Integer state;

    // 进货单号
    private String saleNumber;

    // 供应商
    private String customer;

    // 操作员
    private String user;

}
