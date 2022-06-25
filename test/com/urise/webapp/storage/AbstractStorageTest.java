package com.urise.webapp.storage;

import com.urise.webapp.Config;
import com.urise.webapp.ResumeTestDate;
import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public abstract class AbstractStorageTest {
    protected static final File STORAGE_DIR = Config.get().getStorageDir();
    protected final Storage storage;

    private final Resume RESUME_1;
    private final Resume RESUME_2;
    private final Resume RESUME_3;
    private final Resume RESUME_4;

    private final String UUID_1 = UUID.randomUUID().toString();
    private final String UUID_2 = UUID.randomUUID().toString();
    private final String UUID_3 = UUID.randomUUID().toString();
    private final String UUID_4 = UUID.randomUUID().toString();

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
        RESUME_1 = ResumeTestDate.createResume(UUID_1, "uuid1 Name");
        RESUME_2 = ResumeTestDate.createResume(UUID_2, "uuid2 Name");
        RESUME_3 = ResumeTestDate.createResume(UUID_3, "uuid3 Name");
        RESUME_4 = ResumeTestDate.createResume(UUID_4, "uuid4 Name");
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
        assertEquals(RESUME_4, storage.get(UUID_4));
        assertEquals(4, storage.size());
    }

    @Test(expected = ExistStorageException.class)
    public void saveShouldThrowException() {
        storage.save(RESUME_1);
    }

    @Test
    public void getShouldReturnResume() {
        assertEquals(RESUME_3, storage.get(RESUME_3.getUuid()));
    }

    @Test(expected = NotExistStorageException.class)
    public void getShouldThrowException() {
        assertEquals(RESUME_4, storage.get(UUID_4));
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteShouldRemoveResume() {
        storage.delete("uuid1");
        assertEquals(2, storage.size());
        storage.get("uuid1");
    }

    @Test
    public void updateShouldReplaceResume() {
        assertEquals(ResumeTestDate.createResume(UUID_1, "uuid1 Name"), storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateShouldThrowException() {
        storage.update(RESUME_4);
    }

    @Test
    public void getSortedAll() {
        List<Resume> list = storage.getAllSorted();
//        assertEquals(3, list.size());
        assertEquals(storage.getAllSorted(), Arrays.asList(RESUME_1,RESUME_2, RESUME_3));
    }

    @Test
    public void size() {
        assertEquals(3, storage.size());
    }
}
