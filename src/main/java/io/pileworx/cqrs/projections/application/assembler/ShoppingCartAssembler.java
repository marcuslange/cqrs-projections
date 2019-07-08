package io.pileworx.cqrs.projections.application.assembler;

import io.pileworx.cqrs.projections.application.dto.ShoppingCartDto;
import io.pileworx.cqrs.projections.domain.ShoppingCart;

import javax.inject.Named;

@Named
public class ShoppingCartAssembler {

    public ShoppingCartDto toDto(ShoppingCart entity) {
        return new ShoppingCartDto();
    }
}
