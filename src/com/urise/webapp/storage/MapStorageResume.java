package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapStorageResume extends AbstractStorage<Resume> {

    private final Map<String, Resume> storageMap = new HashMap<>();

    @Override
    public void clear() {
        storageMap.clear();
    }

    @Override
    protected void doSave(Resume r, Resume resume) {
        storageMap.put(r.getUuid(), r);
    }

    @Override
    protected Resume doGet(Resume resume) {
        return resume;
    }

    @Override
    protected void doDelete(Resume resume) {
        storageMap.remove(resume.getUuid());
    }

    @Override
    protected void doUpdate(Resume r, Resume resume) {
        storageMap.put(resume.getUuid(), r);
    }

    @Override
    protected List<Resume> doCopyAll() {
        return new ArrayList<>(storageMap.values());
    }

    @Override
    protected boolean isExist(Resume resume) {
        return resume != null;
    }

    @Override
    public int size() {
        return storageMap.size();
    }

    @Override
    protected Resume getSearchKey(String uuid) {
        return storageMap.get(uuid);
    }

}
