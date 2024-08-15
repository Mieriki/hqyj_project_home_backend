package com.mugen.inventory.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.mugen.inventory.annotation.LoggerPermission;
import com.mugen.inventory.entity.Position;
import com.mugen.inventory.entity.model.vo.request.PositionQueryVo;
import com.mugen.inventory.entity.model.vo.response.PositionPageVo;
import com.mugen.inventory.service.PositionService;
import com.mugen.inventory.utils.JwtUtils;
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
 * position 前端控制器
 * </p>
 *
 * @author Mieriki
 * @since 2024-08-14
 */
@RestController
@RequestMapping("/positions")
public class PositionController {
    @Resource
    private PositionService service;

    @Resource
    private JwtUtils utils;

    @GetMapping("/get")
    public <T>RestBean<List<Position>> list(){
        return RestBean.success(service.list());
    }

    @PostMapping("/get")
    public <T>RestBean<PositionPageVo> list(@RequestBody PositionQueryVo vo) {
        return RestBean.success(service.queryPage(vo));
    }

    @GetMapping("/get/{id}")
    public <T>RestBean<Position> query(@PathVariable Integer id) {
        return RestBean.success(service.getById(id));
    }

    @LoggerPermission(operation = "新增职位")
    @PostMapping("/post")
    public <T>RestBean<Void> save(HttpServletRequest request, @RequestBody @Validated Position vo) {
        String authorization = request.getHeader("Authorization");
        DecodedJWT jwt = utils.resolveJwt(authorization);
        if (jwt == null) {
            throw new RuntimeException("请先进行登录!");
        }
        vo
                .setId(null)
                .setCreateDate(new Date())
                .setUpdateDate(new Date())
                .setOperationId(utils.getId(jwt));
        return RestBean.messageHandle(vo, service::saveHandler);
    }

    @LoggerPermission(operation = "修改职位信息")
    @PostMapping("/put")
    public <T>RestBean<Void> modify(HttpServletRequest request, @RequestBody @Validated Position vo) {
        String authorization = request.getHeader("Authorization");
        DecodedJWT jwt = utils.resolveJwt(authorization);
        if (jwt == null) {
            throw new RuntimeException("请先进行登录!");
        }
        vo
                .setUpdateDate(new Date())
                .setOperationId(utils.getId(jwt));
        return RestBean.messageHandle(vo, service::modifyHandler);
    }

    @LoggerPermission(operation = "删除职位")
    @GetMapping("/delete/{id}")
    public <T>RestBean<Void> remove(@PathVariable Integer id) {
        return RestBean.messageHandle(id, service::removeHandler);
    }

    @LoggerPermission(operation = "批量删除职位")
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
        String fileName = "Position_" + new Date() + ".xlsx";
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
        OutputStream out = response.getOutputStream();
        List<Position> rowss = CollUtil.newArrayList();
        rowss.addAll(service.list());
        ExcelWriter writer= ExcelUtil.getBigWriter();
        writer.write(rowss);
        writer.flush(out);
        writer.close();
    }

    @LoggerPermission(operation = "批量导入职位")
    @SneakyThrows
    @PostMapping("/post/excel")
    public <T> RestBean<Void> handleFileUpload(HttpServletRequest request, @RequestParam("file") MultipartFile file) {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        List<Position> positionList = reader.readAll(Position.class);
        return RestBean.messageHandle(positionList, service::saveHandler);
    }
}
