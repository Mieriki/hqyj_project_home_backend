package com.mugen.inventory.entity.model.vo.response;

import com.mugen.inventory.entity.model.dto.CartSaleDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartSalePageVo {
    Long count;
    List<CartSaleDto> cartList;
}
