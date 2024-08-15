package com.mugen.inventory.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.mugen.inventory.annotation.LoggerPermission;
import com.mugen.inventory.entity.Menu;
import com.mugen.inventory.entity.model.vo.request.MenuQueryVo;
import com.mugen.inventory.entity.model.vo.request.RoleMenuModifyVo;
import com.mugen.inventory.service.MenuService;
import com.mugen.inventory.utils.JwtUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.validation.annotation.Validated;
import com.mugen.inventory.utils.RestBean;
import org.springframework.web.bind.annotation.RestController;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * menu 前端控制器
 * </p>
 *
 * @author Mieriki
 * @since 2024-07-24
 */
@Log4j2
@RestController
@RequestMapping("/menus")
public class MenuController {
    @Resource
    private MenuService service;

    @Resource
    private JwtUtils utils;

    @GetMapping("/get")
    public <T>RestBean<List> list(){
        return RestBean.success(service.list());
    }

    @GetMapping("/get/{id}")
    public <T>RestBean<Menu> query(@PathVariable Integer id) {
        return RestBean.success(service.getById(id));
    }

    @LoggerPermission(operation = "新增菜单")
    @PostMapping("/post")
    public <T>RestBean<Void> save(@RequestBody @Validated Menu vo) {
        return RestBean.messageHandle(vo, service::saveHandler);
    }

    @LoggerPermission(operation = "修改菜单")
    @PostMapping("/put")
    public <T>RestBean<Void> modify(@RequestBody @Validated Menu vo) {
        return RestBean.messageHandle(vo, service::modifyHandler);
    }

    @LoggerPermission(operation = "删除菜单")
    @GetMapping("/delete/{id}")
    public <T>RestBean<Void> remove(@PathVariable Integer id) {
        return RestBean.messageHandle(id, service::removeHandler);
    }

    @PostMapping("/delete")
    public <T>RestBean<Void> remove(@RequestBody List<Integer> idList) {
        return RestBean.messageHandle(idList, service::removeHandler);
    }

    @GetMapping("/get/count")
    public <T>RestBean<Long> count() {
        return RestBean.success(service.count());
    }

    @GetMapping("/get/router")
    public <T>RestBean<List> queryRouter(HttpServletRequest request){
        String authorization = request.getHeader("Authorization");
        DecodedJWT jwt = utils.resolveJwt(authorization);
        if (jwt == null) {
            throw new RuntimeException("请先进行登录!");
        }
        return RestBean.success(service.queryRouterByAdminId(utils.getId(jwt)));
    }

    @GetMapping("/get/tree/{id}")
    public <T>RestBean<List> queryTree(@PathVariable Integer id) {
        return RestBean.success(service.queryTree(id));
    }

    @GetMapping("/get/tree/default/{id}")
    public <T>RestBean<List> queryChickTree(@PathVariable Integer id) {
        return RestBean.success(service.queryMenuListChicked(id));
    }

    @LoggerPermission(operation = "修改角色菜单")
    @PostMapping("/put/tree")
    public <T>RestBean<Void> modifyTree(@RequestBody @Validated RoleMenuModifyVo vo) {
        return RestBean.messageHandle(vo, service::modifyTreeHandler);
    }

    @PostMapping("/get/tree")
    public <T>RestBean<List> queryTree(@RequestBody @Validated MenuQueryVo vo) {
        return RestBean.success(service.queryTree(vo));
    }

    @SneakyThrows
    @GetMapping("/get/excel")
    public void exportData(HttpServletResponse response) {
        String fileName = "Menu" + new Date() + ".xlsx";
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
        OutputStream out = response.getOutputStream();
        List<Menu> rowss = CollUtil.newArrayList();
        rowss.addAll(service.list());
        ExcelWriter writer= ExcelUtil.getBigWriter();
        writer.write(rowss);
        writer.flush(out);
        writer.close();
    }
}
