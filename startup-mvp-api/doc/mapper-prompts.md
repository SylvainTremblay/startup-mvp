
# Prompt for Sabre Service Implementation


## Task Description
I want you to generate a DAO for each of Sabre's queue services:
- QueueAccess
- QueueCount
- QueuePlace

The implementation should respect the pattern established for the Ping service. Create one service that groups all DAOs.

## Implementation Requirements
1. Create 1 DAO per Sabre service
2. Create one mapper for each RQ (request) and one mapper for each RS (response) model
3. Create model objects for responses under the model.queue package

## Coding Standards
1. **Never use inner model classes** - Create separate model classes for each entity
2. **Use Apache Commons Collections and Commons Lang for null checks** - Utilize CollectionUtils and StringUtils
3. **Never use reflection** - Avoid using getClass().getMethod().invoke() patterns
4. **Use appropriate data types** - If a field or attribute is named "date", "time", or "datetime", use LocalDate, LocalTime, or LocalDateTime type in the model
5. **Avoid try-catch in mappers and constructRequest methods** - Handle exceptions at a higher level
6. **Use dedicated construction methods for list items** - When mapping lists, use a separate constructXXX() method to build each object
7. **Don't check for null or blank values before setting builder values** - You don't need to check if a field is blank or null before setting the value in the builder. For example, instead of:
   ```java
   if (StringUtils.isNotBlank(queueInfo.getPseudoCityCode())) {
       builder.pseudoCityCode(queueInfo.getPseudoCityCode());
   }
   ```
   Simply use:
   ```java
   builder.pseudoCityCode(queueInfo.getPseudoCityCode());
   ```
8. **Use @Builder.Default in model objects to initialize collections** - Initialize collections in model objects using @Builder.Default annotation:
   ```java
   @Builder.Default
   private List<QueueInfo> queueInfos = new ArrayList<>();
   ```
9. **Use var for local variables in methods** - When defining a local variable in a method, use `var` instead of the explicit type:
   ```java
   // Use this:
   var builder = QueueIdentifier.builder();
   
   // Instead of:
   QueueIdentifier.QueueIdentifierBuilder builder = QueueIdentifier.builder();
   ```
10. ** Use DateUtils.parseDateTime() for date parsing** - This method already handles null value or exception in parsing , so we don't need to add these checks. Use the DateUtils class to parse date and time strings:
    ```java
    DateUtils.toLocalDateTime(line.getDateTime(), DateUtils.MM_DD_HH_MM_FORMATTER);
    ```
## Important Sabre WSDL Information
The Sabre classes are defined in the following dependency:
```
com.westjet.sabre.wsdl:sabre-wsdl:3.0.0.20241008.1
```

### Sabre Class Information
For the queue services, use these specific Sabre classes:

1. **QueueAccess Service**:
   - Request: `com.sabre.webservices.sabrexml._2011._10.QueueAccessRQ`
   - Response: `com.sabre.webservices.sabrexml._2011._10.QueueAccessRS`
   - Service Action Code: "QueueAccessLLSRQ"
   - Version: "2.1.1"

2. **QueueCount Service**:
   - Request: `com.sabre.webservices.sabrexml._2011._10.QueueCountRQ`
   - Response: `com.sabre.webservices.sabrexml._2011._10.QueueCountRS`
   - Service Action Code: "QueueCountLLSRQ"
   - Version: "2.2.1"

3. **QueuePlace Service**:
   - Request: `com.sabre.webservices.sabrexml._2011._10.QueuePlaceRQ`
   - Response: `com.sabre.webservices.sabrexml._2011._10.QueuePlaceRS`
   - Service Action Code: "QueuePlaceLLSRQ"
   - Version: "2.0.4"

### SabreServiceEnum Updates
Update the SabreServiceEnum to use the actual Sabre classes from the information found in the XML

### Mapper Implementation
For each mapper, implement the map method to extract data from the actual Sabre response objects.
The XML design files in the /doc directory provide the structure of these objects.

Use Apache Commons Collections and Lang for collection null checks:
```java
if (CollectionUtils.isNotEmpty(queueAccessRS.getLine())) {
    // process lines
}
```

Don't check for null values in the response objects, instead use a filter in the dao to handle null values and log them.

Example for QueueAccessMapper:

```java
import java.time.LocalDateTime;

public SabreQueueAccess map(QueueAccessRS queueAccessRS) {
   var builder = SabreQueueAccess.builder();

   // Extract lines from the response using a dedicated method
   if (CollectionUtils.isNotEmpty(queueAccessRS.getLine())) {
      var lines = queueAccessRS.getLine().stream()
              .map(this::constructQueueLine)
              .collect(Collectors.toList());
      builder.lines(lines);
   }

   // Extract paragraphs from the response
   if (queueAccessRS.getParagraph() != null &&
           CollectionUtils.isNotEmpty(queueAccessRS.getParagraph().getText())) {
      var paragraphs = queueAccessRS.getParagraph().getText();
      builder.paragraphs(paragraphs);
   }

   return builder.build();
}

private QueueLine constructQueueLine(QueueAccessRS.Line line) {
   var builder = QueueLine.builder();

   // Set values directly without checking for null or blank
   builder.dateTime(LocalDateTime.parse(line.getDateTime(), "yyyy-MM-dd'T'HH:mm:ss"));
   builder.number(line.getNumber());
   // Set other fields...

   return builder.build();
}
```

