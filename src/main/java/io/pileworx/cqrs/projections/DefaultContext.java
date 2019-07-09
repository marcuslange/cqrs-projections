package io.pileworx.cqrs.projections;

import io.pileworx.cqrs.projections.domain.ShoppingCart;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class DefaultContext {

    @Bean
    public Map<String, ShoppingCart> shoppingCartsMap() {
        return new HashMap<>();
    }
}