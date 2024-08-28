package com.mugen.inventory.service;

import com.mugen.inventory.entity.EmployeeTrain;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mugen.inventory.entity.model.vo.request.EmployeeTrainQueryVo;
import com.mugen.inventory.entity.model.vo.response.EmployeeTrainPageVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Mieriki
 * @since 2024-08-20
 */
public interface EmployeeTrainService extends IService<EmployeeTrain> {
    String saveHandler(EmployeeTrain employeetrain);
    String saveHandler(List<EmployeeTrain> employeetrainList);
    String modifyHandler(EmployeeTrain employeetrain);
    String modifyHandler(List<EmployeeTrain> employeetrainList);
    String removeHandler(Integer id);
    String removeHandler(List<Integer> idList);
    EmployeeTrainPageVo queryPage(EmployeeTrainQueryVo vo);
}
