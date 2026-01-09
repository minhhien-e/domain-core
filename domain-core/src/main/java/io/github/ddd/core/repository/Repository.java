package io.github.ddd.core.repository;

import io.github.ddd.core.aggregate.AggregateRoot;
import io.github.ddd.core.specification.Specification;

import java.util.List;
import java.util.Optional;

public interface Repository<T extends AggregateRoot<ID>, ID> {
    void save(T aggregate);

    Optional<T> findById(ID id);

    /**
     * Retrieves an entity by its id.
     * Should throw an exception if not found.
     */
    T getById(ID id);

    /**
     * Find all aggregates matching the specification.
     */
    List<T> findAll(Specification<T> spec);

    /**
     * Check if an aggregate exists by id.
     * Use this for performance optimization instead of findById.
     */
    boolean existsById(ID id);

    /**
     * Check if any aggregate matches the specification.
     */
    boolean exists(Specification<T> spec);

    void delete(T aggregate);

    void deleteById(ID id);
}
