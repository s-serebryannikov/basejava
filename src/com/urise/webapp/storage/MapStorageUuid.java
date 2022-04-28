package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapStorageUuid extends AbstractStorage<String> {

    private final Map<String, Resume> storageMap = new HashMap<>();

    @Override
    public void clear() {
        storageMap.clear();
    }

    @Override
    protected void doSave(Resume r, String uuid) {
        storageMap.put(r.getUuid(), r);
    }

    @Override
    protected Resume doGet(String uuid) {
        return storageMap.get(uuid);
    }

    @Override
    protected void doDelete(String uuid) {
        storageMap.remove(uuid);
    }

    @Override
    protected void doUpdate(Resume r, String uuid) {
        storageMap.put(uuid, r);
    }

    @Override
    protected List<Resume> doCopyAll() {
        return new ArrayList<>(storageMap.values());
    }

    @Override
    protected boolean isExist(String uuid) {
        return storageMap.containsKey(uuid);
    }

    @Override
    public int size() {
        return storageMap.size();
    }

    @Override
    protected String getSearchKey(String uuid) {
        return storageMap.containsKey(uuid) ? uuid : null;
    }


}
