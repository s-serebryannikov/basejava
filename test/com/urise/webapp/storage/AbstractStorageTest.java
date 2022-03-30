package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public abstract class AbstractStorageTest {
    protected final Storage storage;

    private static final Resume RESUME_1 = new Resume("uuid1","Иван Иванов");
    private static final Resume RESUME_2 = new Resume("uuid2","Иван Иванов");
    private static final Resume RESUME_3 = new Resume("uuid3","Иван Иванов");
    private static final Resume RESUME_4 = new Resume("uuid4","Иван Иванов");
    private static final Resume[] RESUMES = {RESUME_1, RESUME_2, RESUME_3};

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void clearShouldDeleteAllResume() {
        storage.clear();
        assertEquals(0, storage.size());
        assertEquals(storage.getAllSorted(), Collections.emptyList());
    }

    @Test
    public void saveShouldAddNewResume() {
        storage.save(RESUME_4);
        assertEquals(RESUME_4, storage.get("uuid4"));
        assertEquals(4, storage.size());
    }

    @Test(expected = ExistStorageException.class)
    public void saveShouldThrowException() {
        storage.save(RESUME_1);
    }

    @Test
    public void getShouldReturnResume() {
        assertEquals(RESUME_1, storage.get(RESUME_1.getUuid()));
    }

    @Test(expected = NotExistStorageException.class)
    public void getShouldThrowException() {
        assertEquals(RESUME_4, storage.get("uuid4"));
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteShouldRemoveResume() {
        storage.delete("uuid2");
        storage.get("uuid2");
        assertEquals(2, storage.size());
    }

    @Test
    public void updateShouldReplaceResume() {
        Resume resume = new Resume("uuid1","Иван Сидоров");
        storage.update(resume);
        assertSame(resume, storage.get("uuid1"));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateShouldThrowException() {
        Resume resume = new Resume("uuid5","Петр Иванов");
        storage.update(resume);
    }

    @Test
    public void getSortedAll() {
        assertEquals(Arrays.asList(Arrays.copyOf(RESUMES,3)), storage.getAllSorted());
    }

    @Test
    public void size() {
        assertEquals(3, storage.size());
    }
}