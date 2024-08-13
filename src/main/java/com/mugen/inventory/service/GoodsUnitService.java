package com.mugen.inventory.service;

import com.mugen.inventory.entity.GoodsUnit;
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
public interface GoodsUnitService extends IService<GoodsUnit> {
    String saveHandler(GoodsUnit goodsunit);
    String saveHandler(List<GoodsUnit> goodsunitList);
    String modifyHandler(GoodsUnit goodsunit);
    String modifyHandler(List<GoodsUnit> goodsunitList);
    String removeHandler(Integer id);
    String removeHandler(List<Integer> idList);
}
