package com.mugen.inventory.entity.model.vo.response;

import com.mugen.inventory.entity.Position;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PositionPageVo {
    private Long count;
    private List<PositionVo> positionList;
}
