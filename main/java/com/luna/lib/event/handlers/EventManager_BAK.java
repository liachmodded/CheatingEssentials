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
import com.luna.lib.event.EventBase;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("all")
public final class EventManager_BAK {
    private static volatile EventManager_BAK instance = new EventManager_BAK();

    private volatile Map<Object, LinkedList<Method>> eventListeners;

    private EventManager_BAK() {
        eventListeners = new ConcurrentHashMap<Object, LinkedList<Method>>();
        eventListeners.clear();
    }

    public static final EventManager_BAK getInstance() {
        return instance;
    }

    public final void addListener(final Object o) {
        final Method[] declared = o.getClass().getDeclaredMethods();
        final LinkedList<Method> listeners;
        listeners = new LinkedList<Method>();

        for (final Method e : declared) {
            if (e.isAnnotationPresent(EventListener.class)) {
                listeners.add(e);
            }
        }

        synchronized (eventListeners) {
            eventListeners.put(o, listeners);
        }
    }

    public final void removeListener(final Object o) {
        synchronized (eventListeners) {
            for (final Map.Entry<Object, LinkedList<Method>> e : eventListeners.entrySet()) {
                if (e.getKey().equals(o)) {
                    eventListeners.remove(e.getKey());
                }
            }
        }
    }

    public final Map<Object, LinkedList<Method>> getEventListeners() {
        synchronized (eventListeners) {
            return eventListeners;
        }
    }

    public final void fire(final EventBase event) {
        // DO ALL THIS SORTING FIRST!!
        synchronized (eventListeners) {
            //for( final EnumEventPriority pr : EnumEventPriority.values() ) {
            for (final Map.Entry<Object, LinkedList<Method>> e : getEventListeners().entrySet()) {
                final List<Method> f = e.getValue();
                for (final Method g : f) {
                    final EventListener h = g.getAnnotation(EventListener.class);
                    if (h.event().equals(event.getClass())) {
                        //if( !h.priority().equals( pr ) ) {
                        //	continue;
                        //}
                        g.setAccessible(true);
                        try {
                            if (g.getParameterAnnotations().length == 0) {
                                g.invoke(e.getKey());
                                continue;
                            }
                            if (g.getParameterAnnotations().length == 1) {
                                g.invoke(e.getKey(), event);
                                continue;
                            }
                            throw new IllegalArgumentException("Too many parameters!");
                        } catch (final IllegalAccessException e1) {
                            e1.printStackTrace();
                        } catch (final InvocationTargetException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
            //}
        }
    }
}
