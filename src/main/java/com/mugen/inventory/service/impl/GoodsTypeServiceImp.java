package com.mugen.inventory.service.impl;

import com.mugen.inventory.entity.GoodsType;
import com.mugen.inventory.entity.model.vo.response.GoodsTypeTreeVo;
import com.mugen.inventory.mapper.GoodsTypeMapper;
import com.mugen.inventory.service.GoodsTypeService;
import com.mugen.inventory.utils.constant.InventoryMessageConstant;
import jakarta.annotation.Resource;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Mieriki
 * @since 2024-07-31
 */
@Service
@Transactional
public class GoodsTypeServiceImp extends ServiceImpl<GoodsTypeMapper, GoodsType> implements GoodsTypeService {
    @Resource
    private GoodsTypeMapper mapper;

    @Override
    public String saveHandler(GoodsType goodstype) {
        if (this.save(goodstype))
            return null;
        else
            return InventoryMessageConstant.SAVE_FAILURE_MESSAGE;
    }

    @Override
        public String saveHandler(List<GoodsType> goodstypeList) {
        if (this.saveBatch(goodstypeList))
            return null;
        else
          return InventoryMessageConstant.SAVE_FAILURE_MESSAGE;
    }

    @Override
    public String modifyHandler(GoodsType goodstype) {
        if (this.updateById(goodstype))
            return null;
        else
            return InventoryMessageConstant.MODIFY_FAILURE_MESSAGE;
    }

    @Override public String modifyHandler(List<GoodsType> goodstypeList) {
        if (this.updateBatchById(goodstypeList))
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
    public List<GoodsTypeTreeVo> queryTree() {
        // 模拟从数据库中查询到的数据
        List<GoodsTypeTreeVo> goodsTypeTreeVoList = mapper.selectTree();
        List<GoodsTypeTreeVo> result = new ArrayList<>();
        // 构建用于存放根节点的 Map，key 是父节点的 id，value 是父节点的 children 列表
        Map<Integer, List<GoodsTypeTreeVo>> parentMap = new HashMap<>();

        // 遍历原始数据，将每个节点按照其父节点 id 放入 parentMap
        for (GoodsTypeTreeVo node : goodsTypeTreeVoList) {
            if (node.getPId() == -1) {
                // 如果 pid 为 -1，则认为是顶层节点，直接加入 result 中
                result.add(node);
            } else {
                // 否则将节点放入其父节点对应的 children 列表中
                parentMap.computeIfAbsent(node.getPId(), k -> new ArrayList<>()).add(node);
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
    private void buildTree(List<GoodsTypeTreeVo> nodes, Map<Integer, List<GoodsTypeTreeVo>> parentMap) {
        for (GoodsTypeTreeVo node : nodes) {
            List<GoodsTypeTreeVo> children = parentMap.get(node.getId());
            if (children != null) {
                // 对 children 进行排序，如果有需要的话
                children.sort(Comparator.comparing(GoodsTypeTreeVo::getId));
                node.setChildren(children);
                // 递归构建子树
                buildTree(children, parentMap);
            }
        }
    }

}
