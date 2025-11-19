## Demo Movie Ticket Booking Application

This project is a microservices-based system that uses **Apache Kafka** for event processing and follows the **Saga Choreography Pattern** to maintain consistency across all services.

### 🔧 Microservices Involved

#### 1. **Booking-Service**
- Acts as the main entry point for user interactions  
- Handles booking requests and initiates the saga workflow

#### 2. **SeatInventory-Service**
- Internal service responsible for seat availability checks  
- Ensures selected seats are available before confirming the booking

#### 3. **Payment-Service**
- Dummy payment microservice  
- Uses mock wallet data to simulate fair and controlled financial transactions

### 🧵 Saga Choreography Pattern
- Ensures data consistency across all three services  
- Each service publishes events to Kafka and reacts to events from other services  
- No centralized orchestrator—each service manages its own state transitions

### 🔗 Apache Kafka Integration
- Used as the backbone for event-driven communication  
- Ensures reliable delivery and synchronization of booking, inventory, and payment events
