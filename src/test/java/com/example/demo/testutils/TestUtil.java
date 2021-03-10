package com.example.demo.testutils;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

public class TestUtil {
    public static String dataFromResources(String path) {
        try (InputStream in = TestUtil.class.getClassLoader().getResourceAsStream(path)) {
            assert in != null;
            return new String(IOUtils.toByteArray(in));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}
