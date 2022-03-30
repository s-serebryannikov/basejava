package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;

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

    private Object findSeachKeyIfExist(String uuid) {
        Object key = searchKey(uuid);
        if (isExist(key)) {
            throw new ExistStorageException(uuid);
        }
        return key;
    }

    private Object findSeachKeyIfNotExist(String uuid) {
        Object key = searchKey(uuid);
        if (!isExist(key)) {
            throw new NotExistStorageException(uuid);
        }
        return key;
    }


    @Override
    public List<Resume> getAllSorted() {
        Comparator<Resume> resumeComparator = new Comparator<Resume>() {
            @Override
            public int compare(Resume r1, Resume r2) {
                if (!r1.getFullName().equals(r2.getFullName())) {
                    return r1.getFullName().compareTo(r2.getFullName());
                }
                return r1.getUuid().compareTo(r2.getUuid());
            }
        };
        List<Resume> sortedList = returnListResumes();
        sortedList.sort(resumeComparator);
        return sortedList;
    }

    protected abstract List<Resume> returnListResumes();

    protected abstract boolean isExist(Object searchKey);

    protected abstract void saveResume(Resume r, Object key);

    protected abstract Object searchKey(String uuid);

    protected abstract void deleteResume(Object key);

    protected abstract void updateResume(Resume r, Object key);

    protected abstract Resume getResume(Object key);
}
