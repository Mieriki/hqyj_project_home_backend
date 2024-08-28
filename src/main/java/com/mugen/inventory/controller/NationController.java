package com.mugen.inventory.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.mugen.inventory.annotation.LoggerPermission;
import com.mugen.inventory.entity.Nation;
import com.mugen.inventory.entity.model.vo.request.NationQueryVo;
import com.mugen.inventory.entity.model.vo.response.NationPageVo;
import com.mugen.inventory.service.NationService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
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
 * nation 前端控制器
 * </p>
 *
 * @author Mieriki
 * @since 2024-08-16
 */
@RestController
@RequestMapping("/nations")
public class NationController {
    @Resource
    private NationService service;

    @GetMapping("/get")
    public <T>RestBean<List<Nation>> list(){
        return RestBean.success(service.list());
    }

    @PostMapping("/get")
    public <T>RestBean<NationPageVo> list(@RequestBody NationQueryVo vo) {
        return RestBean.success(service.queryPage(vo));
    }

    @GetMapping("/get/{id}")
    public <T>RestBean<Nation> query(@PathVariable Integer id) {
        return RestBean.success(service.getById(id));
    }

    @LoggerPermission(operation = "新增民族")
    @PostMapping("/post")
    public <T>RestBean<Void> save(@RequestBody @Validated Nation vo) {
        return RestBean.messageHandle(vo, service::saveHandler);
    }

    @LoggerPermission(operation = "修改民族")
    @PostMapping("/put")
    public <T>RestBean<Void> modify(@RequestBody @Validated Nation vo) {
        return RestBean.messageHandle(vo, service::modifyHandler);
    }

    @LoggerPermission(operation = "删除民族")
    @GetMapping("/delete/{id}")
    public <T>RestBean<Void> remove(@PathVariable Integer id) {
        return RestBean.messageHandle(id, service::removeHandler);
    }

    @LoggerPermission(operation = "批量删除民族")
    @PostMapping("/delete")
    public <T>RestBean<Void> remove(@RequestBody List<Integer> idList) {
        return RestBean.messageHandle(idList, service::removeHandler);
    }

    @GetMapping("/get/count")
    public <T>RestBean<Long> count() {
        return RestBean.success(service.count());
    }

    @SneakyThrows
    @GetMapping("/get/excel")
    public void exportData(HttpServletResponse response) {
        String fileName = "Nation_" + new Date() + ".xlsx";
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
        OutputStream out = response.getOutputStream();
        List<Nation> rowss = CollUtil.newArrayList();
        rowss.addAll(service.list());
        ExcelWriter writer= ExcelUtil.getBigWriter();
        writer.write(rowss);
        writer.flush(out);
        writer.close();
    }

    @LoggerPermission(operation = "批量导入民族")
    @SneakyThrows
    @PostMapping("/post/excel")
    public <T> RestBean<Void> handleFileUpload(HttpServletRequest request, @RequestParam("file") MultipartFile file) {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        List<Nation> nationList = reader.readAll(Nation.class);
        return RestBean.messageHandle(nationList, service::saveHandler);
    }
}
