package io.pileworx.cqrs.projections.application.port.secondary.inmemory;

import io.pileworx.cqrs.projections.application.assembler.ShoppingCartAssembler;
import io.pileworx.cqrs.projections.application.dto.ShoppingCartDto;
import io.pileworx.cqrs.projections.application.finder.ShoppingCartFinder;
import io.pileworx.cqrs.projections.domain.ShoppingCart;
import io.pileworx.cqrs.projections.domain.cart.ShoppingCartId;
import io.pileworx.cqrs.projections.domain.cart.ShoppingCartJackson;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ShoppingCartFinderInMemory implements ShoppingCartFinder {

    private final Map<String, ShoppingCart> carts;
    private final ShoppingCartAssembler assembler;

    @Inject
    public ShoppingCartFinderInMemory(Map<String, ShoppingCart> carts,
                                      ShoppingCartAssembler assembler) {
        this.carts = carts;
        this.assembler = assembler;
    }

    @Override
    public List<ShoppingCartDto> findAll() {
        return carts.values().stream().map(assembler::toDto).collect(Collectors.toList());
    }

    @Override
    public Optional<ShoppingCartDto> findById(ShoppingCartId id) {
        return carts.containsKey(id.getValue())
                ? Optional.of(assembler.toDto(carts.get(id.getValue())))
                : Optional.empty();
    }

    @Override
    public List<ShoppingCartDto> findWithProductName(String productName) {
        return carts.values().stream()
                .filter(sc -> containsProductName(sc, productName))
                .map(assembler::toDto)
                .collect(Collectors.toList());
    }

    private boolean containsProductName(ShoppingCart sc, String productName) {
        var scj = (ShoppingCartJackson) sc;

        return scj.getLineItems().stream()
                .anyMatch(li -> li.getProduct().getName().equalsIgnoreCase(productName));
    }
}
