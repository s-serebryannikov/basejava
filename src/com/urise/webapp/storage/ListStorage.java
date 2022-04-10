package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {

    private final List<Resume> storageList = new ArrayList<>();

    @Override
    public void clear() {
        storageList.clear();
    }

    @Override
    protected void saveResume(Resume r, Integer index) {
        storageList.add(r);
    }

    @Override
    public Resume getResume(Integer index) {
        return storageList.get(index);
    }

    @Override
    public void deleteResume(Integer index) {
        storageList.remove(index.intValue());
    }

    @Override
    public void updateResume(Resume r, Integer index) {
        storageList.set(index, r);
    }

    @Override
    public int size() {
        return storageList.size();
    }

    @Override
    protected List<Resume> receiveListResumes() {
        return new ArrayList<>(storageList);
    }

    @Override
    protected boolean isExist(Integer searchKey) {
        return searchKey != null;
    }

    @Override
    protected Integer searchKey(String uuid) {
        for (int i = 0; i < storageList.size(); i++) {
            if (uuid.equals(storageList.get(i).getUuid())) {
                return i;
            }
        }
        return null;
    }
}
