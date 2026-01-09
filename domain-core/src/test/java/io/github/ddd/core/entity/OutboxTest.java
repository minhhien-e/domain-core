package io.github.ddd.core.entity;

import io.github.ddd.core.event.DomainEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

public class OutboxTest {

    static record TestPayload(String message) implements DomainEvent {
        @Override
        public UUID eventId() { return UUID.randomUUID(); }
        @Override
        public LocalDateTime occurredOn() { return LocalDateTime.now(); }
        @Override
        public String type() { return "TestPayload"; }
    }

    @Test
    void testOutboxCreation() {
        UUID aggId = UUID.randomUUID();
        TestPayload payload = new TestPayload("Hello World");
        
        Outbox<TestPayload> outbox = new Outbox<>(
            "TestAggregate",
            aggId,
            payload.type(),
            payload
        );

        Assertions.assertNotNull(outbox.getId());
        Assertions.assertEquals("TestAggregate", outbox.getAggregateType());
        Assertions.assertEquals(aggId, outbox.getAggregateId());
        Assertions.assertEquals("TestPayload", outbox.getType());
        Assertions.assertEquals("Hello World", outbox.getPayload().message());
    }
}
