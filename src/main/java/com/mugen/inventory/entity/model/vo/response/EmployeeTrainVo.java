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

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Accessors(chain = true)
public class EmployeeTrainVo implements Serializable {
    // id
    private Integer id;

    // 员工编号
    private Integer eid;
    private String eName;

    // 培训日期
    private Date trainDate;

    // 培训内容
    private String trainContent;

    // 备注
    private String remark;
}
