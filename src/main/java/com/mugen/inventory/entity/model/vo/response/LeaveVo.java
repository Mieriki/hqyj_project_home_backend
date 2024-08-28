package com.mugen.inventory.entity.model.vo.response;

import com.mugen.inventory.utils.BaseData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class LeaveVo implements BaseData {
    String id;
    String taskId;
    String name;
    String day;
    String username;
    Integer userId;
    String reason;
}
