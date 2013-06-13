package com.rohankar.mapservice.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.rohankar.mapservice.bean.Poi;
import com.rohankar.mapservice.exception.DaoException;

/**
 * @author Sagar Rohankar
 */
@Repository
public class JdbcPoiDao implements PoiDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcPoiDao(final DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public String store(final Poi poi) {
        final PreparedStatementCreator psc = new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(final Connection con) throws SQLException {
                final PreparedStatement ps =
                    con.prepareStatement("INSERT INTO poi VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                ps.setNull(1, Types.INTEGER);
                ps.setString(2, poi.getName());
                ps.setString(3, poi.getLatitude().toPlainString());
                ps.setString(4, poi.getLongitude().toPlainString());
                return ps;
            }
        };
        final KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(psc, generatedKeyHolder);
        return generatedKeyHolder.getKey().toString();
    }

    @Override
    public Poi find(final String id) {
        return jdbcTemplate.query(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(final Connection con) throws SQLException {
                final PreparedStatement ps = con.prepareStatement("SELECT * FROM poi WHERE id = ?");
                ps.setInt(1, Integer.parseInt(id));
                return ps;
            }
        }, new ResultSetExtractor<Poi>() {

            @Override
            public Poi extractData(final ResultSet rs) throws SQLException, DataAccessException {
                final Poi poi = new Poi();
                rs.next();
                poi.setId(rs.getInt(1) + "");
                poi.setName(rs.getString(2));
                poi.setLatitude(new BigDecimal(rs.getString(3)));
                poi.setLongitude(new BigDecimal(rs.getString(4)));
                return poi;
            }
        });
    }

    @Override
    public void delete(final String id) {
        if (jdbcTemplate.update("DELETE FROM poi WHERE id = ?", id) != 1) {
            throw new DaoException("Couldn't delete POI with id '" + id + "'");
        }
    }
}
