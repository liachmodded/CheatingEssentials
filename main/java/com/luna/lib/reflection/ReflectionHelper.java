package com.luna.lib.reflection;

import com.luna.lib.annotations.reflection.searching.ObfuscatedField;
import com.luna.lib.annotations.reflection.searching.ObfuscatedMethod;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.logging.Logger;

@SuppressWarnings("unused")
public final class ReflectionHelper {
    private static Logger logger = Logger.getLogger("ReflectionHelper");

    public static Field findFieldOfTypeInClass(final Class<?> source, final Class<?> type) {
        for (final Field e : source.getDeclaredFields()) {
            if (e.getType().equals(type)) {
                e.setAccessible(true);
                return e;
            }
        }
        return null;
    }

    public static Method getMethodByReturnType(final Class<?> source, final Class<?> type) {
        for (final Method e : source.getDeclaredMethods()) {
            if (e.getReturnType().isAssignableFrom(type)) {
                return e;
            }
        }
        return null;
    }

    public static Method getMethodByParametersWithAnnotation(final Class<?> source,
                                                             final Class<?>[] params, final Class<? extends Annotation> annotation) {
        for (final Method e : source.getDeclaredMethods()) {
            if (Arrays.deepEquals(e.getParameterTypes(), params)) {
                if (e.isAnnotationPresent(annotation)) {
                    return e;
                }
            }
        }
        return null;
    }

    public static Method getMethodByParameters(final Class<?> source, final Class<?>[] params) {
        for (final Method e : source.getDeclaredMethods()) {
            if (Arrays.deepEquals(e.getParameterTypes(), params)) {
                return e;
            }
        }
        return null;
    }

    @SuppressWarnings("all")
    public static <T extends Object> T callMethod(final Method e) {
        return callMethod(e, (Object[]) null);
    }

    @SuppressWarnings("all")
    public static <T extends Object> T callMethod(final Method e, final Object... params) {

        try {
            return (T) e.invoke(e.getDeclaringClass(), params);
        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        } catch (IllegalArgumentException e1) {
            e1.printStackTrace();
        } catch (InvocationTargetException e1) {
            e1.printStackTrace();
        }

        return null;
    }

    public static Class<?> findClass(final String fullName) {
        try {
            return Class.forName(fullName);
        } catch (final ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T getClassType(final T clazz) {
        return (T) clazz.getClass();
    }

    @SuppressWarnings("unchecked")
    public static <T> T cast(final T source, final Class<?> cast) {
        if (source.getClass().equals(cast)) {
            logger.warning(
                    "Source (" + source.toString() + ") is equal to Cast (" + cast.toString()
                            + ") - returning Source."
            );
            return source;
        }
        return (T) cast.cast(source);
    }

    public static Field findObfuscatedFieldByName(Class<?> clazz, String fieldName) {
        for (Field e : clazz.getDeclaredFields()) {
            if (e.isAnnotationPresent(ObfuscatedField.class)) {
                if (e.getAnnotation(ObfuscatedField.class).name().equalsIgnoreCase(fieldName)) {
                    return e;
                }
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static <T, C> T getObfuscatedFieldValue(Class<C> clazz, C instance, String name) {
        Field field = findObfuscatedFieldByName(clazz, name);
        field.setAccessible(true);
        try {
            return (T) field.get(instance);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Method findObfuscatedMethodByName(Class<?> clazz, String methodName) {
        for (Method e : clazz.getDeclaredMethods()) {
            if (e.isAnnotationPresent(ObfuscatedMethod.class)) {
                if (e.getDeclaredAnnotation(ObfuscatedMethod.class).name().equalsIgnoreCase(methodName)) {
                    return e;
                }
            }
        }
        return null;
    }
}
