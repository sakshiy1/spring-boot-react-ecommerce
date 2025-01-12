**E-Commerce Backend Application (Spring Boot)**
**Technologies Used:**
Spring Boot: Framework for building the backend microservices.
Eureka: Service discovery server for microservices.
Spring Cloud Gateway: Manages routing and security of incoming API requests.
Feign Client: Enables service-to-service communication via REST APIs.
MySQL: Relational database management system for data storage.
React.js: Frontend framework for building the user interface.
Axios: For making HTTP requests from the frontend.
Docker: Containerization of backend and frontend applications to ensure consistent deployment.
Spring Security: Provides basic authentication and authorization for securing API endpoints.
Custom Exception Handling: Provides better control and management of application-specific errors.

**Description:**
This is a Spring Boot-based e-commerce backend application designed using a Microservices Architecture. It includes core features such as product management, order processing, service discovery, exception handling, and security. The application integrates Spring Cloud Gateway, Eureka Naming Server, Feign Client for service communication, and Spring Security for securing APIs. Docker is used for consistent deployment.

**Key Features:**
Microservices Architecture: The backend is structured into independent services that interact with each other through REST APIs.

Product Service: Manages product details (add, update, delete, view).
Order Service: Handles order creation, processing, and status management.
Service Discovery:
Eureka Server: Used for service registration and discovery, enabling dynamic interaction between microservices.
API Gateway:
Spring Cloud Gateway: Acts as a reverse proxy to route client requests to the appropriate backend services, adding security and monitoring features.
Service Communication:
Feign Client: Simplifies service-to-service communication using declarative REST client calls.

**Frontend:**
Built with React.js, communicating with the backend via Axios for API calls.

**Database:**
MySQL is used to store product and order data.

**Containerization:**
Docker is used for creating consistent containers for both backend and frontend applications, ensuring smooth deployment across environments.

**Exception Handling:**
Customized Exception Handling: Implements custom exceptions to handle errors more effectively, such as product not found or order processing issues.
Global Exception Handler: Uses @ControllerAdvice for a global exception handler, returning meaningful error messages and appropriate HTTP statuses.

**Security:**
Spring Security: Secures the backend APIs with authentication and authorization. Ensures that only authorized users can access certain endpoints, such as admin routes for managing products and orders.

Upcoming Features:
Payment Service: The payment service will be added to handle payment processing and integration with third-party services like PayPal or Stripe.
Authentication Service: A future service will manage user login, registration, and token-based authentication.
