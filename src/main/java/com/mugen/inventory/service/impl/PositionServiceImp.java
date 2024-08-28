package com.mugen.inventory.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mugen.inventory.entity.Admin;
import com.mugen.inventory.entity.Employee;
import com.mugen.inventory.entity.Position;
import com.mugen.inventory.entity.model.vo.request.PositionQueryVo;
import com.mugen.inventory.entity.model.vo.response.PosTbVo;
import com.mugen.inventory.entity.model.vo.response.PositionPageVo;
import com.mugen.inventory.entity.model.vo.response.PositionVo;
import com.mugen.inventory.mapper.EmployeeMapper;
import com.mugen.inventory.mapper.PositionMapper;
import com.mugen.inventory.service.AdminService;
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
 * @since 2024-08-14
 */
@Service
@Transactional
public class PositionServiceImp extends ServiceImpl<PositionMapper, Position> implements PositionService {
    @Resource
    private PositionMapper mapper;

    @Resource
    private EmployeeMapper employeeMapper;

    @Resource
    AdminService adminService;

    @Override
    public String saveHandler(Position position) {
        if (this.save(position))
            return null;
        else
            return InventoryMessageConstant.SAVE_FAILURE_MESSAGE;
    }

    @Override
        public String saveHandler(List<Position> positionList) {
        if (this.saveBatch(positionList))
            return null;
        else
          return InventoryMessageConstant.SAVE_FAILURE_MESSAGE;
    }

    @Override
    public String modifyHandler(Position position) {
        if (this.updateById(position))
            return null;
        else
            return InventoryMessageConstant.MODIFY_FAILURE_MESSAGE;
    }

    @Override public String modifyHandler(List<Position> positionList) {
        if (this.updateBatchById(positionList))
            return null;
        else
            return InventoryMessageConstant.MODIFY_FAILURE_MESSAGE;
    }

    @Override
    public String removeHandler(Integer id) {
        if (employeeMapper.selectCount(new QueryWrapper<Employee>().eq("posId", id)) > 0) return "该岗位下存在员工，请先删除员工后再删除岗位";
        if (this.updateById(Position.builder()
               .id(id)
               .isDelete(true)
               .build()))
            return null;
        else
            return InventoryMessageConstant.REMOVE_FAILURE_MESSAGE;
    }

    @Override
    public String removeHandler(List<Integer> idList) {
        if (employeeMapper.selectCount(new QueryWrapper<Employee>().in("posId", idList)) > 0) return "该岗位下存在员工，请先删除员工后再删除岗位";
        if (this.updateBatchById(idList.stream()
               .map(id -> Position.builder()
                       .id(id)
                       .isDelete(true)
                       .build())
                .toList()))
            return null;
        else
            return InventoryMessageConstant.REMOVE_FAILURE_MESSAGE;
    }

    @Override
    public PositionPageVo queryPage(PositionQueryVo vo) {
        QueryWrapper<Position> wrapper =new QueryWrapper<>();
        wrapper.eq("is_delete",0);
        Map<Integer, String> usernameMap = adminService.list().stream().collect(
                Collectors.toMap(Admin::getId, Admin::getUserName)
        );
        if(vo.getName()!=null){
            wrapper.like("name",vo.getName());
        }
        return new PositionPageVo(
                mapper.selectCount(wrapper),
                mapper.selectPage(new Page<Position>(vo.getCurrentPage(),vo.getPageSize()), wrapper).getRecords()
                        .stream()
                        .map(position -> position.asViewObject(PositionVo.class, v -> v.setOperationName(usernameMap.get(position.getOperationId()))))
                        .toList()
        );
    }

    @Override
    public List<PosTbVo> queryPosTbList() {
        return mapper.selectPosTbList();
    }
}
