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
    public void saveImplements(Resume r, int index) {
        if (size == STORAGE_LIMIT) {
            throw new StorageExeption("Storage overflow", r.getUuid());
        }
        saveResume(r, index);
        size++;
    }

    @Override
    public Resume getImplements(int index) {
        return storage[index];
    }

    @Override
    public void deleteImplements(int index) {
        deleteResume(index);
        storage[size - 1] = null;
        size--;
    }

    @Override
    public void updateImplements(Resume r, int index) {
        storage[index] = r;
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    @Override
    public int size() {
        return size;
    }

    protected abstract void saveResume(Resume r, int index);

    protected abstract void deleteResume(int index);
}