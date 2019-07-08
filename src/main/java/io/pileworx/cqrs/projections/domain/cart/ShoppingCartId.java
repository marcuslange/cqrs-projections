package io.pileworx.cqrs.projections.domain.cart;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.pileworx.cqrs.projections.common.domain.Identity;

import java.util.UUID;

public class ShoppingCartId implements Identity<String> {

    private final UUID id;

    public ShoppingCartId(UUID id) {
        this.id = id;
    }

    @JsonCreator
    public ShoppingCartId(@JsonProperty("value") String id) {
        this.id = UUID.fromString(id);
    }

    @Override
    @JsonProperty("value")
    public String value() {
        return id.toString();
    }
}
