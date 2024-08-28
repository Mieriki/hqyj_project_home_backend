package com.mugen.inventory.service;

import com.mugen.inventory.entity.Admin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mugen.inventory.entity.model.vo.request.AdminQueryPageVo;
import com.mugen.inventory.entity.model.vo.response.AdminPageVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Mieriki
 * @since 2024-07-28
 */
public interface AdminService extends IService<Admin> {
    String saveHandler(Admin admin);
    String saveHandler(List<Admin> adminList);
    String modifyHandler(Admin admin);
    String modifyHandler(List<Admin> adminList);
    String removeHandler(Integer id);
    String removeHandler(List<Integer> idList);
    AdminPageVo queryPage(AdminQueryPageVo vo);
    List<String> queryRoleNamebYAdminId(Integer id);
}
