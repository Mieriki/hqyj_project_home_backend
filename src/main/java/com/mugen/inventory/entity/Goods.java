package com.mugen.inventory.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.io.Serial;

import com.mugen.inventory.utils.BaseData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * t_goods 
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
@TableName("t_goods")
public class Goods implements Serializable, BaseData {
    @Serial
    private static final long serialVersionUID = 1L;

     // 主键id
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

     // 商品编码
    @TableField("code")
    private String code;

     // 商品名称
    @TableField("name")
    private String name;

     // 库存数量
    @TableField("inventory_quantity")
    private Integer inventoryQuantity;

     // 库存上线
    @TableField("max_num")
    private Integer maxNum;

     // 库存下线
    @TableField("min_num")
    private Integer minNum;

     // 型号
    @TableField("model")
    private String model;

     // 生产产商
    @TableField("producer")
    private String producer;

     // 采购价
    @TableField("purchasing_price")
    private Double purchasingPrice;

     // 备注
    @TableField("remarks")
    private String remarks;

     // 出库价
    @TableField("selling_price")
    private Double sellingPrice;

     // 商品单位
    @TableField("unit")
    private String unit;

     // 商品类型
    @TableField("type_id")
    private Integer typeId;

     // 上一次采购价格
    @TableField("last_purchasing_price")
    private Double lastPurchasingPrice;
}
