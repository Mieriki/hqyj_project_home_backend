package com.mugen.inventory.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import java.io.Serial;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * t_purchase_list_goods 
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
@TableName("t_purchase_list_goods")
public class PurchaseListGoods implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

     // 主键id
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

     // 商品编号
    @TableField("code")
    private String code;

     // 商品型号
    @TableField("model")
    private String model;

     // 商品名称
    @TableField("name")
    private String name;

     // 商品数量
    @TableField("num")
    private Integer num;

     // 单价
    @TableField("price")
    private Double price;

     // 总价
    @TableField("total")
    private Double total;

     // 商品单位
    @TableField("unit")
    private String unit;

     // 进货单id
    @TableField("purchase_list_id")
    private Integer purchaseListId;

     // 商品类型id
    @TableField("type_id")
    private Integer typeId;

     // 商品id
    @TableField("goods_id")
    private Integer goodsId;

     // 日期
    @TableField("current_data")
    private Date currentData;
}
