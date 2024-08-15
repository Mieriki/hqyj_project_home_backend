package com.mugen.inventory.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mugen.inventory.entity.Syslog;
import com.mugen.inventory.entity.model.vo.request.SyslogQueryVo;
import com.mugen.inventory.entity.model.vo.response.SyslogPageVo;
import com.mugen.inventory.mapper.SyslogMapper;
import com.mugen.inventory.service.SyslogService;
import com.mugen.inventory.utils.constant.InventoryMessageConstant;
import jakarta.annotation.Resource;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Mieriki
 * @since 2024-08-13
 */
@Service
@Transactional
public class SyslogServiceImp extends ServiceImpl<SyslogMapper, Syslog> implements SyslogService {
    @Resource
    private SyslogMapper mapper;

    @Override
    public String saveHandler(Syslog syslog) {
        if (this.save(syslog))
            return null;
        else
            return InventoryMessageConstant.SAVE_FAILURE_MESSAGE;
    }

    @Override
        public String saveHandler(List<Syslog> syslogList) {
        if (this.saveBatch(syslogList))
            return null;
        else
          return InventoryMessageConstant.SAVE_FAILURE_MESSAGE;
    }

    @Override
    public String modifyHandler(Syslog syslog) {
        if (this.updateById(syslog))
            return null;
        else
            return InventoryMessageConstant.MODIFY_FAILURE_MESSAGE;
    }

    @Override public String modifyHandler(List<Syslog> syslogList) {
        if (this.updateBatchById(syslogList))
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
    public SyslogPageVo queryPage(SyslogQueryVo vo) {
        QueryWrapper<Syslog> queryWrapper = new QueryWrapper<>();
        if (vo.getOperation() != null && !vo.getOperation().equals(""))
            queryWrapper.eq("operation", vo.getOperation());
        queryWrapper
                .like("user_name", vo.getName())
                .orderByDesc("id");
        return new SyslogPageVo(mapper.selectCount(queryWrapper), mapper.selectPage(new Page<>(vo.getCurrentPage(), vo.getPageSize()), queryWrapper).getRecords());

    }
}
