package com.test.generics.fbound;

public class FBoundTest {
    public static void main(String[] args) {
        KuberneteDeployment kd = new KuberneteDeployment().getType().withImage("abc");
        AWSDeployment ad = new AWSDeployment().getType().withRegion("us-east-1");

    }
}
