package com.test.cdi.bean.impl;

import com.test.cdi.bean.DownTimeBean;
import com.test.cdi.bean.PersistantProvider;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Qualifier;

@ApplicationScoped
@PersistantProvider(type = PersistantProvider.Type.OBJECT_STORE)
public class ObjectStorePersistance implements DownTimeBean {


    @Override
    public void save(String message) {
        System.out.println("ObjectStorePersistance.save");
    }
}
