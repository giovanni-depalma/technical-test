package com.example.exercise1.app.test.util.faker;



public class Faker {
    private final RandomService randomService;
    private final Internet internet;
    private final DateAndTime date;

    public Faker(){
        this(new RandomService());
    }

    public Faker(RandomService randomService){
        this.randomService = randomService;
        this.internet = new Internet(this);
        this.date = new DateAndTime(this);
    }

    public RandomService random() {
        return this.randomService;
    }

    public Internet internet(){
        return internet;
    }
    
    public DateAndTime date() {
        return date;
    }

}
