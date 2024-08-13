package com.mugen.inventory.service.impl;

import com.mugen.inventory.entity.PurchaseListGoods;
import com.mugen.inventory.mapper.PurchaseListGoodsMapper;
import com.mugen.inventory.service.PurchaseListGoodsService;
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
 * @since 2024-07-31
 */
@Service
@Transactional
public class PurchaseListGoodsServiceImp extends ServiceImpl<PurchaseListGoodsMapper, PurchaseListGoods> implements PurchaseListGoodsService {
    @Resource
    private PurchaseListGoodsMapper mapper;

    @Override
    public String saveHandler(PurchaseListGoods purchaselistgoods) {
        if (this.save(purchaselistgoods))
            return null;
        else
            return InventoryMessageConstant.SAVE_FAILURE_MESSAGE;
    }

    @Override
        public String saveHandler(List<PurchaseListGoods> purchaselistgoodsList) {
        if (this.saveBatch(purchaselistgoodsList))
            return null;
        else
          return InventoryMessageConstant.SAVE_FAILURE_MESSAGE;
    }

    @Override
    public String modifyHandler(PurchaseListGoods purchaselistgoods) {
        if (this.updateById(purchaselistgoods))
            return null;
        else
            return InventoryMessageConstant.MODIFY_FAILURE_MESSAGE;
    }

    @Override public String modifyHandler(List<PurchaseListGoods> purchaselistgoodsList) {
        if (this.updateBatchById(purchaselistgoodsList))
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
