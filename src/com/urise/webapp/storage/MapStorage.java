package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapStorage extends AbstractStorage {

    private final Map<String, Resume> storageMap = new HashMap<>();

    @Override
    public void clear() {
        storageMap.clear();
    }

    @Override
    protected boolean isException(Object searchKey) {
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
    public Resume[] getAll() {
        List<Resume> list = new ArrayList(storageMap.values());
        Collections.sort(list);
        return list.toArray(new Resume[0]);
    }

    @Override
    protected String searchKey(String uuid) {
        return storageMap.containsKey(uuid) ? uuid : null;
    }

    @Override
    public int size() {
        return storageMap.size();
    }
}
