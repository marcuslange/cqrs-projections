package io.pileworx.cqrs.projections.domain.port.secondary.postgresql.strategy;

import io.pileworx.cqrs.projections.common.domain.repository.PersistenceStrategy;
import io.pileworx.cqrs.projections.domain.ShoppingCart;
import io.pileworx.cqrs.projections.domain.cart.ShoppingCartId;

public class BombPersistenceStrategy implements PersistenceStrategy<ShoppingCart, ShoppingCartId> {

    @Override
    public void save(ShoppingCart ar) {
        throw new RuntimeException("boom.");
    }

    @Override
    public void delete(ShoppingCartId id) {
        throw new RuntimeException("boom.");
    }
}