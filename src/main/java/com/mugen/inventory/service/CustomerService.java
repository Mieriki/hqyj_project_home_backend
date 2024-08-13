package com.mugen.inventory.service;

import com.mugen.inventory.entity.Customer;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mugen.inventory.entity.model.vo.request.CustomerQueryPageVo;
import com.mugen.inventory.entity.model.vo.response.CustomerPageVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Mieriki
 * @since 2024-07-25
 */
public interface CustomerService extends IService<Customer> {
    String saveHandler(Customer customer);
    String saveHandler(List<Customer> customerList);
    String modifyHandler(Customer customer);
    String modifyHandler(List<Customer> customerList);
    String removeHandler(Integer id);
    String removeHandler(List<Integer> idList);
    CustomerPageVo queryPage(CustomerQueryPageVo vo);
}
