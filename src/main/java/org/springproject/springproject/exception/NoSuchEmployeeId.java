package org.springproject.springproject.exception;

public class NoSuchEmployeeId extends RuntimeException{
    public NoSuchEmployeeId(String id) {
        super("Employee with id = "+id+" doesn't exist");
    }
}
