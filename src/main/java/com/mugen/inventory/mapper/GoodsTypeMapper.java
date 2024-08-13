package com.mugen.inventory.mapper;

import com.mugen.inventory.entity.GoodsType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mugen.inventory.entity.model.vo.response.GoodsTypeTreeVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Mieriki
 * @since 2024-07-31
 */
@Mapper
public interface GoodsTypeMapper extends BaseMapper<GoodsType> {
    @Select("select * from t_goods_type order by p_id")
    List<GoodsTypeTreeVo> selectTree();
}
