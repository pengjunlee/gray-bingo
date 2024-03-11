package gray.bingo.mybatis.generator;

import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import gray.bingo.common.utils.LogicUtil;
import gray.bingo.common.utils.StringUtil;

import java.util.Objects;


/**
 * Mybatis-Plus 代码生成配置实体
 *
 * @作者 二月菌
 * @版本 1.0
 * @日期 2024-01-21 16:15
 */
public class BingoGeneratorConfig {

    /**
     * @return 单module项目专用Builder
     */
    public static SingleModuleBuilder build4SingleModule() {
        return new SingleModuleBuilder();
    }

    /**
     * @return 多module项目专用Builder
     */
    public static MultiModuleBuilder build4MultiModule() {
        return new MultiModuleBuilder();
    }

    /**
     * 单Module项目，代码生成配置的建造者
     */
    public static class SingleModuleBuilder {

        private final BingoGeneratorConfig generatorConfig;

        public SingleModuleBuilder() {
            super();
            this.generatorConfig = new BingoGeneratorConfig(false);
        }

        /**
         * 设置数据库
         * @param dbUrl 数据库Url
         * @param userName 用户名
         * @param password 密码
         * @return SingleModuleBuilder
         */
        public SingleModuleBuilder dataSource(String dbUrl,String userName,String password){
            DataSourceConfig.Builder builder = new DataSourceConfig.Builder(dbUrl, userName, password);
            this.generatorConfig.DATABASE = builder;
            return this;
        }



        /**
         * @param author 作者
         * @return SingleModuleBuilder
         */
        public SingleModuleBuilder author(String author) {
            this.generatorConfig.AUTHOR = author;
            return this;
        }

        /**
         * 启用swagger注解
         * @return SingleModuleBuilder
         */
        public SingleModuleBuilder enableSwagger() {
            this.generatorConfig.ENABLE_SWAGGER = true;
            return this;
        }

        /**
         * @param moduleName 模块名
         * @return SingleModuleBuilder
         */
        public SingleModuleBuilder moduleName(String moduleName) {
            this.generatorConfig.DEFAULT_MODULE_NAME = moduleName;
            return this;
        }

        /**
         * @param packageName 包名
         * @return SingleModuleBuilder
         */
        public SingleModuleBuilder packageName(String packageName) {
            this.generatorConfig.DEFAULT_PACKAGE_NAME = packageName;
            return this;
        }

        /**
         * 生成Service代码
         * @return SingleModuleBuilder
         */
        public SingleModuleBuilder enableService() {
            this.generatorConfig.ENABLE_SERVICE = true;
            return this;
        }

        /**
         * 生成Controller代码
         * @return SingleModuleBuilder
         */
        public SingleModuleBuilder enableController() {
            this.generatorConfig.ENABLE_CONTROLLER = true;
            return this;
        }

        /**
         * 构造代码生成配置
         * @return BingoGeneratorConfig
         */
        public BingoGeneratorConfig build() {
            beforeBuild();
            return this.generatorConfig;
        }

        /**
         * 构造配置前校验
         */
        private void beforeBuild() {
            LogicUtil.mustTrue(Objects.nonNull(this.generatorConfig.DATABASE)).throwException("数据库未设置！");
            LogicUtil.mustTrue(StringUtil.isNotBlank(this.generatorConfig.defaultPackageName())).throwException("包名未设置！");
        }

    }

    /**
     * 多Module项目，代码生成配置的建造者
     */
    public static class MultiModuleBuilder {

        private final BingoGeneratorConfig generatorConfig;


        public MultiModuleBuilder() {
            super();
            this.generatorConfig = new BingoGeneratorConfig(true);
        }

        /**
         * 设置数据库
         * @param dbUrl 数据库Url
         * @param userName 用户名
         * @param password 密码
         * @return MultiModuleBuilder
         */
        public MultiModuleBuilder dataSource(String dbUrl,String userName,String password){
            DataSourceConfig.Builder builder = new DataSourceConfig.Builder(dbUrl, userName, password);
            this.generatorConfig.DATABASE = builder;
            return this;
        }

        /**
         * 设置作者
         *
         * @param author 作者
         * @return MultiModuleBuilder
         */
        public MultiModuleBuilder author(String author) {
            this.generatorConfig.AUTHOR = author;
            return this;
        }

        /**
         * 启用Swagger注解，默认不启用
         * @return MultiModuleBuilder
         */
        public MultiModuleBuilder enableSwagger() {
            this.generatorConfig.ENABLE_SWAGGER = true;
            return this;
        }

        /**
         * 设置默认模块名
         * 生成的代码未设置moduleName时，会使用默认模块名
         *
         * @param defaultModuleName 默认模块名
         * @return MultiModuleBuilder
         */
        public MultiModuleBuilder defaultModuleName(String defaultModuleName) {
            this.generatorConfig.DEFAULT_MODULE_NAME = defaultModuleName;
            return this;
        }

