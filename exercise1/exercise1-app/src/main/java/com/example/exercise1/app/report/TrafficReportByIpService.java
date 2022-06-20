package com.example.exercise1.app.report;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Comparator;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.example.exercise1.app.request.HttpStatus;
import com.example.exercise1.app.request.Request;
import com.example.exercise1.app.request.RequestRepository;
import com.example.exercise1.lib.log.LoggerFactory;

public class TrafficReportByIpService {
	private static final Logger log = LoggerFactory.getLogger(TrafficReportController.class);
	private final RequestRepository requestRepository;
	
    public TrafficReportByIpService(RequestRepository requestRepository) {
		super();
		this.requestRepository = requestRepository;
	}


    public long getTotalBytes(Collection<Request> requests){
        return requests.stream().mapToLong(Request::bytes).sum();
    }

    public int pecentage(int part, int total) {
        return part*100/total;
    }

    public int pecentage(long part, long total) {
        return (int)(part * 100 / total);
    }

    public void doReport(Path pathRequests, long from, long to, Consumer<IpAddrReport> consumer){
    	try(Stream<Request> request = requestRepository.getAll(pathRequests)){
    		log.log(Level.FINE, "starting report stream, from: {0}, to: {1}", new Object[] { from, to });
    		final int unknownPercentage = 0;
            var result = request
            .filter(r -> r.timestamp() >= from && r.timestamp() <= to)
            .filter(r -> HttpStatus.OK.equals(r.status()))
            .collect(Collectors.groupingBy(Request::remoteAddr))
            .entrySet().stream().map(entry -> {
                var entryRequestList = entry.getValue();
                var entryTotalBytes = getTotalBytes(entryRequestList);
                //percentage is still unknown
                return new IpAddrReport(entry.getKey(), entryRequestList.size(), unknownPercentage, entryTotalBytes,unknownPercentage);
            }).toList();
            var totalRequest = result.stream().mapToInt(IpAddrReport::nRequests).sum();
            var totalBytes = result.stream().mapToLong(IpAddrReport::totalBytes).sum();
            result.stream().map(i -> new IpAddrReport(i.ipAddress(), i.nRequests(), pecentage(i.nRequests(), totalRequest), 
                    i.totalBytes(), pecentage(i.totalBytes(), totalBytes)))
                    .sorted(Comparator.comparingInt(IpAddrReport::nRequests).reversed()).forEach(consumer);
    	}
    }

}
