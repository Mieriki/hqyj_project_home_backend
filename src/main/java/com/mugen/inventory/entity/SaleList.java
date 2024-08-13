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
 * t_sale_list 
 * </p>
 *
 * @author Mieriki
 * @since 2024-08-01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Accessors(chain = true)
@TableName("t_sale_list")
public class SaleList implements Serializable, BaseData {
    @Serial
    private static final long serialVersionUID = 1L;

     // 主键id	主键id
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

     // 实付金额
    @TableField("amount_paid")
    private Double amountPaid;

     // 应付金额
    @TableField("amount_payable")
    private Double amountPayable;

     // 备注
    @TableField("remarks")
    private String remarks;

     // 销售日期
    @TableField("sale_date")
    private Date saleDate;

     // 销售数量
    @TableField("sale_number")
    private String saleNumber;

     // 状态（1、已付款/2、未付款）
    @TableField("state")
    private Integer state;

     // 操作员id
    @TableField("user_id")
    private Integer userId;

     // 客户id
    @TableField("customer_id")
    private Integer customerId;
}
