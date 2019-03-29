package com.zzg.mybatis.generator.plugins;

/**
 * Created by zouzhigang on 2016/6/14.
 */

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class MySQLSelectByPlugin extends PluginAdapter {

    @Override
    public boolean validate(List<String> list) {
        return true;
    }

    /**
     * 为每个Example类添加limit和offset属性已经set、get方法
     */
    @Override
    public boolean modelExampleClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        try {
            Constructor<PrimitiveTypeWrapper> declaredConstructor = PrimitiveTypeWrapper.class.getDeclaredConstructor(String.class, String.class);
            declaredConstructor.setAccessible(true);
            PrimitiveTypeWrapper stringWrapper = declaredConstructor.newInstance("java.lang.String", "stringValue()");

            Field selectByClause = new Field();
            selectByClause.setName("selectByClause");
            selectByClause.setVisibility(JavaVisibility.PRIVATE);
            selectByClause.setType(stringWrapper);
            topLevelClass.addField(selectByClause);

            Method setGroupByClause = new Method();
            setGroupByClause.setVisibility(JavaVisibility.PUBLIC);
            setGroupByClause.setName("setSelectByClause");
            setGroupByClause.addParameter(new Parameter(stringWrapper, "selectByClause"));
            setGroupByClause.addBodyLine("this.selectByClause = selectByClause;");
            topLevelClass.addMethod(setGroupByClause);

            Method getSelectByClause = new Method();
            getSelectByClause.setVisibility(JavaVisibility.PUBLIC);
            getSelectByClause.setReturnType(stringWrapper);
            getSelectByClause.setName("getSelectByClause");
            getSelectByClause.addBodyLine("return selectByClause;");
            topLevelClass.addMethod(getSelectByClause);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 为Mapper.xml的selectByExample添加limit
     */
    @Override
    public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(XmlElement element,
                                                                     IntrospectedTable introspectedTable) {
        XmlElement ifSelectByNotNullElement = new XmlElement("if");
        ifSelectByNotNullElement.addAttribute(new Attribute("test", "selectByClause != null"));
        ifSelectByNotNullElement.addElement(new TextElement("${selectByClause}"));
        XmlElement ifSelectByNullElement = new XmlElement("if");
        ifSelectByNullElement.addAttribute(new Attribute("test", "selectByClause == null"));
        ifSelectByNullElement.addElement(element.getElements().get(2));
        element.getElements().remove(2);
        element.addElement(2,ifSelectByNotNullElement);
        element.addElement(3,ifSelectByNullElement);
        return true;
    }
}
