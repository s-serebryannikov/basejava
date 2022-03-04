package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage{

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public void save(Resume r) {
        int index = findIndex(r.getUuid());
        if (index <= -1 || size == 0) {
            storage[size] = r;
            size++;
        } else if (size >= storage.length) {
            System.out.println("Массив переполнен!!!");
        } else {
            System.out.println("Резюме с " + r.getUuid() + " уже существует");
        }
    }

    @Override
    public void update(Resume r) {
        int index = findIndex(r.getUuid());
        if (index >= 0) {
            storage[index] = r;
        } else {
            System.out.println("Резюме с " + r.getUuid() + " не существует");
        }
    }

    @Override
    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        } else {
            System.out.println("Резюме с " + uuid + " не существует");
        }
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    @Override
    protected int findIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage,0,size,searchKey);
    }
}
