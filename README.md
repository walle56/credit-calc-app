## Spring Boot application for calculate credit monthly payment.

### Preconditions:
-- AdoptOpenJDK 11
-- maven

### Start project:
-- compile code with `mvn clean install`
-- run Spring Boot application with `mvn spring-boot:run`
-- send POST request to `localhost:8080/calculate` with the json body 
`{ "totalCost": "50.0",
"percentage": "2",
"userOwnPayment": "10",
"years": 10  }`
-- numbers can be in the format `50` or `50.00`    

### Frameworks/Methods used:
`StringToNumberConstraint` and `StringToNumberValidator` to validate numbers in DTO.
`CreditDataMapper` to convert DTO to entity.
`CalcControllerTest` for end-to-end integration tests, it checks 'happy' path.
