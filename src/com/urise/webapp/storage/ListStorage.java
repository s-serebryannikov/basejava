package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {

    private final List<Resume> storageList = new ArrayList<>();

    @Override
    public void clear() {
        storageList.clear();
    }

    @Override
    protected void saveResume(Resume r, Object index) {
        storageList.add(r);
    }

    @Override
    public Resume getResume(Object index) {
        return storageList.get((Integer) index);
    }

    @Override
    public void deleteResume(Object index) {
        storageList.remove((int) index);
    }

    @Override
    public void updateResume(Resume r, Object index) {
        storageList.set((int) index, r);
    }

    @Override
    public Resume[] getAll() {
        return storageList.toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return storageList.size();
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return (int) searchKey >= 0;
    }

    @Override
    protected Integer searchKey(String uuid) {
        for (int i = 0; i < storageList.size(); i++) {
            if (uuid.equals(storageList.get(i).getUuid())) {
                return i;
            }
        }
        return -1;
    }
}
