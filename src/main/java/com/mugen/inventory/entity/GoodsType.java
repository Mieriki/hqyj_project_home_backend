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
 * t_goods_type 
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
@TableName("t_goods_type")
public class GoodsType implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

     // 主键id
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

     // 类型名称
    @TableField("name")
    private String name;

     // 父级id
    @TableField("p_id")
    private Integer pId;

     // 节点类型
    @TableField("state")
    private Integer state;

     // 图标
    @TableField("icon")
    private String icon;
}
