package com.urise.webapp;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MainCollections {
    private static final Resume RESUME_1 = new Resume("uuid1");
    private static final Resume RESUME_2 = new Resume("uuid2");
    private static final Resume RESUME_3 = new Resume("uuid3");
    private static final Resume RESUME_4 = new Resume("uuid4");

    public static void main(String[] args) {
        Collection<Resume> collection = new ArrayList<>();

        collection.add(RESUME_1);
        collection.add(RESUME_2);
        collection.add(RESUME_3);

//        for(Resume r: collection){
//            System.out.println(r);
//            if(Objects.equals(r.getUuid(),"uuid1")){
////                collection.remove(r);
//            }
//        }
//
//        Iterator<Resume> iterator = collection.iterator();
//        while (iterator.hasNext()){
//            Resume r = iterator.next();
//            System.out.println(r);
//            if(Objects.equals(r.getUuid(),"uuid1")){
//                iterator.remove();
//            }
//        }
//        System.out.println(collection);

        Map<String, Resume> map = new HashMap<>();
        map.put("uuid1", RESUME_1);
        map.put("uuid2", RESUME_2);
        map.put("uuid3", RESUME_3);

        for(String uuid : map.keySet()){
            System.out.println(map.get(uuid));
        }

        for(Map.Entry<String,Resume> entry: map.entrySet()){
            System.out.println(entry.getValue());
        }

        List<Resume> resumes = Arrays.asList(RESUME_1,RESUME_2,RESUME_3);
        System.out.println(resumes);
        resumes .remove(1);
        System.out.println(resumes);




    }
}
