package com.mugen.inventory.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.io.Serial;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * t_nation 
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
@TableName("t_nation")
public class Nation implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

     // id
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

     // 民族
    @TableField("name")
    private String name;
}
