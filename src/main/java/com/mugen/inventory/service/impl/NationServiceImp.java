package com.mugen.inventory.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mugen.inventory.entity.Nation;
import com.mugen.inventory.entity.model.vo.request.NationQueryVo;
import com.mugen.inventory.entity.model.vo.response.NationPageVo;
import com.mugen.inventory.mapper.NationMapper;
import com.mugen.inventory.service.NationService;
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
 * @since 2024-08-16
 */
@Service
@Transactional
public class NationServiceImp extends ServiceImpl<NationMapper, Nation> implements NationService {
    @Resource
    private NationMapper mapper;

    @Override
    public String saveHandler(Nation nation) {
        if (this.save(nation))
            return null;
        else
            return InventoryMessageConstant.SAVE_FAILURE_MESSAGE;
    }

    @Override
        public String saveHandler(List<Nation> nationList) {
        if (this.saveBatch(nationList))
            return null;
        else
          return InventoryMessageConstant.SAVE_FAILURE_MESSAGE;
    }

    @Override
    public String modifyHandler(Nation nation) {
        if (this.updateById(nation))
            return null;
        else
            return InventoryMessageConstant.MODIFY_FAILURE_MESSAGE;
    }

    @Override public String modifyHandler(List<Nation> nationList) {
        if (this.updateBatchById(nationList))
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
    public NationPageVo queryPage(NationQueryVo vo) {
        QueryWrapper<Nation> wrapper = new QueryWrapper<>();
        if (vo.getName() != null && !vo.getName().equals(""))
            wrapper.like("name",vo.getName());
        return new NationPageVo(mapper.selectCount(wrapper), mapper.selectPage(new Page<>(vo.getCurrentPage(),vo.getPageSize()),wrapper).getRecords());
    }
}
