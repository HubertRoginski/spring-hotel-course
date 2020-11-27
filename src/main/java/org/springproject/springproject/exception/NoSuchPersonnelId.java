package org.springproject.springproject.exception;

public class NoSuchPersonnelId extends RuntimeException{
    public NoSuchPersonnelId(String id) {
        super("Personnel with id = "+id+" doesn't exist");
    }
}
