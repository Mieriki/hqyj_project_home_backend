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
 * t_department 
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
@TableName("t_department")
public class Department implements Serializable, BaseData {
    @Serial
    private static final long serialVersionUID = 1L;

     // id
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

     // 部门名称
    @TableField("name")
    private String name;

     // 父id
    @TableField("parent_id")
    private Integer parentId;

     // 路径
    @TableField("dep_path")
    private String depPath;

     // 是否启用
    @TableField("enabled")
    private Boolean enabled;

     // 是否上级
    @TableField("is_parent")
    private Boolean isParent;
}
