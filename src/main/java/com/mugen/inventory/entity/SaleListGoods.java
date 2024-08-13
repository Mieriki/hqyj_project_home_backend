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
 * t_sale_list_goods 
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
@TableName("t_sale_list_goods")
public class SaleListGoods implements Serializable {
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

     // 商品名字
    @TableField("name")
    private String name;

     // 销售数量
    @TableField("num")
    private Integer num;

     // 销售单价
    @TableField("price")
    private Double price;

     // 总价
    @TableField("total")
    private Double total;

     // 商品单位
    @TableField("unit")
    private String unit;

     // 销售单id
    @TableField("sale_list_id")
    private Integer saleListId;

     // 商品类型
    @TableField("type_id")
    private Integer typeId;

     // 商品id
    @TableField("goods_id")
    private Integer goodsId;

     // 销售时间
    @TableField("current_data")
    private Date currentData;
}
