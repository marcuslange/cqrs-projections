package io.pileworx.cqrs.projections.common.domain;

import java.util.Optional;

public interface Repository<T extends AggregateRoot<I>, I extends Identity> {
    I nextId();
    Optional<T> findById(I id);
    void save(T ar);
    void delete(I id);
}