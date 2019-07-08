package io.pileworx.cqrs.projections.domain;

import io.pileworx.cqrs.projections.common.domain.Repository;
import io.pileworx.cqrs.projections.domain.cart.ShoppingCartId;

public interface ShoppingCartRepository extends Repository<ShoppingCart, ShoppingCartId> {}