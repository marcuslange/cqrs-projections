package io.pileworx.cqrs.projections.application.port.secondary.postgresql;

import io.pileworx.cqrs.projections.application.dto.ShoppingCartDto;
import io.pileworx.cqrs.projections.application.finder.ShoppingCartFinder;
import io.pileworx.cqrs.projections.application.port.secondary.postgresql.mapper.ShoppingCartResultSetExtractor;
import io.pileworx.cqrs.projections.domain.cart.ShoppingCartId;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Named
public class ShoppingCartProjectionFinder implements ShoppingCartFinder {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final ShoppingCartResultSetExtractor extractor;

    @Inject
    public ShoppingCartProjectionFinder(NamedParameterJdbcTemplate jdbcTemplate,
                                        ShoppingCartResultSetExtractor extractor) {
        this.jdbcTemplate = jdbcTemplate;
        this.extractor = extractor;
    }

    @Override
    public List<ShoppingCartDto> findAll() {
        return null;
    }

    @Override
    public Optional<ShoppingCartDto> findById(ShoppingCartId id) {
        return Optional.empty();
    }

    @Override
    public List<ShoppingCartDto> findWithProductName(String productName) {
        var params = Map.of("product_name", productName);
        var findAllSql =  "SELECT * FROM prj_shopping_cart AS sc INNER JOIN prj_line_item li on sc.id = li.shopping_cart_id WHERE sc.id IN ( SELECT DISTINCT sc.id FROM prj_shopping_cart AS sc INNER JOIN prj_line_item li on sc.id = li.shopping_cart_id WHERE li.product_name = :product_name ) ORDER BY sc.id";
        return jdbcTemplate.query(findAllSql, params, extractor);
    }
}
