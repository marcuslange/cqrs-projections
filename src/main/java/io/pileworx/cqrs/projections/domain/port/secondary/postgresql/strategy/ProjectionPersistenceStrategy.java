package io.pileworx.cqrs.projections.domain.port.secondary.postgresql.strategy;

import io.pileworx.cqrs.projections.common.domain.repository.PersistenceStrategy;
import io.pileworx.cqrs.projections.domain.ShoppingCart;
import io.pileworx.cqrs.projections.domain.cart.LineItem;
import io.pileworx.cqrs.projections.domain.cart.ProductId;
import io.pileworx.cqrs.projections.domain.cart.ShoppingCartId;
import io.pileworx.cqrs.projections.domain.cart.ShoppingCartJackson;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.Map;
import java.util.Objects;

public class ProjectionPersistenceStrategy implements PersistenceStrategy<ShoppingCart, ShoppingCartId>  {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ProjectionPersistenceStrategy(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(ShoppingCart ar) {
        if (projectionExists(ar.getId()))
            update(ar);
        else
            create(ar);

        saveChildren(ar);
    }

    @Override
    public void delete(ShoppingCartId id) {}

    private void create(ShoppingCart ar) {
        var cart = (ShoppingCartJackson) ar;
        var insertSql = "INSERT INTO prj_shopping_cart (id, subtotal) VALUES (:id, :subtotal)";

        var params = Map.of(
                "id", cart.getId().toString(),
                "subtotal", cart.getSubtotal());

        jdbcTemplate.update(insertSql, params);
    }

    private void update(ShoppingCart ar) {
        var cart = (ShoppingCartJackson) ar;
        var updateSql = "UPDATE prj_shopping_cart SET subtotal = :subtotal WHERE id = :id";

        var params = Map.of(
                "id", cart.getId().toString(),
                "subtotal", cart.getSubtotal());

        jdbcTemplate.update(updateSql, params);
    }

    private boolean projectionExists(ShoppingCartId id) {
        var projectionExistsSql = "SELECT COUNT(id) FROM prj_shopping_cart WHERE id = :id";
        var rowCount = jdbcTemplate.queryForObject(projectionExistsSql, Map.of("id", id.toString()), Integer.class);
        return Objects.equals(1, rowCount);
    }

    private void saveChildren(ShoppingCart ar) {
        var cart = (ShoppingCartJackson) ar;
        cart.getLineItems().forEach(it -> {
            if(itemProjectionExists(cart.getId(), it.getProduct().getId()))
                updateItem(cart.getId(), it);
            else
                saveItem(cart.getId(), it);
        });
    }

    private void saveItem(ShoppingCartId sid, LineItem li) {
        var insertSql = "INSERT INTO prj_line_item (shopping_cart_id, product_id, product_name, product_price, quantity) VALUES (:shopping_cart_id, :product_id, :product_name, :product_price, :quantity)";
        var params = Map.of(
                "shopping_cart_id", sid.toString(),
                "product_id", li.getProduct().getId().toString(),
                "product_name", li.getProduct().getName(),
                "product_price", li.getProduct().getPrice(),
                "quantity", li.getQuantity());

        jdbcTemplate.update(insertSql, params);
    }

    private void updateItem(ShoppingCartId sid, LineItem li) {
        var updateSql = "UPDATE prj_line_item SET product_name = :product_name, product_price = :product_price, quantity = :quantity WHERE shopping_cart_id = :shopping_cart_id AND product_id = :product_id";
        var params = Map.of(
                "shopping_cart_id", sid.toString(),
                "product_id", li.getProduct().getId().toString(),
                "product_name", li.getProduct().getName(),
                "product_price", li.getProduct().getPrice(),
                "quantity", li.getQuantity());

        jdbcTemplate.update(updateSql, params);
    }


    private boolean itemProjectionExists(ShoppingCartId sid, ProductId pid) {
        var projectionExistsSql = "SELECT COUNT(shopping_cart_id) FROM prj_line_item WHERE shopping_cart_id = :shopping_cart_id AND product_id = :product_id";
        var params = Map.of(
                "shopping_cart_id", sid.toString(),
                "product_id", pid.toString());
        var rowCount = jdbcTemplate.queryForObject(projectionExistsSql, params, Integer.class);
        return Objects.equals(1, rowCount);
    }
}
