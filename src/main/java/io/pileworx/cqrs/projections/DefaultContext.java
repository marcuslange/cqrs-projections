package io.pileworx.cqrs.projections;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaxxer.hikari.HikariDataSource;
import io.pileworx.cqrs.projections.common.domain.repository.PersistenceStrategy;
import io.pileworx.cqrs.projections.domain.ShoppingCart;
import io.pileworx.cqrs.projections.domain.cart.ShoppingCartId;
import io.pileworx.cqrs.projections.domain.cart.ShoppingCartJackson;
import io.pileworx.cqrs.projections.domain.port.secondary.postgresql.rowmapper.JsonRowMapper;
import io.pileworx.cqrs.projections.domain.port.secondary.postgresql.strategy.BombPersistenceStrategy;
import io.pileworx.cqrs.projections.domain.port.secondary.postgresql.strategy.DomainPersistenceStrategy;
import io.pileworx.cqrs.projections.domain.port.secondary.postgresql.strategy.ProjectionPersistenceStrategy;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
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
    public List<PersistenceStrategy<ShoppingCart, ShoppingCartId>> shoppingCartPersistenceStrategies(@Qualifier("domainJdbcTemplate") NamedParameterJdbcTemplate domainJdbcTemplate,
                                                                                                     @Qualifier("projectionJdbcTemplate") NamedParameterJdbcTemplate projectionJdbcTemplate,
                                                                                                     JsonRowMapper<ShoppingCart, ShoppingCartJackson> rowMapper) {
        return List.of(
                new DomainPersistenceStrategy(domainJdbcTemplate, rowMapper),
                new ProjectionPersistenceStrategy(projectionJdbcTemplate)
                //new BombPersistenceStrategy()
        );
    }

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.ds-domain")
    public DataSourceProperties domainDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "domainDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.ds-domain.hikari")
    public DataSource domainDataSource() {
        return domainDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Bean(name = "domainTransactionManager")
    public DataSourceTransactionManager domainTransactionManager(@Qualifier("domainDataSource") DataSource domainDataSource) {
        return new DataSourceTransactionManager(domainDataSource);
    }

    @Bean(name = "domainJdbcTemplate")
    public NamedParameterJdbcTemplate domainJdbcTemplate(@Qualifier("domainDataSource") DataSource domainDataSource) {
        return new NamedParameterJdbcTemplate(domainDataSource);
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.ds-projection")
    public DataSourceProperties projectionDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "projectionDataSource")
    @ConfigurationProperties(prefix = "spring.ds-projection.hikari")
    public DataSource projectionDataSource() {
        return  projectionDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Bean(name = "projectionTransactionManager")
    public DataSourceTransactionManager projectionTransactionManager(@Qualifier("projectionDataSource") DataSource projectionDataSource) {
        return new DataSourceTransactionManager(projectionDataSource);
    }

    @Bean(name = "projectionJdbcTemplate")
    public NamedParameterJdbcTemplate projectionJdbcTemplate(@Qualifier("projectionDataSource") DataSource projectionDataSource) {
        return new NamedParameterJdbcTemplate(projectionDataSource);
    }

    @Bean(name = "chainedTransactionManager")
    public ChainedTransactionManager transactionManager(@Qualifier("domainTransactionManager") PlatformTransactionManager domainTransactionManager,
                                                        @Qualifier("projectionTransactionManager") PlatformTransactionManager projectionTransactionManager) {
        return new ChainedTransactionManager(
                domainTransactionManager,
                projectionTransactionManager);
    }
}