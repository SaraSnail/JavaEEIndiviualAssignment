package com.example.assignmentindividualjavaee.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MissingParameterException extends RuntimeException {
    private String object;
    private String field;
    private Object value;

    public MissingParameterException(String object,String field, Object value) {
        super(String.format("'%s' requires %s, it can't be '%s'", object,field, value));
        this.object = object;
        this.field = field;
        this.value = value;
    }

    public String getObject() {
        return object;
    }

    public Object getValue() {
        return value;
    }

    public String getField() {
        return field;
    }
}
