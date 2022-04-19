package com.urise.webapp;

import java.io.File;

public class MainFile {
    public static void main(String[] args) {
//        String filePath = ".\\.gitignore";
//
//        File file = new File(".\\.gitignore");
//        try {
//            System.out.println(file.getCanonicalFile());//преобразует в канонический вид путь к файлу
//        } catch (IOException e) {
//            throw new RuntimeException("error", e);
//        }
//        System.out.println(file);
//
//        File dir = new File(".\\src\\com\\urise\\webapp");
//
//        System.out.println(dir.isDirectory());//проверка на директорию
//        String[] list = dir.list();
//        if(list != null) {
//            for (String name : list) {//dir.list() возвращает массив стрингов (dir.listFiles() возвращает массив файлов)
//                System.out.println(name);
//            }
//        }
//
//        try (FileInputStream fis = new FileInputStream(filePath)){
//            System.out.println(fis.read());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        File dir = new File(".\\src");
        getListFiles(dir);
    }


    public static void getListFiles(File dir) {
        File[] files = dir.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    System.out.println("Directory: " + file.getName());
                    getListFiles(file);
                } else if (file.isFile()) {
                    System.out.println("File: " + file.getName());
                }
            }
        }
    }
}
