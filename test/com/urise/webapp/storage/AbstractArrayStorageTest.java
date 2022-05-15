package com.urise.webapp.storage;

import com.urise.webapp.ResumeTestDate;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Test;

import static com.urise.webapp.storage.AbstractArrayStorage.STORAGE_LIMIT;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {

    public AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void saveShouldThrowStorageException() {
        storage.clear();
        try {
            for (int i = 0; i < STORAGE_LIMIT; i++) {
                storage.save(ResumeTestDate.createResume("uuid" + i, "uuid" + i + " Name"));
            }
        } catch (StorageException e) {
            Assert.fail("Storage overflow occurred ahead of time");
        }
        storage.save(new Resume("uuID"));
    }
}