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
    protected List<Resume> returnListResumes() {
        List<Resume> list = new ArrayList(storageMap.values());
        return list;
    }

    @Override
    protected boolean isExist(Object keyResume) {
        return keyResume != null;
    }

    @Override
    protected void saveResume(Resume r, Object keyResume) {
        storageMap.put(r.getUuid(),r);
    }

    @Override
    protected Resume getResume(Object keyResume) {
        return (Resume) keyResume;
    }

    @Override
    protected void deleteResume(Object keyResume) {
        Resume resume = (Resume) keyResume;
        storageMap.remove(resume.getUuid());
    }

    @Override
    protected void updateResume(Resume r, Object keyResume) {
        Resume resume = (Resume) keyResume;
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
