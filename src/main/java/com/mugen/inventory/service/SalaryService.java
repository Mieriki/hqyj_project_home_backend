package com.mugen.inventory.service;

import com.mugen.inventory.entity.Salary;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mugen.inventory.entity.model.vo.request.SalaryQueryVo;
import com.mugen.inventory.entity.model.vo.response.SalaryPageVo;
import com.mugen.inventory.entity.model.vo.response.SyslogPageVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Mieriki
 * @since 2024-08-20
 */
public interface SalaryService extends IService<Salary> {
    String saveHandler(Salary salary);
    String saveHandler(List<Salary> salaryList);
    String modifyHandler(Salary salary);
    String modifyHandler(List<Salary> salaryList);
    String removeHandler(Integer id);
    String removeHandler(List<Integer> idList);
    SalaryPageVo queryPage(SalaryQueryVo vo);
}
