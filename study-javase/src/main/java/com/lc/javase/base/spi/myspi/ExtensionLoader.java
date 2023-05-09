package com.lc.javase.base.spi.myspi;

import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author gujixian
 * @since 2023/5/10
 */
public class ExtensionLoader<T> {
    private static final Map<Class<?>, ExtensionLoader<?>> EXTENSION_LOADERS = new ConcurrentHashMap<>();
    private final Map<String, T> extensionInstances = new ConcurrentHashMap<>();
    private final Class<T> type;

    private ExtensionLoader(Class<T> type) {
        this.type = type;
    }

    public static <T> ExtensionLoader<T> getExtensionLoader(Class<T> type) {
        if (!type.isInterface()) {
            throw new IllegalArgumentException("Extension type must be an interface");
        }
        if (!type.isAnnotationPresent(Extension.class)) {
            throw new IllegalArgumentException("Extension type must be annotated with @Extension");
        }
        ExtensionLoader<T> extensionLoader = (ExtensionLoader<T>) EXTENSION_LOADERS.get(type);

        if (extensionLoader == null) {
            extensionLoader = new ExtensionLoader<>(type);
            EXTENSION_LOADERS.put(type, extensionLoader);
        }
        return extensionLoader;
    }

    public T getExtension() {
        Extension spiAnnotation = type.getAnnotation(Extension.class);
        return this.getExtension(spiAnnotation.value());
    }

    public T getExtension(String name) {
        T instance = extensionInstances.get(name);
        if (instance == null) {
            synchronized (extensionInstances) {
                instance = extensionInstances.get(name);
                if (instance == null) {
                    instance = createExtension(name);
                    extensionInstances.put(name, instance);
                }
            }
        }
        return instance;
    }

    private T createExtension(String name) {
        Class<?> clazz = findExtensionClass(name);
        if (clazz == null) {
            throw new RuntimeException("No extension found for name " + name);
        }
        try {
            T instance = (T) clazz.newInstance();
            extensionInstances.putIfAbsent(name, instance);
            return instance;
        } catch (Exception e) {
            throw new RuntimeException("Could not instantiate extension " + clazz.getName(), e);
        }
    }

    private Class<?> findExtensionClass(String name) {
        String extensionPoint = type.getName();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try (InputStream is = classLoader.getResourceAsStream("META-INF/extensions/" + extensionPoint)) {
            if (is == null) {
                throw new RuntimeException("Could not find extension configuration file for " + extensionPoint);
            }
            Properties props = new Properties();
            props.load(is);
            String className = props.getProperty(name);
            if (StringUtils.isEmpty(className)) {
                throw new RuntimeException("Could not find extension implementation for " + name);
            }
            return Class.forName(className, true, classLoader);
        } catch (Exception e) {
            throw new RuntimeException("Error while loading extension implementation for " + name, e);
        }
    }
}
