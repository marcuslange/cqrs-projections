package io.pileworx.cqrs.projections.domain.port.secondary.postgresql;

import io.pileworx.cqrs.projections.common.domain.repository.PersistenceStrategy;
import io.pileworx.cqrs.projections.common.domain.repository.StrategyRepository;
import io.pileworx.cqrs.projections.domain.ShoppingCart;
import io.pileworx.cqrs.projections.domain.ShoppingCartRepository;
import io.pileworx.cqrs.projections.domain.cart.ShoppingCartId;
import io.pileworx.cqrs.projections.domain.cart.ShoppingCartJackson;
import io.pileworx.cqrs.projections.domain.port.secondary.postgresql.rowmapper.JsonRowMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Named
public class ShoppingCartRepositoryAtomic extends StrategyRepository<ShoppingCart, ShoppingCartId> implements ShoppingCartRepository {

    private static final String TABLE_NAME = "shopping_cart";

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final JsonRowMapper<ShoppingCart, ShoppingCartJackson> rowMapper;

    @Inject
    public ShoppingCartRepositoryAtomic(List<PersistenceStrategy<ShoppingCart, ShoppingCartId>> persistenceStrategies,
                                        @Named("domainJdbcTemplate") NamedParameterJdbcTemplate jdbcTemplate,
                                        JsonRowMapper<ShoppingCart, ShoppingCartJackson> rowMapper) {
        super(persistenceStrategies);
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = rowMapper;
    }

    @Override
    public ShoppingCartId nextId() {
        return new ShoppingCartId(UUID.randomUUID());
    }

    @Override
    public Optional<ShoppingCart> findById(ShoppingCartId id) {
        try {
            var findByIdSql = String.format("SELECT json_document FROM %s WHERE id = :id", TABLE_NAME);
            ShoppingCart ar = jdbcTemplate.queryForObject(findByIdSql, Map.of("id", id.toString()), rowMapper);
            return null == ar ? Optional.empty() : Optional.of(ar);
        } catch (EmptyResultDataAccessException erdaex) {
            log.debug("Object not found", erdaex);
            return Optional.empty();
        }
    }
}