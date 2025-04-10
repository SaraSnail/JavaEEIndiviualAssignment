package com.example.assignmentindividualjavaee.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    private final String object;
    private final String field;
    private final Object value;

    public ResourceNotFoundException(String object, String field, Object value) {
        super(String.format("%s with %s '%s' not found",object,field,value));
        this.object = object;
        this.field = field;
        this.value = value;
    }

    public String getObject() {
        return object;
    }

    public String getField() {
        return field;
    }

    public Object getValue() {
        return value;
    }
}
