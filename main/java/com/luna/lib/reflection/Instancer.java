package com.luna.lib.reflection;

import com.luna.lib.annotations.reflection.loading.Instance;
import com.luna.lib.util.logging.SimpleLogHandler;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.logging.Logger;

public class Instancer {
    private static final Logger logger = Logger.getLogger("Instancer");

    static {
        logger.setUseParentHandlers(false);
        logger.addHandler(new SimpleLogHandler());
    }

    public static <T> void instance(Class<T> clazz) {
        try {
            logger.info(String.format("Attempting to instance class %s", clazz.getSimpleName()));
            Field e = ReflectionHelper.findFieldOfTypeInClass(clazz, clazz);
            e.setAccessible(true);
            logger.info(String.format("Instance field appears to be '%s'", e.getName()));
            if (!Modifier.isStatic(e.getModifiers())) {
                throw new RuntimeException("But the field isn't static!");
            }
            if (!e.isAnnotationPresent(Instance.class)) {
                logger.info(String.format(
                        "Field '%s' does not have the @Instance annotation! Stopping instancing of %s...",
                        e.getName(), clazz.getSimpleName()));
                return;
            }
            logger.info(String.format("Creating new instance of class %s...", clazz.getSimpleName()));
            Constructor<T> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            T instance = constructor.newInstance();
            e.set(clazz, instance);
            logger.info("Done!");
        } catch (Exception f) {
            f.printStackTrace();
            throw new RuntimeException(f);
        }
    }

    public static void massInstance(Class... clazzes) {
        for (Class<?> clazz : clazzes) {
            instance(clazz);
        }
    }
}
