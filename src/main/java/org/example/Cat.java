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
