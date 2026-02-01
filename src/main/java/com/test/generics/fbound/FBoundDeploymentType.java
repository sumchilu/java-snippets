package com.test.generics.fbound;

/**
 * The FBoundDeploymentType interface represents a type that is bounded by itself.
 * It provides a method to retrieve the type instance.
 *
 * FBound Generics( <D extends FBoundDeploymentType<D>) will have context of the sub-types
 * for example getType() method return this and type cast to D, So that it will have a method chain.
 *
 * Here KuberneteDeployment accessing getType() method of FBoundDeploymentType if
 * FBoundDeploymentType is not implemented FBound Generic it should be typeCasted explicitly
 * {@link com.test.generics.fbound.UpperBoundInterFaceTypeTest}
 *
 * KuberneteDeployment kd = new KuberneteDeployment().getType().withImage("abc");
 * AWSDeployment ad = new AWSDeployment().getType().withRegion("us-east-1");
 *
 * If Generic is not FBound type then Above statement look like below.
 * KuberneteDeployment kd = ((KuberneteDeployment)new KuberneteDeployment().getType()).withImage("abc");
 *
 * @param <D> the type parameter that extends FBoundDeploymentType
 */
public interface FBoundDeploymentType<D extends FBoundDeploymentType<D>> {
    /**
     * Returns the instance of the type.
     *
     * @return the instance of the type
     */
    default D getType(){
        return (D) this;
    }
}
