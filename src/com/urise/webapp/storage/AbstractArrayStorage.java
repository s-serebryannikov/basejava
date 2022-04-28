package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    public static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];

    protected int size;

    protected abstract void saveToArray(Resume r, Integer index);

    protected abstract void deleteFromArray(Integer index);

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public void doSave(Resume r, Integer index) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", r.getUuid());
        }
        saveToArray(r, index);
        size++;
    }

    @Override
    public Resume doGet(Integer index) {
        return storage[index];
    }

    @Override
    public void doDelete(Integer index) {
        deleteFromArray(index);
        storage[size - 1] = null;
        size--;
    }

    @Override
    public void doUpdate(Resume r, Integer index) {
        storage[index] = r;
    }

    @Override
    protected List<Resume> doCopyAll() {
        return Arrays.asList(Arrays.copyOf(storage, size));
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    protected boolean isExist(Integer index) {
        return index >= 0;
    }
}