package com.travelers.util;

@FunctionalInterface
public interface SupplierWithThrowable<T>  {

    T get() throws ReflectiveOperationException;
}
