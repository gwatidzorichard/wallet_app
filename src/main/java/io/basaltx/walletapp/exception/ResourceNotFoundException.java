package io.basaltx.walletapp.exception;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException {

    private final String operation;
    private final String resourceName;
    private final String fieldName;
    private final Object fieldValue;

    public ResourceNotFoundException(String operation, String resourceName, String fieldName, Object fieldValue) {
        super(String.format("Could not find %s with %s: [%s]", resourceName, fieldName, fieldValue));
        this.operation = operation;
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
