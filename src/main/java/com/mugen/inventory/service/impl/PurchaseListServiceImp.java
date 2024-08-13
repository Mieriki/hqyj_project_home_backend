package com.mugen.inventory.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mugen.inventory.entity.*;
import com.mugen.inventory.entity.model.dto.Turnover;
import com.mugen.inventory.entity.model.vo.request.PurchaseQueryPageVo;
import com.mugen.inventory.entity.model.vo.request.GoodCartVo;
import com.mugen.inventory.entity.model.vo.request.GoodsCartVo;
import com.mugen.inventory.entity.model.dto.CartPurchaseDto;
import com.mugen.inventory.entity.model.vo.response.CartPurchasePageVo;
import com.mugen.inventory.entity.model.vo.response.TurnoverVo;
import com.mugen.inventory.mapper.PurchaseListMapper;
import com.mugen.inventory.service.*;
import com.mugen.inventory.utils.constant.InventoryMessageConstant;
import jakarta.annotation.Resource;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Mieriki
 * @since 2024-07-31
 */
@Log4j2
@Service
@Transactional
public class PurchaseListServiceImp extends ServiceImpl<PurchaseListMapper, PurchaseList> implements PurchaseListService {
    @Resource
    private PurchaseListMapper mapper;

    @Resource
    private GoodsService goodsService;

    @Resource
    private PurchaseListGoodsService purchaseListGoodsService;

    @Resource
    private SupplierService supplierService;

    @Resource
    private AdminService adminService;

    @Resource
    private SaleListService saleListService;

    @Override
    public String saveHandler(PurchaseList purchaselist) {
        if (this.save(purchaselist))
            return null;
        else
            return InventoryMessageConstant.SAVE_FAILURE_MESSAGE;
    }

    @Override
        public String saveHandler(List<PurchaseList> purchaselistList) {
        if (this.saveBatch(purchaselistList))
            return null;
        else
          return InventoryMessageConstant.SAVE_FAILURE_MESSAGE;
    }

    @Override
    public String modifyHandler(PurchaseList purchaselist) {
        if (this.updateById(purchaselist))
            return null;
        else
            return InventoryMessageConstant.MODIFY_FAILURE_MESSAGE;
    }

