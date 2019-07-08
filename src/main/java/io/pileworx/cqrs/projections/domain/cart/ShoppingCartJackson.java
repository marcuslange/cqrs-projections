package io.pileworx.cqrs.projections.domain.cart;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.pileworx.cqrs.projections.domain.ShoppingCart;
import io.pileworx.cqrs.projections.domain.command.CreateCartCmd;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ShoppingCartJackson implements ShoppingCart {

    @JsonProperty("id")
    private final ShoppingCartId id;
    @JsonProperty("line_items")
    private final List<LineItem> lineItems;

    public static ShoppingCart create(ShoppingCartId id, CreateCartCmd cmd) {
        var lineItems = new ArrayList<LineItem>();

        if(null != cmd.getLineItem())
            lineItems.add(LineItem.create(cmd.getLineItem()));

        return new ShoppingCartJackson(id, lineItems);
    }

    @JsonCreator
    public ShoppingCartJackson(@JsonProperty("id") ShoppingCartId id,
                               @JsonProperty("line_items") List<LineItem> lineItems) {
        this.id = id;
        this.lineItems = lineItems;
    }

    @JsonProperty("subtotal")
    public double getSubtotal() {
        return lineItems.stream().mapToDouble(LineItem::getTotal).sum();
    }
}