        /**
         * 设置默认包名
         * 生成的代码未设置packageName时，会使用默认包名
         *
         * @param defaultPackageName 默认包名
         * @return MultiModuleBuilder
         */
        public MultiModuleBuilder defaultPackageName(String defaultPackageName) {
            this.generatorConfig.DEFAULT_PACKAGE_NAME = defaultPackageName;
            return this;
        }

        /**
         * 设置生成的entity代码所属的模块名和包名
         *
         * @param moduleName 模块名
         * @param packageName 包名
         * @return MultiModuleBuilder
         */
        public MultiModuleBuilder entity(String moduleName, String packageName) {
            this.generatorConfig.ENTITY_MODULE_NAME = moduleName;
            this.generatorConfig.ENTITY_PACKAGE_NAME = packageName;
            return this;
        }

        /**
         * 设置生成的mapper代码所属的模块名和包名
         *
         * @param moduleName 模块名
         * @param packageName 包名
         * @return MultiModuleBuilder
         */
        public MultiModuleBuilder mapper(String moduleName, String packageName) {
            this.generatorConfig.MAPPER_MODULE_NAME = moduleName;
            this.generatorConfig.MAPPER_PACKAGE_NAME = packageName;
            return this;
        }

        /**
         * 设置生成的service和serviceImpl代码所属的模块名和包名
         *
         * @param serviceModuleName service代码所属模块名
         * @param servicePackageName service代码包名
         * @param serviceImplModuleName serviceImpl代码所属模块名
         * @param serviceImplPackageName serviceImpl代码包名
         * @return MultiModuleBuilder
         */
        public MultiModuleBuilder service(String serviceModuleName, String servicePackageName, String serviceImplModuleName, String serviceImplPackageName) {
            this.generatorConfig.SERVICE_MODULE_NAME = serviceModuleName;
            this.generatorConfig.SERVICE_PACKAGE_NAME = servicePackageName;
            this.generatorConfig.SERVICE_IMPL_MODULE_NAME = serviceImplModuleName;
            this.generatorConfig.SERVICE_IMPL_PACKAGE_NAME = serviceImplPackageName;
            this.generatorConfig.ENABLE_SERVICE = true;
            return this;
        }


        /**
         * 设置生成的controller代码所属的模块名和包名
         *
         * @param moduleName 模块名
         * @param packageName 包名
         * @return MultiModuleBuilder
         */
        public MultiModuleBuilder controller(String moduleName, String packageName) {
            this.generatorConfig.CONTROLLER_MODULE_NAME = moduleName;
            this.generatorConfig.CONTROLLER_PACKAGE_NAME = packageName;
            this.generatorConfig.ENABLE_CONTROLLER = true;
            return this;
        }

        /**
         * 构造代码生成配置
         * @return BingoGeneratorConfig
         */
        public BingoGeneratorConfig build() {
            beforeBuild();
            return this.generatorConfig;
        }

        /**
         * 构造配置前校验
         */
        public void beforeBuild() {
            LogicUtil.mustTrue(Objects.nonNull(this.generatorConfig.DATABASE)).throwException("数据库未设置！");
            if (StringUtil.isNotBlank(this.generatorConfig.defaultPackageName())) return;
            LogicUtil.mustTrue(StringUtil.isNotBlank(this.generatorConfig.entityPackageName())).throwException("entity包名未设置！");
            LogicUtil.mustTrue(StringUtil.isNotBlank(this.generatorConfig.mapperPackageName())).throwException("mapper包名未设置！");
            if (this.generatorConfig.enableService()) {
                LogicUtil.mustTrue(StringUtil.isNotBlank(this.generatorConfig.servicePackageName())).throwException("service包名未设置！");
                LogicUtil.mustTrue(StringUtil.isNotBlank(this.generatorConfig.serviceImplPackageName())).throwException("serviceImpl包名未设置！");
            }

            if (this.generatorConfig.enableController()) {
                LogicUtil.mustTrue(StringUtil.isNotBlank(this.generatorConfig.controllerPackageName())).throwException("controller包名未设置！");
            }
        }

    }

    /**
     * 项目根路径
     */
    private static final String PARENT_DIR = System.getProperty("user.dir");

    /**
     * 路径分隔符
     */
    private static final String FILE_SEPARATOR = "/";

    /**
     * 源代码基本路径
     */
    private static final String SRC_MAIN_JAVA = "/src/main/java/";

    /**
     * 资源文件基本路径
     */
    private static final String SRC_MAIN_RESOURCES = "/src/main/resources/";

    private DataSourceConfig.Builder DATABASE;

    /**
     * 是否生成Service代码
     */
    private boolean ENABLE_SERVICE = false;

