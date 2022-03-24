package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapStorage extends AbstractStorage {

    Map<String, Resume> storageMap = new HashMap();

    @Override
    public void clear() {
        storageMap.clear();
    }

    @Override
    protected void saveResume(Resume r,int index) {
        storageMap.put(r.getUuid(),r);
    }

    @Override
    protected Resume getResume(int index) {
        ArrayList<String> key = new ArrayList<>(storageMap.keySet());
        return storageMap.get(key.get(index));
    }

    @Override
    protected void deleteResume(int index) {
        ArrayList<String> key = new ArrayList<>(storageMap.keySet());
        storageMap.remove(key.get(index));
    }

    @Override
    protected void updateResume(Resume r, int index) {
        storageMap.put(r.getUuid(),r);
    }

    @Override
    public Resume[] getAll() {
        List<Resume> list = new ArrayList(storageMap.values());
        Collections.sort(list);
        return list.toArray(new Resume[0]);
    }

    @Override
    protected int findIndex(String uuid) {
        for(Map.Entry<String,Resume> entry: storageMap.entrySet()){
            if(entry.getKey().equals(uuid)){
                ArrayList<String> key = new ArrayList<>(storageMap.keySet());
                return key.indexOf(uuid);
            }
        }
        return -1;
    }

    @Override
    public int size() {
        return storageMap.size();
    }
}
