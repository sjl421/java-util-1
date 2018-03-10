package com.smy.util.create;

import com.smy.model.BigBean;
import com.smy.model.SmallBean;
import com.smy.model.User;
import net.sf.cglib.beans.BeanMap;
import net.sf.cglib.core.ReflectUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;

import static org.testng.Assert.*;

/**
 * <p>Author: smy
 * <p>Date: 2018/3/10
 */
public class BeanCreatorTest {

    @DataProvider
    public Object[][] data() {
        return new Object[][]{
                {SmallBean.class, 10000},
                {SmallBean.class, 1000000},
                {BigBean.class, 10000},
                {BigBean.class, 1000000},
        };
    }

    @Test(dataProvider = "data",enabled = false)
    public void testJava(Class c, int count) throws Exception {
        Object o = c.newInstance();
        Field[] fields = c.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            field.set(o, 100);
        }
        long time = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            o = c.newInstance();
            for (Field field : fields) {
                field.set(o, 100);
            }
        }
        System.out.println("Java:" + count + ":" + (System.currentTimeMillis() - time));
    }

    @Test(dataProvider = "data",enabled = false)
    public void testCglib1(Class c, int count) throws Exception {
        Object o = c.newInstance();
        BeanMap beanMap = BeanMap.create(o);
        Field[] fields = c.getDeclaredFields();
        for (Field field : fields) {
            beanMap.put(field.getName(), 100);
            o = beanMap.getBean();
        }
        long time = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            o = c.newInstance();
            beanMap = BeanMap.create(o);
            for (Field field : fields) {
                beanMap.put(field.getName(), 100);
                o = beanMap.getBean();
            }
        }
        System.out.println("Cglib1:" + count + ":" + (System.currentTimeMillis() - time));
    }

    @Test(dataProvider = "data",enabled = false)
    public void testCglib2(Class c, int count) throws Exception {
        PropertyDescriptor[] properties = ReflectUtils.getBeanProperties(c);
        Object o = c.newInstance();
        BeanMap beanMap = BeanMap.create(o);
        for (PropertyDescriptor property : properties) {
            beanMap.put(property.getName(), 100);
            o = beanMap.getBean();
        }
        long time = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            o = c.newInstance();
            beanMap = BeanMap.create(o);
            for (PropertyDescriptor field : properties) {
                for (PropertyDescriptor property : properties) {
                    beanMap.put(property.getName(), 100);
                    o = beanMap.getBean();
                }
            }
        }
        System.out.println("Cglib2:" + count + ":" + (System.currentTimeMillis() - time));
    }
}