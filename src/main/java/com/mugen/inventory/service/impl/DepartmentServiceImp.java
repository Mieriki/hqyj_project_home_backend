package com.mugen.inventory.service.impl;

import com.mugen.inventory.entity.Department;
import com.mugen.inventory.entity.model.vo.response.DepartmentTreeVo;
import com.mugen.inventory.mapper.DepartmentMapper;
import com.mugen.inventory.service.DepartmentService;
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
 * @since 2024-08-14
 */
@Service
@Transactional
public class DepartmentServiceImp extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {
    @Resource
    private DepartmentMapper mapper;

    @Override
    public String saveHandler(Department department) {
        if (this.save(department))
            return null;
        else
            return InventoryMessageConstant.SAVE_FAILURE_MESSAGE;
    }

    @Override
        public String saveHandler(List<Department> departmentList) {
        if (this.saveBatch(departmentList))
            return null;
        else
          return InventoryMessageConstant.SAVE_FAILURE_MESSAGE;
    }

    @Override
    public String modifyHandler(Department department) {
        if (this.updateById(department))
            return null;
        else
            return InventoryMessageConstant.MODIFY_FAILURE_MESSAGE;
    }

    @Override public String modifyHandler(List<Department> departmentList) {
        if (this.updateBatchById(departmentList))
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
    public List<DepartmentTreeVo> queryTree() {
        List<DepartmentTreeVo> departmentTreeVoList = mapper.selectList(null)
                .stream().map(department -> department.asViewObject(DepartmentTreeVo.class)).toList();
        return null;
    }
}
