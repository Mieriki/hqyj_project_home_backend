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
 * t_salary 
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
@TableName("t_salary")
public class Salary implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

     // id
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

     // 基本工资
    @TableField("basicSalary")
    private Integer basicSalary;

     // 奖金
    @TableField("bonus")
    private Integer bonus;

     // 午餐补助
    @TableField("lunchSalary")
    private Integer lunchSalary;

     // 交通补助
    @TableField("trafficSalary")
    private Integer trafficSalary;

     // 应发工资
    @TableField("allSalary")
    private Integer allSalary;

     // 养老金基数
    @TableField("pensionBase")
    private Integer pensionBase;

     // 养老金比率
    @TableField("pensionPer")
    private Double pensionPer;

     // 启用时间
    @TableField("createDate")
    private Date createDate;

     // 医疗基数
    @TableField("medicalBase")
    private Integer medicalBase;

     // 医疗保险比率
    @TableField("medicalPer")
    private Double medicalPer;

     // 公积金基数
    @TableField("accumulationFundBase")
    private Integer accumulationFundBase;

     // 公积金比率
    @TableField("accumulationFundPer")
    private Double accumulationFundPer;

     // 名称
    @TableField("name")
    private String name;
}
