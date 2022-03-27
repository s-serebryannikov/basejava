package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public abstract class AbstractStorageTest {
    protected final Storage arrayStorage;

    private static final Resume RESUME_1 = new Resume("uuid1");
    private static final Resume RESUME_2 = new Resume("uuid2");
    private static final Resume RESUME_3 = new Resume("uuid3");
    private static final Resume RESUME_4 = new Resume("uuid4");
    private static final Resume[] RESUMES = {RESUME_1, RESUME_2, RESUME_3};

    public AbstractStorageTest(Storage storage) {
        this.arrayStorage = storage;
    }

    @Before
    public void setUp() {
        arrayStorage.clear();
        arrayStorage.save(RESUME_1);
        arrayStorage.save(RESUME_2);
        arrayStorage.save(RESUME_3);
    }

    @Test
    public void clearShouldDeleteAllResume() {
        arrayStorage.clear();
        assertEquals(0, arrayStorage.size());
        assertArrayEquals(arrayStorage.getAll(), new Resume[0]);
    }

    @Test
    public void saveShouldAddNewResume() {
        arrayStorage.save(RESUME_4);
        assertEquals(RESUME_4, arrayStorage.get("uuid4"));
        assertEquals(4, arrayStorage.size());
    }

    @Test(expected = ExistStorageException.class)
    public void saveShouldThrowException() {
        arrayStorage.save(RESUME_1);
    }

    @Test
    public void getShouldReturnResume() {
        assertEquals(RESUME_1, arrayStorage.get(RESUME_1.getUuid()));
    }

    @Test(expected = NotExistStorageException.class)
    public void getShouldThrowException() {
        assertEquals(RESUME_4, arrayStorage.get("uuid4"));
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteShouldRemoveResume() {
        arrayStorage.delete("uuid2");
        arrayStorage.get("uuid2");
        assertEquals(2, arrayStorage.size());
    }

    @Test
    public void updateShouldReplaceResume() {
        Resume resume = new Resume("uuid1");
        arrayStorage.update(resume);
        assertSame(resume, arrayStorage.get("uuid1"));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateShouldThrowException() {
        Resume resume = new Resume("uuid5");
        arrayStorage.update(resume);
    }

    @Test
    public void getAll() {
        assertArrayEquals(RESUMES, arrayStorage.getAll());
    }

    @Test
    public void size() {
        assertEquals(3, arrayStorage.size());
    }
}
