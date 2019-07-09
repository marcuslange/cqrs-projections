package io.pileworx.cqrs.projections.application.finder;

import io.pileworx.cqrs.projections.application.dto.ShoppingCartDto;
import io.pileworx.cqrs.projections.domain.cart.ShoppingCartId;

import java.util.List;
import java.util.Optional;

public interface ShoppingCartFinder {
    List<ShoppingCartDto> findAll();
    Optional<ShoppingCartDto> findById(ShoppingCartId id);
    List<ShoppingCartDto> findWithProductName(String productName);
}