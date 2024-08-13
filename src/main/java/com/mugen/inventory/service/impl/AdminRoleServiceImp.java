package com.mugen.inventory.service.impl;

import com.mugen.inventory.entity.AdminRole;
import com.mugen.inventory.mapper.AdminRoleMapper;
import com.mugen.inventory.service.AdminRoleService;
import com.mugen.inventory.utils.constant.InventoryMessageConstant;
import jakarta.annotation.Resource;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Mieriki
 * @since 2024-07-28
 */
@Service
@Transactional
public class AdminRoleServiceImp extends ServiceImpl<AdminRoleMapper, AdminRole> implements AdminRoleService {
    @Resource
    private AdminRoleMapper mapper;

    @Override
    public String saveHandler(AdminRole adminrole) {
        if (this.save(adminrole))
            return null;
        else
            return InventoryMessageConstant.SAVE_FAILURE_MESSAGE;
    }

    @Override
        public String saveHandler(List<AdminRole> adminroleList) {
        if (this.saveBatch(adminroleList))
            return null;
        else
          return InventoryMessageConstant.SAVE_FAILURE_MESSAGE;
    }

    @Override
    public String modifyHandler(AdminRole adminrole) {
        if (this.updateById(adminrole))
            return null;
        else
            return InventoryMessageConstant.MODIFY_FAILURE_MESSAGE;
    }

    @Override public String modifyHandler(List<AdminRole> adminroleList) {
        if (this.updateBatchById(adminroleList))
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
}
