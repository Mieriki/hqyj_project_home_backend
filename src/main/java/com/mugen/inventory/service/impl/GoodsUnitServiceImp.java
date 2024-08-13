package com.mugen.inventory.service.impl;

import com.mugen.inventory.entity.GoodsUnit;
import com.mugen.inventory.mapper.GoodsUnitMapper;
import com.mugen.inventory.service.GoodsUnitService;
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
public class GoodsUnitServiceImp extends ServiceImpl<GoodsUnitMapper, GoodsUnit> implements GoodsUnitService {
    @Resource
    private GoodsUnitMapper mapper;

    @Override
    public String saveHandler(GoodsUnit goodsunit) {
        if (this.save(goodsunit))
            return null;
        else
            return InventoryMessageConstant.SAVE_FAILURE_MESSAGE;
    }

    @Override
        public String saveHandler(List<GoodsUnit> goodsunitList) {
        if (this.saveBatch(goodsunitList))
            return null;
        else
          return InventoryMessageConstant.SAVE_FAILURE_MESSAGE;
    }

    @Override
    public String modifyHandler(GoodsUnit goodsunit) {
        if (this.updateById(goodsunit))
            return null;
        else
            return InventoryMessageConstant.MODIFY_FAILURE_MESSAGE;
    }

    @Override public String modifyHandler(List<GoodsUnit> goodsunitList) {
        if (this.updateBatchById(goodsunitList))
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
