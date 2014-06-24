/*
 * Isis modified client for Minecraft.
 * Copyright (C) 2014-2015  godshawk
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * ===========================================================================
 *
 * ALL SOURCE CODE WITHOUT THIS COPYRIGHT IS THE PROPERTY OF ITS RESPECTIVE
 * OWNER(S). I CLAIM NO RIGHT TO OR OWNERSHIP OF ANY OF IT.
 *
 * Minecraft is owned by Mojang AB.
 * Java itself is owned by Oracle.
 * All other code is not owned by me.
 * Thank you, and have a good day!
 */

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
