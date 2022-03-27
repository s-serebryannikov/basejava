package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageExeption;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    public static final int STORAGE_LIMIT = 5;

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
        saveToArray(r,(int) index);
        size++;
    }

    @Override
    public Resume getResume(Object index) {
        return storage[(int) index];
    }

    @Override
    public void deleteResume(Object index) {
        deleteFromArray((int) index);
        storage[size - 1] = null;
        size--;
    }

    @Override
    public void updateResume(Resume r, Object index) {
        storage[(int) index] = r;
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    protected boolean isException(Object index) {
        return (int) index >= 0;
    }

    protected abstract void saveToArray(Resume r, int index);

    protected abstract void deleteFromArray(int index);
}