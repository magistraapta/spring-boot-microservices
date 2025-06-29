# Payment Service

This service handles payment processing for orders in the microservice architecture.

## Features

- **Order Queue Consumer**: Automatically processes orders from the `order-queue`
- **Payment Creation**: Creates payments based on order information
- **Product Integration**: Fetches product prices from the product service
- **Order Status Updates**: Updates order payment status after successful payment

## Architecture

### Order Queue Processing

The payment service listens to the `order-queue` for new orders. When an order is received:

1. **Order Reception**: The `OrderConsumer` receives the order from RabbitMQ
2. **Product Lookup**: Fetches product information from the product service to get the price
3. **Amount Calculation**: Calculates total amount (price × quantity)
4. **Payment Creation**: Creates a payment record with the calculated amount
5. **Order Update**: Updates the order's payment status to `PAID`

### Components

#### OrderConsumer
- **Location**: `src/main/java/com/payment/payment/consumer/OrderConsumer.java`
- **Purpose**: Listens to the order queue and processes incoming orders
- **Features**:
  - Automatic payment creation
  - Product price fetching
  - Fallback to default price if product service is unavailable
  - Error handling and logging

#### PaymentService
- **Location**: `src/main/java/com/payment/payment/service/PaymentService.java`
- **Purpose**: Business logic for payment operations
- **Methods**:
  - `createPayment()`: Manual payment creation
  - `createPaymentFromOrder()`: Payment creation from order data
  - `getPaymentById()`: Retrieve payment by ID

#### ProductClient
- **Location**: `src/main/java/com/payment/payment/client/ProductClient.java`
- **Purpose**: Feign client for communicating with the product service
- **Endpoint**: `GET /api/products/{id}`

## Configuration

### Application Properties

```properties
# Service URLs
product-service.url=http://localhost:8084
order-service.url=http://localhost:8082

# RabbitMQ Configuration
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest
```

### Queue Configuration

The service uses the `order-queue` for receiving orders:

```java
public static final String QUEUE = "order-queue";
```

## Flow Diagram

```
Order Service → RabbitMQ → Payment Service
     ↓              ↓           ↓
Create Order → Send to Queue → Process Order
                              ↓
                         Fetch Product Price
                              ↓
                         Calculate Amount
                              ↓
                         Create Payment
                              ↓
                         Update Order Status
```

## Error Handling

- **Product Service Unavailable**: Falls back to default price ($10.00 per unit)
- **Invalid Order**: Logs error and continues processing
- **Payment Creation Failure**: Logs error for investigation

## Testing

Run the tests to verify the consumer functionality:

```bash
mvn test
```

The test suite includes:
- Successful order processing
- Product service failure scenarios
- Fallback price calculation

## Dependencies

- Spring Boot 3.5.0
- Spring Cloud OpenFeign
- Spring AMQP (RabbitMQ)
- PostgreSQL
- Flyway (Database migrations)
- Lombok 