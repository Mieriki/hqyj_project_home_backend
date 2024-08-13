package com.mugen.inventory.service;

import com.mugen.inventory.entity.AdminRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Mieriki
 * @since 2024-07-28
 */
public interface AdminRoleService extends IService<AdminRole> {
    String saveHandler(AdminRole adminrole);
    String saveHandler(List<AdminRole> adminroleList);
    String modifyHandler(AdminRole adminrole);
    String modifyHandler(List<AdminRole> adminroleList);
    String removeHandler(Integer id);
    String removeHandler(List<Integer> idList);
}
