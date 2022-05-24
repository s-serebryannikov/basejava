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
    protected void doSave(Resume r, Integer index) {
        storageList.add(r);
    }

    @Override
    public Resume doGet(Integer index) {
        return storageList.get(index);
    }

    @Override
    public void doDelete(Integer index) {
        storageList.remove(index.intValue());
    }

    @Override
    public void doUpdate(Resume r, Integer index) {
        storageList.set(index, r);
    }

    @Override
    public int size() {
        return storageList.size();
//        return 1;
    }

    @Override
    protected List<Resume> doCopyAll() {
        return new ArrayList<>(storageList);
    }

    @Override
    protected boolean isExist(Integer searchKey) {
        return searchKey != null;
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < storageList.size(); i++) {
            if (uuid.equals(storageList.get(i).getUuid())) {
                return i;
            }
        }
        return null;
    }
}
