package io.pileworx.cqrs.projections.application.assembler;

import io.pileworx.cqrs.projections.application.dto.LineItemDto;
import io.pileworx.cqrs.projections.application.dto.ShoppingCartDto;
import io.pileworx.cqrs.projections.domain.ShoppingCart;
import io.pileworx.cqrs.projections.domain.cart.LineItem;
import io.pileworx.cqrs.projections.domain.cart.ShoppingCartJackson;

import javax.inject.Named;
import java.util.stream.Collectors;

@Named
public class ShoppingCartAssembler {

    public ShoppingCartDto toDto(ShoppingCart entity) {
        var sc = (ShoppingCartJackson) entity;
        return new ShoppingCartDto(
                sc.getId(),
                sc.getLineItems().stream().map(this::mapLineItem).collect(Collectors.toList()),
                sc.getSubtotal());
    }

    private LineItemDto mapLineItem(LineItem li) {
        return new LineItemDto(
                li.getProduct().getId(),
                li.getProduct().getPrice(),
                li.getProduct().getName(),
                li.getQuantity());
    }
}
