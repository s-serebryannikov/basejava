package com.urise.webapp;

import com.urise.webapp.model.Resume;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {
    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, ClassNotFoundException, InvocationTargetException {
        Resume r = new Resume("id1");

        Field field = r.getClass().getDeclaredFields()[0];
        field.setAccessible(true);
        System.out.println(field.getName());
        System.out.println(field.get(r));
        field.get(r);
        field.set(r, "id145");
        System.out.println(r);

        System.out.println("\n=================\n");

        Class resumeClass = Resume.class;
        Method method1 = resumeClass.getMethod("toString");
        System.out.println(method1.invoke(r));
    }
}
