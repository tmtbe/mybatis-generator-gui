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

public class MySQLGroupByPlugin extends PluginAdapter {

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

            Field groupByClause = new Field();
            groupByClause.setName("groupByClause");
            groupByClause.setVisibility(JavaVisibility.PRIVATE);
            groupByClause.setType(stringWrapper);
            topLevelClass.addField(groupByClause);

            Method setGroupByClause = new Method();
            setGroupByClause.setVisibility(JavaVisibility.PUBLIC);
            setGroupByClause.setName("setGroupByClause");
            setGroupByClause.addParameter(new Parameter(stringWrapper, "groupByClause"));
            setGroupByClause.addBodyLine("this.groupByClause = groupByClause;");
            topLevelClass.addMethod(setGroupByClause);

            Method getGroupByClause = new Method();
            getGroupByClause.setVisibility(JavaVisibility.PUBLIC);
            getGroupByClause.setReturnType(stringWrapper);
            getGroupByClause.setName("getGroupByClause");
            getGroupByClause.addBodyLine("return groupByClause;");
            topLevelClass.addMethod(getGroupByClause);
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
        XmlElement ifGroupByNotNullElement = new XmlElement("if");
        ifGroupByNotNullElement.addAttribute(new Attribute("test", "groupByClause != null"));
        ifGroupByNotNullElement.addElement(new TextElement(" group by ${groupByClause}"));
        element.addElement(5, ifGroupByNotNullElement);

        return true;
    }

    /**
     * 为Mapper.xml的selectByExampleWithBLOBs添加limit
     */
    @Override
    public boolean sqlMapSelectByExampleWithBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        XmlElement ifGroupByNotNullElement = new XmlElement("if");
        ifGroupByNotNullElement.addAttribute(new Attribute("test", "groupByClause != null"));
        ifGroupByNotNullElement.addElement(new TextElement(" group by ${groupByClause}"));
        element.addElement(5, ifGroupByNotNullElement);
        return true;
    }
}
