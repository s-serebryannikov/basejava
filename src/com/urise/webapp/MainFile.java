package com.urise.webapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MainFile {
    public static void main(String[] args) {
        String filePath = ".\\.gitignore";

        File file = new File(".\\.gitignore");
        try {
            System.out.println(file.getCanonicalFile());//преобразует в канонический вид путь к файлу
        } catch (IOException e) {
            throw new RuntimeException("error", e);
        }
        System.out.println(file);

        File dir = new File(".\\src\\com\\urise\\webapp");

        System.out.println(dir.isDirectory());//проверка на директорию
        String[] list = dir.list();
        if(list != null) {
            for (String name : list) {//dir.list() возвращает массив стрингов (dir.listFiles() возвращает массив файлов)
                System.out.println(name);
            }
        }

        try (FileInputStream fis = new FileInputStream(filePath)){
            System.out.println(fis.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        File dir = new File(".\\src");
//        printDirectoryDeeply(dir);
        doRecurs(new File(".\\src"), 0);
    }


    //    public static void printDirectoryDeeply(File dir) {
//        File[] files = dir.listFiles();
//
//        if (files != null) {
//            for (File file : files) {
//                if (file.isDirectory()) {
//                    System.out.println("Directory: " + file.getName());
//                    printDirectoryDeeply(file);
//                } else if (file.isFile()) {
//                    System.out.println("    File: " + file.getName());
//                }
//            }
//        }
//    }


    static String repeat(int n, String value) {
        return new String(new char[n]).replace("\0", value);
    }

    static void doRecurs(File dir, int level) {
        final String indent = repeat(level, "   ");

        if (dir.isDirectory()) {
            File[] list = dir.listFiles();
            if (list == null)
                return;

            for (File name : list) {
                if (name.isFile()) {
                    System.out.println(indent + "File - " + name.getName());
                } else {
                    System.out.println(indent + "Directory - " + name.getName());
                    doRecurs(name, level + 1);
                }
            }

        } else {
            System.out.println(dir.getName() + " не является папкой");
        }
    }


}
