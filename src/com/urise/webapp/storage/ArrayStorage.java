package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public class ArrayStorage extends AbstractArrayStorage{

    @Override
    public void save(Resume r) {
        int index = findIndex(r.getUuid());
        if (index != -1) {
            System.out.println("Резюме с " + r.getUuid() + " уже существует");
        } else if (size >= storage.length) {
            System.out.println("Массив переполнен!!!");
        } else {
            storage[size] = r;
            size++;
        }
    }

    protected int findIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}