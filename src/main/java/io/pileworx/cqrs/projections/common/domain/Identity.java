package io.pileworx.cqrs.projections.common.domain;

public interface Identity<T> {
    T getValue();
}
