package io.github.ddd.core.aggregate;

import io.github.ddd.core.entity.base.DomainEntity;
import io.github.ddd.core.event.DomainEvent;

import java.util.ArrayList;
import java.util.List;
public abstract class AggregateRoot<ID> extends DomainEntity<ID> {

    protected AggregateRoot() {
        super();
    }

    protected AggregateRoot(ID id, long version) {
        super(id, version);
    }

    // Domain Events
    private final List<DomainEvent> domainEvents = new ArrayList<>();

    public void addDomainEvent(DomainEvent event) {
        domainEvents.add(event);
    }

    public List<DomainEvent> getDomainEvents() {
        return List.copyOf(domainEvents);
    }

    public void clearDomainEvents() {
        domainEvents.clear();
    }
}
