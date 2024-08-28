package com.mugen.inventory.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mugen.inventory.entity.Employee;
import com.mugen.inventory.entity.EmployeeTrain;
import com.mugen.inventory.entity.model.vo.request.EmployeeTrainQueryVo;
import com.mugen.inventory.entity.model.vo.response.EmployeeTrainPageVo;
import com.mugen.inventory.entity.model.vo.response.EmployeeTrainVo;
import com.mugen.inventory.mapper.EmployeeMapper;
import com.mugen.inventory.mapper.EmployeeTrainMapper;
import com.mugen.inventory.service.EmployeeTrainService;
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
 * @since 2024-08-20
 */
@Service
@Transactional
public class EmployeeTrainServiceImp extends ServiceImpl<EmployeeTrainMapper, EmployeeTrain> implements EmployeeTrainService {
    @Resource
    private EmployeeTrainMapper mapper;

    @Resource
    private EmployeeMapper employeeMapper;

    @Override
    public String saveHandler(EmployeeTrain employeetrain) {
        if (this.save(employeetrain))
            return null;
        else
            return InventoryMessageConstant.SAVE_FAILURE_MESSAGE;
    }

    @Override
        public String saveHandler(List<EmployeeTrain> employeetrainList) {
        if (this.saveBatch(employeetrainList))
            return null;
        else
          return InventoryMessageConstant.SAVE_FAILURE_MESSAGE;
    }

    @Override
    public String modifyHandler(EmployeeTrain employeetrain) {
        if (this.updateById(employeetrain))
            return null;
        else
            return InventoryMessageConstant.MODIFY_FAILURE_MESSAGE;
    }

    @Override public String modifyHandler(List<EmployeeTrain> employeetrainList) {
        if (this.updateBatchById(employeetrainList))
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
    public EmployeeTrainPageVo queryPage(EmployeeTrainQueryVo vo) {
        QueryWrapper<EmployeeTrain> wrapper = new QueryWrapper<>();
        if (vo.getEName()!= null && !vo.getEName().equals("")) {
            List<Integer> idList = employeeMapper.selectList(new QueryWrapper<Employee>().like("name", vo.getEName()))
                    .stream().map(Employee::getId).toList();
            wrapper.in("eid", idList);
        }
        if (vo.getTrainContent()!= null && !vo.getTrainContent().equals(""))
            wrapper.like("trainContent", vo.getTrainContent());
        Map<Integer, String> employeeMap = employeeMapper.selectList(null)
                .stream()
                .collect(Collectors.toMap(Employee::getId, Employee::getName));
        Long count = mapper.selectCount(wrapper);
        List<EmployeeTrainVo> employeeTrainList =  mapper.selectPage(new Page<EmployeeTrain>(vo.getCurrentPage(), vo.getPageSize()), wrapper)
                .getRecords()
                .stream()
                .map(employeeTrain -> employeeTrain
                .asViewObject(EmployeeTrainVo.class, v -> v.setEName(employeeMap.get(employeeTrain.getEid())))
        ).toList();
        return new EmployeeTrainPageVo(count, employeeTrainList);
    }
}
