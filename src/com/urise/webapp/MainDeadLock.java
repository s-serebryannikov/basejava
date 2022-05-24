package com.urise.webapp;

public class MainDeadLock {
    public static final Object lock1 = new Object();
    public static final Object lock2 = new Object();

    public static void main(String[] args) {
        Thread1 thread10 = new Thread1();
        Thread2 thread20 = new Thread2();
        thread10.start();
        thread20.start();
    }
}

class Thread1 extends Thread{
    public void run(){
        System.out.println("Thread1 - Попытка захватить монитор объекта Lock1");
        synchronized (MainDeadLock.lock1){
            System.out.println("Thread1 - Монитор объекта Lock1 захвачен");

            System.out.println("Thread1 - Попытка захватить монитор объекта Lock2");
            synchronized (MainDeadLock.lock2){
                System.out.println("Thread1 - Мониторы объектов Lock1 и Lock2 захвачены");
            }
        }
    }
}

class Thread2 extends Thread{
    public void run(){
        System.out.println("Thread2 - Попытка захватить монитор объекта Lock2");
        synchronized (MainDeadLock.lock2){
            System.out.println("Thread2 - Монитор объекта Lock2 захвачен");

            System.out.println("Thread2 - Попытка захватить монитор объекта Lock1");
            synchronized (MainDeadLock.lock1){
                System.out.println("Thread2 - Мониторы объектов Lock1 и Lock2 захвачены");
            }
        }
    }
}