package com.example.exercise1.app.report;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.exercise1.app.request.HttpStatus;
import com.example.exercise1.app.request.Request;
import com.example.exercise1.app.request.RequestFakeBuilder;
import com.example.exercise1.app.test.util.faker.Faker;

public class TrafficReportByIpServiceTest {

    private Faker faker;
    private Date from;
    private Date to;
    private TrafficReportByIpService service;
    private RequestFakeBuilder builder;

    @BeforeEach
    private void init(){
        faker = new Faker();
        var validDayFrom = 5;
        var validDayTo = 4;
        from = faker.date().past(validDayFrom, validDayTo, TimeUnit.DAYS);
        to = faker.date().past(validDayTo, TimeUnit.DAYS);
        builder = new RequestFakeBuilder(from, to);
        service = new TrafficReportByIpService();
    }
    
    @Test
    public void filterByDate(){
        var size = 1;
        var reqs = new ArrayList<Request>();
        // add requests inside interval
        reqs.addAll(builder.buildRequest(size));
        //add requests before interval 
        reqs.addAll(getRequestBefore(from, size));
        // add requests after interval
        reqs.addAll(getRequestAfter(to, size));
        var actual = callService(reqs, from, to);
        assertEquals(size, actual.size());
    }

    @Test
    public void filterByStatus() {
        var size = 1;
        var reqs = new ArrayList<Request>();
        // add requests with status OK
        reqs.addAll(builder.buildRequest(size));
        // add requests with status != OK
        builder.setStatus(HttpStatus.BAD_REQUEST);
        reqs.addAll(builder.buildRequest(1));
        builder.setStatus(HttpStatus.NOT_FOUND);
        reqs.addAll(builder.buildRequest(1));
        var actual = callService(reqs, from, to);
        assertEquals(size, actual.size());
    }

    @Test
    public void groupingByIpOrderedByRequestsDesc(){
        //fixed bytes for request, percentage will be the same of requests
        long bytesForRequest = 2000l;
        builder.setBytes(bytesForRequest);
        var reqs = new ArrayList<Request>();
        // add requests with ipAddress1
        var sizeIpAddress1 = 5; //33% of requests
        var ipAddress1 = faker.internet().ipV4Address();
        builder.setAddress(ipAddress1);
        reqs.addAll(builder.buildRequest(sizeIpAddress1));
        // add requests with ipAddress2
        var sizeIpAddress2 = sizeIpAddress1*2;//66% of requests
        var ipAddress2 = faker.internet().ipV4Address();
        builder.setAddress(ipAddress2);
        reqs.addAll(builder.buildRequest(sizeIpAddress2));
        var actual = callService(reqs, from, to);
        int expectedSize = 2;
        assertEquals(expectedSize, actual.size());
        // 1st result
        assertEquals(ipAddress2, actual.get(0).ipAddress());
        assertEquals(sizeIpAddress2, actual.get(0).nRequests());
        assertEquals(66, actual.get(0).percentageRequests());
        assertEquals(66, actual.get(0).percentageBytes());
        assertEquals(bytesForRequest * sizeIpAddress2, actual.get(0).totalBytes());
        // 2nd result
        assertEquals(ipAddress1, actual.get(1).ipAddress());
        assertEquals(sizeIpAddress1, actual.get(1).nRequests());
        assertEquals(33, actual.get(1).percentageRequests());
        assertEquals(33, actual.get(1).percentageBytes());
        assertEquals(bytesForRequest * sizeIpAddress1, actual.get(1).totalBytes());
    }

