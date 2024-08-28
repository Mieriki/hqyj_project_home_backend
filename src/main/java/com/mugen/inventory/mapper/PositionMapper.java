package com.mugen.inventory.mapper;

import com.mugen.inventory.entity.Position;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mugen.inventory.entity.model.vo.response.PosTbVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Mieriki
 * @since 2024-08-14
 */
@Mapper
public interface PositionMapper extends BaseMapper<Position> {
    @Select("select p.name, count(p.name) value from t_position p right join t_employee e on p.id = e.posId where p.enabled = 1 and is_delete = 0 group by p.name;")
    List<PosTbVo> selectPosTbList();
}
