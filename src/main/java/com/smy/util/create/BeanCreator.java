package com.smy.util.create;

import com.smy.util.common.ObjectUtil;
import net.sf.cglib.beans.BeanMap;
import net.sf.cglib.core.ReflectUtils;

import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * <p>Author: smy
 * <p>Date: 2018/2/1
 */
public class BeanCreator<T> implements Creator<T> {

    private Class<T> paramType;

    private PropertyDescriptor[] properties;

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
        properties = ReflectUtils.getBeanProperties(paramType);
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
        BeanMap beanMap = BeanMap.create(ObjectUtil.newInstance(paramType));
        Stream.of(properties)
                .forEach(property -> beanMap.put(property.getName(), findFieldCreator(property).createOne()));
        return (T) beanMap.getBean();
    }

    private Creator findFieldCreator(PropertyDescriptor property) {
        Creator creator = fieldCreators.get(property.getName());
        if (creator != null) {
            return creator;
        }
        creator = typeCreators.get(property.getPropertyType());
        return creator == null ? defaultCreator : creator;
    }

}
