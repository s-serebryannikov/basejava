package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageExeption;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    public static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume r) {
        String uuid = r.getUuid();
        int index = findIndex(uuid);
        if (size == STORAGE_LIMIT) {
            throw new StorageExeption("Storage overflow", uuid);
        }
        if (index >= 0) throw new ExistStorageException(uuid);
        saveResume(r, index);
        size++;
    }

    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) return storage[index];
        throw new NotExistStorageException(uuid);
    }

    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        deleteResume(index);
        storage[size - 1] = null;
        size--;
    }

    public void update(Resume r) {
        String uuid = r.getUuid();
        int index = findIndex(r.getUuid());
        if (index < 0) throw new NotExistStorageException(uuid);
        storage[index] = r;
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    protected abstract void saveResume(Resume r, int index);

    protected abstract void deleteResume(int index);

    protected abstract int findIndex(String uuid);
}