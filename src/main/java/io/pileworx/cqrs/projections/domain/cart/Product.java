package io.pileworx.cqrs.projections.domain.cart;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.pileworx.cqrs.projections.common.domain.Entity;
import lombok.Getter;

@Getter
public class Product implements Entity<ProductId> {

    @JsonProperty("id")
    private final ProductId id;
    @JsonProperty("price")
    private final double price;
    @JsonProperty("name")
    private final String name;

    @JsonCreator
    public Product(@JsonProperty("id") ProductId id,
                   @JsonProperty("price") double price,
                   @JsonProperty("name") String name) {
        this.id = id;
        this.price = price;
        this.name = name;
    }
}