package com.smy.util.common;

import org.apache.commons.io.FileUtils;
import org.testng.annotations.Test;

import java.io.File;

import static org.testng.Assert.assertEquals;

/**
 * <p>Author: smy
 * <p>Date: 2018/3/8
 */
public class IOUtilTest {
    @Test
    public void testGzip() throws Exception {
        String file = FileUtils.readFileToString(new File("pom.xml"));
        String gzip = IOUtil.gzip(file);
        String ungzip = IOUtil.ungzip(gzip);
        assertEquals(ungzip, file);
    }


}