package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage{

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
    protected int findIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage,0,size,searchKey);
    }
}