package com.mugen.inventory.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mugen.inventory.entity.Admin;
import com.mugen.inventory.entity.model.vo.request.AdminQueryPageVo;
import com.mugen.inventory.entity.model.vo.response.AdminPageVo;
import com.mugen.inventory.mapper.AdminMapper;
import com.mugen.inventory.service.AdminService;
import com.mugen.inventory.utils.HostHolder;
import com.mugen.inventory.utils.ParameterUtils;
import com.mugen.inventory.utils.constant.InventoryMessageConstant;
import jakarta.annotation.Resource;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
@Log4j2
@Service
@Transactional
public class AdminServiceImp extends ServiceImpl<AdminMapper, Admin> implements AdminService, UserDetailsService {
    @Resource
    private AdminMapper mapper;

    @Resource
    private HostHolder hostHolder;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Admin admin = mapper.selectOne(new QueryWrapper<Admin>().eq("user_name", userName));
        if (admin == null) throw new UsernameNotFoundException("用户名或密码错误");
        hostHolder.setAccount(admin);
        return User
                .withUsername(userName)
                .password(admin.getPassword())
                .roles(mapper.selectRoleNamesByAdminId(admin.getId()).toArray(new String[]{}))
                .disabled(!admin.getEnabled())
                .build();
    }

    @Override
    public String saveHandler(Admin admin) {
        if (mapper.selectCount(new QueryWrapper<Admin>().eq("user_name", admin.getUserName())) > 0) throw new InternalAuthenticationServiceException("用户名已存在");
        if (this.save(admin))
            return null;
        else
            return InventoryMessageConstant.SAVE_FAILURE_MESSAGE;
    }

    @Override
        public String saveHandler(List<Admin> adminList) {
        if (mapper.selectCount(new QueryWrapper<Admin>().in("user_name", adminList.stream().map(Admin::getUserName).toList())) > 0) throw new InternalAuthenticationServiceException("用户名已存在");
        if (this.saveBatch(adminList))
            return null;
        else
          return InventoryMessageConstant.SAVE_FAILURE_MESSAGE;
    }

    @Override
    public String modifyHandler(Admin admin) {
        if (this.updateById(admin))
            return null;
        else
            return InventoryMessageConstant.MODIFY_FAILURE_MESSAGE;
    }

    @Override public String modifyHandler(List<Admin> adminList) {
        if (this.updateBatchById(adminList))
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
    public AdminPageVo queryPage(AdminQueryPageVo vo) {
        vo.setCurrentPage(ParameterUtils.getCurrentPage(vo.getCurrentPage(), vo.getPageSize()));
        return new AdminPageVo(mapper.selectCountLikeNameOrUsernameAndAddress(vo), mapper.selectPageLikeNameOrUsernameAndAddress(vo));
    }

    @Override
    public List<String> queryRoleNamebYAdminId(Integer id) {
        return mapper.selectRoleNamesByAdminId(id);
    }
}
