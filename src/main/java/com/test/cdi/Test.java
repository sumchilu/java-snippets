package com.test.cdi;

import com.test.cdi.bean.DownTimeBean;
import com.test.cdi.bean.PersistanceQualifier;
import com.test.cdi.bean.PersistantProvider;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

public class Test {
    public static void main(String[] args) {
        //SeContainerInitializer containerInitializer = SeContainerInitializer.newInstance();
        // SeContainer container = containerInitializer.initialize();
        Weld weld = new Weld();
        WeldContainer container = weld.initialize();

        DownTimeBean osProducer =
                container.select(DownTimeBean.class, new PersistanceQualifier(PersistantProvider.Type.OBJECT_STORE.toString())).get();
        DownTimeBean fsProducer =
                container.select(DownTimeBean.class, new PersistanceQualifier(PersistantProvider.Type.FILE_STORE.toString())).get();
        DownTimeBean nsProducer =
                container.select(DownTimeBean.class, new PersistanceQualifier(PersistantProvider.Type.NO_STORE.toString())).get();

        osProducer.save("Hi");
        fsProducer.save("Hi");
        nsProducer.save("Hi");
    }
}
