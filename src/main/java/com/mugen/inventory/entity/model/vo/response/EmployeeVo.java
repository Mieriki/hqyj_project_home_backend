package com.mugen.inventory.entity.model.vo.response;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Accessors(chain = true)
public class EmployeeVo {
    // 员工编号
    private Integer id;

    // 员工姓名
    private String name;

    // 性别
    private String gender;

    // 出生日期
    private Date birthday;

    // 身份证号
    private String idCard;

    // 婚姻状况
    private String wedlock;

    // 民族
    private Integer nationId;

    private String nationName;

    // 邮箱
    private String email;

    // 电话号码
    private String phone;

    // 联系地址
    private String address;

    // 所属部门
    private Integer departmentId;
    private String departmentName;

    // 职位ID
    private Integer posId;
    private String posName;
}