    /**
     * 是否生成Controller代码
     */
    private boolean ENABLE_CONTROLLER = false;

    /**
     * 作者
     */
    private String AUTHOR;

    /**
     * 启用swagger注解，默认不启用
     */
    private boolean ENABLE_SWAGGER = false;

    /**
     * 默认模块名
     */
    private String DEFAULT_MODULE_NAME;

    /**
     * 默认包名
     */
    private String DEFAULT_PACKAGE_NAME;

    /**
     * entity类所属模块
     */
    private String ENTITY_MODULE_NAME;

    /**
     * entity类包名
     */
    private String ENTITY_PACKAGE_NAME;

    /**
     * mapper类所属模块
     */
    private String MAPPER_MODULE_NAME;

    /**
     * mapper类包名
     */
    private String MAPPER_PACKAGE_NAME;

    /**
     * service类所属模块
     */
    private String SERVICE_MODULE_NAME;

    /**
     * service类包名
     */
    private String SERVICE_PACKAGE_NAME;

    /**
     * service实现类所属模块
     */
    private String SERVICE_IMPL_MODULE_NAME;

    /**
     * service实现类包名
     */
    private String SERVICE_IMPL_PACKAGE_NAME;

    /**
     * controller类所属模块
     */
    private String CONTROLLER_MODULE_NAME;

    /**
     * controller类包名
     */
    private String CONTROLLER_PACKAGE_NAME;


    private BingoGeneratorConfig(boolean multiModule) {
        super();
    }


    public DataSourceConfig.Builder database(){
        return DATABASE;
    }


    public String author() {
        return AUTHOR;
    }

    public boolean enableSwagger() {
        return ENABLE_SWAGGER;
    }

    public boolean enableService() {
        return ENABLE_SERVICE;
    }

    public boolean enableController() {
        return ENABLE_CONTROLLER;
    }

    public String defaultModuleName() {
        return DEFAULT_MODULE_NAME;
    }

    public String defaultPackageName() {
        return DEFAULT_PACKAGE_NAME;
    }

    public String entityModuleName() {
        return ENTITY_MODULE_NAME;
    }

    public String entityPackageName() {
        return StringUtil.isNotBlank(ENTITY_PACKAGE_NAME)?ENTITY_PACKAGE_NAME:DEFAULT_PACKAGE_NAME;
    }

    public String mapperModuleName() {
        return MAPPER_MODULE_NAME;
    }

    public String mapperPackageName() {
        return StringUtil.isNotBlank(MAPPER_PACKAGE_NAME)?MAPPER_PACKAGE_NAME:DEFAULT_PACKAGE_NAME;
    }

    public String serviceModuleName() {
        return SERVICE_MODULE_NAME;
    }

    public String servicePackageName() {
        return StringUtil.isNotBlank(SERVICE_PACKAGE_NAME)?SERVICE_PACKAGE_NAME:DEFAULT_PACKAGE_NAME;
    }

    public String serviceImplModuleName() {
        return SERVICE_IMPL_MODULE_NAME;
    }

    public String serviceImplPackageName() {
        return StringUtil.isNotBlank(SERVICE_IMPL_PACKAGE_NAME)?SERVICE_IMPL_PACKAGE_NAME:DEFAULT_PACKAGE_NAME;
    }

    public String controllerModuleName() {
        return CONTROLLER_MODULE_NAME;
    }

    public String controllerPackageName() {
        return StringUtil.isNotBlank(CONTROLLER_PACKAGE_NAME)?CONTROLLER_PACKAGE_NAME:DEFAULT_PACKAGE_NAME;
    }

    /**
     * xml文件存放路径
     */
    public String xmlPath() {
        String modulePath = StringUtil.isNotBlank(MAPPER_MODULE_NAME) ? FILE_SEPARATOR + MAPPER_MODULE_NAME : StringUtil.isNotBlank(DEFAULT_MODULE_NAME) ? FILE_SEPARATOR + DEFAULT_MODULE_NAME : "";
        return PARENT_DIR + modulePath + SRC_MAIN_RESOURCES + "mappers";
    }

    /**
     * entity文件存放路径
     */
    public String entityPath() {
        String modulePath = StringUtil.isNotBlank(ENTITY_MODULE_NAME) ? FILE_SEPARATOR + ENTITY_MODULE_NAME : StringUtil.isNotBlank(DEFAULT_MODULE_NAME) ? FILE_SEPARATOR + DEFAULT_MODULE_NAME : "";
        String packagePath = StringUtil.isNotBlank(ENTITY_PACKAGE_NAME) ? ENTITY_PACKAGE_NAME.replaceAll("\\.", FILE_SEPARATOR) : DEFAULT_PACKAGE_NAME.replaceAll("\\.", FILE_SEPARATOR) + FILE_SEPARATOR + "entity";
        return PARENT_DIR + modulePath + SRC_MAIN_JAVA + packagePath;
    }

