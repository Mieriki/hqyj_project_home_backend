package com.mugen.inventory.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mugen.inventory.entity.Menu;
import com.mugen.inventory.entity.MenuRole;
import com.mugen.inventory.entity.model.dto.MenuDto;
import com.mugen.inventory.entity.model.vo.request.MenuQueryVo;
import com.mugen.inventory.entity.model.vo.request.RoleMenuModifyVo;
import com.mugen.inventory.entity.model.vo.response.MenuEnabledVo;
import com.mugen.inventory.mapper.MenuMapper;
import com.mugen.inventory.mapper.MenuRoleMapper;
import com.mugen.inventory.service.MenuService;
import com.mugen.inventory.utils.constant.InventoryMessageConstant;
import jakarta.annotation.Resource;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Mieriki
 * @since 2024-07-24
 */
@Log4j2
@Service
@Transactional
public class MenuServiceImp extends ServiceImpl<MenuMapper, Menu> implements MenuService {
    @Resource
    private MenuMapper mapper;

    @Resource
    private MenuRoleMapper menuRoleMapper;

    @Override
    public List<MenuEnabledVo> queryRouterByAdminId(Integer id) {
        List<Integer> roleIdList = mapper.selectRoleIdByAdminId(id);
        List<MenuEnabledVo> menuEnabledVoList = mapper.selectMenuEnabledVoAll();
        List<Menu> menuList = new ArrayList<>();
        roleIdList.forEach(roleId -> {
            menuList.addAll(mapper.selectMenuListByRoleId(roleId));
        });
        Set<Integer> menuIds = menuList.stream().distinct()
                .map(Menu::getId)
                .collect(Collectors.toSet());
        menuEnabledVoList.forEach(vo -> {
            vo.setLeaf(menuIds.contains(vo.getId()));
            vo.getChildren().forEach(child -> {
                child.setLeaf(menuIds.contains(child.getId()));
            });
        });
        return menuEnabledVoList;
    }

    @Override
    public String saveHandler(Menu menu) {
        if (this.save(menu))
            return null;
        else
            return InventoryMessageConstant.SAVE_FAILURE_MESSAGE;
    }

    @Override
    public String modifyHandler(Menu menu) {
        if (this.updateById(menu))
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
    public List<MenuEnabledVo> queryTree(Integer id) {
        List<MenuEnabledVo> menuEnabledVoList = mapper.selectMenuEnabledVoAll();
        List<Menu> menuList = mapper.selectMenuListByRoleId(id);
        Set<Integer> menuIds = menuList.stream()
                .map(Menu::getId)
                .collect(Collectors.toSet());
        menuEnabledVoList.forEach(vo -> {
            vo.setLeaf(menuIds.contains(vo.getId()));
            vo.getChildren().forEach(child -> {
                child.setLeaf(menuIds.contains(child.getId()));
            });
        });

        return menuEnabledVoList;
    }

    @Override
    public List<MenuEnabledVo> queryTree(MenuQueryVo vo) {
        return mapper.selectPageVoAll(vo);
    }


    @Override
    public List<Integer> queryMenuListChicked(Integer id) {
        return mapper.selectMenuListChicked(id).stream().map(Menu::getId).toList();
    }

    @Override
    public String modifyTreeHandler(RoleMenuModifyVo vo) {
        List<Menu> menuList = mapper.selectList(null);
        Set<Integer> menuIds = new HashSet<>();
        Map<Integer, Integer> menuMap = menuList.stream()
                .collect(Collectors.toMap(Menu::getId, Menu::getParentId));
        menuRoleMapper.delete(new QueryWrapper<MenuRole>().eq("rid", vo.getId()));
        vo.getMenuIdList().forEach(menuId -> {
            if (menuMap.get(menuId) > 0) {
                menuRoleMapper.insert(MenuRole.builder().mid(menuId).rid(vo.getId()).build());
                if (menuMap.get(menuId) > 0) {
                    menuIds.add(menuMap.get(menuId));
                }
            }
        });
        menuIds.forEach(menuId -> {
            menuRoleMapper.insert(MenuRole.builder().mid(menuId).rid(vo.getId()).build());
        });
        return null;
    }
}
