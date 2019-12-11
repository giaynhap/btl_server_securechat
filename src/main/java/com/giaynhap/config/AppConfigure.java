package com.giaynhap.config;

import org.apache.commons.lang3.SystemUtils;

import java.io.FileInputStream;
import java.util.Properties;

public class AppConfigure {
    private String path;
    private Properties properties;
    private Properties config;
    private static AppConfigure instance;

    private AppConfigure() {
        properties = new Properties();
        config = new Properties();
        try {
            properties.load(this.getClass().getClassLoader().getResourceAsStream("application.properties"));
            if (SystemUtils.IS_OS_LINUX)
                path = "/home/giaynhap/server.conf";
            else
                path = "server.conf";
            config.load(new FileInputStream(path));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String _getProperty(String key) {
        return properties.getProperty(key);
    }

    private String _getConfig(String key) {
        return config.getProperty(key);
    }

    public static String getProperty(String key) {
        if (instance == null)
            instance = new AppConfigure();
        System.out.print(key + ": " + instance._getProperty(key));
        return instance._getProperty(key);
    }

    public static String getConfig(String key) {
        if (instance == null)
            instance = new AppConfigure();
        System.out.print(key + ": " + instance._getConfig(key));
        return instance._getConfig(key);
    }
}
