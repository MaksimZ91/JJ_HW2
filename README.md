# Урок 2. Reflection API.
Задача 1:  
Создайте абстрактный класс "Animal" с полями "name" и "age".  
Реализуйте два класса-наследника от "Animal" (например, "Dog" и "Cat") с уникальными полями и методами.  
Создайте массив объектов типа "Animal" и с использованием Reflection API выполните следующие действия:  
Выведите на экран информацию о каждом объекте. 
Вызовите метод "makeSound()" у каждого объекта, если такой метод присутствует.  

![2](https://github.com/MaksimZ91/JJ_HW2/assets/72209139/40c0c49e-fa7c-44ef-b4fb-748557cad9ea)

## Class Main 
```java
package org.example;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;


public class Main {
    public static void main(String[] args)  {
        Checker checker = new Checker();
        Collection <Animal> animals = new ArrayList<>();
        animals.add(new Cat("Барсик", 12, "Black"));
        animals.add(new Cat("Мурзик", 5, "White"));
        animals.add(new Dog("Мухтар", 4, "Хаски"));
        animals.add(new Dog("Симба", 4, "Такса"));
        animals.stream().forEach(animal -> {
            checker.getInfo(animal.getClass(), animal);
            try {
                System.out.println(animal.getClass().getMethod("makeSound").invoke(animal));
                System.out.println("--------------------------------------");
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }

        });
    }
}
```
## Class Dog
```java
package org.example;

public class Dog extends Animal implements Voice{
    private String breed;



    public Dog(String name, int age, String breed) {
        super(name, age);
        this.breed = breed;
    }
    private void sleep(int sleepTime){}

    @Override
    public String makeSound() {
        return "Гав-гав!";
    }
}
```
## Class Cat
```java
package org.example;

public class Cat extends Animal implements Voice{
    private String color;

    public Cat(String name, int age, String color) {
        super(name, age);
        this.color = color;
    }

    private void claw(int clawCount, Object claw){ }

    @Override
    public String makeSound() {
        return "Мрр-ррр";
    }
}
```
## Interface Voice 
```java
package org.example;

public interface Voice {
    String makeSound();
}
```

## Class Checker
```java
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

```
