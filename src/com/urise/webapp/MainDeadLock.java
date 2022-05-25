package com.urise.webapp;

public class MainDeadLock {
    public static final Object LOCK1 = new Object();
    public static final Object LOCK2 = new Object();

    public static void main(String[] args) {
        new Thread(() -> {
            monitorLock(LOCK1, LOCK2);
        }).start();
        new Thread(() -> {
            monitorLock(LOCK2, LOCK1);
        }).start();
    }

    private static void monitorLock(Object lock1, Object lock2) {
        System.out.println(Thread.currentThread().getName() + " - Попытка захватить монитор объекта lock1");
        synchronized (lock1) {
            System.out.println(Thread.currentThread().getName() + " - Монитор объекта Lock1 захвачен");
            System.out.println(Thread.currentThread().getName() + " - Попытка захватить монитор объекта Lock2");
            synchronized (lock2) {
                System.out.println(Thread.currentThread().getName() + " - Мониторы объектов Lock1 и Lock2 захвачены");
            }
        }
    }
}

