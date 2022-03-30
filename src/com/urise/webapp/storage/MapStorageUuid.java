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
    protected List<Resume> returnListResumes() {
        List<Resume> list = new ArrayList(storageMap.values());
        return list;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return searchKey != null;
    }

    @Override
    protected void saveResume(Resume r, Object key) {
        storageMap.put(r.getUuid(), r);
    }

    @Override
    protected Resume getResume(Object key) {
        return storageMap.get(key);
    }

    @Override
    protected void deleteResume(Object key) {
        storageMap.remove((String) key);
    }

    @Override
    protected void updateResume(Resume r, Object key) {
        storageMap.put((String) key, r);
    }

    @Override
    protected String searchKey(String uuid) {
        return storageMap.containsKey((String) uuid) ? (String) uuid : null;
    }

    @Override
    public int size() {
        return storageMap.size();
    }
}
