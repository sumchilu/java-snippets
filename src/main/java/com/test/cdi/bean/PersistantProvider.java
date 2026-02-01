package com.test.cdi.bean;

import jakarta.inject.Qualifier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.PARAMETER, ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
@Qualifier
public @interface PersistantProvider {
    public Type type() default Type.NO_STORE;

    enum Type {
        OBJECT_STORE,
        FILE_STORE,
        NO_STORE
    }
}
