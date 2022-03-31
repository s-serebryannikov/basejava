package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapStorageUuid extends AbstractStorage {

    private final Map<String, Resume> storageMap = new HashMap<>();

    @Override
    public void clear() {
        storageMap.clear();
    }

    @Override
    protected List<Resume> receiveListResumes() {
        return new ArrayList<>(storageMap.values());
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return searchKey != null;
    }

    @Override
    protected void saveResume(Resume r, Object searchKey) {
        storageMap.put(r.getUuid(), r);
    }

    @Override
    protected Resume getResume(Object searchKey) {
        return storageMap.get(searchKey);
    }

    @Override
    protected void deleteResume(Object searchKey) {
        storageMap.remove((String) searchKey);
    }

    @Override
    protected void updateResume(Resume r, Object searchKey) {
        storageMap.put((String) searchKey, r);
    }

    @Override
    protected String searchKey(String uuid) {
        return storageMap.containsKey(uuid) ?  uuid : null;
    }

    @Override
    public int size() {
        return storageMap.size();
    }
}
