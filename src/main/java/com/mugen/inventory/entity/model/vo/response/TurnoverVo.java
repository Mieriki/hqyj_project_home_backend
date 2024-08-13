package com.mugen.inventory.entity.model.vo.response;

import com.mugen.inventory.entity.model.dto.Turnover;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TurnoverVo {
    List<Turnover> purchaseList;
    List<Turnover> salesList;
}
