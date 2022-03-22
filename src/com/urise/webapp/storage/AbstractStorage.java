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
        saveImplements(r, index);
    }

    @Override
    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) return getImplements(index);
        throw new NotExistStorageException(uuid);
    }

    @Override
    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index < 0)
            throw new NotExistStorageException(uuid);
        deleteImplements(index);
    }

    @Override
    public void update(Resume r) {
        String uuid = r.getUuid();
        int index = findIndex(r.getUuid());
        if (index < 0) throw new NotExistStorageException(uuid);
        updateImplements(r, index);
    }

    protected abstract void saveImplements(Resume r, int index);

    protected abstract int findIndex(String uuid);

    protected abstract void deleteImplements(int index);

    protected abstract void updateImplements(Resume r, int index);

    protected abstract Resume getImplements(int index);
}
