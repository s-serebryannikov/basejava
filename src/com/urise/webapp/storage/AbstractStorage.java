package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void save(Resume r) {
        String uuid = r.getUuid();
        int index = findIndex(uuid);
        if (index >= 0) throw new ExistStorageException(uuid);
        saveResume(r, index);
    }

    @Override
    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) return getResume(index);
        throw new NotExistStorageException(uuid);
    }

    @Override
    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index < 0)
            throw new NotExistStorageException(uuid);
        deleteResume(index);
    }

    @Override
    public void update(Resume r) {
        String uuid = r.getUuid();
        int index = findIndex(r.getUuid());
        if (index < 0) throw new NotExistStorageException(uuid);
        updateResume(r, index);
    }

    protected abstract void saveResume(Resume r, int index);

    protected abstract int findIndex(String uuid);

    protected abstract void deleteResume(int index);

    protected abstract void updateResume(Resume r, int index);

    protected abstract Resume getResume(int index);
}
