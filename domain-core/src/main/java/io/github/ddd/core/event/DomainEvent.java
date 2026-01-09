package io.github.ddd.core.event;

import java.time.LocalDateTime;
import java.util.UUID;

public interface DomainEvent {
    UUID eventId();

    LocalDateTime occurredOn();

    String type();
}
