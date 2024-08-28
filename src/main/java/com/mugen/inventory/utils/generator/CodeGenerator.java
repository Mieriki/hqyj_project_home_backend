package com.mugen.inventory.utils.generator;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;
import com.baomidou.mybatisplus.generator.fill.Property;

import java.sql.Types;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CodeGenerator {
    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql:///mugen_rskr", "root", "Inaba")
                .globalConfig(builder -> {
                    builder.author("Mieriki") // 设置作者
//                            .enableSwagger() // 开启 swagger 模式
                            .outputDir(System.getProperty("user.dir") + "/src/main/java")
                            .commentDate("yyyy-MM-dd")
                            .dateType(DateType.ONLY_DATE)
                            .disableOpenDir();
                })
                .dataSourceConfig(builder ->
                        builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                            int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                            if (typeCode == Types.SMALLINT) {
                                // 自定义类型转换
                                return DbColumnType.INTEGER;
                            }
                            return typeRegistry.getColumnType(metaInfo);
                        })
                )
                .packageConfig(builder -> builder
                        .parent("com.mugen.inventory")
                        .entity("entity")
                        .mapper("mapper")
                        .service("service")
                        .serviceImpl("service.impl")
                        .controller("controller")
                        .xml("mapper") // 设置 Mapper XML 包名
                        .controller("controller") // 设置 Controller 包名
                        .pathInfo(Collections.singletonMap(OutputFile.xml, System.getProperty("user.dir") + "/src/main/resources/mapper"))

                )
                .strategyConfig(builder ->
                        builder.addInclude("t_salary") // 设置需要生成的表名
                                .addTablePrefix("t_", "c_") // 设置过滤表前缀
                                .entityBuilder()
                                .enableLombok() // 启用 Lombok
                                .enableTableFieldAnnotation() // 启用字段注解
                                .controllerBuilder()
                                .enableRestStyle()
                                .entityBuilder()// 实体类策略配置
                                .idType(IdType.ASSIGN_ID)//主键策略  雪花算法自动生成的id
                                .addTableFills(new Column("create_time", FieldFill.INSERT)) // 自动填充配置
                                .addTableFills(new Property("update_time", FieldFill.INSERT_UPDATE))
                                .enableLombok() //开启lombok
                                .logicDeleteColumnName("deleted")// 说明逻辑删除是哪个字段
                                .enableTableFieldAnnotation()// 属性加上注解说明
                                .controllerBuilder() //controller 策略配置
                                .formatFileName("%sController")
                                .enableRestStyle() // 开启RestController注解
                                .mapperBuilder()// mapper策略配置
                                .formatMapperFileName("%sMapper")
                                .formatXmlFileName("%sMapper")
                )
                .strategyConfig(builder ->
                        builder.serviceBuilder().formatServiceFileName("%sService")
                                .formatServiceImplFileName("%sServiceImp") // 增加过滤表前缀
                                .build())
//                .injectionConfig(consumer -> {
//                    Map<String, String> customFile = new HashMap<>();
//                    // DTO
//                    customFile.put("DTO.java", "/templates/entityDTO.java.ftl");
//                    consumer.customFile(customFile);
//                })
//                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
