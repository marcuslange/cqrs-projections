package io.pileworx.cqrs.projections;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.pileworx.cqrs.projections.common.domain.repository.PersistenceStrategy;
import io.pileworx.cqrs.projections.domain.ShoppingCart;
import io.pileworx.cqrs.projections.domain.cart.ShoppingCartId;
import io.pileworx.cqrs.projections.domain.cart.ShoppingCartJackson;
import io.pileworx.cqrs.projections.domain.port.secondary.postgresql.rowmapper.JsonRowMapper;
import io.pileworx.cqrs.projections.domain.port.secondary.postgresql.strategy.BombPersistenceStrategy;
import io.pileworx.cqrs.projections.domain.port.secondary.postgresql.strategy.DomainPersistenceStrategy;
import io.pileworx.cqrs.projections.domain.port.secondary.postgresql.strategy.ProjectionPersistenceStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class DefaultContext {

    @Bean
    public Map<String, ShoppingCart> shoppingCartsMap() {
        return new HashMap<>();
    }

    @Bean
    public JsonRowMapper<ShoppingCart, ShoppingCartJackson> shoppingCartRowMapper(ObjectMapper mapper) {
        return new JsonRowMapper<>(mapper, ShoppingCartJackson.class);
    }

    @Bean
    public List<PersistenceStrategy<ShoppingCart, ShoppingCartId>> shoppingCartPersistenceStrategies(NamedParameterJdbcTemplate jdbcTemplate, JsonRowMapper<ShoppingCart, ShoppingCartJackson> rowMapper) {
        return List.of(
                new DomainPersistenceStrategy(jdbcTemplate, rowMapper),
                new ProjectionPersistenceStrategy(jdbcTemplate),
                new BombPersistenceStrategy());
    }
}