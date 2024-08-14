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
 * t_syslog 
 * </p>
 *
 * @author Mieriki
 * @since 2024-08-13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Accessors(chain = true)
@TableName("t_syslog")
public class Syslog implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

     // 主键id
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

     // 操作步骤
    @TableField("operation")
    private String operation;

     // 方法名
    @TableField("method")
    private String method;

     // 参数
    @TableField("params")
    private String params;

     // 账号名
    @TableField("user_name")
    private String userName;

     // 用时多少
    @TableField("time")
    private Integer time;

     // 创建时间
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

     // ip地址
    @TableField("ip")
    private String ip;
}
