# Domain Core Library

A lightweight, purely Java-based Domain-Driven Design (DDD) foundation library for building scalable microservices.

![Java 17](https://img.shields.io/badge/Java-17-green)
![DDD](https://img.shields.io/badge/Pattern-DDD-blue)

## Architecture

This library is designed as a **Shared Kernel** to enforce Clean Architecture principles across your services. It contains **no dependencies** on frameworks (Spring, Hibernate, Jackson) or databases, ensuring your Domain Layer remains pure.

### Key Features
- **Generic Identity**: Support for any ID type (`UUID`, `Long`, `String`, `TSID`...).
- **Rich AggregateRoot**: Built-in support for Event Sourcing (`domainEvents`) and Business Rule Validation (`checkRule`).
- **Standardized Repository**: A pragmatic interface combining `findById` (Optional), `getById` (Strict), and `exists`.
- **Specification Pattern**: Dynamic query criteria logic living inside the Domain.
- **Auditing**: Built-in `createdAt`, `createdBy`... fields in `DomainEntity`.
- **Error Handling**: A base `DomainException` system with Error Codes.

---

## Installation

### 1. Add JitPack Repository
Add the JitPack repository to your root `build.gradle` (or `settings.gradle` in newer Gradle versions):

```gradle
repositories {
    mavenCentral()
    maven { url 'https://jitpack.io' }
}
```

### 2. Add Dependency
Add the dependency to your service's `build.gradle`. Replace `TAG` with the specific release tag (e.g., `v1.0.0`) or `main-SNAPSHOT` for the latest dev version.

```gradle
dependencies {
    implementation 'com.github.minhhien-e:domain-core:TAG'
}
```

---

## Usage Guide

### 1. Define an Aggregate Root
Extend `AggregateRoot<ID>`. Use `checkRule` for validation.

```java
import io.github.ddd.core.aggregate.AggregateRoot;

public class Order extends AggregateRoot<UUID> {
    private BigDecimal totalAmount;
    private OrderStatus status;

    // Constructor for creation
    public static Order create(List<OrderItem> items) {
        Order order = new Order();
        order.id = UUID.randomUUID();
        order.status = OrderStatus.PENDING;
        order.calculateTotal(items);
        
        // Business Rule Check
        order.checkRule(order.totalAmount.compareTo(BigDecimal.ZERO) > 0, "ORDER_TOTAL_MUST_BE_POSITIVE", "Total must be positive");
        
        // Domain Event
        order.addDomainEvent(new OrderCreatedEvent(order.id));
        return order;
    }
}
```

### 2. Define a Repository
Extend the generic `Repository` interface. This interface stays in your **Domain Layer**.

```java
import io.github.ddd.core.repository.Repository;

public interface OrderRepository extends Repository<Order, UUID> {
    // You get save, findById, getById, existsById, findAll(Spec) for free!
    
    // Add custom domain-specific queries
    List<Order> findByStatus(OrderStatus status);
}
```

### 3. Use Specification Pattern
Avoid leaking query logic to Infrastructure. Define it in Domain.

```java
import io.github.ddd.core.specification.Specification;

public class HighValueOrderSpecification implements Specification<Order> {
    @Override
    public boolean isSatisfiedBy(Order order) {
        return order.getTotalAmount().compareTo(new BigDecimal("1000")) > 0;
    }
}

// In Application Service:
List<Order> vips = orderRepo.findAll(new HighValueOrderSpecification());
```

### 4. Implementing Infrastructure (MongoDB Example)
In your Infrastructure Layer (e.g., using Spring Data MongoDB):

```java
@Repository
@RequiredArgsConstructor
public class OrderMongoRepositoryAdapter implements OrderRepository {
    private final SpringDataOrderRepo springRepo;
    private final OrderMapper mapper;

    @Override
    public void save(Order order) {
        OrderEntity entity = mapper.toEntity(order);
        springRepo.save(entity);
    }

    @Override
    public Order getById(UUID id) {
        return springRepo.findById(id)
                .map(mapper::toDomain)
                .orElseThrow(() -> new EntityNotFoundException("Order", id)); // Core Exception
    }
    
    // ... implement other methods
}
```

## Exception Handling
The library provides a hierarchy of generic exceptions:
- `BusinessRuleViolationException`: Logic errors (400/422).
- `EntityNotFoundException`: Data missing errors (404).

Resulting in consistent error responses across all microservices.
