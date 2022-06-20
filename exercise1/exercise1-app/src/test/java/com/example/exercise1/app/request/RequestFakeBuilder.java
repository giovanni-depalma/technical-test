package com.example.exercise1.app.request;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.IntStream;

import com.example.exercise1.app.test.util.faker.Faker;

public class RequestFakeBuilder {
    private static final int DEFAULT_MAX_DIFFERENT_ADDRESS = 10;
    private static final int DEFAULT_MAX_BYTES_FOR_REQUEST = 100000;

	private final Faker faker = new Faker();

    private Optional<String> ipV4Address;
    private Optional<HttpStatus> status;
    private Optional<Long> bytes;
    private Date from;
    private Date to;
    
    private Set<String> setAddress;
    private WeightedRandomBag<String> addresses;
    private int maxDifferentAddress;
    
    public RequestFakeBuilder(Date from, Date to) {
        this(from, to, DEFAULT_MAX_DIFFERENT_ADDRESS);
    }
    
    public RequestFakeBuilder(Date from, Date to, int maxDifferentAddress) {
        this.ipV4Address = Optional.empty();
        this.status = Optional.empty();
        this.bytes = Optional.empty();
        this.from = from;
        this.to = to;
        this.setAddress = new HashSet<String>();
        this.maxDifferentAddress = maxDifferentAddress;
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
    
    private String reuseIpV4Address() {
    	if(addresses == null) {
    		addresses = new WeightedRandomBag<String>();
    		for(String item: setAddress) {
    			addresses.addEntry(item);
    		}
    	}
    	return addresses.getRandom();
    	
    }
    
    private String getIpV4Address() {
    	if(setAddress.size()>=maxDifferentAddress) {
    		return reuseIpV4Address();
    	}
    	else {
    		String address = faker.internet().ipV4Address();
    		setAddress.add(address);
    		return address;
    	}
    }

    public List<Request> buildRequest(int nRequest) {
        return IntStream.range(0, nRequest).mapToObj(i -> {
            var itemTimestamp = faker.date().between(from, to).getTime();
            var itemBytes = bytes.orElseGet(() -> Math.abs(faker.random().nextLong(DEFAULT_MAX_BYTES_FOR_REQUEST)));
            var itemStatus = status.orElseGet(() -> HttpStatus.OK);
            var itemIpV4Address = ipV4Address.orElseGet(() -> getIpV4Address());
            return new Request(
                    itemTimestamp,
                    itemBytes, itemStatus, itemIpV4Address);
        }).toList();

    }

    

    public static class WeightedRandomBag<T> {

        private class Entry {
            double accumulatedWeight;
            T object;
        }

        private List<Entry> entries = new ArrayList<>();
        private double accumulatedWeight;
        private Random rand = new Random();

        public void addEntry(T object) {
            addEntry(object, rand.nextDouble()*100);
        }
        
        public void addEntry(T object, double weight) {
            accumulatedWeight += weight;
            Entry e = new Entry();
            e.object = object;
            e.accumulatedWeight = accumulatedWeight;
            entries.add(e);
        }

        public T getRandom() {
            double r = rand.nextDouble() * accumulatedWeight;

            for (Entry entry: entries) {
                if (entry.accumulatedWeight >= r) {
                    return entry.object;
                }
            }
            return null; //should only happen when there are no entries
        }
    }
}
