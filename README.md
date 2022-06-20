# technical-test

The project contains following folders:

- exercise1

## Exercise1

Requirement - Don’t use any frameworks but only the Java standard edition or at most, use light libraries;

### Get Started Immediately
In order to start the project  follow these simple instructions:

- Go inside exercise1 folder
- Run 'docker-compose build' from project folder
- Run 'docker-compose up ' from project folder

Docker-compose is configured to use

- exercise1-app/logfiles/request.log as input data
- exercise1-app/reports as folder for data

It's possible choose the format via *OUTPUT_MODE* environment variables.

### Quick Overview
The project uses the following stack:

- JDK 17
- Maven
- JUnit 5

There are three modules

- exercise1-test-util
- exercise1-lib
- exercise1-app

#### exercise1-test-util

Just simple utilities for testing (to avoid libraries)

#### exercise1-lib

- Basic implementations for json/csv
- Utility for java.util.logging

#### exercise1-app

Exercise1 is the "Main Class". 
It's start a thread which check with fixed delay for a requests.log and generate a report (if not already generated).

Via the file "application.properties" is possible to configure:

```
main.basePath=${BASE_PATH}
main.report.dailyTrafficByIp.reportPath=reports/ipaddr
main.report.dailyTrafficByIp.reportPathTmp=reports/ipaddr.tmp
main.report.dailyTrafficByIp.requestPath=logfiles/requests.log
main.report.dailyTrafficByIp.outputMode=${OUTPUT_MODE}
main.report.dailyTrafficByIp.scheduledInSeconds=10
```

- main.basePath directory which contains report and logfiles to process. By default is empty and matches the working dir. Via envorinoment variables it's possible to change its value.
- main.report.dailyTrafficByIp.reportPath path of report (without extension)
- main.report.dailyTrafficByIp.reportPathTmp path of temporary file during processing. If everything proceeds it is deleted, otherwise it remains for debugging
- main.report.dailyTrafficByIp.requestPath path of requests to be processed
- main.report.dailyTrafficByIp.outputMode possible values are JSON/CSV. If empty by default it will be CSV
- main.report.dailyTrafficByIp.scheduledInSeconds fixed delay for checking of requests to be processed