    @Test
    public void variableBytesForRequest(){
        //requests with ipAddress1
        var reqsIpAddress1 = new ArrayList<Request>();
        var sizeIpAddress1 = 8;//40% of requests
        var ipAddress1 = faker.internet().ipV4Address();
        builder.setAddress(ipAddress1);
        reqsIpAddress1.addAll(builder.buildRequest(sizeIpAddress1));
        long totalBytes1 = reqsIpAddress1.stream().mapToLong(Request::bytes).sum();
        // requests with ipAddress2
        var reqsIpAddress2 = new ArrayList<Request>();
        var sizeIpAddress2 = 2;// 10% of requests
        var ipAddress2 = faker.internet().ipV4Address();
        builder.setAddress(ipAddress2);
        reqsIpAddress2.addAll(builder.buildRequest(sizeIpAddress2));
        long totalBytes2 = reqsIpAddress2.stream().mapToLong(Request::bytes).sum();
        // requests with ipAddress3
        var reqsIpAddress3 = new ArrayList<Request>();
        var sizeIpAddress3 = 10;//50% of requests
        var ipAddress3 = faker.internet().ipV4Address();
        builder.setAddress(ipAddress3);
        reqsIpAddress3.addAll(builder.buildRequest(sizeIpAddress3));
        long totalBytes3 = reqsIpAddress3.stream().mapToLong(Request::bytes).sum();
        //prepare reqs
        var reqs = new ArrayList<Request>();
        reqs.addAll(reqsIpAddress1);
        reqs.addAll(reqsIpAddress2);
        reqs.addAll(reqsIpAddress3);
        //add not valid requests
        List.of(ipAddress1, ipAddress2, ipAddress3).stream().forEach(ip -> {
            int sizeNotValidRequest = 5;
            builder.setAddress(ip);
            builder.setStatus(HttpStatus.OK);
            builder.setBetween(from, to);
            // add requests before interval
            reqs.addAll(getRequestBefore(from, sizeNotValidRequest));
            // add requests after interval
            reqs.addAll(getRequestAfter(to, sizeNotValidRequest));
            // add request not ok
            builder.setStatus(HttpStatus.MOVED_PERMANENTLY);
            reqs.addAll(builder.buildRequest(sizeNotValidRequest));
        });
        //call service
        var actual = callService(reqs, from, to);
        int expectedSize = 3;
        long totalBytes = totalBytes1+totalBytes2+totalBytes3;
        assertEquals(expectedSize, actual.size());
        //1st result
        assertEquals(ipAddress3, actual.get(0).ipAddress());
        assertEquals(sizeIpAddress3, actual.get(0).nRequests());
        assertEquals(50, actual.get(0).percentageRequests());
        assertEquals(service.pecentage(totalBytes3, totalBytes), actual.get(0).percentageBytes());
        assertEquals(totalBytes3, actual.get(0).totalBytes());
        //2nd result
        assertEquals(ipAddress1, actual.get(1).ipAddress());
        assertEquals(sizeIpAddress1, actual.get(1).nRequests());
        assertEquals(40, actual.get(1).percentageRequests());
        assertEquals(service.pecentage(totalBytes1, totalBytes), actual.get(1).percentageBytes());
        assertEquals(totalBytes1, actual.get(1).totalBytes());
        // 3rd result
        assertEquals(ipAddress2, actual.get(2).ipAddress());
        assertEquals(sizeIpAddress2, actual.get(2).nRequests());
        assertEquals(10, actual.get(2).percentageRequests());
        assertEquals(service.pecentage(totalBytes2, totalBytes), actual.get(2).percentageBytes());
        assertEquals(totalBytes2, actual.get(2).totalBytes());
    }
    
    private List<IpAddrReport> callService(Collection<Request> requests, Date from, Date to){
        var actual = new ArrayList<IpAddrReport>();
        service.doReport(requests.stream(), from.getTime(), to.getTime(), actual::add);
        return actual;
    }

    private List<Request> getRequestBefore(Date date, int size){
        builder.setDate(new Date(date.getTime() - 1));
        return builder.buildRequest(size);
    }

    private List<Request> getRequestAfter(Date date, int size) {
        builder.setDate(new Date(date.getTime() + 1));
        return builder.buildRequest(size);
    }
}
