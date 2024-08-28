package com.mugen.inventory.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.mugen.inventory.annotation.LoggerPermission;
import com.mugen.inventory.entity.Department;
import com.mugen.inventory.entity.model.vo.request.DepartmentQueryVo;
import com.mugen.inventory.entity.model.vo.response.DepartmentTreeVo;
import com.mugen.inventory.entity.model.vo.response.PosTbVo;
import com.mugen.inventory.service.DepartmentService;
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
 * department 前端控制器
 * </p>
 *
 * @author Mieriki
 * @since 2024-08-14
 */
@RestController
@RequestMapping("/departments")
public class DepartmentController {
    @Resource
    private DepartmentService service;

    @GetMapping("/get")
    public <T>RestBean<List<Department>> list(){
        return RestBean.success(service.list());
    }

    @GetMapping("/get/{id}")
    public <T>RestBean<Department> query(@PathVariable Integer id) {
        return RestBean.success(service.getById(id));
    }

    @LoggerPermission(operation = "新增部门")
    @PostMapping("/post")
    public <T>RestBean<Void> save(@RequestBody @Validated Department vo) {
        return RestBean.messageHandle(vo, service::saveHandler);
    }

    @LoggerPermission(operation = "修改部门信息")
    @PostMapping("/put")
    public <T>RestBean<Void> modify(@RequestBody @Validated Department vo) {
        return RestBean.messageHandle(vo, service::modifyHandler);
    }

    @LoggerPermission(operation = "删除部门")
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

    @PostMapping("/get/tree")
    public <T>RestBean<List<DepartmentTreeVo>> queryTree(@RequestBody DepartmentQueryVo vo) {
        return RestBean.success(service.queryTree(vo));
    }

    @SneakyThrows
    @GetMapping("/get/excel")
    public void exportData(HttpServletResponse response) {
        String fileName = "Department_" + new Date() + ".xlsx";
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
        OutputStream out = response.getOutputStream();
        List<Department> rowss = CollUtil.newArrayList();
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
        List<Department> departmentList = reader.readAll(Department.class);
        return RestBean.messageHandle(departmentList, service::saveHandler);
    }

    @GetMapping("/get/dept-tb")
    public <T> RestBean<List<PosTbVo>> getPosTb() {
        return RestBean.success(service.queryDeptTbList());
    }
}
