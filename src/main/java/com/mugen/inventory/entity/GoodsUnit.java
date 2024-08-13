package com.mugen.inventory.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.io.Serial;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * t_goods_unit 
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
@TableName("t_goods_unit")
public class GoodsUnit implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

     // 主键id
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

     // 单位名称
    @TableField("name")
    private String name;
}
