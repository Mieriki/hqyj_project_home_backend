package com.mugen.inventory.service;

import com.mugen.inventory.entity.Goods;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mugen.inventory.entity.model.vo.request.GoodsQueryPageVo;
import com.mugen.inventory.entity.model.vo.response.GoodsPageVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Mieriki
 * @since 2024-07-31
 */
public interface GoodsService extends IService<Goods> {
    String saveHandler(Goods goods);
    String saveHandler(List<Goods> goodsList);
    String modifyHandler(Goods goods);
    String modifyHandler(List<Goods> goodsList);
    String removeHandler(Integer id);
    String removeHandler(List<Integer> idList);
    GoodsPageVo queryPage(GoodsQueryPageVo vo);
    GoodsPageVo queryStorePage(GoodsQueryPageVo vo);
    GoodsPageVo queryInstallPage(GoodsQueryPageVo vo);
    List<Goods> queryCard();
}
