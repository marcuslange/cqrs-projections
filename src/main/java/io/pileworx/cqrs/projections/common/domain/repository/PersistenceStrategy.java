package io.pileworx.cqrs.projections.common.domain.repository;

import io.pileworx.cqrs.projections.common.domain.AggregateRoot;
import io.pileworx.cqrs.projections.common.domain.Identity;

public interface PersistenceStrategy<T extends AggregateRoot<I>, I extends Identity> {
    void save(T ar);
    void delete(I id);
}
