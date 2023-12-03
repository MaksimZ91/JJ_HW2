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
