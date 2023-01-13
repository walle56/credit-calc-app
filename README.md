## Application to calculate credit monthly payment. 
`It is still under development`
                 
### Description:
This project shows the basics of the Layered Architecture.    
It has examples of how to:    
-- write the Spring Boot application and run it  
-- use Logback for logging activities  
-- convert DTO to Entity and vice versa,    
-- validate the DTO on the controller level using Spring    
-- calculate money    
-- write integration tests using SpringBootTest    
-- write unit tests with Mockito    
-- use frameworks like Lombok, Mapstruct, H2 embedded DB  

### Preconditions:
-- AdoptOpenJDK 11  
-- Maven 3.6.x 

### Run the project:
-- compile the code with `mvn clean install`  
-- run Spring Boot application with `mvn spring-boot:run`  
-- send PUT request to `localhost:8081/calculate` with the json body   
`{ "totalCost": "500.0",
"percentage": "5.78",
"userOwnPayment": "100.20",
"years": 10  }`  
-- numbers can be in the format `50` or `50.00`  
-- send GET request to `localhost:8081/calculate/list` to get the list of all previous calculations 
