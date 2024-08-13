package com.mugen.inventory.mapper;

import com.mugen.inventory.entity.Customer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mugen.inventory.entity.model.vo.request.CustomerQueryPageVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Mieriki
 * @since 2024-07-25
 */
@Mapper
public interface CustomerMapper extends BaseMapper<Customer> {
    @Select("select * from t_customer where name like concat('%', #{name}, '%') and contact like concat('%', #{contact}, '%') and address like concat('%', #{address}, '%') and is_del = 0 limit #{currentPage}, #{pageSize};")
    List<Customer> selectPageLikeNameAndAddress(CustomerQueryPageVo vo);

    @Select("select count(id) from t_customer where name like concat('%', #{name}, '%') and contact like concat('%', #{contact}, '%') and address like concat('%', #{address}, '%') and is_del = 0")
    Integer selectCountLikeName(CustomerQueryPageVo vo);
}
