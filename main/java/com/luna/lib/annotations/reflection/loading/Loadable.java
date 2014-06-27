package com.luna.lib.annotations.reflection.loading;

import java.lang.annotation.*;

/**
 * Marks a class as loadable by the ClassEnumerator
 *
 * @author luna
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Loadable {

}
