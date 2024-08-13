package com.mugen.inventory.service;

import com.mugen.inventory.entity.PurchaseListGoods;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Mieriki
 * @since 2024-07-31
 */
public interface PurchaseListGoodsService extends IService<PurchaseListGoods> {
    String saveHandler(PurchaseListGoods purchaselistgoods);
    String saveHandler(List<PurchaseListGoods> purchaselistgoodsList);
    String modifyHandler(PurchaseListGoods purchaselistgoods);
    String modifyHandler(List<PurchaseListGoods> purchaselistgoodsList);
    String removeHandler(Integer id);
    String removeHandler(List<Integer> idList);
}
