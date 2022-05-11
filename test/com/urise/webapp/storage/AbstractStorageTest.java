package com.urise.webapp.storage;

import com.urise.webapp.ResumeTestDate;
import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public abstract class AbstractStorageTest {
    protected static final File STORAGE_DIR = new File("D:\\BaseJava\\basejava\\storage");
    protected final Storage storage;

    private final Resume RESUME_1;
    private final Resume RESUME_2;
    private final Resume RESUME_3;
    private final Resume RESUME_4;

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
        RESUME_1 = ResumeTestDate.createResume(new Resume("uuid1", "uuid1 Name"));
        RESUME_2 = ResumeTestDate.createResume(new Resume("uuid2", "uuid2 Name"));
        RESUME_3 = ResumeTestDate.createResume(new Resume("uuid3", "uuid3 Name"));
        RESUME_4 = ResumeTestDate.createResume(new Resume("uuid4", "uuid4 Name"));
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
        assertEquals(0, storage.getAllSorted().size());
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
        assertEquals(RESUME_2, storage.get(RESUME_2.getUuid()));
        assertEquals(RESUME_3, storage.get(RESUME_3.getUuid()));
    }

    @Test(expected = NotExistStorageException.class)
    public void getShouldThrowException() {
        assertEquals(RESUME_4, storage.get("uuid4"));
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteShouldRemoveResume() {
        storage.delete("uuid1");
        assertEquals(2, storage.size());
        storage.get("uuid1");
    }

    @Test
    public void updateShouldReplaceResume() {
        Resume resume = new Resume("uuid1", "Иван Сидоров");
        storage.update(resume);
        assertTrue(resume.equals(storage.get("uuid1")));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateShouldThrowException() {
        Resume resume = new Resume("uuid5", "Петр Иванов");
        storage.update(resume);
    }

    @Test
    public void getSortedAll() {
        List<Resume> list = storage.getAllSorted();
        assertEquals(3, list.size());
        assertEquals(list, Arrays.asList(RESUME_1, RESUME_2, RESUME_3));
    }

    @Test
    public void size() {
        assertEquals(3, storage.size());
    }
}
