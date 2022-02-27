package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[6];
    private int size;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume r) {
        int count = 0;
        if (size != storage.length) {
            for (int i = 0; i < size; i++) {
                if (storage[i].getUuid().equals(r.getUuid())) {
                    count++;
                }
            }
            if (count == 0) {
                storage[size] = r;
                size++;
            } else System.out.println("Резюме с таким uuid уже существует");
        } else System.out.println("Архив резюме переполнен");
    }

    public void update(Resume r) {
        int count = 0;
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(r.getUuid())) {
                storage[i] = r;
                System.out.println("Резюме " + r.getUuid() + " удачно перезаписано");
                count++;
                break;
            }
        }
        if (count == 0) System.out.println("Резюме с " + r.getUuid() + " не существует.");
    }

    public Resume get(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return storage[i];
            } else System.out.println("Резюме с " + uuid + " не существует");
        }
        return null;
    }

    public void delete(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                storage[i] = storage[size - 1];
                storage[size - 1] = null;
                size--;
            } else {
                System.out.println("Резюме c " + uuid + " отсутствует!");
            }
            break;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }
}