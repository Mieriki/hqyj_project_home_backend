package com.mugen.inventory.service.impl;

import com.mugen.inventory.entity.SaleListGoods;
import com.mugen.inventory.mapper.SaleListGoodsMapper;
import com.mugen.inventory.service.SaleListGoodsService;
import com.mugen.inventory.utils.constant.InventoryMessageConstant;
import jakarta.annotation.Resource;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Mieriki
 * @since 2024-08-01
 */
@Service
@Transactional
public class SaleListGoodsServiceImp extends ServiceImpl<SaleListGoodsMapper, SaleListGoods> implements SaleListGoodsService {
    @Resource
    private SaleListGoodsMapper mapper;

    @Override
    public String saveHandler(SaleListGoods salelistgoods) {
        if (this.save(salelistgoods))
            return null;
        else
            return InventoryMessageConstant.SAVE_FAILURE_MESSAGE;
    }

    @Override
        public String saveHandler(List<SaleListGoods> salelistgoodsList) {
        if (this.saveBatch(salelistgoodsList))
            return null;
        else
          return InventoryMessageConstant.SAVE_FAILURE_MESSAGE;
    }

    @Override
    public String modifyHandler(SaleListGoods salelistgoods) {
        if (this.updateById(salelistgoods))
            return null;
        else
            return InventoryMessageConstant.MODIFY_FAILURE_MESSAGE;
    }

    @Override public String modifyHandler(List<SaleListGoods> salelistgoodsList) {
        if (this.updateBatchById(salelistgoodsList))
            return null;
        else
            return InventoryMessageConstant.MODIFY_FAILURE_MESSAGE;
    }

    @Override
    public String removeHandler(Integer id) {
        if (this.removeById(id))
            return null;
        else
            return InventoryMessageConstant.REMOVE_FAILURE_MESSAGE;
    }

    @Override
    public String removeHandler(List<Integer> idList) {
        if (this.removeByIds(idList))
            return null;
        else
            return InventoryMessageConstant.REMOVE_FAILURE_MESSAGE;
    }
}
