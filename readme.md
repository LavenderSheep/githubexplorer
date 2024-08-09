# Github Explorer

A simple project for exploring user repositories through GitHub REST API

### Created with
* Java 21
* Spring Boot 3.3.2
* Maven 3.9.7

### Usage
Run the app with Maven's `./mvnw spring-boot:run` command or build and run the jar

The app accepts requests GET requests from the main page:
* `http://localhost:8080`

GET request format:
* `Header: “Accept: application/json"`
* `parameter: username`

The app returns the following information as a list for the provided user for each non-fork repository of that user:
* Repository Name
* Owner Login
* A list that includes Each branch name and last commit sha of the repository

In case of a non-existent user, the app returns a 404 error message in the following format:
* Error code
* Error message

### Request limits and authentication
The app by default uses unauthenticated API requests, limiting the usage to 60 requests per hour.