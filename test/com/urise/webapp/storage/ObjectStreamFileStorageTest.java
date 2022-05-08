package com.urise.webapp.storage;

public class ObjectStreamFileStorageTest extends AbstractStorageTest {

    public ObjectStreamFileStorageTest() {
        super(new AbstractFileStorage(STORAGE_DIR,new ObjectStreamStorage()));
    }
}
