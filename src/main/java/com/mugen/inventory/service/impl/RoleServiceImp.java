package com.mugen.inventory.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mugen.inventory.entity.AdminRole;
import com.mugen.inventory.entity.Role;
import com.mugen.inventory.entity.model.vo.request.RoleQueryPageVo;
import com.mugen.inventory.entity.model.vo.response.RoleOrganizeVo;
import com.mugen.inventory.entity.model.vo.response.RolePageVo;
import com.mugen.inventory.mapper.RoleMapper;
import com.mugen.inventory.service.AdminRoleService;
import com.mugen.inventory.service.RoleService;
import com.mugen.inventory.utils.ParameterUtils;
import com.mugen.inventory.utils.constant.InventoryMessageConstant;
import jakarta.annotation.Resource;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Mieriki
 * @since 2024-07-28
 */
@Log4j2
@Service
@Transactional
public class RoleServiceImp extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Resource
    private RoleMapper mapper;

    @Resource
    private AdminRoleService adminRoleService;

    @Override
    public String saveHandler(Role role) {
        if (this.save(role))
            return null;
        else
            return InventoryMessageConstant.SAVE_FAILURE_MESSAGE;
    }

    @Override
        public String saveHandler(List<Role> roleList) {
        if (this.saveBatch(roleList))
            return null;
        else
          return InventoryMessageConstant.SAVE_FAILURE_MESSAGE;
    }

    @Override
    public String modifyHandler(Role role) {
        if (this.updateById(role))
            return null;
        else
            return InventoryMessageConstant.MODIFY_FAILURE_MESSAGE;
    }

    @Override public String modifyHandler(List<Role> roleList) {
        if (this.updateBatchById(roleList))
            return null;
        else
            return InventoryMessageConstant.MODIFY_FAILURE_MESSAGE;
    }

    @Override
    public String removeHandler(Integer id) {
        if (this.removeById(id))
            return null;
        else
            return InventoryMessageConstant.REMOVE_FAILURE_MESSAGE;
    }

    @Override
    public String removeHandler(List<Integer> idList) {
        if (this.removeByIds(idList))
            return null;
        else
            return InventoryMessageConstant.REMOVE_FAILURE_MESSAGE;
    }

    @Override
    public RoleOrganizeVo queryRoleOrganizeByUserId(Integer id) {
        return new RoleOrganizeVo(mapper.selectByAdminId(id), mapper.selectList(new QueryWrapper<Role>()));
    }

    @Override
    public String organizeHandler(Integer id, List<Integer> idList) {
        List<Role> organizeList = mapper.selectByAdminId(id);
        Set<Integer> organizeSet = new HashSet<>(idList);
        idList.forEach(roleId -> {
            if (organizeList.stream().noneMatch(role -> role.getId().equals(roleId))) {
                adminRoleService.save(AdminRole.builder()
                       .adminId(id)
                       .rid(roleId)
                       .build());
            }
        });
        organizeList.stream().map(Role::getId).filter(roleId -> !organizeSet.contains(roleId)).toList().forEach(roleId -> {
            adminRoleService.remove(new QueryWrapper<AdminRole>()
                   .eq("adminId", id)
                   .eq("rid", roleId));
        });
        return null;
    }

    @Override
    public RolePageVo queryPage(RoleQueryPageVo vo) {
        vo.setCurrentPage(ParameterUtils.getCurrentPage(vo.getCurrentPage(), vo.getPageSize()));
        return new RolePageVo(mapper.selectCountLikeNameAndNameZh(vo), mapper.selectPageLikeNameAndNameZh(vo));
    }
}
