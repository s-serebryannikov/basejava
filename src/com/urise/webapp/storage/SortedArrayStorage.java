package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {

//    private static class ResumeComparator implements Comparator<Resume> {//один из вариантов реализации Comparator
//
//        @Override
//        public int compare(Resume o1, Resume o2) {
//            return o1.getUuid().compareTo(o2.getUuid());
//        }
//    }

//    private static final Comparator<Resume> RESUME_COMPARATOR = (o1, o2) -> o1.getUuid().compareTo(o2.getUuid());//реализация через лямбда выражение

    private static final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getUuid);

    @Override
    protected void saveToArray(Resume r, Integer index) {
        index = -index - 1;
        System.arraycopy(storage, index, storage, index + 1, size - index);
        storage[index] = r;
    }

    @Override
    protected void deleteFromArray(Integer index) {
        if (size - index - 1 >= 0) System.arraycopy(storage, index + 1, storage, index, size - index - 1);
    }

    @Override
    protected Integer searchKey(String uuid) {
        Resume searchKey = new Resume(uuid, "dummy");
        return Arrays.binarySearch(storage, 0, size, searchKey, RESUME_COMPARATOR);
    }
}