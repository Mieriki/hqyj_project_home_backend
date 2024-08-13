package com.mugen.inventory.service;

import com.mugen.inventory.entity.PurchaseList;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mugen.inventory.entity.model.dto.Turnover;
import com.mugen.inventory.entity.model.vo.request.PurchaseQueryPageVo;
import com.mugen.inventory.entity.model.vo.request.GoodCartVo;
import com.mugen.inventory.entity.model.vo.response.CartPurchasePageVo;
import com.mugen.inventory.entity.model.vo.response.TurnoverVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Mieriki
 * @since 2024-07-31
 */
public interface PurchaseListService extends IService<PurchaseList> {
    String saveHandler(PurchaseList purchaselist);
    String saveHandler(List<PurchaseList> purchaselistList);
    String modifyHandler(PurchaseList purchaselist);
    String modifyHandler(List<PurchaseList> purchaselistList);
    String removeHandler(Integer id);
    String removeHandler(List<Integer> idList);
    String cartHandler(GoodCartVo vo);
    CartPurchasePageVo queryPage(PurchaseQueryPageVo vo);
    List<Turnover> queryTurnoverYear();
    List<Turnover> queryTurnoverMonth();
    List<Turnover> queryTurnoverDay();
    TurnoverVo queryTurnoverYearFilter();
    TurnoverVo queryTurnoverMonthFilter();
    TurnoverVo queryTurnoverDayFilter();
}
