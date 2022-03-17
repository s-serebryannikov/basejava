package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public abstract class AbstractArrayStorageTest {
    public final Storage ARRAY_STORAGE;

    public AbstractArrayStorageTest(Storage storage) {
        this.ARRAY_STORAGE = storage;
    }

    private static final Resume UUID_1 = new Resume("uuid1");
    private static final Resume UUID_2 = new Resume("uuid2");
    private static final Resume UUID_3 = new Resume("uuid3");
    private static final Resume UUID_4 = new Resume("uuid4");
    private static final Resume[] RESUMES = {UUID_1, UUID_2, UUID_3};

    @Before
    public void setUpAbstractArrayStorage() {
        ARRAY_STORAGE.clear();
        ARRAY_STORAGE.save(UUID_1);
        ARRAY_STORAGE.save(UUID_2);
        ARRAY_STORAGE.save(UUID_3);
    }

    @Test
    public void clearShouldDeleteAllResume() {
        ARRAY_STORAGE.clear();
        Assert.assertEquals(0, ARRAY_STORAGE.size());
    }

    @Test
    public void saveShouldAddNewResume() {
        ARRAY_STORAGE.save(UUID_4);
        Assert.assertEquals(UUID_4, ARRAY_STORAGE.get("uuid4"));
    }

    @Test(expected = ExistStorageException.class)
    public void saveShouldThrowException() {
        ARRAY_STORAGE.save(UUID_1);
        Assert.assertEquals(UUID_1, ARRAY_STORAGE.get("uuid4"));
    }

    @Test
    public void getShouldReturnResume() {
        Assert.assertEquals(UUID_1, ARRAY_STORAGE.get(UUID_1.getUuid()));
    }

    @Test(expected = NotExistStorageException.class)
    public void getShouldThrowException() {
        Assert.assertEquals(UUID_4, ARRAY_STORAGE.get("uuid4"));
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteShouldRemoveResume(){
    ARRAY_STORAGE.delete("uuid2");
        Assert.assertNull(ARRAY_STORAGE.get("uuid2"));
    }

    @Test
    public void updateShouldReplaceResume(){
        Resume resume = new Resume("uuid1");
        ARRAY_STORAGE.update(resume);
        Assert.assertEquals(resume, ARRAY_STORAGE.get("uuid1"));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateShouldThrowException(){
        Resume resume = new Resume("uuid5");
        ARRAY_STORAGE.update(resume);
    }

    @Test
    public void getAll(){
        Assert.assertArrayEquals(RESUMES, ARRAY_STORAGE.getAll());
    }

    @Test
    public void size() {
        Assert.assertEquals(3, ARRAY_STORAGE.size());
    }
}