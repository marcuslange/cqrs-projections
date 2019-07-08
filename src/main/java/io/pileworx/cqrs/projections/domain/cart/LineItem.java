package io.pileworx.cqrs.projections.domain.cart;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.pileworx.cqrs.projections.domain.command.AddLineItemCmd;
import lombok.Getter;

@Getter
public class LineItem {

    @JsonProperty("quantity")
    private final int quantity;
    @JsonProperty("product")
    private final Product product;

    @JsonCreator
    public LineItem(@JsonProperty("quantity") int quantity,
                    @JsonProperty("product") Product product) {
        this.quantity = quantity;
        this.product = product;
    }

    static LineItem create(AddLineItemCmd cmd) {
        var product = new Product(new ProductId(cmd.getProductRef()), cmd.getPrice(), cmd.getName());
        return new LineItem(cmd.getQuantity(),product);
    }

    @JsonProperty("total")
    public double getTotal() {
        return quantity * product.getPrice();
    }
}