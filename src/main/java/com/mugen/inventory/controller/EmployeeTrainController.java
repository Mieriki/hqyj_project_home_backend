package com.mugen.inventory.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.mugen.inventory.annotation.LoggerPermission;
import com.mugen.inventory.entity.EmployeeTrain;
import com.mugen.inventory.entity.model.vo.request.EmployeeTrainQueryVo;
import com.mugen.inventory.entity.model.vo.response.EmployeeTrainPageVo;
import com.mugen.inventory.service.EmployeeTrainService;
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
 * employeeTrain 前端控制器
 * </p>
 *
 * @author Mieriki
 * @since 2024-08-20
 */
@RestController
@RequestMapping("/employeeTrains")
public class EmployeeTrainController {
    @Resource
    private EmployeeTrainService service;

    @GetMapping("/get")
    public <T>RestBean<List<EmployeeTrain>> list(){
        return RestBean.success(service.list());
    }

    @PostMapping("/get")
    public <T>RestBean<EmployeeTrainPageVo> list(@RequestBody EmployeeTrainQueryVo vo) {
        return RestBean.success(service.queryPage(vo));
    }

    @GetMapping("/get/{id}")
    public <T>RestBean<EmployeeTrain> query(@PathVariable Integer id) {
        return RestBean.success(service.getById(id));
    }

    @LoggerPermission(operation = "新增员工培训信息")
    @PostMapping("/post")
    public <T>RestBean<Void> save(@RequestBody @Validated EmployeeTrain vo) {
        return RestBean.messageHandle(vo, service::saveHandler);
    }

    @LoggerPermission(operation = "修改员工培训信息")
    @PostMapping("/put")
    public <T>RestBean<Void> modify(@RequestBody @Validated EmployeeTrain vo) {
        return RestBean.messageHandle(vo, service::modifyHandler);
    }

    @LoggerPermission(operation = "删除员工培训信息")
    @GetMapping("/delete/{id}")
    public <T>RestBean<Void> remove(@PathVariable Integer id) {
        return RestBean.messageHandle(id, service::removeHandler);
    }


    @LoggerPermission(operation = "批量删除员工培训信息")
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
        String fileName = "EmployeeTrain_" + new Date() + ".xlsx";
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
        OutputStream out = response.getOutputStream();
        List<EmployeeTrain> rowss = CollUtil.newArrayList();
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
        List<EmployeeTrain> employeetrainList = reader.readAll(EmployeeTrain.class);
        return RestBean.messageHandle(employeetrainList, service::saveHandler);
    }
}
