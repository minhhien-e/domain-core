package io.github.ddd.core.aggregate;

import io.github.ddd.core.event.DomainEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

public class AggregateRootTest {

    static class TestAggregate extends AggregateRoot {
        public TestAggregate() {
            super();
        }

        public void doSomething() {
            addDomainEvent(new TestEvent(this.getId()));
        }
    }

    static record TestEvent(UUID aggregateId) implements DomainEvent {
        @Override
        public UUID eventId() { return UUID.randomUUID(); }
        @Override
        public LocalDateTime occurredOn() { return LocalDateTime.now(); }
        @Override
        public String type() { return "TestEvent"; }
    }

    @Test
    void testDomainEventsAccumulation() {
        TestAggregate aggregate = new TestAggregate();
        Assertions.assertEquals(0, aggregate.getDomainEvents().size());

        aggregate.doSomething();
        Assertions.assertEquals(1, aggregate.getDomainEvents().size());
        Assertions.assertEquals("TestEvent", aggregate.getDomainEvents().get(0).type());
    }

    @Test
    void testClearDomainEvents() {
        TestAggregate aggregate = new TestAggregate();
        aggregate.doSomething();
        Assertions.assertEquals(1, aggregate.getDomainEvents().size());

        aggregate.clearDomainEvents();
        Assertions.assertEquals(0, aggregate.getDomainEvents().size());
    }

    @Test
    void testDomainEventsAreDefensiveCopy() {
        TestAggregate aggregate = new TestAggregate();
        aggregate.doSomething();
        
        // Assert that the returned list is immutable or at least modifying it doesn't affect internal state
        // List.copyOf returns an unmodifiable list in Java 10+
        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            aggregate.getDomainEvents().clear();
        });
    }
}
