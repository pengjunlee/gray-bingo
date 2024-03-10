package gray.bingo.mybatis.generator;/*
 * Copyright (c) 2018, S.F. Express Inc. All rights reserved.
 */

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.internal.DefaultCommentGenerator;
import org.mybatis.generator.internal.util.StringUtility;


/**
 * CaffeineHelper的建造者
 *
 * @作者 二月菌
 * @版本 1.0
 * @日期 2024-01-21 16:15
 */
public class BingoCommentGenerator extends DefaultCommentGenerator {

    //是否生成Swagger 注解
    private Boolean swaggerFlag = false;

    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        // 添加字段注释
        field.addJavaDocLine("/**");
        if (introspectedColumn.getRemarks() != null)
            field.addJavaDocLine(" * " + introspectedColumn.getRemarks());
        field.addJavaDocLine(" */");

        //添加Swagger 注解
        if (swaggerFlag) {
            field.addJavaDocLine("@ApiModelProperty(value=\"" + introspectedColumn.getRemarks() + "\",name=\"" + introspectedColumn.getJavaProperty() + "\",required=true,example=\"\")");
        }

    }


    @Override
    public void addModelClassComment(TopLevelClass topLevelClass,
                                     IntrospectedTable introspectedTable) {
        StringBuilder sb = new StringBuilder();
        topLevelClass.addJavaDocLine("/**"); //$NON-NLS-1$
        String remarks = introspectedTable.getRemarks();
        if (StringUtility.stringHasValue(remarks)) {
            topLevelClass.addJavaDocLine(" * " + (remarks == null ? "" : remarks) + " Entity类");
        }
        sb.append(" * 对应 "); //$NON-NLS-1$
        sb.append(introspectedTable.getFullyQualifiedTable());
        sb.append("表 ");
        topLevelClass.addJavaDocLine(sb.toString());
        topLevelClass.addJavaDocLine(" * ");
        topLevelClass.addJavaDocLine(" * @author " + System.getProperty("user.name"));
        topLevelClass.addJavaDocLine(" */");

        //添加Swagger 注解
        if (swaggerFlag) {
            StringBuilder swagger = new StringBuilder();
            swagger.append("@ApiModel(value=\"" + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "\",description=\"" + introspectedTable.getRemarks() + "\")");
            topLevelClass.addJavaDocLine(swagger.toString());
        }
    }


}