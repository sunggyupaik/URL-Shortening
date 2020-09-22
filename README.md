# URL-Shortening

# Functional Requirement
1. Users can retrieve a shortened url with a original url. (both urls should exist)
2. Users can retrieve a original url with a shortened url. (both urls should exist)
3. Users enter a original url. service should save that url and generate a shortened url
4. Users enter a shortened url, then service redirects to a original link
5. use REST API

# Non-Functional Requirement
1. excepts a unmeaningful input url
2. offers security
3. shortened urls will expire after a certain amount of default time.

# Contents
- User level API
- System Level API
- UseCase
- Class Diagram
- Sequence Diagram
- DB Schema
- Data Flow Diagram(DFD)

## 1. User level API

- `GET /urls/shortenedurls/{originalurl}`

/ | Http Status Code | Body
---- | ---- | ----
Request | ---- | { "originalurl" : "{originalurl}" }
Success Response | 200 | { "status" : "200", "message":"OK", "url" : "shortenedurl" }
Error Response | 404 | { "status" : "404", "message" : "NOT FOUND", "reason : "Invalid Input"}     
```
<description>
retrieve ShortenedUrl with OriginalUrl
404 : occurs when clients enters Invalid Input(not in DB)
```    

- `GET /urls/originalurls/{shortenedurl}`

/ | Http Status Code | Body
---- | ---- | ----
Request | ---- | { "shortenedurl" : "{shortenedurl}" }
Success Response | 200 | { "status" : "200" , "message" : "OK", url : "originalurl" }
Error Response | 404 | { "status" : "404", "message" : "NOT FOUND", "reason : "Invalid Input"}
   ```
<description>
retrieve OriginalUrl with ShortenedUrl
404 : occurs when clients enters Invalid Input(not in DB)
```    

- `POST /urls`

/ | Http Status Code | Body
---- | ---- | ----
Request | ---- | { "originalurl" : "{originalurl}" }
Success Response | 201 | { "status" : "201", "message" : "CREATED", "url" :  "shortenedUrl"  }
Error Response | 404 | { "status" : "404", "message" : "NOT FOUND", "reason : "Invalid Input"}
Error Response | 409 | { "status" : "409", "message" : "CONFLICT", "reason : "Duplicated Input"}

```
<description>
Create ShortenedUrl with OriginalUrl
404 : occurs when clients enters Invalid Input(not in DB)
409 : occurs when clients enters Duplicated Input
```    


- `GET /{shortenedurl}`

/ | Http Status Code | Body
---- | ---- | ----
Request | ---- | { "Host" : "{shortenedurl}" }
Success Response | 301 |  { "status" : "301", "message" : "Moved Permanently" "url" : "originalurl" }
Error Response | 404 | { "status" : "404", "message" : "NOT FOUND", "reason : "Invalid Input"}

```
<desription> 
redirect from ShortenedUrl to OriginalUrl
301 : occurs when clients enters shortenedurls. and redirect to originaurl website.
404 : occurs when clients enters Invalid Input(not in DB)
```    
    
## 2. System Level API

```
This system has 6 layers.

<Controller Layer>
checks client  API request and returns to relevant Service Layer
   
<Service Layer>
handles specific client API requests with Dto Layer, Domain Layer

<Dtos Layers>
use Dtos objects for request and response data of Urls

<Repository Layer>
roles as a Database

<Domain Model>
roles as a Database Table

<ResponseHandler Layer>
checks if there is any error, and returns ErrorResponse or SuccessResponse
```

### Controller Layer
- UrlController.java
```java
/*
* This Method is for retrieveing shortenedurl with originalurl
* @param originalurl    
* @return - ErrorResponse when url is null, else SuccessResponse
*/
public ResponseEntity<?> findByOriginalUrl(@PathVariable String originalurl);

/*
* This Method is for retrieveing originalurl with shortenedurl 
* @param shortenedurl
* @return - ErrorResponse when url is null, SuccessResponse when url is not null
*/
public ResponseEntity<?> findByShortenedUrl(@PathVariable String shortenedurl);

/*
* This Method is for creating shortenedurl using Dto class
* @param createShortenedUrlRequestDto - for Asynchronous
* @return - ErrorResponse when url is too short(length<8) and duplicated, else SuccessResponse 
*/
public ResponseEntity<?> createShortenedUrl (@RequestBody CreateShortenedUrlRequestDto createShortenedUrlRequestDto);

/*
* This Method is for making originalredirecturl from shortenedurl
* @param shortenedUrl
* @return - ErrorResponse when url is null, else SuccessResponse
*/
public ResponseEntity<?> redirectToOriginalUrl(@PathVariable String shortenedUrl);
```

### Service Layer
- UrlGetService.java
- UrlCreateService.java
- UrlRedirectService.java
```java
/*UrlGetService.java*/
/*
* This method is service method for retrieving shortenedurl in UrlsRepository 
* @param originalUrl 
* @return - Url in UrlsReposiory
*/
public GetShortenedUrlRequestDto findByOriginalUrl(String originalUrl);

/*
* This method is service method for retrieving originalurl in UrlsRepository 
* @param shortenedUrl
* @return - Url in UrlsReposiory
*/
public GetOriginalUrlRequestDto findByShortenedUrl(String shortenedUrl);

/*UrlCreateService.java*/
/*
* This method is service method for creating shortenedurl with Dto class and save in UrlsRepository 
* @param createShortenedUrlRequestDto
* @return - shortenedurl
*/
public String createShortenedUrl(CreateShortenedUrlRequestDto createShortenedUrlRequestDto);

/*UrlRedirectService.java*/
/*
* This method is service method for making redirect url with originalurl 
* @param createShortenedUrlRequestDto
* @return - url for redirect
*/
public String makeUrlForRedirect(String originalUrl);
```

### Dto Layer
- GetShortenedUrlRequestDto.java
- GetOriginalUrlRequestDto.java
- CreateShortenedUrlRequestDto.java
- RedirectToOriginalUrlRequestDto.java
```java
/*GetShortenedUrlRequestDto.java
* This class is for retrieving shortenedurl and responding Instead of Url constructor
* @param entity - entity object from UrlsRepository
* @return -  constructor
*/ 
public GetShortenedUrlRequestDto(Urls entity);

/*GetOriginalUrlRequestDto.java
* This class is for retrieving original and responding Instead of Url constructor
* @param entity - entity object from UrlsRepository
* @return -  constructor
*/
public GetOriginalUrlRequestDto(Urls entity);

/*CreateShortenedUrlRequestDto.java
* This class is for retrieving original Instead of Urls constructor
*/

/*
* This method is for creating Urls DB when toEntity is called Instead of Urls constructor
* @param originalUrl
* @return -  constructor
*/
public createShortenedUrlRequestDto(String originalUrl);

/*
* This method is for creating Urls DB with shortenedurl
* @param shortenedUrl
* @return -  constructor
*/
public Urls toEntity(String shortenedUrl);

/*
* This method is for creating url for redirect with originalurl using base62convert technic
* @param originaUrl
* @return -  url for redirect
*/
public String base62Convert(String originalUrl);
```

### Repository Layer
- UrlsRepository.java
```java
@Query("select urls from Urls urls where shortenedUrl = :shortenedUrl");
Urls findByShortenedUrl(@Param("shortenedUrl") String shortenedUrl);

@Query("select urls from Urls urls where originalUrl =:originalUrl");
Urls findByOriginalUrl(@Param("originalUrl") String originalUrl);
```

### Domain Model
- Urls.java
```java
public class Urls {
    private Long id;
    private String originalUrl;
    private String shortenedUrl;
    public Urls(String originalUrl, String shortenedUrl);
}
```

### ResponseHandler Layer
- ErrorResponse.java
- SuccessResponse.java
- GlobalExceptionHandler.java
```java
/*ErrorResponse.java
* This class is for ErrorResponse
* @param status -404, 409
* @param message - NOT FOUND, CONFLICT
* @param reason - Invalid Input, Duplicated
*/ 
public ErrorResponse(int status, String message, String reason);

/*SuccessResponse.java
* This class is for SucessResponse
* @param status - 200, 301
* @param message - OK, MOVED PERMANENTLY
* @param url - shortenedurl, originalurl 
*/
public SuccessResponse(int status, String message, String url);

/*GlobalExceptionHandler.java*/

/*
* This is method for Invalid Input Error Exception
* @param InvalidInputException - when url is null or length<8
* @return - 404 , NOT FOUND, Invalid Input ResponseError 
*/
protected ResponseEntity<ErrorResponse> handleInvalidInputException(InvalidInputException e);

/*
* This is method for Invalid Input Error Exception
* @param DuplicatedInputException - url is duplicated
* @return - 409 , CONFLICT, Duplicated ResponseError 
*/
protected ResponseEntity<ErrorResponse> handleDuplicatedInputException(DuplicatedInputException e);
```

## 3. System Architecture
![System Architecture](https://user-images.githubusercontent.com/45138206/84617326-16eef600-aebe-11ea-988e-8cd3b2c9a73f.png)

- `Load Balancer` distributes traffic to serveral servers. 
- splits one server to service components for preventing one specific overhead 
- makes `Cache` for fast origianlUrl or shortenedUrl retriving 

## 4. UseCase
![usecase](https://user-images.githubusercontent.com/45138206/84617793-992bea00-aebf-11ea-9886-07f68cedc601.png)

## 5. Class Diagram
![ClassDiagram](https://user-images.githubusercontent.com/45138206/84617555-dcd22400-aebe-11ea-91cd-b1aa377fac27.png)

## Sequence Diagram

### 1. CreateShortenedUrl

![CreateShortenedUrl](https://user-images.githubusercontent.com/45138206/84617666-46523280-aebf-11ea-94de-3c40e63da754.png)

### 2. GetOriginalUrl

![GetOriginalUrl](https://user-images.githubusercontent.com/45138206/84618512-f2951880-aec1-11ea-80b8-e24859d20800.png)

### 3. GetShortenedUrl

![GetShortenedUrl](https://user-images.githubusercontent.com/45138206/84618507-ec9f3780-aec1-11ea-9777-c11be527a9c2.png)

### 4. RedirectToOriginalUrl

![RedirectToOriginalUrl](https://user-images.githubusercontent.com/45138206/84617732-671a8800-aebf-11ea-9233-cdde51cd36b9.png)

### 6. DB Schema
![DB_Schema](https://user-images.githubusercontent.com/45138206/84595367-79ca9800-ae92-11ea-8076-f08e06e4bac5.png)

### 7. Data Flow Diagram(DFD)

- find originalUrl

![DFD(findoriginalUrl)](https://user-images.githubusercontent.com/45138206/84595398-9e267480-ae92-11ea-87ec-d9de5fcf0774.png)

- find shortenedUrl

![DFD(findshortenedUrl)](https://user-images.githubusercontent.com/45138206/84595388-96ff6680-ae92-11ea-913d-27f350848d7a.png)

- create shortenedUrl

![DFD(createshortenedUrl)](https://user-images.githubusercontent.com/45138206/84595417-b0081780-ae92-11ea-9071-06255fd7f914.png)

- redirect to OriginalUrl

![DFD(RedirectToOriginalUrl)](https://user-images.githubusercontent.com/45138206/84595405-a54d8280-ae92-11ea-951c-8847eda85ea4.png)
