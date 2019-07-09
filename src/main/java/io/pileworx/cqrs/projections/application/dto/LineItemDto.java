package io.pileworx.cqrs.projections.application.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.pileworx.cqrs.projections.domain.cart.ProductId;
import lombok.Getter;

@Getter
public class LineItemDto {

    @JsonProperty("id")
    private final ProductId id;
    @JsonProperty("price")
    private final double price;
    @JsonProperty("name")
    private final String name;
    @JsonProperty("quantity")
    private final int quantity;

    @JsonCreator
    public LineItemDto(@JsonProperty("id") ProductId id,
                       @JsonProperty("price") double price,
                       @JsonProperty("name") String name,
                       @JsonProperty("quantity") int quantity) {
        this.id = id;
        this.price = price;
        this.name = name;
        this.quantity = quantity;
    }
}
