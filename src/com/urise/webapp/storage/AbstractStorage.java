package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<SK> implements Storage {
//    protected final Logger log = Logger.getLogger(getClass().getName());

    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    protected abstract List<Resume> receiveListResumes();

    protected abstract boolean isExist(SK searchKey);

    protected abstract void saveResume(Resume r, SK searchKey);

    protected abstract SK searchKey(String uuid);

    protected abstract void deleteResume(SK searchKey);

    protected abstract void updateResume(Resume r, SK searchKey);

    protected abstract Resume getResume(SK searchKey);

    @Override
    public void save(Resume r) {
        LOG.info("Save " + r);
        SK searchKey = getNotExistedSearchKey(r.getUuid());
        saveResume(r, searchKey);
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        SK searchKey = getExistedSearchKey(uuid);
        return getResume(searchKey);
    }

    @Override
    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        SK searchKey = getExistedSearchKey(uuid);
        deleteResume(searchKey);
    }

    @Override
    public void update(Resume r) {
        LOG.info("Update " + r);
        SK searchKey = getExistedSearchKey(r.getUuid());
        updateResume(r, searchKey);
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("getAllSorted");

        //           new Comparator<Resume>() {
//            @Override
//            public int compare(Resume r1, Resume r2) {
//                if (!r1.getFullName().equals(r2.getFullName())) {
//                    return r1.getFullName().compareTo(r2.getFullName());
//                }
//                return r1.getUuid().compareTo(r2.getUuid());
//            }
//        };

        Comparator<Resume> resumeComparator = Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);
        List<Resume> sortedList = receiveListResumes();
        sortedList.sort(resumeComparator);
        return sortedList;
    }

    private SK getNotExistedSearchKey(String uuid) {
        SK searchKey = searchKey(uuid);
        if (isExist(searchKey)) {
            LOG.warning("Resume " + uuid + " already exist");
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    private SK getExistedSearchKey(String uuid) {
        SK searchKey = searchKey(uuid);
        if (!isExist(searchKey)) {
            LOG.warning("Resume " + uuid + " not exist");
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }
}
