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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    //        List<GoodsTypeTreeVo> goodsTypeTreeVoList = mapper.selectTree();
    //        List<GoodsTypeTreeVo> result = new ArrayList<>();
    //        // 构建用于存放根节点的 Map，key 是父节点的 id，value 是父节点的 children 列表
    //        Map<Integer, List<GoodsTypeTreeVo>> parentMap = new HashMap<>();
    //
    //        // 遍历原始数据，将每个节点按照其父节点 id 放入 parentMap
    //        for (GoodsTypeTreeVo node : goodsTypeTreeVoList) {
    //            if (node.getPId() == -1) {
    //                // 如果 pid 为 -1，则认为是顶层节点，直接加入 result 中
    //                result.add(node);
    //            } else {
    //                // 否则将节点放入其父节点对应的 children 列表中
    //                parentMap.computeIfAbsent(node.getPId(), k -> new ArrayList<>()).add(node);
    //            }
    //        }
    //
    //        // 递归构建树状结构
    //        buildTree(result, parentMap);
    //
    //        return result;
    @Override
    public List<DepartmentTreeVo> queryTree() {
        List<DepartmentTreeVo> departmentTreeVoList = mapper.selectList(null)
                .stream().map(department -> department.asViewObject(DepartmentTreeVo.class)).toList();
        List<DepartmentTreeVo> result = new ArrayList<>();
        // 构建用于存放根节点的 Map，key 是父节点的 id，value 是父节点的 children 列表
        Map<Integer, List<DepartmentTreeVo>> parentMap = new HashMap<>();
        return null;
    }
}
