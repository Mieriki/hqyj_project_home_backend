package com.mugen.inventory.entity.model.vo.response;

import com.mugen.inventory.entity.model.dto.CartPurchaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartPurchasePageVo {
    private Long count;
    private List<CartPurchaseDto> cartList;
}
