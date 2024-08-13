package com.mugen.inventory.service;

import com.mugen.inventory.entity.GoodsType;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mugen.inventory.entity.model.vo.response.GoodsTypeTreeVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Mieriki
 * @since 2024-07-31
 */
public interface GoodsTypeService extends IService<GoodsType> {
    String saveHandler(GoodsType goodstype);
    String saveHandler(List<GoodsType> goodstypeList);
    String modifyHandler(GoodsType goodstype);
    String modifyHandler(List<GoodsType> goodstypeList);
    String removeHandler(Integer id);
    String removeHandler(List<Integer> idList);
    List<GoodsTypeTreeVo> queryTree();
}
