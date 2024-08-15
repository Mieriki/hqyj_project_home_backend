package com.mugen.inventory.entity.model.vo.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class DepartmentSaveVo {
    String depName;
    int parentId;
    int enabled;
    Integer result;
    Integer result2;
}