    @Override public String modifyHandler(List<PurchaseList> purchaselistList) {
        if (this.updateBatchById(purchaselistList))
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
    public String cartHandler(GoodCartVo vo) {
        Double kane = 0.0;
        PurchaseList purchaselist = vo.asViewObject(PurchaseList.class, v -> {
            v.setPurchaseDate(new Date())
                    .setAmountPayable(0d);
        });
        mapper.insert(purchaselist);
        PurchaseList purchaselistTemp = mapper.selectOne(new QueryWrapper<PurchaseList>().eq("purchase_number", purchaselist.getPurchaseNumber()));
        for (GoodsCartVo cart : vo.getGoodsInList()) {
            Goods goods = goodsService.getById(cart.getId());
            kane += goods.getPurchasingPrice() * cart.getSize();
            goods.setInventoryQuantity(goods.getInventoryQuantity() + cart.getSize());
            goodsService.updateById(Goods.builder().id(goods.getId()).inventoryQuantity(goods.getInventoryQuantity()).build());
            PurchaseListGoods purchaseListGoods = goods.asViewObject(PurchaseListGoods.class, v -> {
                v.setNum(cart.getSize())
                        .setPrice(goods.getPurchasingPrice())
                        .setTotal(goods.getPurchasingPrice() * cart.getSize())
                        .setId(null)
                        .setGoodsId(goods.getId())
                        .setCurrentData(purchaselist.getPurchaseDate())
                        .setPurchaseListId(purchaselistTemp.getId());
            });
            purchaseListGoodsService.save(purchaseListGoods);
        }
        mapper.updateById(PurchaseList.builder().id(purchaselist.getId()).amountPayable(kane).build());
        return null;
    }

    @Override
    public CartPurchasePageVo queryPage(PurchaseQueryPageVo vo) {
        List<PurchaseList> purchaselistList;
        Long count;
        if (vo.getState() != null && vo.getState() >= 0) {
            purchaselistList = mapper.selectPage(new Page<PurchaseList>(vo.getCurrentPage(), vo.getPageSize()), new QueryWrapper<PurchaseList>()
                    .eq("state", vo.getState())
                    .like("purchase_number", vo.getPurchaseNumber()).orderByDesc("id")).getRecords();
            count = mapper.selectCount(new QueryWrapper<PurchaseList>()
                    .eq("state", vo.getState())
                    .like("purchase_number", vo.getPurchaseNumber()));
        } else {
            purchaselistList = mapper.selectPage(new Page<PurchaseList>(vo.getCurrentPage(), vo.getPageSize()), new QueryWrapper<PurchaseList>()
                    .like("purchase_number", vo.getPurchaseNumber()).orderByDesc("id")).getRecords();
            count = mapper.selectCount(new QueryWrapper<PurchaseList>()
                    .like("purchase_number", vo.getPurchaseNumber()));
        }
        Map<Integer, String> adminMap = adminService.list().stream().collect(Collectors.toMap(Admin::getId, Admin::getName));
        Map<Integer, String> supplierMap = supplierService.list().stream().collect(Collectors.toMap(Supplier::getId, Supplier::getName));
        return new CartPurchasePageVo(count, purchaselistList.stream().map(purchaselist ->
                purchaselist.asViewObject(CartPurchaseDto.class, v -> {
                    v.setSupplier(supplierMap.get(purchaselist.getSupplierId()));
                    v.setUser(adminMap.get(purchaselist.getUserId()));
                })).toList());
    }

    @Override
    public List<Turnover> queryTurnoverYear() {
        return mapper.selectTurnoverYear();
    }

    @Override
    public List<Turnover> queryTurnoverMonth() {
        return mapper.selectTurnoverMonth();
    }

    @Override
    public List<Turnover> queryTurnoverDay() {
        return mapper.selectTurnoverDay();
    }

    @Override
    public TurnoverVo queryTurnoverYearFilter() {
        List<Turnover> purchaselist = mapper.selectTurnoverYear();
        List<Turnover> salelist = saleListService.queryTurnoverYear();
        if (purchaselist.size() > salelist.size()) {
            salelist.addAll(purchaselist.stream().filter(turnover ->
                    salelist.stream().noneMatch(turnoverSale ->
                            turnoverSale.getDate().equals(turnover.getDate()))).map(turn ->
                    turn.setNum(0).setPaid(0d)).toList());

            purchaselist.addAll(salelist.stream().filter(turnover ->
                    salelist.stream().noneMatch(turnoverPurchase ->
                            turnoverPurchase.getDate().equals(turnover.getDate()))).map(turn ->
                    turn.setNum(0).setPaid(0d)).toList());
        }
        return new TurnoverVo(purchaselist, salelist);
    }

    @Override
    public TurnoverVo queryTurnoverMonthFilter() {
        List<Turnover> purchaselist = mapper.selectTurnoverMonth();
        List<Turnover> salelist = saleListService.queryTurnoverMonth();

        log.error("purchaselist: {}", purchaselist);
        log.error("salelist: {}", salelist);

        if (purchaselist.size() != salelist.size()) {
            salelist.addAll(purchaselist.stream().filter(turnover ->
                    salelist.stream().noneMatch(turnoverSale ->
                            turnoverSale.getDate().equals(turnover.getDate()))).map(turn ->
                    turn.setNum(0).setPaid(0d)).toList());

            purchaselist.addAll(salelist.stream().filter(turnover ->
                    salelist.stream().noneMatch(turnoverPurchase ->
                            turnoverPurchase.getDate().equals(turnover.getDate()))).map(turn ->
                    turn.setNum(0).setPaid(0d)).toList());
        }
        log.error("purchaselist: {}", purchaselist);
        log.error("salelist: {}", salelist);
        return new TurnoverVo(
                purchaselist.stream().map(turnover -> turnover.setDate(turnover.getYear() + "-" + turnover.getDate())).toList(),
                salelist.stream().map(turnover -> turnover.setDate(turnover.getYear() + "-" + turnover.getDate())).toList()
        );
    }

    @Override
    public TurnoverVo queryTurnoverDayFilter() {
        List<Turnover> purchaselist = mapper.selectTurnoverDay();
        List<Turnover> salelist = saleListService.queryTurnoverDay();
        if (purchaselist.size() > salelist.size()) {
            salelist.addAll(purchaselist.stream().filter(turnover ->
                    salelist.stream().noneMatch(turnoverSale ->
                            turnoverSale.getDate().equals(turnover.getDate()))).map(turn ->
                    turn.setNum(0).setPaid(0d)).toList());

            purchaselist.addAll(salelist.stream().filter(turnover ->
                    salelist.stream().noneMatch(turnoverPurchase ->
                            turnoverPurchase.getDate().equals(turnover.getDate()))).map(turn ->
                    turn.setNum(0).setPaid(0d)).toList());
        }
        return new TurnoverVo(purchaselist, salelist);
    }
}
