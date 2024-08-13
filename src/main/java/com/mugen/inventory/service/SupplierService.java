package com.mugen.inventory.service;

import com.mugen.inventory.entity.Supplier;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mugen.inventory.entity.model.dto.Turnover;
import com.mugen.inventory.entity.model.vo.request.CustomerQueryPageVo;
import com.mugen.inventory.entity.model.vo.request.SupplierQueryPageVo;
import com.mugen.inventory.entity.model.vo.response.SupplierPageVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Mieriki
 * @since 2024-07-30
 */
public interface SupplierService extends IService<Supplier> {
    String saveHandler(Supplier supplier);
    String saveHandler(List<Supplier> supplierList);
    String modifyHandler(Supplier supplier);
    String modifyHandler(List<Supplier> supplierList);
    String removeHandler(Integer id);
    String removeHandler(List<Integer> idList);
    SupplierPageVo queryPage(SupplierQueryPageVo vo);
}
