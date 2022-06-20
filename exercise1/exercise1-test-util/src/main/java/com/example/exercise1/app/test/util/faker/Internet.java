package com.example.exercise1.app.test.util.faker;

public class Internet {

    private final Faker faker;

    public Internet(Faker faker) {
        this.faker = faker;
    }

    /**
     * returns an IPv4 address in dot separated octets.
     * 
     * @return a correctly formatted IPv4 address.
     */
    public String ipV4Address() {
        return String.format("%d.%d.%d.%d",
                faker.random().nextInt(254) + 2,
                faker.random().nextInt(254) + 2,
                faker.random().nextInt(254) + 2,
                faker.random().nextInt(254) + 2);
    }
}
