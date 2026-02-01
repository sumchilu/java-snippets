package com.test.generics.fbound;

public class UpperBoundInterFaceTypeTest {
    public static void main(String[] args) {

        //Chaing doesn't work.

        //UpperBoundInterFaceTypeImpl upperBoundInterFaceType
         //       = new UpperBoundInterFaceTypeImpl().getType().getName();

        UpperBoundInterFaceTypeImpl upperBoundInterFaceType
                = ((UpperBoundInterFaceTypeImpl)new UpperBoundInterFaceTypeImpl().getType()).getName();

    }
}
