package testPackageSupp;

import java.util.Date;

public class Dog extends Animal {
    private String breed;
    private double weight;
    private boolean isVaccinated;
    private String coatColor;
    private int age;
    private String[] favoriteToys;
    private boolean isNeutered;
    private String ownerName;
    private Date lastCheckupDate;
    private double tailLength;

    public Dog(String name, String breed, double weight, int age, String coatColor) {
        super(name, age);
        this.breed = breed;
        this.weight = weight;
        this.age = age;
        this.coatColor = coatColor;
        this.isVaccinated = false;
        this.isNeutered = false;
        this.favoriteToys = new String[3];
        this.lastCheckupDate = new Date();
    }

    // Getters and setters for all attributes
    
    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
/*
    public boolean isVaccinated() {
        return isVaccinated;
    }

    public void setVaccinated(boolean vaccinated) {
        isVaccinated = vaccinated;
    }

    public String getCoatColor() {
        return coatColor;
    }

    public void setCoatColor(String coatColor) {
        this.coatColor = coatColor;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String[] getFavoriteToys() {
        return favoriteToys;
    }

    public void setFavoriteToys(String[] favoriteToys) {
        this.favoriteToys = favoriteToys;
    }

    public boolean isNeutered() {
        return isNeutered;
    }

    public void setNeutered(boolean neutered) {
        isNeutered = neutered;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Date getLastCheckupDate() {
        return lastCheckupDate;
    }

    public void setLastCheckupDate(Date lastCheckupDate) {
        this.lastCheckupDate = lastCheckupDate;
    }

    public double getTailLength() {
        return tailLength;
    }

    public void setTailLength(double tailLength) {
        this.tailLength = tailLength;
    }

    // Other methods
    */
    @Override
    public void makeSound() {
        System.out.println("Woof!");
    }
    
    public void wagTail() {
        System.out.println(getName() + " is wagging its tail!");
    }
/*
    public void fetch() {
        System.out.println(getName() + " is fetching the ball!");
    }

    public void eat(String food) {
        System.out.println(getName() + " is eating " + food);
    }

    public void sleep() {
        System.out.println(getName() + " is sleeping.");
    }
    */
}



