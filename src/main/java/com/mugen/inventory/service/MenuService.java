package com.mugen.inventory.service;

import com.mugen.inventory.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mugen.inventory.entity.model.vo.request.MenuQueryVo;
import com.mugen.inventory.entity.model.vo.request.RoleMenuModifyVo;
import com.mugen.inventory.entity.model.vo.response.MenuEnabledVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Mieriki
 * @since 2024-07-24
 */
public interface MenuService extends IService<Menu> {
    List<MenuEnabledVo> queryRouterByAdminId(Integer id);
    String saveHandler(Menu menu);
    String modifyHandler(Menu menu);
    String removeHandler(Integer id);
    String removeHandler(List<Integer> idList);
    List<MenuEnabledVo> queryTree(Integer id);
    List<MenuEnabledVo> queryTree(MenuQueryVo vo);
    List<Integer> queryMenuListChicked(Integer id);
    String modifyTreeHandler(RoleMenuModifyVo vo);

}
