package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public class ArrayStorage extends AbstractArrayStorage{

    @Override
    public void save(Resume r) {
        String uuid = r.getUuid();
        int index = findIndex(uuid);
        if (index <= -1 || size == 0) {
            storage[size] = r;
            size++;
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
        if (index == -1) {
            System.out.println("Резюме с " + uuid + " не существует");
        } else {
            storage[index] = r;
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
    protected int findIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}