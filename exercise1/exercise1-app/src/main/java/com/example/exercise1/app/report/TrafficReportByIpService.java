package com.example.exercise1.app.report;

import java.util.Collection;
import java.util.Comparator;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.example.exercise1.app.request.HttpStatus;
import com.example.exercise1.app.request.Request;
import com.example.exercise1.lib.log.LoggerFactory;

public class TrafficReportByIpService {
	private static final Logger log = LoggerFactory.getLogger(TrafficReportController.class);
    public record RequestStatistic(long bytes){

    }

    public RequestStatistic getTotalBytes(Collection<Request> requests){
        long bytes = requests.stream().mapToLong(Request::bytes).sum();
        return new RequestStatistic(bytes);
    }

    public int pecentage(int part, int total) {
        return part*100/total;
    }

    public int pecentage(long part, long total) {
        return (int) (part * 100 / total);
    }

    
    public void doReport(Stream<Request> request, long from, long to, Consumer<IpAddrReport> consumer){
    	log.log(Level.FINE, "starting report stream, from: {1}, to: {2}", new Object[] { from, to });
        var result = request
        .filter(r -> r.timestamp() >= from && r.timestamp() <= to)
        .filter(r -> HttpStatus.OK.equals(r.status()))
        .collect(Collectors.groupingBy(Request::remoteAddr))
        .entrySet().stream().map(entry -> {
            var rList = entry.getValue();
            var statistic = getTotalBytes(rList);
            return new IpAddrReport(entry.getKey(), rList.size(), 0, statistic.bytes(), 0);
        }).toList();
        var totalRequest = result.stream().mapToInt(IpAddrReport::nRequests).sum();
        var totalBytes = result.stream().mapToLong(IpAddrReport::totalBytes).sum();
        result.stream().map(i -> new IpAddrReport(i.ipAddress(), i.nRequests(), pecentage(i.nRequests(), totalRequest), 
                i.totalBytes(), pecentage(i.totalBytes(), totalBytes)))
                .sorted(Comparator.comparingInt(IpAddrReport::nRequests).reversed()).forEach(consumer);
        
    }
}
