package com.smy.util.common;

import com.google.gson.internal.$Gson$Types;

import java.io.File;
import java.lang.reflect.*;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.function.Predicate;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 对象相关操作工具包。
 * <p>Author: smy
 * <p>Date: 2016/8/19
 */
public class ObjectUtil {


    public static <T extends Cloneable> T clone(T object) {
        try {
            Method method = object.getClass().getMethod("clone");
            return (T) method.invoke(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T newInstance(Class<T> c) {
        try {
            return c.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void set(Object o, Field field, Object v) {
        try {
            field.set(o, v);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T newProxyInstance(Class<T> c, InvocationHandler methodProxy) {
        return (T) Proxy.newProxyInstance(c.getClassLoader(), new Class[]{c}, methodProxy);
    }

    /**
     * @see ParameterizedType
     */
    public static Class getParamType(Class c, int point) {
        return getParamType(c.getGenericSuperclass(), point);
    }

    public static Class getParamType(Type type, int point) {
        ParameterizedType parameterizedType = (ParameterizedType) type;
        Type type1 = $Gson$Types.canonicalize(parameterizedType.getActualTypeArguments()[point]);
        return $Gson$Types.getRawType(type1);
    }

    public static List<Class> findClass(String pack) {
        return findClass(pack, o -> true);
    }

    public static List<Class> findClass(String pack, Predicate<Class> filter) {
        List<Class> list = new ArrayList<>();
        String packageDirName = pack.replace('.', '/');
        Enumeration<URL> dirs;
        try {
            dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
            while (dirs.hasMoreElements()) {
                URL url = dirs.nextElement();
                String protocol = url.getProtocol();
                if ("file".equals(protocol)) {//扫描file包中的类
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    addFileClass(pack, filePath, list, filter);
                } else if ("jar".equals(protocol)) {//扫描jar包中的类
                    JarFile jarFile = ((JarURLConnection) url.openConnection()).getJarFile();
                    addJarClass(jarFile, packageDirName, list, filter);
                }
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
        return list;
    }

    private static void addFileClass(String packName, String filePath, List<Class> list, Predicate<Class> filter) throws Exception {
        File dir = new File(filePath);
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        File[] dirFiles = dir.listFiles(file -> {
            boolean acceptDir = file.isDirectory();// 接受dir目录
            boolean acceptClass = file.getName().endsWith(".class");// 接受class文件
            return acceptDir || acceptClass;
        });
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        for (File file : dirFiles) {
            if (file.isDirectory()) {
                addFileClass(packName + "." + file.getName(), file.getAbsolutePath(), list, filter);
            } else {
                String className = file.getName().substring(0, file.getName().length() - 6);
                Class clazz = loader.loadClass(packName + "." + className);
                if (filter.test(clazz)) {
                    list.add(clazz);
                }
            }
        }
    }

    private static void addJarClass(JarFile jarFile, String filepath, List<Class> list, Predicate<Class> filter) throws Exception {
        List<JarEntry> jarEntryList = new ArrayList<>();
        Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            // 过滤出满足我们需求的东西
            if (entry.getName().startsWith(filepath) && entry.getName().endsWith(".class")) {
                jarEntryList.add(entry);
            }
        }
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        for (JarEntry entry : jarEntryList) {
            String className = entry.getName().replace('/', '.');
            className = className.substring(0, className.length() - 6);
            Class clazz = loader.loadClass(className);
            if (filter.test(clazz)) {
                list.add(clazz);
            }
        }
    }

}
