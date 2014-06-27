package com.luna.lib.annotations.reflection.event;

import com.luna.lib.event.EventBase;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Tells the EventManager that the given method handles Events.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface EventListener {
    Class<? extends EventBase> event();

    // EnumEventPriority priority() default EnumEventPriority.NORMAL;
}
