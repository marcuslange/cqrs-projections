package io.pileworx.cqrs.projections.domain.port.secondary.inmemory;

import io.pileworx.cqrs.projections.domain.ShoppingCart;
import io.pileworx.cqrs.projections.domain.ShoppingCartRepository;
import io.pileworx.cqrs.projections.domain.cart.ShoppingCartId;

import javax.inject.Named;
import java.util.*;

@Named
public class ShoppingCartRepositoryInMemory implements ShoppingCartRepository {

    private final Map<String, ShoppingCart> carts = new HashMap<>();

    @Override
    public ShoppingCartId nextId() {
        return new ShoppingCartId(UUID.randomUUID());
    }

    @Override
    public Optional<ShoppingCart> findById(ShoppingCartId id) {
        return carts.containsKey(id.value())
                ? Optional.of(carts.get(id.value()))
                : Optional.empty();
    }

    @Override
    public void save(ShoppingCart ar) {
        carts.put(ar.getId().value(), ar);
    }
}