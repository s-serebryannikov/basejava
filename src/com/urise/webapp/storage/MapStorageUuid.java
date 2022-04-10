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
    protected void saveResume(Resume r, String uuid) {
        storageMap.put(r.getUuid(), r);
    }

    @Override
    protected Resume getResume(String uuid) {
        return storageMap.get(uuid);
    }

    @Override
    protected void deleteResume(String uuid) {
        storageMap.remove(uuid);
    }

    @Override
    protected void updateResume(Resume r, String uuid) {
        storageMap.put(uuid, r);
    }

    @Override
    protected List<Resume> receiveListResumes() {
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
    protected String searchKey(String uuid) {
        return storageMap.containsKey(uuid) ? uuid : null;
    }


}
