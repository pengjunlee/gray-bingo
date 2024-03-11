package gray.bingo.mybatis.generator;

import com.baomidou.mybatisplus.generator.config.DataSourceConfig;

import java.io.InputStream;

/**
 * Mybatis代码生成测试类
 *
 * @作者 二月菌
 * @版本 1.0
 * @日期 2024-01-21 16:16
 */
public class BingoGeneratorTest {

    /**
     * 测试Mybatis代码生成
     */
    private static void mybatisGenerate() {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("leech.xml");
        BingoGenerator.generate(is);
    }


    /**
     * 测试Mybatis-Plus代码生成
     */
    private static void mybatisPlusGenerate() {
        /**
         * 数据库url
         */
        final String DB_URL = "jdbc:mysql://192.168.2.2:3306/blossom?useUnicode=true&characterEncoding=UTF8&autoReconnect=true&zeroDateTimeBehavior=CONVERT_TO_NULL&useSSL=false&serverTimezone=Asia/Shanghai";
        /**
         * 数据库用户名
         */
        final String USERNAME = "root";
        /**
         * 数据库密码
         */
        final String PASSWORD = "123456";
        DataSourceConfig.Builder builder = new DataSourceConfig.Builder(DB_URL, USERNAME, PASSWORD);
        String[] tableNames = {"base_user"};
        BingoGeneratorConfig generatorConfig = BingoGeneratorConfig.build4MultiModule()
                .author("二月菌")
                .enableSwagger()
                // .entity("demo-common", "gray.demo.common.entity")
                .entity("", "")
                .mapper("demo-infrastructure", "gray.demo.infrastructure.mapper")
                .service("demo-infrastructure", "gray.demo.infrastructure.service", "demo-infrastructure", "gray.demo.infrastructure.service.impl")
                // .controller("demo-adapter", "gray.demo.adapter.controller")
                .build();

//        BingoPlusConfig generatorConfig = BingoPlusConfig.build4SingleModule()
//                .author("二月菌")
//                .enableSwagger(false)
//                .packageName("gray.demo.infrastructure")
//                .enableService(true)
//                .enableController(true)
//                .build();
        BingoGenerator.generate(builder, tableNames, generatorConfig);
    }

    /**
     * 代码示例
     */
    public static void main(String[] args) {

        // 测试Mybatis
        mybatisGenerate();

        // 测试mybatis-plus
        mybatisPlusGenerate();

    }
}