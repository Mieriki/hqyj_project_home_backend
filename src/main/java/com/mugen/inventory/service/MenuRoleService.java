package com.mugen.inventory.service;

import com.mugen.inventory.entity.MenuRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Mieriki
 * @since 2024-07-30
 */
public interface MenuRoleService extends IService<MenuRole> {
    String saveHandler(MenuRole menurole);
    String saveHandler(List<MenuRole> menuroleList);
    String modifyHandler(MenuRole menurole);
    String modifyHandler(List<MenuRole> menuroleList);
    String removeHandler(Integer id);
    String removeHandler(List<Integer> idList);
}
