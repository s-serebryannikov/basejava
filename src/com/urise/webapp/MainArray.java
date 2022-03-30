package com.urise.webapp;

import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.MapStorageResume;
import com.urise.webapp.storage.Storage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class MainArray {
    final static Storage ARRAY_STORAGE = new MapStorageResume();

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Resume r;
        while (true) {
            System.out.print("Введите одну из команд - (list | size | save uuid| update uuid | delete uuid | get uuid | clear | exit): ");
            String[] params = reader.readLine().trim().toLowerCase().split(" ");
            if (params.length < 1 || params.length > 4) {
                System.out.println("Неверная команда.");
                continue;
            }
            String uuid = null;
            String fullName = null;
            if (params.length == 4) {
                uuid = params[1].intern();
                fullName = params[2] + " " + params[3];
            }
            if (params.length == 2) {
                uuid = params[1].intern();
            }
            switch (params[0]) {
                case "list":
                    printAll();
                    break;
                case "size":
                    System.out.println(ARRAY_STORAGE.size());
                    break;
                case "save":
                    r = new Resume(uuid,fullName);
                    ARRAY_STORAGE.save(r);
                    printAll();
                    break;
                case "update":
                    r = new Resume(uuid,fullName);
                    ARRAY_STORAGE.update(r);
                    printAll();
                    break;
                case "delete":
                    ARRAY_STORAGE.delete(uuid);
                    printAll();
                    break;
                case "get":
                    System.out.println(ARRAY_STORAGE.get(uuid));
                    break;
                case "clear":
                    ARRAY_STORAGE.clear();
                    printAll();
                    break;
                case "exit":
                    return;
                default:
                    System.out.println("Неверная команда.");
                    break;
            }
        }
    }

    static void printAll() {
        List<Resume> list= ARRAY_STORAGE.getAllSorted();
        System.out.println("----------------------------");
        if (list.size() == 0) {
            System.out.println("Empty");
        } else {
            for (Resume r : list ){
                System.out.println(r);
            }
        }
        System.out.println("----------------------------");
    }
}
