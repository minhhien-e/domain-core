package io.github.ddd.core.annotation;

import java.lang.annotation.*;

/**
 * Marks a class as a Domain Service.
 * A Domain Service contains domain logic that doesn't naturally fit within a strictly object-oriented Entity or Value Object.
 * Examples: Complex calculations, transfers between accounts.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface DomainService {
}
