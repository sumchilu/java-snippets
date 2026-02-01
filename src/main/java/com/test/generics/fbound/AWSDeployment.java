package com.test.generics.fbound;

public class AWSDeployment implements FBoundDeploymentType<AWSDeployment> {

    public AWSDeployment withRegion(String region){
        return this;
    }
}