    /**
     * mapper文件存放路径
     */
    public String mapperPath() {
        String modulePath = StringUtil.isNotBlank(MAPPER_MODULE_NAME) ? FILE_SEPARATOR + MAPPER_MODULE_NAME : StringUtil.isNotBlank(DEFAULT_MODULE_NAME) ? FILE_SEPARATOR + DEFAULT_MODULE_NAME : "";
        String packagePath = StringUtil.isNotBlank(MAPPER_PACKAGE_NAME) ? MAPPER_PACKAGE_NAME.replaceAll("\\.", FILE_SEPARATOR) : DEFAULT_PACKAGE_NAME.replaceAll("\\.", FILE_SEPARATOR) + FILE_SEPARATOR + "dao";
        return PARENT_DIR + modulePath + SRC_MAIN_JAVA + packagePath;
    }

    /**
     * service文件存放路径
     */
    public String servicePath() {
        String modulePath = StringUtil.isNotBlank(SERVICE_MODULE_NAME) ? FILE_SEPARATOR + SERVICE_MODULE_NAME : StringUtil.isNotBlank(DEFAULT_MODULE_NAME) ? FILE_SEPARATOR + DEFAULT_MODULE_NAME : "";
        String packagePath = StringUtil.isNotBlank(SERVICE_PACKAGE_NAME) ? SERVICE_PACKAGE_NAME.replaceAll("\\.", FILE_SEPARATOR) : DEFAULT_PACKAGE_NAME.replaceAll("\\.", FILE_SEPARATOR) + FILE_SEPARATOR + "service";
        return PARENT_DIR + modulePath + SRC_MAIN_JAVA + packagePath;
    }

    /**
     * mapper文件存放路径
     */
    public String serviceImplPath() {
        String modulePath = StringUtil.isNotBlank(SERVICE_IMPL_MODULE_NAME) ? FILE_SEPARATOR + SERVICE_IMPL_MODULE_NAME : StringUtil.isNotBlank(DEFAULT_MODULE_NAME) ? FILE_SEPARATOR + DEFAULT_MODULE_NAME : "";
        String packagePath = StringUtil.isNotBlank(SERVICE_IMPL_PACKAGE_NAME) ? SERVICE_IMPL_PACKAGE_NAME.replaceAll("\\.", FILE_SEPARATOR) : DEFAULT_PACKAGE_NAME.replaceAll("\\.", FILE_SEPARATOR) + FILE_SEPARATOR + "service/impl";
        return PARENT_DIR + modulePath + SRC_MAIN_JAVA + packagePath;
    }

    /**
     * controller文件存放路径
     */
    public String controllerPath() {
        String modulePath = StringUtil.isNotBlank(CONTROLLER_MODULE_NAME) ? FILE_SEPARATOR + CONTROLLER_MODULE_NAME : StringUtil.isNotBlank(DEFAULT_MODULE_NAME) ? FILE_SEPARATOR + DEFAULT_MODULE_NAME : "";
        String packagePath = StringUtil.isNotBlank(CONTROLLER_PACKAGE_NAME) ? CONTROLLER_PACKAGE_NAME.replaceAll("\\.", FILE_SEPARATOR) : DEFAULT_PACKAGE_NAME.replaceAll("\\.", FILE_SEPARATOR) + FILE_SEPARATOR + "controller";
        return PARENT_DIR + modulePath + SRC_MAIN_JAVA + packagePath;
    }


    /**
     * entity包名
     */
    public String entityPackage() {
        return StringUtil.isNotBlank(ENTITY_PACKAGE_NAME) ? ENTITY_PACKAGE_NAME : DEFAULT_PACKAGE_NAME + ".entity";
    }

    /**
     * mapper包名
     */
    public String mapperPackage() {
        return StringUtil.isNotBlank(MAPPER_PACKAGE_NAME) ? MAPPER_PACKAGE_NAME : DEFAULT_PACKAGE_NAME + ".dao";
    }

    /**
     * service包名
     */
    public String servicePackage() {
        return StringUtil.isNotBlank(SERVICE_PACKAGE_NAME) ? SERVICE_PACKAGE_NAME : DEFAULT_PACKAGE_NAME + ".service";
    }

    /**
     * serviceImpl包名
     */
    public String serviceImplPackage() {
        return StringUtil.isNotBlank(SERVICE_IMPL_PACKAGE_NAME) ? SERVICE_IMPL_PACKAGE_NAME : DEFAULT_PACKAGE_NAME + ".serviceImpl";
    }

    /**
     * controller包名
     */
    public String controllerPackage() {
        return StringUtil.isNotBlank(CONTROLLER_PACKAGE_NAME) ? CONTROLLER_PACKAGE_NAME : DEFAULT_PACKAGE_NAME + ".controller";
    }
}
