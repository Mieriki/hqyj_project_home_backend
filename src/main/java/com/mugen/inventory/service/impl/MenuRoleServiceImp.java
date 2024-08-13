package com.mugen.inventory.service.impl;

import com.mugen.inventory.entity.MenuRole;
import com.mugen.inventory.mapper.MenuRoleMapper;
import com.mugen.inventory.service.MenuRoleService;
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
 * @since 2024-07-30
 */
@Service
@Transactional
public class MenuRoleServiceImp extends ServiceImpl<MenuRoleMapper, MenuRole> implements MenuRoleService {
    @Resource
    private MenuRoleMapper mapper;

    @Override
    public String saveHandler(MenuRole menurole) {
        if (this.save(menurole))
            return null;
        else
            return InventoryMessageConstant.SAVE_FAILURE_MESSAGE;
    }

    @Override
        public String saveHandler(List<MenuRole> menuroleList) {
        if (this.saveBatch(menuroleList))
            return null;
        else
          return InventoryMessageConstant.SAVE_FAILURE_MESSAGE;
    }

    @Override
    public String modifyHandler(MenuRole menurole) {
        if (this.updateById(menurole))
            return null;
        else
            return InventoryMessageConstant.MODIFY_FAILURE_MESSAGE;
    }

    @Override public String modifyHandler(List<MenuRole> menuroleList) {
        if (this.updateBatchById(menuroleList))
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
