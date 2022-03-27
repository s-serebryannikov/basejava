package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void save(Resume r) {
        Object key = checkSerchKeyExistStorage(r.getUuid());
        saveResume(r, key);
    }

    @Override
    public Resume get(String uuid) {
        Object key = checkSerchKeyNotExistStorage(uuid);
        return getResume(key);
    }

    @Override
    public void delete(String uuid) {
        Object key = checkSerchKeyNotExistStorage(uuid);
        deleteResume(key);
    }

    @Override
    public void update(Resume r) {
        Object key = checkSerchKeyNotExistStorage(r.getUuid());
        updateResume(r, key);
    }

    public Object checkSerchKeyExistStorage(String uuid) {
        Object key = searchKey(uuid);
        if(isException(key)){
            throw new ExistStorageException(uuid);
        }
        return key;
    }

    public Object checkSerchKeyNotExistStorage(String uuid) {
        Object key = searchKey(uuid);
        if(!isException(key)){
            throw new NotExistStorageException(uuid);
        }
        return key;
    }

    protected abstract boolean isException(Object searchKey);

    protected abstract void saveResume(Resume r, Object index);

    protected abstract Object searchKey(String uuid);

    protected abstract void deleteResume(Object key);

    protected abstract void updateResume(Resume r, Object key);

    protected abstract Resume getResume(Object key);

}
