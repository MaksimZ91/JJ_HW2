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