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
 * t_employee_train 
 * </p>
 *
 * @author Mieriki
 * @since 2024-08-20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Accessors(chain = true)
@TableName("t_employee_train")
public class EmployeeTrain implements Serializable, BaseData {
    @Serial
    private static final long serialVersionUID = 1L;

     // id
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

     // 员工编号
    @TableField("eid")
    private Integer eid;

     // 培训日期
    @TableField("trainDate")
    private Date trainDate;

     // 培训内容
    @TableField("trainContent")
    private String trainContent;

     // 备注
    @TableField("remark")
    private String remark;
}
