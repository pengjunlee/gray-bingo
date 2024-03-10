package gray.bingo.mybatis.generator;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
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
 * Mybatis/Mybatis-Plus 实体生成工具类
 *
 * @作者 二月菌
 * @版本 1.0
 * @日期 2024-01-21 16:15
 */

@Slf4j
public class BingoMybatisGenerator {


    /**
     * mybatis 实体生成方法
     *
     * @param is 配置文件流
     */
    public static void generate(InputStream is) {
        try {
            List<String> warnings = new ArrayList<String>();
            boolean overwrite = true;
            /*ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream is = classloader.getResourceAsStream("leech.xml");*/
            ConfigurationParser cp = new ConfigurationParser(warnings);
            Configuration config = cp.parseConfiguration(is);
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
            myBatisGenerator.generate(null);
            System.out.println("end");
        } catch (SQLException e) {
            log.error("SQLException:{}", e.getMessage());
        } catch (IOException e) {
            log.error("IOException:{}", e.getMessage());
        } catch (InterruptedException e) {
            log.error("InterruptedException:{}", e.getMessage());
        } catch (InvalidConfigurationException e) {
            log.error("InvalidConfigurationException:{}", e.getMessage());
        } catch (XMLParserException e) {
            log.error("XMLParserException:{}", e.getMessage());
        }
    }


    /**
     * Mybatis-Plus 实体生成方法，适用于 mybatis-plus 3.5.1 之后版本
     *
     * @param dataSourceConfig 数据源配置
     * @param tablesNames      要生成代码的表名
     * @param generatorConfig  生成代码的配置
     */
    public static void generate(DataSourceConfig.Builder dataSourceConfig, String[] tablesNames, BingoPlusConfig generatorConfig) {

        FastAutoGenerator.create(dataSourceConfig)
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
                                    .mapper(generatorConfig.mapperPackageName()).pathInfo(getPathInfo(generatorConfig));
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
                                    //.enableFileOverride()
                                    .enableChainModel()
                                    .enableLombok()
                                    .enableRemoveIsPrefix()
                                    .logicDeleteColumnName("is_delete")
                                    .idType(IdType.ASSIGN_ID)
                                    .addTableFills(new Column("create_time", FieldFill.INSERT))
                                    .addTableFills(new Property("updateTime", FieldFill.INSERT_UPDATE))
                                    // mapper
                                    .mapperBuilder()
                                    //.enableFileOverride()
                                    .enableBaseResultMap()
                                    .enableBaseColumnList()
                                    .superClass(BaseMapper.class)
                                    .formatMapperFileName("%sMapper")
                                    .formatXmlFileName("%sMapper")
                                    .enableMapperAnnotation();

                            if (generatorConfig.enableService()) {
                                // service
                                builder.serviceBuilder()
                                        //.enableFileOverride()
                                        .superServiceClass(IService.class)
                                        .formatServiceFileName("%sService")
                                        .formatServiceImplFileName("%sServiceImp");
                            }

                            if (generatorConfig.enableController()) {
                                // controller
                                builder.controllerBuilder()
                                        //.enableFileOverride()
                                        .enableRestStyle()
                                        .formatFileName("%sController");
                            }
                        }
                )
                // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }

    /**
     * 获取路径信息map
     *
     * @return {@link Map <  OutputFile , String> }
     * @author MK
     * @date 2022/4/21 21:21
     */
    private static Map<OutputFile, String> getPathInfo(BingoPlusConfig generatorConfig) {
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
    /**
     * 代码示例
     */

//    public static void main(String[] args) {
//        /**
//         * 数据库url
//         */
//        final String DB_URL = "jdbc:mysql://192.168.2.2:3306/blossom?useUnicode=true&characterEncoding=UTF8&autoReconnect=true&zeroDateTimeBehavior=CONVERT_TO_NULL&useSSL=false&serverTimezone=Asia/Shanghai";
//        /**
//         * 数据库用户名
//         */
//        final String USERNAME = "root";
//        /**
//         * 数据库密码
//         */
//        final String PASSWORD = "123456";
//        DataSourceConfig.Builder builder = new DataSourceConfig.Builder(DB_URL, USERNAME, PASSWORD);
//        String[] tableNames = {"base_user"};
//        BingoPlusGeneratorConfig generatorConfig = BingoPlusGeneratorConfig.build4MultiModule()
//                .author("二月菌")
//                .enableSwagger(false)
//                // .entity("demo-common", "gray.demo.common.entity")
//                .entity("", "")
//                .mapper("demo-infrastructure", "gray.demo.infrastructure.mapper")
//                .service("demo-infrastructure", "gray.demo.infrastructure.service", "demo-infrastructure", "gray.demo.infrastructure.service.impl")
//                // .controller("demo-adapter", "gray.demo.adapter.controller")
//                .build();
//
//        BingoPlusGeneratorConfig generatorConfig = BingoPlusGeneratorConfig.build4SingleModule()
//                .author("二月菌")
//                .enableSwagger(false)
//                .packageName("gray.demo.infrastructure")
//                .enableService(true)
//                .enableController(true)
//                .build();
//        BingoPlusGenerator.generate(builder, tableNames, generatorConfig);
//
//    }
}