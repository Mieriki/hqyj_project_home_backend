package com.mugen.inventory.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mugen.inventory.entity.Department;
import com.mugen.inventory.entity.Employee;
import com.mugen.inventory.entity.Nation;
import com.mugen.inventory.entity.Position;
import com.mugen.inventory.entity.model.vo.request.EmployeeQueryVo;
import com.mugen.inventory.entity.model.vo.request.PositionQueryVo;
import com.mugen.inventory.entity.model.vo.response.EmployeePageVo;
import com.mugen.inventory.entity.model.vo.response.EmployeeVo;
import com.mugen.inventory.mapper.EmployeeMapper;
import com.mugen.inventory.service.DepartmentService;
import com.mugen.inventory.service.EmployeeService;
import com.mugen.inventory.service.NationService;
import com.mugen.inventory.service.PositionService;
import com.mugen.inventory.utils.constant.InventoryMessageConstant;
import jakarta.annotation.Resource;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
public class EmployeeServiceImp extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
    @Resource
    private EmployeeMapper mapper;

    @Resource
    private PositionService positionService;

    @Resource
    private DepartmentService departmentService;

    @Resource
    private NationService nationService;

    @Override
    public String saveHandler(Employee employee) {
        if (this.save(employee))
            return null;
        else
            return InventoryMessageConstant.SAVE_FAILURE_MESSAGE;
    }

    @Override
        public String saveHandler(List<Employee> employeeList) {
        if (this.saveBatch(employeeList))
            return null;
        else
          return InventoryMessageConstant.SAVE_FAILURE_MESSAGE;
    }

    @Override
    public String modifyHandler(Employee employee) {
        if (this.updateById(employee))
            return null;
        else
            return InventoryMessageConstant.MODIFY_FAILURE_MESSAGE;
    }

    @Override public String modifyHandler(List<Employee> employeeList) {
        if (this.updateBatchById(employeeList))
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
    public EmployeePageVo queryPage(EmployeeQueryVo vo) {
        Map<Integer, String> positionMap = positionService.list()
               .stream()
               .collect(Collectors.toMap(Position::getId, Position::getName));
        Map<Integer, String> departmentMap = departmentService.list()
               .stream()
               .collect(Collectors.toMap(Department::getId, Department::getName));
        Map<Integer, String> nationMap = nationService.list()
               .stream()
               .collect(Collectors.toMap(Nation::getId, Nation::getName));
        QueryWrapper<Employee> wrapper = new QueryWrapper<>();
        if (vo.getName()!= null && !vo.getName().equals(""))
            wrapper.like("name",vo.getName());
        if (vo.getPosId() != null && vo.getPosId() > 0)
            wrapper.eq("posId",vo.getPosId());
        if (vo.getDepartmentIdList() != null && !vo.getDepartmentIdList().isEmpty())
            wrapper.in("departmentId",vo.getDepartmentIdList());
        Long count = mapper.selectCount(wrapper);
        List<EmployeeVo> employeeVoList = mapper.selectPage(new Page<>(vo.getCurrentPage(), vo.getPageSize()), wrapper).getRecords()
                .stream()
                .map(employee -> employee.asViewObject(EmployeeVo.class, v -> {
                    v.setPosName(positionMap.get(employee.getPosId()));
                    v.setDepartmentName(departmentMap.get(employee.getDepartmentId()));
                    v.setNationName(nationMap.get(employee.getNationId()));
                })).toList();
        return new EmployeePageVo(count, employeeVoList);
    }
}
