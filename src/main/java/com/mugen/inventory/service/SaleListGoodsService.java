package com.mugen.inventory.service;

import com.mugen.inventory.entity.SaleListGoods;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Mieriki
 * @since 2024-08-01
 */
public interface SaleListGoodsService extends IService<SaleListGoods> {
    String saveHandler(SaleListGoods salelistgoods);
    String saveHandler(List<SaleListGoods> salelistgoodsList);
    String modifyHandler(SaleListGoods salelistgoods);
    String modifyHandler(List<SaleListGoods> salelistgoodsList);
    String removeHandler(Integer id);
    String removeHandler(List<Integer> idList);
}
