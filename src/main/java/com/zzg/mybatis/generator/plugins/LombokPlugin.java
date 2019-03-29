package com.zzg.mybatis.generator.plugins;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.util.List;

/**
 * Project: mybatis-generator-gui
 *
 * @author slankka on 2017/12/13.
 */
public class LombokPlugin extends PluginAdapter {

    private FullyQualifiedJavaType annotationRepository;
    private String annotation = "@Builder";

    public LombokPlugin() {
        annotationRepository = new FullyQualifiedJavaType("lombok.Builder"); //$NON-NLS-1$
    }

    @Override
    public boolean validate(List<String> list) {
        return true;
    }

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        topLevelClass.addImportedType(annotationRepository);
        topLevelClass.addAnnotation(annotation);
        return true;
    }

    @Override
    public boolean modelExampleClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        topLevelClass.addImportedType(annotationRepository);
        topLevelClass.addAnnotation(annotation);
        return true;
    }
}
