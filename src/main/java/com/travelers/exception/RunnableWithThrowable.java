package com.travelers.exception;

import java.io.IOException;

@FunctionalInterface
public
interface RunnableWithThrowable {
    void runWithThrowable() throws IOException;
}
