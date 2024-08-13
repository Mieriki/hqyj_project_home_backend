package com.mugen.inventory.entity.model.vo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class AdminQueryPageVo {
    String name;
    String address;
    Integer currentPage;
    Integer pageSize;
}
