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
 * t_employee 
 * </p>
 *
 * @author Mieriki
 * @since 2024-08-16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Accessors(chain = true)
@TableName("t_employee")
public class Employee implements Serializable, BaseData {
    @Serial
    private static final long serialVersionUID = 1L;

     // 员工编号
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

     // 员工姓名
    @TableField("name")
    private String name;

     // 性别
    @TableField("gender")
    private String gender;

     // 出生日期
    @TableField("birthday")
    private Date birthday;

     // 身份证号
    @TableField("idCard")
    private String idCard;

     // 婚姻状况
    @TableField("wedlock")
    private String wedlock;

     // 民族
    @TableField("nationId")
    private Integer nationId;

     // 邮箱
    @TableField("email")
    private String email;

     // 电话号码
    @TableField("phone")
    private String phone;

     // 联系地址
    @TableField("address")
    private String address;

     // 所属部门
    @TableField("departmentId")
    private Integer departmentId;

     // 职位ID
    @TableField("posId")
    private Integer posId;
}
