package com.mugen.inventory.mapper;

import com.mugen.inventory.entity.Customer;
import com.mugen.inventory.entity.Supplier;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mugen.inventory.entity.model.vo.request.CustomerQueryPageVo;
import com.mugen.inventory.entity.model.vo.request.SupplierQueryPageVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Mieriki
 * @since 2024-07-30
 */
@Mapper
public interface SupplierMapper extends BaseMapper<Supplier> {
    @Select("select * from t_supplier where name like concat('%', #{name}, '%') and contact like concat('%', #{contact}, '%') and address like concat('%', #{address}, '%') and is_del = 0 limit #{currentPage}, #{pageSize};")
    List<Supplier> selectPageLikeNameAndAddress(SupplierQueryPageVo vo);

    @Select("select count(id) from t_supplier where name like concat('%', #{name}, '%') and contact like concat('%', #{contact}, '%') and address like concat('%', #{address}, '%') and is_del = 0")
    Integer selectCountLikeName(SupplierQueryPageVo vo);
}
