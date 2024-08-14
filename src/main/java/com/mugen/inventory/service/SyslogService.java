package com.mugen.inventory.service;

import com.mugen.inventory.entity.Syslog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Mieriki
 * @since 2024-08-13
 */
public interface SyslogService extends IService<Syslog> {
    String saveHandler(Syslog syslog);
    String saveHandler(List<Syslog> syslogList);
    String modifyHandler(Syslog syslog);
    String modifyHandler(List<Syslog> syslogList);
    String removeHandler(Integer id);
    String removeHandler(List<Integer> idList);
}
