package com.mugen.inventory.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mugen.inventory.entity.Goods;
import com.mugen.inventory.entity.GoodsUnit;
import com.mugen.inventory.entity.model.vo.request.GoodsQueryPageVo;
import com.mugen.inventory.entity.model.vo.response.GoodsPageVo;
import com.mugen.inventory.mapper.GoodsMapper;
import com.mugen.inventory.service.GoodsService;
import com.mugen.inventory.service.GoodsUnitService;
import com.mugen.inventory.utils.ParameterUtils;
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
public class GoodsServiceImp extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {
    @Resource
    private GoodsMapper mapper;

    @Resource
    private GoodsUnitService unitService;

    @Override
    public String saveHandler(Goods goods) {
        if (unitService.count(new QueryWrapper<GoodsUnit>().eq("name", goods.getUnit())) < 1)
            unitService.save(GoodsUnit.builder().name(goods.getUnit()).build());
        goods.setCode(String.format("%011d", System.currentTimeMillis() % 100000000000L))
                .setInventoryQuantity(0)
                .setLastPurchasingPrice(0.0);
        if (this.save(goods))
            return null;
        else
            return InventoryMessageConstant.SAVE_FAILURE_MESSAGE;
    }

    @Override
        public String saveHandler(List<Goods> goodsList) {
        if (this.saveBatch(goodsList))
            return null;
        else
          return InventoryMessageConstant.SAVE_FAILURE_MESSAGE;
    }

    @Override
    public String modifyHandler(Goods goods) {
        goods.setLastPurchasingPrice(mapper.selectById(goods.getId()).getPurchasingPrice());
        if (this.updateById(goods))
            return null;
        else
            return InventoryMessageConstant.MODIFY_FAILURE_MESSAGE;
    }

    @Override public String modifyHandler(List<Goods> goodsList) {
        if (this.updateBatchById(goodsList))
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

    @Override
    public GoodsPageVo queryPage(GoodsQueryPageVo vo) {
        vo.setCurrentPage(ParameterUtils.getCurrentPage(vo.getCurrentPage(), vo.getPageSize()));
        vo.setIdList(vo.getTypeId().toString().substring(1,vo.getTypeId().toString().length() - 1));
        return new GoodsPageVo(mapper.selectCountLikeNameAndProducerByTypeId(vo), mapper.selectPage(vo));
    }

    @Override
    public GoodsPageVo queryStorePage(GoodsQueryPageVo vo) {
        vo.setCurrentPage(ParameterUtils.getCurrentPage(vo.getCurrentPage(), vo.getPageSize()));
        return new GoodsPageVo(mapper.selectStoreCountLikeNameAndProducerByTypeId(vo), mapper.selectStorePage(vo));
    }

    @Override
    public GoodsPageVo queryInstallPage(GoodsQueryPageVo vo) {
        vo.setCurrentPage(ParameterUtils.getCurrentPage(vo.getCurrentPage(), vo.getPageSize()));
        return new GoodsPageVo(mapper.selectInstallCountLikeNameAndProducerByTypeId(vo), mapper.selectInstallPage(vo));
    }

    @Override
    public List<Goods> queryCard() {
        return mapper.selectCard();
    }
}
