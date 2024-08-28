package com.mugen.inventory.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mugen.inventory.entity.Salary;
import com.mugen.inventory.entity.model.vo.request.SalaryQueryVo;
import com.mugen.inventory.entity.model.vo.response.SalaryPageVo;

import com.mugen.inventory.mapper.SalaryMapper;
import com.mugen.inventory.service.SalaryService;
import com.mugen.inventory.utils.constant.InventoryMessageConstant;
import jakarta.annotation.Resource;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Mieriki
 * @since 2024-08-20
 */
@Service
@Transactional
public class SalaryServiceImp extends ServiceImpl<SalaryMapper, Salary> implements SalaryService {
    @Resource
    private SalaryMapper mapper;

    @Override
    public String saveHandler(Salary salary) {
        salary.setCreateDate(new Date());
        if (this.save(salary))
            return null;
        else
            return InventoryMessageConstant.SAVE_FAILURE_MESSAGE;
    }

    @Override
        public String saveHandler(List<Salary> salaryList) {
        salaryList.forEach(salary -> salary.setCreateDate(new Date()));
        if (this.saveBatch(salaryList))
            return null;
        else
          return InventoryMessageConstant.SAVE_FAILURE_MESSAGE;
    }

    @Override
    public String modifyHandler(Salary salary) {
        if (this.updateById(salary))
            return null;
        else
            return InventoryMessageConstant.MODIFY_FAILURE_MESSAGE;
    }

    @Override public String modifyHandler(List<Salary> salaryList) {
        if (this.updateBatchById(salaryList))
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
    public SalaryPageVo queryPage(SalaryQueryVo vo) {
        QueryWrapper<Salary> wrapper = new QueryWrapper<>();
        if (vo.getName()!= null && !vo.getName().equals(""))
            wrapper.like("name",vo.getName());
        Long count = mapper.selectCount(wrapper);
        List<Salary> salaryList = mapper.selectPage(new Page<Salary>(vo.getCurrentPage(), vo.getPageSize()), wrapper).getRecords();
        return new SalaryPageVo(count, salaryList);
    }
}
