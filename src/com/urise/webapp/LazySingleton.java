package com.urise.webapp;

public class LazySingleton {
    private static LazySingleton INSTANCE;

    private LazySingleton() {
    }

//    public static LazySingleton getInstance() {
//        if (INSTANCE == null) {
//            synchronized (LazySingleton.class) {
//                if (INSTANCE == null) {
//                    INSTANCE = new LazySingleton();
//                }
//            }
//        }
//        return INSTANCE;
//    }

    private static class LazySingletonHolder{
        private static final LazySingleton INSTANCE = new LazySingleton();
    }
    public static LazySingleton getInstance() {
        return LazySingletonHolder.INSTANCE;
    }
}
