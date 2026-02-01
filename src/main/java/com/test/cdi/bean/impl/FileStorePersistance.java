package com.test.cdi.bean.impl;

import com.test.cdi.bean.DownTimeBean;
import com.test.cdi.bean.PersistantProvider;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
@PersistantProvider(type = PersistantProvider.Type.FILE_STORE)
public class FileStorePersistance implements DownTimeBean {
    @Override
    public void save(String message) {
        System.out.println("FileStorePersistance.save");
    }
}
