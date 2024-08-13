package com.mugen.inventory.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
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
 * t_customer 
 * </p>
 *
 * @author Mieriki
 * @since 2024-07-25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Accessors(chain = true)
@TableName("t_customer")
public class Customer implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

     // 主键id
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

     // 客户地址
    @TableField("address")
    private String address;

     // 客户联系方式
    @TableField("contact")
    private String contact;

     // 客户名字
    @TableField("name")
    private String name;

     // 客户电话
    @TableField("number")
    private String number;

     // 客户备注
    @TableField("remarks")
    private String remarks;

     // 是否删除（0,否1,是）
    @TableField("is_del")
    private Integer isDel;

     // 创建时间
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;
}
