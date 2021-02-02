Instructions 

Overview:
This application has 3 endpoints to manage user points with payers 

Language / Technologies:
Java, Spring Boot

How to run the application:
- Download the project fetchPoints from Git
- Import as maven project in Eclipse
- Run the project from /fetchPoints/src/main/java/com/rewards/fetch/fetchPoints/FetchPointsApplication.java

Assumptions:
- If user does not have balance and deduct call is made application should throw the Low Balance Exception 
- in Deduct endpoints only positive points are allowed
- While adding point to payer if points are negative 
	1. deduct the points from existing payer points (if points available)
	2. throw Low Balance Exception (if sufficient points not available)

Endpoints:

1. http://localhost:8080/points?userName=<userName> (GET)
	takes userName as query parameter and returns all matching transactions 
	e.g. http://localhost:8080/points?userName=FetchRewards
	
2. http://localhost:8080/addPoints?userName=<userName>&payerName=<payerName>&points=<points> (POST)
	takes 3 query parameters userName, payerName and Points (can be positive or negative points) and adds points to transactions
	returns Success or Exception (Low Balance)
	e.g.
	http://localhost:8080/addPoints?userName=FetchRewards&payerName=newPayr1&points=1000
	http://localhost:8080/addPoints?userName=FetchRewards&payerName=newPayr1&points=-500
	
3. http://localhost:8080/deductPoints?userName=<userName>&points=<points>
	takes userName and points to be deducted ( can be positive points) and deduct points from users points
	returns Success or Exception (Negative Points or Low Balance)
	e.g. http://localhost:8080/deductPoints?userName=FetchRewards&points=900

	
	