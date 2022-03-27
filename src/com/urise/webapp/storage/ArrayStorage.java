package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected void saveToArray(Resume r, Integer index) {
        storage[size] = r;
    }

    @Override
    protected void deleteFromArray(Integer index) {
        storage[index] = storage[size - 1];
    }

    @Override
    protected Integer searchKey(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}