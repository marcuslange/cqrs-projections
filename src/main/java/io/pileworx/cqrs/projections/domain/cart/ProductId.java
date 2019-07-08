package io.pileworx.cqrs.projections.domain.cart;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.pileworx.cqrs.projections.common.domain.Identity;

import java.net.URI;

public class ProductId implements Identity<String> {

    private final URI id;

    public ProductId(URI id) {
        this.id = id;
    }

    @JsonCreator
    public ProductId(@JsonProperty("value") String id) {
        this.id = URI.create(id);
    }

    @Override
    @JsonProperty("value")
    public String value() {
        return id.toString();
    }
}