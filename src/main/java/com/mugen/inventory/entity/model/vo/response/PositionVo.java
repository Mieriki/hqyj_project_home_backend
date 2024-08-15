package com.mugen.inventory.entity.model.vo.response;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

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
public class PositionVo implements Serializable {
     // id
    private Integer id;

     // 职位
    private String name;

     // 创建时间
    private Date createDate;

     // 是否启用1启用，0未启用
    private Boolean enabled;

     // 修改时间
    private Date updateDate;

     // 操作人用户名
    private String operationName;

    // 操作人id
    private Integer operationId;

     // 1-删除，0未删除
    private Boolean isDelete;
}
