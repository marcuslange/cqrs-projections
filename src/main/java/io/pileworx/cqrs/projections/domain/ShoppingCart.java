package io.pileworx.cqrs.projections.domain;

import io.pileworx.cqrs.projections.common.domain.AggregateRoot;
import io.pileworx.cqrs.projections.domain.cart.ShoppingCartId;
import io.pileworx.cqrs.projections.domain.cart.ShoppingCartJackson;
import io.pileworx.cqrs.projections.domain.command.CreateCartCmd;

public interface ShoppingCart extends AggregateRoot<ShoppingCartId> {
    static ShoppingCart create(ShoppingCartId id, CreateCartCmd cmd) {
        return ShoppingCartJackson.create(id, cmd);
    }
}
