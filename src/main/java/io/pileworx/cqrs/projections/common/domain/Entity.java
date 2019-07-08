package io.pileworx.cqrs.projections.common.domain;

public interface Entity<T extends Identity> {
    T getId();
}