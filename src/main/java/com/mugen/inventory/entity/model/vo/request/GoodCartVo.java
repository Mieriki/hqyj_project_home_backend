package com.mugen.inventory.entity.model.vo.request;

import com.mugen.inventory.entity.Goods;
import com.mugen.inventory.utils.BaseData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Accessors(chain = true)
public class GoodCartVo implements BaseData {
    private List<GoodsCartVo> goodsInList;
    private String purchaseNumber;
    private String saleNumber;
    private Integer supplierId;
    private Integer customerId;
    private Double amountPayable;
    private Double amountPaid;
    private String remarks;
    private Integer state;
    private Integer userId;
}
