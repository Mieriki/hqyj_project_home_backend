package com.mugen.inventory.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mugen.inventory.entity.Department;
import com.mugen.inventory.entity.model.vo.request.DepartmentDeleteVo;
import com.mugen.inventory.entity.model.vo.request.DepartmentQueryVo;
import com.mugen.inventory.entity.model.vo.request.DepartmentSaveVo;
import com.mugen.inventory.entity.model.vo.response.DepartmentTreeVo;
import com.mugen.inventory.mapper.DepartmentMapper;
import com.mugen.inventory.service.DepartmentService;
import com.mugen.inventory.utils.constant.InventoryMessageConstant;
import jakarta.annotation.Resource;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Mieriki
 * @since 2024-08-14
 */
@Log4j2
@Service
@Transactional
public class DepartmentServiceImp extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {
    @Resource
    private DepartmentMapper mapper;

    @Override
    public String saveHandler(Department department) {
        DepartmentSaveVo vo = DepartmentSaveVo.builder()
                .depName(department.getName())
                .enabled(1)
                .parentId(department.getParentId())
                .result(0)
                .result2(0)
                .build();
        mapper.instertOption(vo);
        if (vo.getResult() > 0 && vo.getResult2() > 0)
            return null;
        throw new RuntimeException(InventoryMessageConstant.SAVE_FAILURE_MESSAGE);
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
        if (mapper.selectOne(new QueryWrapper<Department>().eq("id", id)).getIsParent())
            return "请先删除下属部门";
        DepartmentDeleteVo vo = DepartmentDeleteVo.builder()
                .id(id)
                .result(0)
                .build();
        mapper.deleteOption(vo);
        if (vo.getResult() > 0)
            return null;
        throw new RuntimeException(InventoryMessageConstant.REMOVE_FAILURE_MESSAGE);
    }

    @Override
    public String removeHandler(List<Integer> idList) {
        if (this.removeByIds(idList))
            return null;
        else
            return InventoryMessageConstant.REMOVE_FAILURE_MESSAGE;
    }

    @Override
    public List<DepartmentTreeVo> queryTree(DepartmentQueryVo vo) {
        if (vo.getName() != null && !vo.getName().equals("")) {
            return  mapper.selectList(new QueryWrapper<Department>().like("name", vo.getName()))
                    .stream().map(department -> department.asViewObject(DepartmentTreeVo.class)).toList();
        }
        List<DepartmentTreeVo> departmentTreeVoList = mapper.selectList(null)
                .stream().map(department -> department.asViewObject(DepartmentTreeVo.class)).toList();
        List<DepartmentTreeVo> result = new ArrayList<>();
        // 构建用于存放根节点的 Map，key 是父节点的 id，value 是父节点的 children 列表
        Map<Integer, List<DepartmentTreeVo>> parentMap = new HashMap<>();
        // 遍历原始数据，将每个节点按照其父节点 id 放入 parentMap
        for (DepartmentTreeVo node : departmentTreeVoList) {
            if (node.getParentId() == -1) {
                result.add(node);
            } else {
                parentMap.computeIfAbsent(node.getParentId(), k -> new ArrayList<>()).add(node);
            }
        }
        // 递归构建树状结构
        buildTree(result, parentMap);
        return result;
    }

    /**
     * 递归构建树状结构
     * @param nodes 当前处理的节点列表
     * @param parentMap 父节点映射
     */
    private void buildTree(List<DepartmentTreeVo> nodes, Map<Integer, List<DepartmentTreeVo>> parentMap) {
        for (DepartmentTreeVo node : nodes) {
            List<DepartmentTreeVo> children = parentMap.get(node.getId());
            if (children != null) {
                // 对 children 进行排序，如果有需要的话
                children.sort(Comparator.comparing(DepartmentTreeVo::getId));
                node.setChildren(children);
                // 递归构建子树
                buildTree(children, parentMap);
            }
        }
    }
}
