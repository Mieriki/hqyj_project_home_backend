package com.mugen.inventory.mapper;

import com.mugen.inventory.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mugen.inventory.entity.model.dto.MenuDto;
import com.mugen.inventory.entity.model.dto.MenuEnabledDto;
import com.mugen.inventory.entity.model.vo.request.MenuQueryVo;
import com.mugen.inventory.entity.model.vo.response.MenuEnabledVo;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Mieriki
 * @since 2024-07-24
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {
    @Select("select * from t_menu where parent_id = -1;")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "url", column = "url"),
            @Result(property = "path", column = "path"),
            @Result(property = "component", column = "component"),
            @Result(property = "name", column = "name"),
            @Result(property = "iconCls", column = "icon_cls"),
            @Result(property = "parentId", column = "parent_id"),
            @Result(property = "enabled", column = "enabled"),
            @Result(property = "children", column = "id", javaType = List.class, many = @Many(select = "com.mugen.inventory.mapper.MenuMapper.selectMenuEnabledDtoByParentId"))
    })
    List<MenuEnabledVo> selectMenuEnabledVoAll();

    @Select("select * from t_menu where parent_id = -1 and name like concat('%',#{name},'%');")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "url", column = "url"),
            @Result(property = "path", column = "path"),
            @Result(property = "component", column = "component"),
            @Result(property = "name", column = "name"),
            @Result(property = "iconCls", column = "icon_cls"),
            @Result(property = "parentId", column = "parent_id"),
            @Result(property = "enabled", column = "enabled"),
            @Result(property = "children", column = "id", javaType = List.class, many = @Many(select = "com.mugen.inventory.mapper.MenuMapper.selectMenuEnabledDtoByParentId"))
    })
    List<MenuEnabledVo> selectPageVoAll(MenuQueryVo vo);
    @Select("select * from t_menu where parent_id = #{id} and enabled = 1;")
    List<MenuEnabledDto> selectMenuEnabledDtoByParentId(Integer id);

    @Select("select m.* from t_menu m inner join t_menu_role mr on m.id = mr.mid inner join t_role r on mr.rid = r.id where r.id = #{id};")
    List<Menu> selectMenuListByRoleId(Integer id);

    @Select("select * from t_menu m inner join t_menu_role mr on m.id = mr.mid inner join t_role r on mr.rid = r.id where r.id = #{id} and m.parent_id > 0 and enabled = 1;")
    List<Menu> selectMenuListChicked(Integer id);

    @Select("select r.id from t_role r inner join t_admin_role ar on r.id = ar.rid where adminId = #{id};")
    List<Integer> selectRoleIdByAdminId(Integer id);
}
