package com.mugen.inventory.service;

import com.mugen.inventory.entity.SaleList;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mugen.inventory.entity.model.dto.CartSaleDto;
import com.mugen.inventory.entity.model.dto.Turnover;
import com.mugen.inventory.entity.model.vo.request.GoodCartVo;
import com.mugen.inventory.entity.model.vo.request.SaleQueryPageVo;
import com.mugen.inventory.entity.model.vo.request.SupplierQueryPageVo;
import com.mugen.inventory.entity.model.vo.response.CartPurchasePageVo;
import com.mugen.inventory.entity.model.vo.response.CartSalePageVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Mieriki
 * @since 2024-08-01
 */
public interface SaleListService extends IService<SaleList> {
    String saveHandler(SaleList salelist);
    String saveHandler(List<SaleList> salelistList);
    String modifyHandler(SaleList salelist);
    String modifyHandler(List<SaleList> salelistList);
    String removeHandler(Integer id);
    String removeHandler(List<Integer> idList);
    String cartHandler(GoodCartVo vo);
    CartSalePageVo queryPage(SaleQueryPageVo vo);
    List<Turnover> queryTurnoverYear();
    List<Turnover> queryTurnoverMonth();
    List<Turnover> queryTurnoverDay();
}
