/*
 * Decompiled with CFR 0.152.
 */
package com.iassets.common.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.FIELD})
public @interface SearchRequired {
    public boolean isObject() default false;

    public String[] fields() default {};
}
