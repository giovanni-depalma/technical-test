package com.example.exercise1.app.report;

public record IpAddrReport(String ipAddress, int nRequests, int percentageRequests, long totalBytes, int percentageBytes) {
    
}
