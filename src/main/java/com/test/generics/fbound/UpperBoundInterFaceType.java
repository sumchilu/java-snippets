package com.test.generics.fbound;

public interface UpperBoundInterFaceType<D extends UpperBoundInterFaceType> {
    default D getType(){
        return (D) this;
    }
}
