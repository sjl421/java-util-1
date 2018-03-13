package com.smy.model;

import com.smy.util.create.BeanCreator;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author smy
 * @since 2018/3/8
 */
public class UserCreator extends BeanCreator<User> {

    public Map<String, User> map(int count) {
        return stream(count).collect(Collectors.toMap(User::getId, user -> user));
    }
}
