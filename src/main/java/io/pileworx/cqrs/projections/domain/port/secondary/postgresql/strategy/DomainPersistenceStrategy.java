package io.pileworx.cqrs.projections.domain.port.secondary.postgresql.strategy;

import io.pileworx.cqrs.projections.common.domain.repository.PersistenceStrategy;
import io.pileworx.cqrs.projections.domain.ShoppingCart;
import io.pileworx.cqrs.projections.domain.cart.ShoppingCartId;
import io.pileworx.cqrs.projections.domain.cart.ShoppingCartJackson;
import io.pileworx.cqrs.projections.domain.port.secondary.postgresql.rowmapper.JsonRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.Map;
import java.util.Objects;

public class DomainPersistenceStrategy implements PersistenceStrategy<ShoppingCart, ShoppingCartId> {

    private static final String TABLE_NAME = "shopping_cart";

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final JsonRowMapper<ShoppingCart, ShoppingCartJackson> rowMapper;

    public DomainPersistenceStrategy(NamedParameterJdbcTemplate jdbcTemplate,
                                     JsonRowMapper<ShoppingCart, ShoppingCartJackson> rowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = rowMapper;
    }

    @Override
    public void save(ShoppingCart ar) {
        if(null == ar)
            throw new RuntimeException("Aggregate Root must not be null");

        var document = rowMapper.writeDocument(ar);

        if (objectExists(ar.getId()))
            update(ar, document);
        else
            create(ar, document);
    }

    @Override
    public void delete(ShoppingCartId id) {
        var deleteSql = String.format("DELETE FROM %s WHERE id = :id", TABLE_NAME);
        jdbcTemplate.update(deleteSql, Map.of("id", id.toString()));
    }

    private void create(ShoppingCart ar, String document) {
        var insertSql = String.format("INSERT INTO %s (id, json_document) VALUES (:id, :document::jsonb)", TABLE_NAME);

        var params = Map.of(
                "id", ar.getId().toString(),
                "document", document);

        jdbcTemplate.update(insertSql, params);
    }

    private void update(ShoppingCart ar, String document) {
        var updateSql = String.format("UPDATE %s SET json_document = :document::jsonb WHERE id = :id", TABLE_NAME);

        var params = Map.of(
                "id", ar.getId().toString(),
                "document", document);

        jdbcTemplate.update(updateSql, params);
    }

    private boolean objectExists(ShoppingCartId id) {
        var objectExistsSql = String.format("SELECT COUNT(id) FROM %s WHERE id = :id", TABLE_NAME);
        var rowCount = jdbcTemplate.queryForObject(objectExistsSql, Map.of("id", id.toString()), Integer.class);
        return Objects.equals(1, rowCount);
    }
}
