package com.mugen.inventory.service;

import com.mugen.inventory.entity.Nation;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mugen.inventory.entity.model.vo.request.NationQueryVo;
import com.mugen.inventory.entity.model.vo.response.NationPageVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Mieriki
 * @since 2024-08-16
 */
public interface NationService extends IService<Nation> {
    String saveHandler(Nation nation);
    String saveHandler(List<Nation> nationList);
    String modifyHandler(Nation nation);
    String modifyHandler(List<Nation> nationList);
    String removeHandler(Integer id);
    String removeHandler(List<Integer> idList);
    NationPageVo queryPage(NationQueryVo vo);
}
