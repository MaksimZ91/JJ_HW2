package org.example;

import java.lang.reflect.*;
import java.util.Arrays;

public class Checker {

    public Class getInfo(Class clazz, Object obj) {
        if (clazz == Object.class) {
            return clazz;
        }
        System.out.println("Название класса: " + clazz.getName().split("\\.")[2]);
        System.out.println("Кол-во конструкторов: " + clazz.getConstructors().length);
        getFieldsInfo(clazz.getDeclaredFields(), obj);
        getMethodsInfo(clazz.getDeclaredMethods());
        System.out.println("Класс унаследован от: " + clazz.getSuperclass().getName().split("\\.")[2]);
        return getInfo(clazz.getSuperclass(), obj);
    }

    private void getMethodsInfo(Method[] methods) {
        if (methods.length == 0) return;
        System.out.println("Методы: ");
        Arrays.stream(methods).forEach(method -> System.out.println(" " +
                Modifier.toString(method.getModifiers()) + " "
                + parsingString(String.valueOf(method.getReturnType())) + " "
                + method.getName() + "(" + parsingMethodParameters(method.getParameters()) + ")" + " {}"));
    }
    private String parsingMethodParameters(Parameter[] parameters) {
        if (parameters.length == 0) return "";
        StringBuilder sb = new StringBuilder();
        Arrays.stream(parameters).forEach(parameter -> {
            sb.append(parsingString(String.valueOf(parameter.getType()))).append(" ")
                    .append(parameter.getName()).append(", ");
        });
        return sb.toString().substring(0, sb.length() - 2);
    }

    private void getFieldsInfo(Field[] fields, Object obj) {
        if (fields.length == 0) return;
        System.out.println("Свойства: ");
        Arrays.stream(fields).forEach(field -> {
            try {
                field.setAccessible(true);
                System.out.println(" " + Modifier.toString(field.getModifiers()) + " "
                        + parsingString(String.valueOf(field.getType())) + " "
                        + field.getName() + ": " + field.get(obj));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
    }
    private String parsingString(String st) {
        if (st.contains("class java.lang"))
            return st.split("\\.")[2];
        return st;
    }


}
