package com.smy.util.create;

import com.smy.model.BigBean;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * @author smy
 * @since 2018/3/13
 */
public class BeanCreatorTest {

    @Test
    public void test1() throws Exception {
        BeanCreator<BigBean> creator = new BeanCreator<>(BigBean.class);
        creator.list(10);
    }
}