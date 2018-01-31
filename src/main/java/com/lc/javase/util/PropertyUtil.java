package com.lc.javase.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyUtil {
    public static final String URL = "url";
    public static final String NAME = "user";
    public static final String PSWD = "password";

    private static Properties properties;

    static{
        initProperties();
    }

    synchronized static private void initProperties() {
        properties = new Properties();
        InputStream in = null;
        try {
            in = PropertyUtil.class.getClassLoader().getResourceAsStream("jdbc.properties");
            properties.load(in);
        } catch (Exception e) {
            //do nothing
        } finally {
            try {
                if(null != in) {
                    in.close();
                }
            } catch (IOException e) {
                //do nothing
            }
        }
    }

    public static String getProperty(String key){
        if(null == properties) {
            initProperties();
        }
        return properties.getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        if(null == properties) {
            initProperties();
        }
        return properties.getProperty(key, defaultValue);
    }
}
