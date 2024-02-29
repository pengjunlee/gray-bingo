package com.bingo.mybatis.generator;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.bingo.common.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

/**
 * Mybatis-Plus 实体生成工具类
 */
public class SapperPlusGenerator {

    private static String AUTHOR = "二月菌";

    private static String ROOT_FOLDER = null;

    private static String ROOT_PACKAGE = null;

    private static String MODULE_NAME = null;

    private static String MAPPER_PACKAGE = "mapper";

    private static String ENTITY_PACKAGE = "po";

    private static String SERVICE_PACKAGE = "service";

    private static String SERVICE_IMPL_PACKAGE = "service.impl";

    private static String CONTROLLER_PACKAGE = "controller";

    // 默认 null, 例如 %sEntity
    private static String ENTITY_NAME = "%sEntity";

    // 默认 null, 例如 %sMapper
    private static String MAPPER_NAME = "%sMapper";

    // 默认 null, 例如 %sService
    private static String SERVICE_NAME = "%sService";

    // 默认 null, 例如 %sServiceImpl
    private static String SERVICE_IMPL_NAME = "%sServiceImpl";

    // 默认 null, 例如 %sController
    private static String CONTROLLER_NAME = "%sController";
    // 默认 Entity

    private static Boolean OPEN_FOLDER = false;
    private static Boolean GENERATE_CONTROLLER = false;

    /**
     * 数据源配置
     * DataSourceConfig dsc = new DataSourceConfig();
     * dsc.setUrl("jdbc:mysql://192.168.2.2:3306/gray-dev?useUnicode=true&characterEncoding=UTF8&autoReconnect=true&zeroDateTimeBehavior=CONVERT_TO_NULL&useSSL=false&serverTimezone=Asia/Shanghai");
     * dsc.setSchemaName("public");
     * dsc.setDriverName("com.mysql.cj.jdbc.Driver");
     * dsc.setUsername("root");
     * dsc.setPassword("123456");
     * @param dataSourceConfig
     */


    public static void generate(DataSourceConfig dataSourceConfig) {

        if (Objects.isNull(dataSourceConfig)) {
            System.out.println("请设置数据源!");
            return;
        }

        // 指定代码存放目录
        if (StringUtil.isBlank(ROOT_FOLDER)) {
            System.out.println("请设置文件输出目录!");
            return;
        }

        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();
        // 全局配置
        GlobalConfig globalConfig = getGlobalConfig();
        mpg.setGlobalConfig(globalConfig);
        mpg.setDataSource(dataSourceConfig);

        // 包配置
        PackageConfig packageConfig = getPackageConfig();
        mpg.setPackageInfo(packageConfig);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return ROOT_FOLDER + "/src/main/resources/mapper/" + /*pc.getModuleName() + "/" +*/
                        tableInfo.getEntityName() + "Mapper" +
                        StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);
        mpg.setTemplate(new TemplateConfig().setXml(null).disable(TemplateType.CONTROLLER).disable(TemplateType.SERVICE));

        // 生成策略
        StrategyConfig strategyConfig = getStrategyConfig();
        mpg.setStrategy(strategyConfig);

        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }


    private static StrategyConfig getStrategyConfig(){
        // 策略配置	数据库表配置，通过该配置，可指定需要生成哪些表或者排除哪些表
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);         //表名生成策略
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);   //数据库表字段映射到实体的命名策略, 未指定按照 naming 执行
//	    strategy.setCapitalMode(true);			                       // 全局大写命名 ORACLE 注意
//	    strategy.setTablePrefix("prefix");		                       //表前缀
//      strategy.setSuperEntityClass(baseEntity.clazz());              //自定义继承的Entity类全称，带包名
//      strategy.setSuperEntityColumns(baseEntity.columns());          //自定义实体，公共字段
        strategy.setEntityLombokModel(false);                           //【实体】是否为lombok模型（默认 false
        strategy.setRestControllerStyle(GENERATE_CONTROLLER);          //生成 @RestController 控制器
//	    strategy.setSuperControllerClass("com.baomidou.ant.common.BaseController");	//自定义继承的Controller类全称，带包名
//      strategy.setInclude(scanner("表名"));		                 //需要包含的表名，允许正则表达式（与exclude二选一配置）
        strategy.setEntityTableFieldAnnotationEnable(true);
        System.out.println("请输入映射的表名:");

        //获取控制台的数据
        Scanner scanner = new Scanner(System.in);
        String tables = scanner.nextLine();
        String[] tableNames = tables.split(",");
        strategy.setInclude(tableNames);                            // 需要生成的表可以多张表
//	    strategy.setExclude(new String[]{"test"});      // 排除生成的表
//      如果数据库有前缀，生成文件时是否要前缀acl_
//      strategy.setTablePrefix("bus_");
//      strategy.setTablePrefix("sys_");
        strategy.setControllerMappingHyphenStyle(true);        // 驼峰转连字符
        strategy.setTablePrefix(MODULE_NAME + "_");     // 是否生成实体时，生成字段注解
        return strategy;
    }

    /**
     * 获取全局配置
     * @return
     */
    private static GlobalConfig getGlobalConfig(){
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(ROOT_FOLDER + "/src/main/java");       //生成文件的输出目录
        gc.setAuthor(AUTHOR);                                       //作者
        gc.setFileOverride(true);                                   //是否覆蓋已有文件 默认值：false
        gc.setOpen(OPEN_FOLDER);                                           //是否打开输出目录 默认值:true
        gc.setBaseColumnList(true);                                 //开启 baseColumnList 默认false
        gc.setBaseResultMap(true);                                  //开启 BaseResultMap 默认false
        gc.setEntityName(ENTITY_NAME);                              //实体命名方式  默认值：null 例如：%sEntity 生成 UserEntity
        gc.setMapperName(MAPPER_NAME);                              //mapper 命名方式 默认值：null 例如：%sDao 生成 UserDao
        gc.setXmlName(MAPPER_NAME);                                 //Mapper xml 命名方式   默认值：null 例如：%sDao 生成 UserDao.xml
        gc.setServiceName(SERVICE_NAME);                            //service 命名方式   默认值：null 例如：%sBusiness 生成 UserBusiness
        gc.setServiceImplName(SERVICE_IMPL_NAME);                   //service impl 命名方式  默认值：null 例如：%sBusinessImpl 生成 UserBusinessImpl
        gc.setControllerName(CONTROLLER_NAME);                      //controller 命名方式    默认值：null 例如：%sAction 生成 UserAction
        return gc;
    }

    /**
     * 获取包配置
     * @return
     */
    private static PackageConfig getPackageConfig(){
        // 包配置
        PackageConfig pc = new PackageConfig();
        //自定义包配置
        pc.setParent(ROOT_PACKAGE);
        pc.setModuleName(MODULE_NAME);
        pc.setMapper(MAPPER_PACKAGE);
        pc.setEntity(ENTITY_PACKAGE);
        pc.setService(SERVICE_PACKAGE);
        pc.setServiceImpl(SERVICE_IMPL_PACKAGE);
        pc.setController(CONTROLLER_PACKAGE);
        return pc;
    }

    public static void setEntityName(String entityName) {
        ENTITY_NAME = entityName;
    }

    public static void setRootFolder(String rootFolder) {
        ROOT_FOLDER = rootFolder;
    }

    public static void setOpenFolder(Boolean openFolder) {
        OPEN_FOLDER = openFolder;
    }

    public static void setRootPackage(String rootPackage) {
        ROOT_PACKAGE = rootPackage;
    }
}