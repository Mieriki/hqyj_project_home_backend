package com.mugen.inventory.service;

import com.mugen.inventory.entity.Position;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mugen.inventory.entity.model.vo.request.PositionQueryVo;
import com.mugen.inventory.entity.model.vo.response.PosTbVo;
import com.mugen.inventory.entity.model.vo.response.PositionPageVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Mieriki
 * @since 2024-08-14
 */
public interface PositionService extends IService<Position> {
    String saveHandler(Position position);
    String saveHandler(List<Position> positionList);
    String modifyHandler(Position position);
    String modifyHandler(List<Position> positionList);
    String removeHandler(Integer id);
    String removeHandler(List<Integer> idList);
    PositionPageVo queryPage(PositionQueryVo vo);
    List<PosTbVo> queryPosTbList();
}
