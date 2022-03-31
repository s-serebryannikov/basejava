package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageExeption;
import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage {
    public static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];

    protected int size;

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public void saveResume(Resume r, Object index) {
        if (size == STORAGE_LIMIT) {
            throw new StorageExeption("Storage overflow", r.getUuid());
        }
        saveToArray(r, (Integer) index);
        size++;
    }

    @Override
    public Resume getResume(Object index) {
        return storage[(int) index];
    }

    @Override
    public void deleteResume(Object index) {
        deleteFromArray((Integer) index);
        storage[size - 1] = null;
        size--;
    }

    @Override
    public void updateResume(Resume r, Object index) {
        storage[(int) index] = r;
    }

    @Override
    protected List<Resume> receiveListResumes() {
        return Arrays.asList(Arrays.copyOf(storage,size));
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    protected boolean isExist(Object index) {
        return (int) index >= 0;
    }

    protected abstract void saveToArray(Resume r, Integer index);

    protected abstract void deleteFromArray(Integer index);
}