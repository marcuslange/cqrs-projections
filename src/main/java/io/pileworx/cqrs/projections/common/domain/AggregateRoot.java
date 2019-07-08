package io.pileworx.cqrs.projections.common.domain;

public interface AggregateRoot<T extends Identity> extends Entity<T> {}
