package io.github.ddd.core.specification;

public interface Specification<T> {
    /**
     * Check if t satisfies the specification.
     * Used for in-memory validation.
     */
    boolean isSatisfiedBy(T t);

    /**
     * Combine with another specification (AND).
     */
    default Specification<T> and(Specification<T> other) {
        return t -> this.isSatisfiedBy(t) && other.isSatisfiedBy(t);
    }

    /**
     * Combine with another specification (OR).
     */
    default Specification<T> or(Specification<T> other) {
        return t -> this.isSatisfiedBy(t) || other.isSatisfiedBy(t);
    }

    /**
     * Negate the specification (NOT).
     */
    default Specification<T> not() {
        return t -> !this.isSatisfiedBy(t);
    }
}
