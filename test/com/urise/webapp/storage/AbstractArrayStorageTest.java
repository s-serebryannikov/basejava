package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageExeption;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Test;

import static com.urise.webapp.storage.AbstractArrayStorage.STORAGE_LIMIT;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {

    public AbstractArrayStorageTest(Storage arrayStorage) {
        super(arrayStorage);
    }

    @Test(expected = StorageExeption.class)
    public void saveShouldThrowStorageException() {
        arrayStorage.clear();
        try {
            for (int i = 0; i < STORAGE_LIMIT; i++) {
                arrayStorage.save(new Resume("uuid" + (i + 1)));
            }
        } catch (StorageExeption e) {
            Assert.fail("Storage overflow occurred ahead of time");
        }
        arrayStorage.save(new Resume("uuID"));
    }
}