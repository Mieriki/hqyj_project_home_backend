package com.mugen.inventory.service.impl;

import com.mugen.inventory.entity.Supplier;
import com.mugen.inventory.entity.model.vo.request.CustomerQueryPageVo;
import com.mugen.inventory.entity.model.vo.request.SupplierQueryPageVo;
import com.mugen.inventory.entity.model.vo.response.SupplierPageVo;
import com.mugen.inventory.mapper.SupplierMapper;
import com.mugen.inventory.service.SupplierService;
import com.mugen.inventory.utils.ParameterUtils;
import com.mugen.inventory.utils.constant.InventoryMessageConstant;
import jakarta.annotation.Resource;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Mieriki
 * @since 2024-07-30
 */
@Log4j2
@Service
@Transactional
public class SupplierServiceImp extends ServiceImpl<SupplierMapper, Supplier> implements SupplierService {
    @Resource
    private SupplierMapper mapper;

    @Override
    public String saveHandler(Supplier supplier) {
        if (this.save(supplier))
            return null;
        else
            return InventoryMessageConstant.SAVE_FAILURE_MESSAGE;
    }

    @Override
        public String saveHandler(List<Supplier> supplierList) {
        if (this.saveBatch(supplierList))
            return null;
        else
          return InventoryMessageConstant.SAVE_FAILURE_MESSAGE;
    }

    @Override
    public String modifyHandler(Supplier supplier) {
        if (this.updateById(supplier))
            return null;
        else
            return InventoryMessageConstant.MODIFY_FAILURE_MESSAGE;
    }

    @Override public String modifyHandler(List<Supplier> supplierList) {
        if (this.updateBatchById(supplierList))
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
    public SupplierPageVo queryPage(SupplierQueryPageVo vo) {
        vo.setCurrentPage(ParameterUtils.getCurrentPage(vo.getCurrentPage(), vo.getPageSize()));
        return new SupplierPageVo(mapper.selectCountLikeName(vo), mapper.selectPageLikeNameAndAddress(vo));
    }
}
