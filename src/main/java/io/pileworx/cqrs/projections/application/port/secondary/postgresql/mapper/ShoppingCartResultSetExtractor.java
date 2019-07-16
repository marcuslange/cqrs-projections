package io.pileworx.cqrs.projections.application.port.secondary.postgresql.mapper;

import io.pileworx.cqrs.projections.application.dto.LineItemDto;
import io.pileworx.cqrs.projections.application.dto.ShoppingCartDto;
import io.pileworx.cqrs.projections.domain.cart.ProductId;
import io.pileworx.cqrs.projections.domain.cart.ShoppingCartId;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import javax.inject.Named;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Named
public class ShoppingCartResultSetExtractor implements ResultSetExtractor<List<ShoppingCartDto>> {

    @Override
    public List<ShoppingCartDto> extractData(ResultSet rs) throws SQLException, DataAccessException {
        var carts = new HashMap<String, ShoppingCartDto>();

        while(rs.next()) {
            var id = rs.getString("id");
            var cart = carts.get(id);

            if(null == cart) {
                cart = mapShoppingCart(rs);
                carts.put(id, cart);
            }

            cart.addLineItem(mapLineItem(rs));
        }

        return new ArrayList<>(carts.values());
    }

    private ShoppingCartDto mapShoppingCart(ResultSet rs) throws SQLException {
        return new ShoppingCartDto(
                new ShoppingCartId(rs.getString("id")),
                new ArrayList<LineItemDto>(),
                rs.getDouble("subtotal"));
    }

    private LineItemDto mapLineItem(ResultSet rs) throws SQLException {
        return new LineItemDto(
                new ProductId(rs.getString("product_id")),
                rs.getDouble("product_price"),
                rs.getString("product_name"),
                rs.getInt("quantity"));
    }
}
