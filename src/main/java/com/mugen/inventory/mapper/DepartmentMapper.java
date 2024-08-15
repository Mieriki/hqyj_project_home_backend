package com.mugen.inventory.mapper;

import com.mugen.inventory.entity.Department;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mugen.inventory.entity.model.vo.request.DepartmentDeleteVo;
import com.mugen.inventory.entity.model.vo.request.DepartmentSaveVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Mieriki
 * @since 2024-08-14
 */
@Mapper
public interface DepartmentMapper extends BaseMapper<Department> {
    @Select("{CALL addDep(#{depName, mode=IN}, #{parentId, mode=IN}, #{enabled, mode=IN}, #{result, mode=OUT, jdbcType=INTEGER}, #{result2, mode=OUT, jdbcType=INTEGER})}")
    @Options(statementType = StatementType.CALLABLE)
    void instertOption(DepartmentSaveVo vo);

    @Select("{CALL deleteDep(#{id, mode=IN}, #{result, mode=OUT, jdbcType=INTEGER})}")
    @Options(statementType = StatementType.CALLABLE)
    void deleteOption(DepartmentDeleteVo vo);
}
