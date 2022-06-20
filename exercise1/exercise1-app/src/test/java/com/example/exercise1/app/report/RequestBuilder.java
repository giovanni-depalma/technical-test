package com.example.exercise1.app.report;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import com.example.exercise1.app.request.HttpStatus;
import com.example.exercise1.app.request.Request;
import com.example.exercise1.app.test.util.faker.Faker;

public class RequestBuilder {
    private final Faker faker = new Faker();

    private Optional<String> ipV4Address;
    private Optional<HttpStatus> status;
    private Optional<Long> bytes;
    private Date from;
    private Date to;

    public RequestBuilder(Date from, Date to) {
        this.ipV4Address = Optional.empty();
        this.status = Optional.empty();
        this.bytes = Optional.empty();
        this.from = from;
        this.to = to;
    }

    public Faker getFaker() {
        return this.faker;
    }

    public void setBetween(Date from, Date to) {
        this.from = from;
        this.to = to;
    }

    public void setDate(Date date) {
        this.from = date;
        this.to = date;
    }

    public void setStatus(HttpStatus status) {
        this.status = Optional.ofNullable(status);
    }

    public void setAddress(String ipV4Address) {
        this.ipV4Address = Optional.ofNullable(ipV4Address);
    }

    public void setBytes(long bytes) {
        this.bytes = Optional.ofNullable(bytes);
    }

    public List<Request> buildRequest(int nRequest) {
        return IntStream.range(0, nRequest).mapToObj(i -> {
            var itemTimestamp = faker.date().between(from, to).getTime();
            var itemBytes = bytes.orElseGet(() -> faker.random().nextLong());
            var itemStatus = status.orElseGet(() -> HttpStatus.OK);
            var itemIpV4Address = ipV4Address.orElseGet(() -> faker.internet().ipV4Address());
            return new Request(
                    itemTimestamp,
                    itemBytes, itemStatus, itemIpV4Address);
        }).toList();

    }

    

   
}
