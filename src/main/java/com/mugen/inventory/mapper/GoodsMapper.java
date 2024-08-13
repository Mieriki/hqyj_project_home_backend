package com.mugen.inventory.mapper;

import com.mugen.inventory.entity.Goods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mugen.inventory.entity.model.vo.request.GoodsQueryPageVo;
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
public interface GoodsMapper extends BaseMapper<Goods> {
    @Select("select * from t_goods g inner join t_goods_type gt on g.type_id = gt.id where g.type_id in (${idList}) and g.name like concat('%', #{name}, '%') and g.producer like concat('%', #{producer}, '%') order by g.id limit #{currentPage}, #{pageSize};")
    List<Goods> selectPage(GoodsQueryPageVo vo);

    @Select("select count(g.id) from t_goods g inner join t_goods_type gt on g.type_id = gt.id where g.type_id in (${idList}) and g.name like concat('%', #{name}, '%') and g.producer like concat('%', #{producer}, '%') order by g.id;")
    Integer selectCountLikeNameAndProducerByTypeId(GoodsQueryPageVo vo);

    @Select("select * from t_goods g inner join t_goods_type gt on g.type_id = gt.id where g.name like concat('%', #{name}, '%') and g.producer like concat('%', #{producer}, '%') and g.inventory_quantity > 0 order by g.id limit #{currentPage}, #{pageSize};")
    List<Goods> selectStorePage(GoodsQueryPageVo vo);

    @Select("select count(g.id) from t_goods g inner join t_goods_type gt on g.type_id = gt.id where g.name like concat('%', #{name}, '%') and g.producer like concat('%', #{producer}, '%') and g.inventory_quantity > 0 order by g.id;")
    Integer selectStoreCountLikeNameAndProducerByTypeId(GoodsQueryPageVo vo);

    @Select("select * from t_goods g inner join t_goods_type gt on g.type_id = gt.id where g.name like concat('%', #{name}, '%') and g.producer like concat('%', #{producer}, '%') order by g.id limit #{currentPage}, #{pageSize};")
    List<Goods> selectInstallPage(GoodsQueryPageVo vo);

    @Select("select count(g.id) from t_goods g inner join t_goods_type gt on g.type_id = gt.id where g.name like concat('%', #{name}, '%') and g.producer like concat('%', #{producer}, '%') > 0 order by g.id;")
    Integer selectInstallCountLikeNameAndProducerByTypeId(GoodsQueryPageVo vo);

    @Select("select * from t_goods order by inventory_quantity desc limit 0, 10;")
    List<Goods> selectCard();
}
