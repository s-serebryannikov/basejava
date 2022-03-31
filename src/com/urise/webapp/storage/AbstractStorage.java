package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage implements Storage {

    @Override
    public void save(Resume r) {
        Object searchKey = getNotExistedSearchKey(r.getUuid());
        saveResume(r, searchKey);
    }

    @Override
    public Resume get(String uuid) {
        Object searchKey = getExistedSearchKey(uuid);
        return getResume(searchKey);
    }

    @Override
    public void delete(String uuid) {
        Object searchKey = getExistedSearchKey(uuid);
        deleteResume(searchKey);
    }

    @Override
    public void update(Resume r) {
        Object searchKey = getExistedSearchKey(r.getUuid());
        updateResume(r, searchKey);
    }

    private Object getNotExistedSearchKey(String uuid) {
        Object searchKey = searchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    private Object getExistedSearchKey(String uuid) {
        Object searchKey = searchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }


    @Override
    public List<Resume> getAllSorted() {
        Comparator<Resume> resumeComparator = Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);

 //           new Comparator<Resume>() {
//            @Override
//            public int compare(Resume r1, Resume r2) {
//                if (!r1.getFullName().equals(r2.getFullName())) {
//                    return r1.getFullName().compareTo(r2.getFullName());
//                }
//                return r1.getUuid().compareTo(r2.getUuid());
//            }
//        };
        List<Resume> sortedList = receiveListResumes();
        sortedList.sort(resumeComparator);
        return sortedList;
    }

    protected abstract List<Resume> receiveListResumes();

    protected abstract boolean isExist(Object searchKey);

    protected abstract void saveResume(Resume r, Object searchKey);

    protected abstract Object searchKey(String uuid);

    protected abstract void deleteResume(Object searchKey);

    protected abstract void updateResume(Resume r, Object searchKey);

    protected abstract Resume getResume(Object searchKey);
}
