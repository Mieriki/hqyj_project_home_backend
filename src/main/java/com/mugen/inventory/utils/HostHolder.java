package com.mugen.inventory.utils;

import com.mugen.inventory.entity.Admin;
import org.springframework.stereotype.Component;

/**
 * ThreadLocal用于在线程之间传递数据，在这里用于存储当前登录的管理员信息
 */
@Component
public class HostHolder {

    private ThreadLocal<Admin> local = new ThreadLocal<>();

    public void setAccount(Admin admin){
        local.set(admin);
    }

    public Admin getAdmin(){
        return local.get();
    }

    public void clear(){
        local.remove();
    }
}
