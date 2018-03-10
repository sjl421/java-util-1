package com.smy.util.create;

import com.smy.util.common.ObjectUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * bean对象创建。
 * 经基于java8的测试:
 * 1、{@link java.beans.PropertyDescriptor}性能低于{@link java.lang.reflect.Field}
 * 2、java8 性能优于cglib
 * <p>Author: smy
 * <p>Date: 2018/2/1
 */
public class BeanCreator<T> implements Creator<T> {

    private Class<T> paramType;

    private List<Field> fields;

    private Map<Class, Creator> typeCreators = new HashMap<>();

    private Map<String, Creator> fieldCreators = new HashMap<>();

    private Creator defaultCreator = () -> null;

    protected BeanCreator() {
        setParamType(ObjectUtil.getParamType(this.getClass(), 0));
        initType();
    }

    public BeanCreator(Class<T> paramType) {
        setParamType(paramType);
        initType();
    }

    public void setParamType(Class<T> paramType) {
        this.paramType = paramType;
        this.fields = getField(paramType);
    }

    private List<Field> getField(Class c) {
        List<Field> fields = new ArrayList<>();
        while (c != Object.class) {
            Stream.of(c.getDeclaredFields()).forEach(f -> fields.add(f));
            c = c.getSuperclass();
        }
        return fields;
    }

    protected void initType() {
        addType(boolean.class, () -> false);
        addType(byte.class, () -> (byte) 0);
        addType(short.class, () -> (short) 0);
        addType(int.class, () -> 0);
        addType(long.class, () -> 0L);
        addType(float.class, () -> 0.0f);
        addType(double.class, () -> 0.0);
        addType(char.class, () -> '0');
    }

    public BeanCreator<T> addField(String fieldName, Creator creator) {
        fieldCreators.put(fieldName, creator);
        return this;
    }

    public <Type> BeanCreator<T> addType(Class<Type> c, Creator<Type> creator) {
        typeCreators.put(c, creator);
        return this;
    }

    public BeanCreator<T> setDefault(Creator defaultCreator) {
        this.defaultCreator = defaultCreator;
        return this;
    }

    @Override
    public T create() {
        T t = ObjectUtil.newInstance(paramType);
        fields.forEach(f -> {
            ObjectUtil.set(t, f, findFieldCreator(f).createOne());
        });
        return t;
    }

    private Creator findFieldCreator(Field property) {
        Creator creator = fieldCreators.get(property.getName());
        if (creator != null) {
            return creator;
        }
        creator = typeCreators.get(property.getType());
        return creator == null ? defaultCreator : creator;
    }

}
