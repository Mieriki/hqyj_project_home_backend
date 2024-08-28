package com.mugen.inventory.service;

import com.mugen.inventory.entity.Employee;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mugen.inventory.entity.model.vo.request.EmployeeQueryVo;
import com.mugen.inventory.entity.model.vo.request.PositionQueryVo;
import com.mugen.inventory.entity.model.vo.response.EmployeePageVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Mieriki
 * @since 2024-08-16
 */
public interface EmployeeService extends IService<Employee> {
    String saveHandler(Employee employee);
    String saveHandler(List<Employee> employeeList);
    String modifyHandler(Employee employee);
    String modifyHandler(List<Employee> employeeList);
    String removeHandler(Integer id);
    String removeHandler(List<Integer> idList);
    EmployeePageVo queryPage(EmployeeQueryVo vo);
}
