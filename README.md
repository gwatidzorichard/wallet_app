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

1. Start up Docker
2. Run the Docker Compose file inside the docker package (This will start up the PostgreSQL database image)
3. Run the application from an IDE of your choice e.g IntelliJ

### How to run the Application. Happy Flow ###

* First create a user by sending a request via the `registerUser` endpoint (the postman collection is inside the resources package)
* Use the same username and password to login via the `login` endpoint
* The request above will return a token that will be required for authentication to access other secured endpoints (only registerUser and login endpoints are unsecured)
* Insert the token under Authorization - Bearer in postman and try out other endpoints as required
* Flyway automatically inserts 2 records into the (users and user_accounts tables)


### Swagger ###

* Swagger url: http://localhost:8989/wallet/swagger-ui/index.html

### Author ###

* Richard Gwatidzo
* Email: gwatidzorichard@gmail.com 
