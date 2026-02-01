package com.test.cdi.bean;

import jakarta.enterprise.util.AnnotationLiteral;

public class PersistanceQualifier extends AnnotationLiteral<PersistantProvider> implements PersistantProvider {
    Type type;

    public PersistanceQualifier(String pt){
        type = Type.valueOf(pt);
    }

    @Override
    public Type type() {
        return type;
    }
}
