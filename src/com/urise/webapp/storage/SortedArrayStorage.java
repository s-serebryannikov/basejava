package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void saveResume(Resume r, int index) {
        index = Math.abs(index) - 1;
        if (index >= size) {
            storage[size] = r;
            size++;
        } else {
            size++;
            if (size - index >= 0) System.arraycopy(storage, index, storage, index + 1, size - index);
            storage[index] = r;
        }
    }

    @Override
    protected void deleteResume(String uuid, int index) {
        if (size - index >= 0) System.arraycopy(storage, index + 1, storage, index, size - index);
        size--;
    }

    @Override
    protected int findIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}