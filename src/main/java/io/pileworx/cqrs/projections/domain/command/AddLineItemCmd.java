package io.pileworx.cqrs.projections.domain.command;

import lombok.Getter;

import java.net.URI;

@Getter
public class AddLineItemCmd {

    private final URI productRef;
    private final int quantity;
    private final double price;
    private final String name;

    public AddLineItemCmd(URI productRef, int quantity, double price, String name) {
        this.productRef = productRef;
        this.quantity = quantity;
        this.price = price;
        this.name = name;
    }
}
