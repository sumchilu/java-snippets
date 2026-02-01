package com.test.generics.fbound;

public class KuberneteDeployment implements FBoundDeploymentType<KuberneteDeployment> {

    public KuberneteDeployment withImage(String image) {
        return this;
    }
}
