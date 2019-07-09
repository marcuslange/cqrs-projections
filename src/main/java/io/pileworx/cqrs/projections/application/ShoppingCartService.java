package io.pileworx.cqrs.projections.application;

import io.pileworx.cqrs.projections.application.assembler.ShoppingCartAssembler;
import io.pileworx.cqrs.projections.application.dto.ShoppingCartDto;
import io.pileworx.cqrs.projections.application.finder.ShoppingCartFinder;
import io.pileworx.cqrs.projections.domain.ShoppingCart;
import io.pileworx.cqrs.projections.domain.ShoppingCartRepository;
import io.pileworx.cqrs.projections.domain.command.CreateCartCmd;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
public class ShoppingCartService {

    private final ShoppingCartRepository repository;
    private final ShoppingCartFinder finder;
    private final ShoppingCartAssembler assembler;

    @Inject
    public ShoppingCartService(ShoppingCartRepository repository,
                               ShoppingCartFinder finder,
                               ShoppingCartAssembler assembler) {
        this.repository = repository;
        this.finder = finder;
        this.assembler = assembler;
    }

    public ShoppingCartDto createCart(CreateCartCmd cmd) {
        var cart = ShoppingCart.create(repository.nextId(), cmd);
        repository.save(cart);
        return assembler.toDto(cart);
    }

    public List<ShoppingCartDto> findByProductName(String productName) {
        return finder.findWithProductName(productName);
    }
}