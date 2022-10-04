package com.travelers.exception;

@FunctionalInterface
public interface SupplierWithThrowable<T>  {

    T get() throws ReflectiveOperationException;
}
