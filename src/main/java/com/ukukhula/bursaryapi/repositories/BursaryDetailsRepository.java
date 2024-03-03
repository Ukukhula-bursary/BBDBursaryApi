package com.ukukhula.bursaryapi.repositories;

import com.ukukhula.bursaryapi.BursaryApiApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.ukukhula.bursaryapi.entities.BursaryDetails;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.time.Year;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Repository
public class BursaryDetailsRepository {

    private static final String GET_BURSARY_DETAILS_BY_YEAR = 
        "SELECT [BursaryDetailsID], [Year], [TotalAmount] " +
        "FROM [dbo].[BursaryDetails] " +
        "WHERE [dbo].[BursaryDetails].[Year] = ?";
    private static final String DISTRIBUTE_BURSARY_EVENLY = "{? = call [dbo].[uspEvenlyDistributeYearlyAllocation](?, ?)}";     
    
    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public BursaryDetailsRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public BursaryDetails getBursaryDetailsByYear(int year) {

        try {
            return jdbcTemplate.queryForObject(
                    GET_BURSARY_DETAILS_BY_YEAR,
                    bursaryDetailsRowMapper, year);

        } catch (EmptyResultDataAccessException e) {
            System.out.println("\n\n## Bursary Details not found for year: " + year + " ##\n\n");
            System.out.println(e.getMessage());
            return null;
        } catch (Exception e) {
            System.out
                    .println("\n\n## Unexpected error occurred when try to find bursary details by year, at repository level ##\n\n");
            System.out.println(e.getMessage());
            return null;
        }
    }

    public BigDecimal evenlyDistributeBursary(int year) {

        try {

            KeyHolder keyHolder = new GeneratedKeyHolder();

            SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate);

            SqlParameterSource in = new MapSqlParameterSource().addValues(
                new HashMap<String, Object>() {{
                    put("Year", year);
                    put("AmountPerUniversity", 0);
                }}
            );

            Map<String, Object> out = simpleJdbcCall.withProcedureName("uspEvenlyDistributeYearlyAllocation")
                    .execute(in);

            

            return (BigDecimal) out.get("AmountPerUniversity");
            
            } catch (NullPointerException e) {
                System.out.println("Unable to distribute funds evenly for year + " + year);
                return null;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return null;
            }
    }
    

    private final RowMapper<BursaryDetails> bursaryDetailsRowMapper = ((res, rowNum) ->

    new BursaryDetails(
            res.getInt("bursaryDetailsID"),
            res.getInt("year"),
            res.getBigDecimal("totalAmount")

    ));
}