In the RS XML files you don't to map the node `<ApplicationResults>` to the model object.
We don't need this information in the model object.

### DAO Implementation
For each DAO, update the SabreProxy generic types to use the specific Sabre request and response classes:

Also, add a @Span annotation to the public method so the dao can be traced in the APM.
The method parameters should be annotated with a @SpanAttribute annotation.
Don't use @SpanAttribute on these parameter type.
- Session
- SabrePointOfSale

```java
@WithSpan(value = "IgnoreTransactionLLSRQ")
public Mono<SabreQueueAccess> access(Session session,
                                     final SabrePointOfSale pointOfSale,
                                     @SpanAttribute("queue") final String queue,
                                     @SpanAttribute("action") final QueueAction action) {
   return sabreProxy.sendRequest(session, request, pointOfSale)
           .map(queueAccessMapper::map);
}
```

```java
private final SabreProxy<QueueAccessRQ, QueueAccessRS, Session> sabreProxy;
```

Implement the constructRequest method to create proper Sabre request objects based on the XML design files.
Avoid using reflection and try-catch blocks in these methods.

If in the RQ or RS xml files you see a comment mentioning 'can have a value of' please create a Enum for it.
The enum should have a code value.
```xml 
<!--"Action" can have a value of: "I", "IR", "E", "EL", "ER","QR", "QXI", "QXIR", "QXE", "QXR", "QXER".-->
```

Example enum:
```java
public enum QueueAction {
   I("I"),
   IR("IR"),
   E("E"),
   EL("EL"),
   ER("ER"),
   QR("QR"),
   QXI("QXI"),
   QXIR("QXIR"),
   QXE("QXE"),
   QXR("QXR"),
   QXER("QXER");

   private final String code;

   QueueAction(String code) {
      this.code = code;
   }

   public String getCode() {
      return code;
   }
}
```

Also include a method to inspect the response for warnings and errors.
you can re-use the existing implementation from the IgnoreTransactionDao class.
```java
private void inspectResponseForWarningsAndErrors(SabreResponse response) {
   if (response.getApplicationResults() != null) {
      // Log warnings
      ProblemInformationUtil.logWarnings(response.getApplicationResults().getWarning());

      // Check for errors
      if (CollectionUtils.isNotEmpty(response.getApplicationResults().getError())) {
         var errorMessages = ProblemInformationUtil.constructErrorMessages(response.getApplicationResults().getError());
         ProblemInformationUtil.inspectResponseForError(QUEUE_ACCESS, errorMessages);
      }
   }
}
```

### Services Implementation
Create a QueueService class that groups all queue-related DAOs.
This class should provide methods to access each of the DAOs and handle any common logic.

Also, each service method should include a retry mechanism for handling transient errors.
this can be done using the `retryWhen` operator from Project Reactor.
log the retry attempts and the total number of retries.

RETRY_MAX_ATTEMPTS should be set to 2 and RETRY_DELAY_MILLIS should be set to 400.

```java 
public Mono<Boolean> updateAAA(Session session, String pnr, SabrePointOfSale pointOfSale) {
   // We have to call Ignore first, or AAA will fail when reservation is already loaded into the session.
   return ignoreTransactionDao.ignore(session)
           // If we catch any EndTrx or PnrHasBeenUpdated exceptions, we will retry the IgnoreTransactionRQ
           .retryWhen(Retry.backoff(RETRY_MAX_ATTEMPTS, Duration.ofMillis(RETRY_DELAY_MILLIS))
                   .filter(ex -> ex instanceof SabreEndTrxException ||
                           ex instanceof PnrHasBeenUpdatedException)
                   .doBeforeRetry(retrySignal ->
                           log.info("pnr={} - Retrying IgnoreTransactionLLSRQ #{}", pnr, retrySignal.totalRetries())));
}
```

## Reference Implementation
Use the existing PingSabreDao, SabrePingMapper, and ContextChangeService as reference for your implementation.

## Expected Deliverables
1. Updated SabreServiceEnum with proper Sabre classes
2. One DAO class for each Sabre service
3. One mapper class for each Sabre service
4. Separate model classes (not inner classes) for each entity
5. A QueueService class that groups all queue-related DAOs
6. Create a branch to implement the changes
7. Create a CHANGELOG.md file in the root directory to document the changes made
8. Create a commit message that follows the conventional commit format