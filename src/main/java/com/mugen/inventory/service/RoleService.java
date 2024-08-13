package com.mugen.inventory.service;

import com.mugen.inventory.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mugen.inventory.entity.model.vo.request.RoleQueryPageVo;
import com.mugen.inventory.entity.model.vo.response.RoleOrganizeVo;
import com.mugen.inventory.entity.model.vo.response.RolePageVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Mieriki
 * @since 2024-07-28
 */
public interface RoleService extends IService<Role> {
    String saveHandler(Role role);
    String saveHandler(List<Role> roleList);
    String modifyHandler(Role role);
    String modifyHandler(List<Role> roleList);
    String removeHandler(Integer id);
    String removeHandler(List<Integer> idList);
    RoleOrganizeVo queryRoleOrganizeByUserId(Integer id);
    String organizeHandler(Integer id, List<Integer> idList);
    RolePageVo queryPage(RoleQueryPageVo vo);
}
