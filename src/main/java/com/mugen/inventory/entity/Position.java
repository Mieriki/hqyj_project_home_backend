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
 * t_position 
 * </p>
 *
 * @author Mieriki
 * @since 2024-08-14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Accessors(chain = true)
@TableName("t_position")
public class Position implements Serializable, BaseData {
    @Serial
    private static final long serialVersionUID = 1L;

     // id
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

     // 职位
    @TableField("name")
    private String name;

     // 创建时间
    @TableField("create_date")
    private Date createDate;

     // 是否启用1启用，0未启用
    @TableField("enabled")
    private Boolean enabled;

     // 修改时间
    @TableField("update_date")
    private Date updateDate;

     // 操作人id
    @TableField("operation_id")
    private Integer operationId;

     // 1-删除，0未删除
    @TableField("is_delete")
    private Boolean isDelete;
}
