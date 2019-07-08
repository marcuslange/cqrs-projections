package io.pileworx.cqrs.projections.common.domain;

import java.util.Optional;

public interface Repository<T extends AggregateRoot<A>, A extends Identity> {
    A nextId();
    Optional<T> findById(A id);
    void save(T ar);
}
