package com.mugen.inventory.service;

import com.mugen.inventory.entity.Department;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mugen.inventory.entity.model.vo.response.DepartmentTreeVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Mieriki
 * @since 2024-08-14
 */
public interface DepartmentService extends IService<Department> {
    String saveHandler(Department department);
    String saveHandler(List<Department> departmentList);
    String modifyHandler(Department department);
    String modifyHandler(List<Department> departmentList);
    String removeHandler(Integer id);
    String removeHandler(List<Integer> idList);
    List<DepartmentTreeVo> queryTree();
}
