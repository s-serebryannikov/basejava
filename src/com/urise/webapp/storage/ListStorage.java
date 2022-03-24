package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {

    List<Resume> storageList = new ArrayList<>();

    @Override
    public void clear() {
        storageList.clear();
    }

    @Override
    protected void saveResume(Resume r, int index) {
        storageList.add(r);
    }

    @Override
    public Resume getResume(int index) {
        return storageList.get(index);
    }

    @Override
    public void deleteResume(int index) {
        storageList.remove(index);
    }

    @Override
    public void updateResume(Resume r, int index) {
        storageList.set(index, r);
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
    protected int findIndex(String uuid) {
        for (Resume resume : storageList) {
            if (resume.getUuid().equals(uuid)) {
                return storageList.indexOf(resume);
            }
        }
        return -1;
    }
}
