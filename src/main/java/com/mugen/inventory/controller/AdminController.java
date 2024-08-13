package com.mugen.inventory.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.mugen.inventory.entity.Admin;
import com.mugen.inventory.entity.model.vo.request.AdminQueryPageVo;
import com.mugen.inventory.entity.model.vo.response.AdminPageVo;
import com.mugen.inventory.entity.model.vo.response.AuthorizeVO;
import com.mugen.inventory.service.AdminService;
import com.mugen.inventory.utils.JwtUtils;
import com.mugen.inventory.utils.constant.ParameterConstant;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
 * admin 前端控制器
 * </p>
 *
 * @author Mieriki
 * @since 2024-07-28
 */
@RestController
@RequestMapping("/admins")
public class AdminController {
    @Resource
    private AdminService service;

    @Resource
    PasswordEncoder encoder;

    @Resource
    private JwtUtils utils;

    @GetMapping("/get")
    public <T>RestBean<List> list(){
        return RestBean.success(service.list());
    }

    @PostMapping("/get")
    public <T>RestBean<AdminPageVo> queryPage(@RequestBody @Validated AdminQueryPageVo vo) {
        return RestBean.success(service.queryPage(vo));
    }


    @GetMapping("/get/{id}")
    public <T>RestBean<Admin> query(@PathVariable Integer id) {
        return RestBean.success(service.getById(id));
    }

    @PostMapping("/post")
    public <T>RestBean<Void> save(@RequestBody @Validated Admin vo) {
        vo.setPassword(encoder.encode(ParameterConstant.DEFAULT_PASSWORD))
                .setUserFace(ParameterConstant.AVATAR_DEFAULT_URL)
                .setSlot(ParameterConstant.USRE_DEFAULT_SLOT);
        return RestBean.messageHandle(vo, service::saveHandler);
    }

    @PostMapping("/put/enabled")
    public <T>RestBean<Void> modifyEnabled(@RequestBody @Validated Admin vo) {
        return RestBean.messageHandle(vo, service::modifyHandler);
    }

    @PostMapping("/put")
    public <T>RestBean<Void> modify(@RequestBody @Validated Admin vo) {
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

    @GetMapping("/get/me")
    public <T>RestBean<Admin> getMe(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        DecodedJWT jwt = utils.resolveJwt(authorization);
        if (jwt == null) {
            throw new RuntimeException("请先进行登录!");
        }
        return RestBean.success(service.getById(utils.getId(jwt)), "请求成功");
    }

    @SneakyThrows
    @GetMapping("/get/excel")
    public void exportData(HttpServletResponse response) {
        String fileName = "Admin_" + new Date() + ".xlsx";
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
        OutputStream out = response.getOutputStream();
        List<Admin> rowss = CollUtil.newArrayList();
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
        List<Admin> adminList = reader.readAll(Admin.class);
        adminList = adminList.stream().map(admin -> admin.setPassword(encoder.encode(ParameterConstant.DEFAULT_PASSWORD))).toList();
        return RestBean.messageHandle(adminList, service::saveHandler);
    }
}
