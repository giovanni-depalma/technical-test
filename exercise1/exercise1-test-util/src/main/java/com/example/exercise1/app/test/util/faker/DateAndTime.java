package com.example.exercise1.app.test.util.faker;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateAndTime {

    private final Faker faker;

    public DateAndTime(Faker faker) {
        this.faker = faker;
    }

    public Date between(Date from, Date to) throws IllegalArgumentException {
        if (to.before(from)) {
            throw new IllegalArgumentException("Invalid date range, the upper bound date is before the lower bound.");
        }

        if (from.equals(to)) {
            return from;
        }

        long offsetMillis = faker.random().nextLong(to.getTime() - from.getTime());
        return new Date(from.getTime() + offsetMillis);
    }

    public Date past(int atMost, TimeUnit unit) {
        Date now = new Date();
        Date aBitEarlierThanNow = new Date(now.getTime() - 1000);
        return past(atMost, unit, aBitEarlierThanNow);
    }

    public Date past(int atMost, int minimum, TimeUnit unit) {
        Date now = new Date();
        Date minimumDate = new Date(now.getTime() - unit.toMillis(minimum));
        return past(atMost - minimum, unit, minimumDate);
    }

    public Date past(int atMost, TimeUnit unit, Date referenceDate) {
        long upperBound = unit.toMillis(atMost);

        long futureMillis = referenceDate.getTime();
        futureMillis -= 1 + faker.random().nextLong(upperBound - 1);

        return new Date(futureMillis);
    }
}
