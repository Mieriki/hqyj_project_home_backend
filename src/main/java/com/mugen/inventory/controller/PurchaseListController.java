package com.mugen.inventory.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.mugen.inventory.entity.PurchaseList;
import com.mugen.inventory.entity.model.dto.Turnover;
import com.mugen.inventory.entity.model.vo.request.PurchaseQueryPageVo;
import com.mugen.inventory.entity.model.vo.request.GoodCartVo;
import com.mugen.inventory.entity.model.vo.response.CartPurchasePageVo;
import com.mugen.inventory.entity.model.vo.response.TurnoverVo;
import com.mugen.inventory.service.PurchaseListService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.validation.annotation.Validated;
import com.mugen.inventory.utils.RestBean;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * <p>
 * purchaseList 前端控制器
 * </p>
 *
 * @author Mieriki
 * @since 2024-07-31
 */
@Log4j2
@RestController
@RequestMapping("/purchaseLists")
public class PurchaseListController {
    @Resource
    private PurchaseListService service;

    @Resource
    private JwtUtils utils;

    @GetMapping("/get")
    public <T>RestBean<List<PurchaseList>> list(){
        return RestBean.success(service.list());
    }

    @PostMapping("/get")
    public <T>RestBean<CartPurchasePageVo> queryPage(@RequestBody @Validated PurchaseQueryPageVo vo) {
        return RestBean.success(service.queryPage(vo));
    }

    @GetMapping("/get/{id}")
    public <T>RestBean<PurchaseList> query(@PathVariable Integer id) {
        return RestBean.success(service.getById(id));
    }

    @PostMapping("/post")
    public <T>RestBean<Void> save(@RequestBody @Validated PurchaseList vo) {
        return RestBean.messageHandle(vo, service::saveHandler);
    }

    @PostMapping("/put")
    public <T>RestBean<Void> modify(@RequestBody @Validated PurchaseList vo) {
        return RestBean.messageHandle(vo, service::modifyHandler);
    }

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

    @PostMapping("/post/cart")
    public <T>RestBean<Void> saveCart(@RequestBody @Validated GoodCartVo vo, HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        DecodedJWT jwt = utils.resolveJwt(authorization);
        if (jwt == null) {
            throw new RuntimeException("请先进行登录!");
        }
        return RestBean.messageHandle(vo.setUserId(utils.getId(jwt)), service::cartHandler);
    }

    @GetMapping("/get/turnover/year")
    public <T>RestBean<TurnoverVo> queryTurnoverYearFilter() {
        return RestBean.success(service.queryTurnoverYearFilter());
    }

    @GetMapping("/get/turnover/month")
    public <T>RestBean<TurnoverVo> queryTurnoverMonthFilter() {
        return RestBean.success(service.queryTurnoverMonthFilter());
    }

    @GetMapping("/get/turnover/day")
    public <T>RestBean<TurnoverVo> queryTurnoverDayFilter() {
        return RestBean.success(service.queryTurnoverDayFilter());
    }

    @SneakyThrows
    @GetMapping("/get/excel")
    public void exportData(HttpServletResponse response) {
        String fileName = "PurchaseList_" + new Date() + ".xlsx";
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
        OutputStream out = response.getOutputStream();
        List<PurchaseList> rowss = CollUtil.newArrayList();
        rowss.addAll(service.list());
        ExcelWriter writer= ExcelUtil.getBigWriter();
        writer.write(rowss);
        writer.flush(out);
        writer.close();
    }

    @SneakyThrows
    @PostMapping("/post/excel")
    public <T> RestBean<Void> handleFileUpload(HttpServletRequest request, @RequestParam("file") MultipartFile file) {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        List<PurchaseList> purchaselistList = reader.readAll(PurchaseList.class);
        return RestBean.messageHandle(purchaselistList, service::saveHandler);
    }
}
