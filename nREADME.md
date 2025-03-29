
# E-Handel Microservices

This is a simple e-commerce microservices project built with Spring Boot and MySQL, deployed on AWS Elastic Beanstalk.

## Microservices

- **e-handel**: Handles product-related CRUD operations and can fetch user
- **Microservice-User**: Manages user data and can fetch products from Product Service

## Main Endpoints

### e-handel
### Product Service
- `GET /products`
- `GET BY ID/products`
- `PUT by ID/products`
- `POST /products`
- `DELETE by id/products`
- `GET /products/users-from-service`


### Microservice-User
### User Service
- `GET /users`
- `GET BY ID/users`
- `POST /users`
- `PUT /users`
- `DELETE /users`
- `GET /users/products-from-service`

## Deployment

Both services are deployed on AWS Elastic Beanstalk and connect to individual AWS RDS MySQL databases.

## Run Locally

1. Clone the repository
2. Open a terminal and navigate to the microservice you want to  run (e.g., `e-handel` or `Microservice-User`)
3. Update the `application.properties` file with your local MySQL database settings
4. Build and run using Maven Wrapper:

