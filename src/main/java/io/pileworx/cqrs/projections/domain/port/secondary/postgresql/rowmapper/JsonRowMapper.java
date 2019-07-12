package io.pileworx.cqrs.projections.domain.port.secondary.postgresql.rowmapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jdbc.core.RowMapper;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JsonRowMapper<A, C extends A> implements RowMapper<A> {

    private final ObjectMapper jsonMapper;
    private final Class<C> arcClass;

    public JsonRowMapper(ObjectMapper jsonMapper, Class<C> arcClass) {
        this.jsonMapper = jsonMapper;
        this.arcClass = arcClass;
    }

    @Override
    public A mapRow(ResultSet rs, int rowNum) throws SQLException {
        return readDocument(rs.getString("json_document"));
    }

    public Class<C> getAggregateRootClass() {
        return arcClass;
    }

    public A readDocument(String source){
        try {
            return jsonMapper.readValue(source, arcClass);
        } catch (IOException e) {
            var rex = new RuntimeException(source, e);
            throw new RuntimeException("Exception while de-serializing document", rex);
        }
    }

    public String writeDocument(A source) {
        try {
            return jsonMapper.writeValueAsString(source);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Exception while serializing document", e);
        }
    }
}
