package com.mugen.inventory.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mugen.inventory.entity.*;
import com.mugen.inventory.entity.model.dto.CartSaleDto;
import com.mugen.inventory.entity.model.dto.Turnover;
import com.mugen.inventory.entity.model.vo.request.GoodCartVo;
import com.mugen.inventory.entity.model.vo.request.GoodsCartVo;
import com.mugen.inventory.entity.model.vo.request.SaleQueryPageVo;
import com.mugen.inventory.entity.model.vo.request.SupplierQueryPageVo;
import com.mugen.inventory.entity.model.vo.response.CartPurchasePageVo;
import com.mugen.inventory.entity.model.vo.response.CartSalePageVo;
import com.mugen.inventory.mapper.SaleListMapper;
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
 * @since 2024-08-01
 */
@Log4j2
@Service
@Transactional
public class SaleListServiceImp extends ServiceImpl<SaleListMapper, SaleList> implements SaleListService {
    @Resource
    private SaleListMapper mapper;

    @Resource
    private GoodsService goodsService;

    @Resource
    private SaleListGoodsService saleListGoodsService;

    @Resource
    private AdminService adminService;

    @Resource
    private CustomerService customerService;

    @Override
    public String saveHandler(SaleList salelist) {
        if (this.save(salelist))
            return null;
        else
            return InventoryMessageConstant.SAVE_FAILURE_MESSAGE;
    }

    @Override
        public String saveHandler(List<SaleList> salelistList) {
        if (this.saveBatch(salelistList))
            return null;
        else
          return InventoryMessageConstant.SAVE_FAILURE_MESSAGE;
    }

    @Override
    public String modifyHandler(SaleList salelist) {
        if (this.updateById(salelist))
            return null;
        else
            return InventoryMessageConstant.MODIFY_FAILURE_MESSAGE;
    }

    @Override public String modifyHandler(List<SaleList> salelistList) {
        if (this.updateBatchById(salelistList))
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
        double kane = 0d;
        SaleList salelist = vo.asViewObject(SaleList.class, v -> {
            v.setSaleDate(new Date())
                    .setAmountPayable(0d);
        });
        mapper.insert(salelist);
        SaleList salelistTemp = mapper.selectOne(new QueryWrapper<SaleList>().eq("sale_number", salelist.getSaleNumber()));
        for (GoodsCartVo cart : vo.getGoodsInList()) {
            Goods goods = goodsService.getById(cart.getId());
            kane += goods.getSellingPrice() * cart.getSize();
            if (goods.getInventoryQuantity() < cart.getSize()) throw new RuntimeException("库存不足");
            goods.setInventoryQuantity(goods.getInventoryQuantity() - cart.getSize());
            goodsService.updateById(Goods.builder().id(goods.getId()).inventoryQuantity(goods.getInventoryQuantity()).build());
            SaleListGoods saleListGoods = goods.asViewObject(SaleListGoods.class, v -> {
                v.setNum(cart.getSize())
                        .setPrice(goods.getSellingPrice())
                        .setTotal(goods.getSellingPrice() * cart.getSize())
                        .setId(null)
                        .setGoodsId(goods.getId())
                        .setCurrentData(salelistTemp.getSaleDate())
                        .setSaleListId(salelistTemp.getId());
            });
            saleListGoodsService.save(saleListGoods);
        }
        mapper.updateById(SaleList.builder().id(salelistTemp.getId()).amountPayable(kane).build());
        return null;
    }

    @Override
    public CartSalePageVo queryPage(SaleQueryPageVo vo) {
        List<SaleList> saleListList;
        Long count;
        if (vo.getState() != null && vo.getState() >= 0) {
            saleListList = mapper.selectPage(new Page<SaleList>(vo.getCurrentPage(), vo.getPageSize()), new QueryWrapper<SaleList>()
                    .eq("state", vo.getState())
                    .like("sale_number", vo.getSaleNumber())
                    .orderByDesc("id")).getRecords();
            count = mapper.selectCount(new QueryWrapper<SaleList>()
                    .eq("state", vo.getState())
                    .like("sale_number", vo.getSaleNumber()));
        } else {
            saleListList = mapper.selectPage(new Page<SaleList>(vo.getCurrentPage(), vo.getPageSize()), new QueryWrapper<SaleList>()
                    .like("sale_number", vo.getSaleNumber())
                    .orderByDesc("id")).getRecords();
            count = mapper.selectCount(new QueryWrapper<SaleList>()
                    .like("sale_number", vo.getSaleNumber()));
        }
        Map<Integer, String> adminMap = adminService.list().stream().collect(Collectors.toMap(Admin::getId, Admin::getName));
        Map<Integer, String> customerMap = customerService.list().stream().collect(Collectors.toMap(Customer::getId, Customer::getName));
        return new CartSalePageVo(count, saleListList.stream().map(salelist ->
                salelist.asViewObject(CartSaleDto.class, v -> {
                    v.setCustomer(customerMap.get(salelist.getCustomerId()));
                    v.setUser(adminMap.get(salelist.getUserId()));
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
}
