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

package com.luna.lib.event.handlers;

import com.luna.lib.annotations.reflection.event.EventListener;
import com.luna.lib.datastructures.Tuple;
import com.luna.lib.event.EventBase;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings({"unused", "unchecked"})
public class EventManager {
    private static EventManager instance;
    private final ConcurrentHashMap<Object, Map<Class<? extends EventBase>, List<Tuple<Method, Integer>>>> listeners;

    private EventManager() {
        listeners = new ConcurrentHashMap<Object, Map<Class<? extends EventBase>, List<Tuple<Method, Integer>>>>();
    }

    public static EventManager getInstance() {
        return instance == null ? instance = new EventManager() : instance;
    }

    public void registerListener(Object listener) {
        Class<?> listenerClass = listener.getClass();
        List<Method> methodsWithAnnotation = new ArrayList<Method>();
        for(Method m : listenerClass.getDeclaredMethods()) {
            if(m.isAnnotationPresent(EventListener.class)) {
                methodsWithAnnotation.add(m);
            }
        }

        Set<Class<? extends EventBase>> eventClasses = new HashSet<Class<? extends EventBase>>();
        for (Method method : methodsWithAnnotation) {
            EventListener annotation = method.getDeclaredAnnotation(EventListener.class);
            Class<? extends EventBase> eventClass = annotation.event();
            if (!eventClasses.contains(eventClass)) {
                eventClasses.add(eventClass);
            }
        }

        synchronized (listeners) {
            listeners.put(listener, new ConcurrentHashMap<Class<? extends EventBase>, List<Tuple<Method, Integer>>>());
        }

        for (Class<? extends EventBase> eventClass : eventClasses) {
            List<Tuple<Method, Integer>> methodsForEvent = new ArrayList<Tuple<Method, Integer>>();
            /*methodsWithAnnotation.stream().
                    filter(m -> m.getDeclaredAnnotation(EventListener.class).event().equals(eventClass)).sequential().
                    forEach(methodsForEvent::add);*/
            for (Method m : methodsWithAnnotation) {
                if (m.getDeclaredAnnotation(EventListener.class).event().equals(eventClass)) {
                    methodsForEvent.add(new Tuple<Method, Integer>(m, m.getParameterCount()));
                }
            }
            synchronized (listeners) {
                listeners.get(listener).put(eventClass, methodsForEvent);
            }
        }
    }

    public void unregisterListener(Object listener) {
        synchronized (listeners) {
            listeners.remove(listener);
        }
    }

    @SuppressWarnings("unused")
    public Map<Object, Map<Class<? extends EventBase>, List<Tuple<Method, Integer>>>> getListeners() {
        synchronized (listeners) {
            return listeners;
        }
    }

    public <T extends EventBase> void fire(T event) {
        synchronized (listeners) {
            for (Map.Entry<Object, Map<Class<? extends EventBase>, List<Tuple<Method, Integer>>>> entry : listeners.entrySet()) {
                for (Map.Entry<Class<? extends EventBase>, List<Tuple<Method, Integer>>> en : entry.getValue().entrySet()) {
                    if (en.getKey().equals(event.getClass())) {
                        for (Tuple<Method, Integer> m : en.getValue()) {

                            Method method = m.getKey();
                            method.setAccessible(true);
                            {
                                try {
                                    if (m.getValue() == 0) {
                                        method.invoke(entry.getKey());
                                    } else if (m.getValue() == 1) {
                                        method.invoke(entry.getKey(), event);
                                    }
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                } catch (InvocationTargetException e) {
                                    e.printStackTrace();
                                }
                            }
                            method.setAccessible(false);

                            /*m.setAccessible(true);
                            {
                                try {
                                    if (m.getParameterCount() == 0) {
                                        m.invoke(entry.getKey());
                                    }
                                    if (m.getParameterCount() == 1) {
                                        m.invoke(entry.getKey(), event);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            m.setAccessible(false);*/
                        }
                    }
                }
            }
        }
    }
}
