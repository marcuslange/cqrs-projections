package io.pileworx.cqrs.projections.application.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.pileworx.cqrs.projections.domain.cart.ShoppingCartId;
import lombok.Getter;

import java.util.List;

@Getter
public class ShoppingCartDto {

    @JsonProperty("id")
    private final ShoppingCartId id;
    @JsonProperty("line_items")
    private final List<LineItemDto> lineItems;
    @JsonProperty("subtotal")
    private final double subtotal;

    @JsonCreator
    public ShoppingCartDto(@JsonProperty("id") ShoppingCartId id,
                           @JsonProperty("line_items") List<LineItemDto> lineItems,
                           @JsonProperty("subtotal") double subtotal) {
        this.id = id;
        this.lineItems = lineItems;
        this.subtotal = subtotal;
    }

    public void addLineItem(LineItemDto lineItem) {
        lineItems.add(lineItem);
    }
}