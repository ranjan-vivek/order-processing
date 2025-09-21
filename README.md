# order processing microservice

##  Setup & Run Instructions

##  Prerequisites

- Java 17+ installed
- Maven installed
- MYSQL

## Run the Application
git clone https://github.com/ranjan-vivek/order-processing.git
Use any IDE – it will automatically download all Maven dependencies
From terminal - ./mvnw spring-boot:run

## API endpoint examples
Once you set up and run this microservice, it will start on the default port 8080. 
You can access the Swagger UI at http://localhost:8080/swagger-ui.html to view all available API endpoints along with example requests and responses

## Assumptions or design decisions

1. A one-to-many unidirectional relationship is implemented from Order to Product, since a single order can include multiple products.

2. The Order entity is mapped to multiple Product entities using a one-to-many unidirectional association, reflecting the real-world scenario where an order can have many products.
