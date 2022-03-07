package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public void save(Resume r) {
        String uuid = r.getUuid();
        int index = findIndex(uuid);
        if (index < 0 ) {
            index = Math.abs(index)-1;
            if (index >= size) {
                storage[size] = r;
                size++;
            } else {
                size++;
                if (size - index >= 0) System.arraycopy(storage, index, storage, index + 1, size - index);
                storage[index] = r;
            }
        } else if (size >= storage.length) {
            System.out.println("Массив переполнен!!!");
        } else {
            System.out.println("Резюме с " + uuid + " уже существует");
        }
    }

    @Override
    public void update(Resume r) {
        String uuid = r.getUuid();
        int index = findIndex(r.getUuid());
        if (index < 0) {
            storage[Math.abs(index)-1] = r;
        } else {
            System.out.println("Резюме с " + uuid + " не существует");
        }
    }

    @Override
    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            if (size - index >= 0) System.arraycopy(storage, index + 1, storage, index, size - index);
            size--;
        } else {
            System.out.println("Резюме с " + uuid + " не существует");
        }
    }

    @Override
    protected int findIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}