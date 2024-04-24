# README #

This application uses Java 17, Springboot 3.2.4, Maven 3.9.5 and Docker.

### The objective is to create a simple wallet application that can perform the following functions. ###

* Creation of an account
* Account Login
* Transaction History
* Balance of account
* Credit of account
* Debit of account

### How to set up? ###

1. First run the following command `mvn clean package` to build the jar file
2. Start up Docker
3. In your terminal, navigate to the root directory of the project
4. Run this command to build the docker image: `docker build -t wallet-app .`
5. Run this command to run the docker compose: `docker compose -f docker-compose.yml up -d`

### How to run the Application. Happy Flow ###

* First create a user by sending a request via the `registerUser` endpoint (the postman collection is inside the resources package)
* Use the same username and password to login via the `login` endpoint
* The request above will return a token that will be required for authentication to access other secured endpoints (only `registerUser` and `login` endpoints are unsecured)
* Insert the token under Authorization -> Bearer in postman and try out other endpoints as required
* Flyway automatically inserts 2 records into the `users` and `user_accounts` tables

### Swagger ###

* Swagger url: http://localhost:8989/wallet/swagger-ui/index.html

### Author ###

* Richard Gwatidzo
* Email: gwatidzorichard@gmail.com 
