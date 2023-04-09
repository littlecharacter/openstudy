package com.lc.javase.other.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyUtil {
    private static Properties properties;

    static{
        initProperties();
    }

    synchronized static private void initProperties() {
        properties = new Properties();
        InputStream in = null;
        try {
            in = PropertyUtil.class.getClassLoader().getResourceAsStream("conf.properties");
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
