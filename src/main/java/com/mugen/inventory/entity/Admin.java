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
 * t_admin 
 * </p>
 *
 * @author Mieriki
 * @since 2024-07-24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Accessors(chain = true)
@TableName("t_admin")
public class Admin implements Serializable, BaseData {
    @Serial
    private static final long serialVersionUID = 1L;

     // id
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

     // 姓名
    @TableField("name")
    private String name;

     // 手机号码
    @TableField("phone")
    private String phone;

     // 住宅电话
    @TableField("telephone")
    private String telephone;

     // 联系地址
    @TableField("address")
    private String address;

     // 是否启用
    @TableField("enabled")
    private Boolean enabled;

     // 用户名
    @TableField("user_name")
    private String userName;

     // 密码
    @TableField("password")
    private String password;

     // 用户头像
    @TableField("user_face")
    private String userFace;

     // 备注
    @TableField("remark")
    private String remark;

     // 加密盐值
    @TableField("slot")
    private String slot;
}
