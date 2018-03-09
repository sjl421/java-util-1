package com.smy.util.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * <p>Author: smy
 * <p>Date: 2018/3/8
 */
public class IOUtil {

    private static final Charset charset1 = Charset.forName("utf8");
    private static final Charset charset2 = Charset.forName("ISO-8859-1");

    public static String gzip(String source) {
        return new String(gzip(source.getBytes(charset1)), charset2);
    }

    public static String ungzip(String source) {
        return new String(ungzip(source.getBytes(charset2)), charset1);
    }


    public static byte[] gzip(byte[] source) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            GZIPOutputStream gzip = new GZIPOutputStream(out);
            gzip.write(source);
            gzip.close();
        } catch (IOException e) {
            throw new RuntimeException("gzip compress error", e);
        }
        return out.toByteArray();
    }


    public static byte[] ungzip(byte[] bytes) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        try {
            GZIPInputStream ungzip = new GZIPInputStream(in);
            byte[] buffer = new byte[256];
            int n;
            while ((n = ungzip.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
            ungzip.close();
        } catch (IOException e) {
            throw new RuntimeException("gzip uncompress error", e);
        }

        return out.toByteArray();
    }


}
