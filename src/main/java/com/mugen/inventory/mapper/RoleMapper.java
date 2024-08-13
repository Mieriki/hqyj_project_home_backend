package com.mugen.inventory.mapper;

import com.mugen.inventory.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mugen.inventory.entity.model.vo.request.RoleQueryPageVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Mieriki
 * @since 2024-07-28
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    @Select("select r.* from t_role r inner join t_admin_role ar on r.id = ar.rid where adminId = #{id};")
    List<Role> selectByAdminId(Integer id);

    @Select("select * from t_role where name like concat('%', #{name}, '%') or nameZh like concat('%', #{name}, '%') limit #{currentPage}, #{pageSize};")
    List<Role> selectPageLikeNameAndNameZh(RoleQueryPageVo vo);

    @Select("select count(id) from t_role where name like concat('%', #{name}, '%') or nameZh like concat('%', #{name}, '%')")
    Integer selectCountLikeNameAndNameZh(RoleQueryPageVo vo);
}
