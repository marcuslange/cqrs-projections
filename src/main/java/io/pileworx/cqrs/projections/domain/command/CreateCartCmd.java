package io.pileworx.cqrs.projections.domain.command;

import lombok.Getter;

@Getter
public class CreateCartCmd {

    private final AddLineItemCmd lineItem;

    public CreateCartCmd(AddLineItemCmd lineItem) {
        this.lineItem = lineItem;
    }
}
