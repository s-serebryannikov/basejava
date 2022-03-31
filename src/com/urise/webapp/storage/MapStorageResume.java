package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapStorageResume extends AbstractStorage {

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
        storageMap.put(r.getUuid(),r);
    }

    @Override
    protected Resume getResume(Object searchKey) {
        return (Resume) searchKey;
    }

    @Override
    protected void deleteResume(Object searchKey) {
        Resume resume = (Resume) searchKey;
        storageMap.remove(resume.getUuid());
    }

    @Override
    protected void updateResume(Resume r, Object searchKey) {
        Resume resume = (Resume) searchKey;
        storageMap.put(resume.getUuid(),r);
    }

    @Override
    protected Resume searchKey(String uuid) {
        return storageMap.get(uuid);
    }

    @Override
    public int size() {
        return storageMap.size();
    }
}
