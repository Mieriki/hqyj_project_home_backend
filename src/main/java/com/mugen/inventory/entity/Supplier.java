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
 * t_supplier 
 * </p>
 *
 * @author Mieriki
 * @since 2024-07-30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Accessors(chain = true)
@TableName("t_supplier")
public class Supplier implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

     // 供应商id	
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

     // 联系地址
    @TableField("address")
    private String address;

     // 联系人
    @TableField("contact")
    private String contact;

     // 供应商名称
    @TableField("name")
    private String name;

     // 联系电话
    @TableField("number")
    private String number;

     // 备注
    @TableField("remarks")
    private String remarks;

     // 是否删除
    @TableField("is_del")
    private Integer isDel;
}
