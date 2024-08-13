package com.mugen.inventory.mapper;

import com.mugen.inventory.entity.Admin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mugen.inventory.entity.model.vo.request.AdminQueryPageVo;
import org.apache.ibatis.annotations.Mapper;
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
public interface AdminMapper extends BaseMapper<Admin> {
    @Select("select * from t_admin where (name like concat('%',#{name},'%') or user_name like concat('%',#{name},'%')) and address like concat('%',#{address},'%') limit #{currentPage},#{pageSize}")
    List<Admin> selectPageLikeNameOrUsernameAndAddress(AdminQueryPageVo vo);

    @Select("select count(*) from t_admin where (name like concat('%',#{name},'%') or user_name like concat('%',#{name},'%')) and address like concat('%',#{address},'%')")
    Integer selectCountLikeNameOrUsernameAndAddress(AdminQueryPageVo vo);

    @Select("select name from t_role inner join t_admin_role ar on t_role.id = ar.rid where adminId = #{id};")
    List<String> selectRoleNamesByAdminId(Integer id);
}
