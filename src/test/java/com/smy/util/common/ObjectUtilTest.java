package com.smy.util.common;

import com.smy.model.User;
import com.smy.model.UserCreator;
import com.smy.model.UserService;
import org.testng.annotations.Test;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import static org.testng.Assert.*;

/**
 * <p>Author: smy
 * <p>Date: 2018/2/23
 */
public class ObjectUtilTest {
    User user = new User("test", "Jack", 10);

    @Test
    public void testInit() throws Exception {
        new ObjectUtil();
    }

    @Test
    public void testClone() throws Exception {
        User user2 = ObjectUtil.clone(user);
        assertEquals(user, user2);
        assertFalse(user == user2);
    }

    @Test
    public void testNewInstance() throws Exception {
        User user2 = ObjectUtil.newInstance(User.class);
        assertNull(user2.getId());
    }

    @Test
    public void testNewProxyInstance() throws Exception {
        UserService userService = ObjectUtil.newProxyInstance(UserService.class,
                (var1, var2, var3) -> new User((String) var3[0], "张三", 10));
        User user = userService.get("123");
        assertEquals(user.getId(), "123");
        assertEquals(user.getName(), "张三");
    }

    @Test
    public void testGetParamType() throws Exception {
//        Type type = UserCreator.class.getGenericSuperclass();
//        System.out.println(type);
//        if(type instanceof ParameterizedType){
//            ParameterizedType parameterizedType = (ParameterizedType) type;
//            //获取参数的类型
//            System.out.println(parameterizedType.getRawType());
//            //获取参数的泛型列表
//            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
//            for (Type type2 : actualTypeArguments) {
//                System.out.println(type2);
//            }
//        }
//        Type type2 = UserCreator.class.getMethod("map").getGenericReturnType();
//        System.out.println(type2);
    }

    @Test
    public void testFindClass() throws Exception {
        List list = ObjectUtil.findClass("com.smy.model");
        assertTrue(list.contains(User.class));

    }

    @Test
    public void testFindClass1() throws Exception {
    }

}