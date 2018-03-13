package com.smy.util.common;

import com.google.gson.reflect.TypeToken;
import com.smy.model.User;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;

/**
 * @author smy
 * @since 2018/2/23
 */
public class JsonUtilTest {
    User user = new User("test", "Jack", 10);
    String json = "{\"id\":\"test\",\"name\":\"Jack\",\"age\":10}";

    @Test
    public void testInit() throws Exception {
        new JsonUtil();
    }

    @Test
    public void testToJson() throws Exception {
        assertEquals(JsonUtil.toJson(user), json);
    }

    @Test
    public void testFromJson() throws Exception {
        User user2 = JsonUtil.fromJson(json, User.class);
        assertEquals(user.getId(), user2.getId());
    }

    @Test
    public void testFromJsonList() throws Exception {
        String jsonList = "[" + json + "]";
        List<User> list = JsonUtil.fromJson(jsonList, new TypeToken<List<User>>() {
        });
        assertEquals(user.getId(), list.get(0).getId());
    }

}