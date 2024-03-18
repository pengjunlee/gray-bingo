package gray.bingo.mybatis.generator;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;
import com.baomidou.mybatisplus.generator.fill.Property;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Mybatis/Mybatis-Plus 代码生成器
 *
 * @作者 二月の菌
 * @版本 1.0
 * @日期 2024-01-21 16:15
 */

@Slf4j
public class BingoGenerator {

    /**
     * Mybatis-Plus 生成代码，适用于 mybatis-plus 3.5.1 之后版本
     *
     * @param tablesNames     要生成代码的表名
     * @param generatorConfig 生成代码的配置
     */
    public static void generate(String[] tablesNames, BingoGeneratorConfig generatorConfig) {

        FastAutoGenerator.create(generatorConfig.database())
                // 全局配置
                .globalConfig(builder -> {
                            builder.author(generatorConfig.author())
                                    .disableOpenDir()
                                    .dateType(DateType.ONLY_DATE)
                                    .commentDate("yyyy-MM-dd HH:mm:ss");
                            if (generatorConfig.enableSwagger()) {
                                builder.enableSwagger();
                            }
                        }
                )
                // 包配置
                .packageConfig(builder -> {
                            builder
                                    .parent("")
                                    .xml("mappers")
                                    .entity(generatorConfig.entityPackageName())
                                    .mapper(generatorConfig.mapperPackageName())
                                    .pathInfo(getPathInfo(generatorConfig));
                            if (generatorConfig.enableService()) {
                                builder.service(generatorConfig.servicePackageName())
                                        .serviceImpl(generatorConfig.serviceImplPackageName());
                            }

                            if (generatorConfig.enableController()) {
                                builder.controller(generatorConfig.controllerPackageName());

                            }
                        }
                )
                // 策略配置
                .strategyConfig(builder -> {
                            builder
                                    .addInclude(tablesNames)
                                    // entity
                                    .entityBuilder()
                                    .enableChainModel()
                                    .enableLombok()
                                    .enableRemoveIsPrefix()
                                    .logicDeleteColumnName("deleted")
                                    .idType(IdType.ASSIGN_ID)
                                    .addTableFills(new Column("create_time", FieldFill.INSERT))
                                    .addTableFills(new Property("updateTime", FieldFill.INSERT_UPDATE))
                                    .enableTableFieldAnnotation()
                                    // mapper
                                    .mapperBuilder()
                                    .enableBaseResultMap()
                                    .enableBaseColumnList()
                                    .superClass(BaseMapper.class)
                                    .formatMapperFileName("%sMapper")
                                    .formatXmlFileName("%sMapper");
                            if (generatorConfig.fileOverride()) {
                                builder.entityBuilder().enableFileOverride()
                                        .mapperBuilder().enableFileOverride();
                            }
                            if (generatorConfig.enableService()) {
                                // service
                                builder.serviceBuilder()
                                        //.enableFileOverride()
                                        .superServiceClass(IService.class)
                                        .formatServiceFileName(generatorConfig.serviceFileFormatter())
                                        .formatServiceImplFileName(generatorConfig.serviceImplFileFormatter());
                                if (generatorConfig.fileOverride()) {
                                    builder.serviceBuilder().enableFileOverride();
                                }
                            }

                            if (generatorConfig.enableController()) {
                                // controller
                                builder.controllerBuilder()
                                        //.enableFileOverride()
                                        .enableRestStyle()
                                        .formatFileName("%sController");
                                if (generatorConfig.fileOverride()) {
                                    builder.controllerBuilder().enableFileOverride();
                                }
                            }
                        }
                )
                // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }

    /**
     * 获取路径信息map
     */
    private static Map<OutputFile, String> getPathInfo(BingoGeneratorConfig generatorConfig) {
        Map<OutputFile, String> pathInfo = new HashMap<>(5);
        pathInfo.put(OutputFile.entity, generatorConfig.entityPath());
        pathInfo.put(OutputFile.mapper, generatorConfig.mapperPath());
        pathInfo.put(OutputFile.xml, generatorConfig.xmlPath());
        if (generatorConfig.enableService()) {
            pathInfo.put(OutputFile.service, generatorConfig.servicePath());
            pathInfo.put(OutputFile.serviceImpl, generatorConfig.serviceImplPath());
        }

        if (generatorConfig.enableController()) {
            pathInfo.put(OutputFile.controller, generatorConfig.controllerPath());
        }

        return pathInfo;
    }

    public static void main(String[] args) {
        // 数据库url
        final String DB_URL = "jdbc:mysql://192.168.2.2:3306/blossom?useUnicode=true&characterEncoding=UTF8&autoReconnect=true&zeroDateTimeBehavior=CONVERT_TO_NULL&useSSL=false&serverTimezone=Asia/Shanghai";
        // 数据库用户名
        final String USERNAME = "root";
        // 数据库密码
        final String PASSWORD = "123456";
        // 需要生成代码的数据表
        String[] tableNames = {"base_user"};
        BingoGeneratorConfig generatorConfig = BingoGeneratorConfig.build4MultiModule()
                .dataSource(DB_URL, USERNAME, PASSWORD)
                .author("二月の菌")
                .entity("demo-common", "gray.demo.common.entity")
                .mapper("demo-infrastructure", "gray.demo.infrastructure.mapper")
                .service("demo-infrastructure", "gray.demo.infrastructure.repo", "demo-infrastructure", "gray.demo.infrastructure.repo.impl","Repo")
                .controller("demo-adapter", "gray.demo.adapter.controller")
                .build();

//        BingoPlusConfig generatorConfig = BingoPlusConfig.build4SingleModule()
//                .author("二月の菌")
//                .enableSwagger(false)
//                .packageName("gray.demo.infrastructure")
//                .enableService(true)
//                .enableController(true)
//                .build();
        BingoGenerator.generate(tableNames, generatorConfig);
    }
}