## Implementation of the task from  [https://github.com/vas-test/test1](https://github.com/vas-test/test1).

### Requirements
* ✅ Java programming language has to be used. 
* ✅ Sourcecode has to compile and run.
* ✅ A public GIT repository has to be used so its usage can be evaluated
* ✅ The input JSON may have some errors (missing fields, wrong order, invalid value...)
* ✅ The service will have an HTTP endpoint that receives a date parameter (YYYYMMDD). This method will be requested to select the JSON file to process. 
* ✅ The service will have an HTTP endpoint (/metrics) that returns a set of counters related with the processed JSON file:
* ✅ The service will have an HTTP endpoint (/kpis) that returns a set of counters related with the service:

### Getting started

1. Ensure you have working installation of `Maven` and `Java 12 JDK`
2. Run `mvn clean package` command to build JAR file of the application.
3. Run `java -jar target/messageprocessorservice-1.0.0-SNAPSHOT.jar` command to run the server.

### Details about the implementation

**Java version:** 12<br>
**Framework used:** Spring with Spring Boot for configuration<br>
**Documentation:** Every more complex unit well commented. In addition, plenty of // nicetodo: comments suggesting potential areas of improvement in future
**Database:**
- in memory implementation for demonstration purposes (thread unsafe)
- any JPA supported database (allowing potential horizontal scalling of the microservice and extensibility as databases are much more suited for analytical jobs)

**REST endpoints provided:**
1. GET `/kpis` (returns application statistics)
2. GET `/metrics` or `/logs/active/metrics` (returns statistics for currently selected log file, 400 if no file is selected using the 3rd endpoint)
3. PUT `/logs/active` with request body being date in format `yyyyMMdd` (if the log file for the given date was not already processed,  it downloads, parses and analyzes the log file)

for testing purposes only

4. GET `/logs/active?date=yyyyMMdd` same as 3, only GET method is used and the date is in query parameter for easier testing inside a browser

*note: requirements specify that /kpis, /metrics endpoints must be provided, so I opted out of REST api version in the URI but I'd definitely consider adding `v1` if the system was to be used in production to allow easier versioning of the API in future*

#### Using database as backend instead of the default in memory implementation
1. Properly configure database credentials in `src/main/resources/application.properties`.
2. Comment out methods in `cz.artin.vodafone.logprocessorservice.config.RepositoryConfig` that override the use of default JPA repositories with in memory alternatives.
3. Include `org.flywaydb.flyway-core` into the maven dependencies of the project to enable auto migrations.
4. Uncomment `@Transactional` annotations in `cz.artin.vodafone.logprocessorservice.service.log.LogService`

#### Architecture overview

The application's main method is located in `cz.artin.vodafone.logprocessorservice.Application` class that delegates the bootstrap to Spring. The Spring framework
exposes two REST resources `ApplicationResource` and `LogResource` both located in package `cz.artin.vodafone.logprocessorservice.rest.v1`. These classes represent
the presentation tier in my N-Tier architecture system. The presentation tier communicates with the service layer located in package `cz.artin.vodafone.logprocessorservice.service`
which contains all the business logic specific to the application. All data storage storage functionality is stored in the data tier located in Repository classes in 
`cz.artin.vodafone.logprocessorservice.repository`. The N-Tier architecture rules are not stricly followed everywhere due to small size of the application and
the demonstrational nature of the program. 

The download, parsing and analysis of the log files are carried in the ` cz.artin.vodafone.logprocessorservice.service.log` package. After successful completion, the
results are saved into the data layer to allow easier manipulation of the data, allow horizontal scalling of the service and allow system to be easily extensible
in the future. 
