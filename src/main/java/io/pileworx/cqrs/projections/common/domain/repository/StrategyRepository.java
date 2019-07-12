package io.pileworx.cqrs.projections.common.domain.repository;

import io.pileworx.cqrs.projections.common.domain.AggregateRoot;
import io.pileworx.cqrs.projections.common.domain.Identity;
import io.pileworx.cqrs.projections.common.domain.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public abstract class StrategyRepository<T extends AggregateRoot<I>, I extends Identity> implements Repository<T,I> {

    private final List<PersistenceStrategy<T,I>> strategies;

    protected StrategyRepository(List<PersistenceStrategy<T, I>> strategies) {
        this.strategies = strategies;
    }

    @Override
    @Transactional
    public void save(T ar) {
        strategies.forEach(s -> s.save(ar));
    }

    @Override
    @Transactional
    public void delete(I id) {
        strategies.forEach(s -> s.delete(id));
    }
}