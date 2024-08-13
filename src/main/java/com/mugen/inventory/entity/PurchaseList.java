package com.mugen.inventory.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import java.io.Serial;

import com.mugen.inventory.utils.BaseData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * t_purchase_list 
 * </p>
 *
 * @author Mieriki
 * @since 2024-07-31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Accessors(chain = true)
@TableName("t_purchase_list")
public class PurchaseList implements Serializable, BaseData {
    @Serial
    private static final long serialVersionUID = 1L;

     // 主键id
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

     // 实付金额
    @TableField("amount_paid")
    private Double amountPaid;

     // 应付金额
    @TableField("amount_payable")
    private Double amountPayable;

     // 进货日期
    @TableField("purchase_date")
    private Date purchaseDate;

     // 备注
    @TableField("remarks")
    private String remarks;

     // 交易状态 1 已付  2 未付
    @TableField("state")
    private Integer state;

     // 进货单号
    @TableField("purchase_number")
    private String purchaseNumber;

     // 供应商id
    @TableField("supplier_id")
    private Integer supplierId;

     // 操作员id
    @TableField("user_id")
    private Integer userId;
}
