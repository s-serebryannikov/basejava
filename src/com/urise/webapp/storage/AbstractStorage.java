package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void save(Resume r) {
        Object key = findSeachKeyIfExist(r.getUuid());
        saveResume(r, key);
    }

    @Override
    public Resume get(String uuid) {
        Object key = findSeachKeyIfNotExist(uuid);
        return getResume(key);
    }

    @Override
    public void delete(String uuid) {
        Object key = findSeachKeyIfNotExist(uuid);
        deleteResume(key);
    }

    @Override
    public void update(Resume r) {
        Object key = findSeachKeyIfNotExist(r.getUuid());
        updateResume(r, key);
    }

    public Object findSeachKeyIfExist(String uuid) {
        Object key = searchKey(uuid);
        if(isExist(key)){
            throw new ExistStorageException(uuid);
        }
        return key;
    }

    public Object findSeachKeyIfNotExist(String uuid) {
        Object key = searchKey(uuid);
        if(!isExist(key)){
            throw new NotExistStorageException(uuid);
        }
        return key;
    }

    protected abstract boolean isExist(Object searchKey);

    protected abstract void saveResume(Resume r, Object key);

    protected abstract Object searchKey(String uuid);

    protected abstract void deleteResume(Object key);

    protected abstract void updateResume(Resume r, Object key);

    protected abstract Resume getResume(Object key);

}
