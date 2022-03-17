package com.urise.webapp.exception;

public class StorageExeption extends RuntimeException{
    private final String uuid;


    public StorageExeption(String message,String uuid) {
        super(message);
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }
}
