
package com.ukukhula.bursaryapi.repositories;

import com.ukukhula.bursaryapi.entities.UniversityAllocation;
import com.ukukhula.bursaryapi.entities.UniversityStaff;
import com.ukukhula.bursaryapi.entities.UniversityStaffDetails;
import com.ukukhula.bursaryapi.entities.UniversityAllocation;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Year;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class UniversityAllocationRepository {

    private static final String GET_ALL_UNIVERSITY_ALLOCATIONS = 
        "SELECT " +
            "[UniversityAllocationID]" +
            ",[UniversityID]" +
            ",[Amount]" +
            ",[BursaryDetailsID]" +
        "FROM [dbo].[UniversityAllocation]";
    private static final String GET_ALL_UNIVERSITY_ALLOCATIONS_FOR_YEAR = 
        "SELECT " +
            "ua.[UniversityAllocationID] " +
            ",ua.[UniversityID] " +
            ",ua.[Amount] " +
            ",ua.[BursaryDetailsID]" +
        "FROM [dbo].[UniversityAllocation] ua " +
            "INNER JOIN [dbo].[BursaryDetails] bd " +
            "ON ua.[BursaryDetailsID] = bd.[BursaryDetailsID] " +
        "WHERE bd.[Year] = ?";
    private static final String GET_ALL_UNIVERSITY_ALLOCATIONS_FOR_UNIVERSITY = 
        "SELECT " +
            "[UniversityAllocationID] " +
            ",[UniversityID] " +
            ",[Amount] " +
            ",[BursaryDetailsID] " +
        "FROM [dbo].[UniversityAllocation] " +
        "WHERE [UniversityID] = ? ";
    private static final String GET_ALL_UNIVERSITY_ALLOCATIONS_FOR_UNIVERSITY_YEAR = 
        "SELECT " +
            "ua.[UniversityAllocationID] " +
            ",ua.[UniversityID] " +
            ",ua.[Amount] " +
            ",ua.[BursaryDetailsID]" +
        "FROM [dbo].[UniversityAllocation] ua " +
            "INNER JOIN [dbo].[BursaryDetails] bd " +
            "ON ua.[BursaryDetailsID] = bd.[BursaryDetailsID] " +
        "WHERE bd.[Year] = ? " +
        "AND ua.[UniversityID] = ?";
    private static final String GET_REMAINING_UNIVERSITY_ALLOCATION = 
        "{? = call [dbo].[udfCalculateRemainingUniversityFundsNotRejectedApplications](?, ?)}";


   
    private final JdbcTemplate jdbcTemplate;

    public UniversityAllocationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    public List<UniversityAllocation> getAllUniversityAllocations() {
        try {
            return jdbcTemplate.query(GET_ALL_UNIVERSITY_ALLOCATIONS, universityAllocationRowMapper);
        } catch (EmptyResultDataAccessException e) {
            System.out.println("\n\n## Couldn't retrieve university allocation list from database ##\n\n");
            System.out.println(e.getMessage());
            return null;
        } catch (Exception e) {
            System.out
                    .println(
                            "\n\n## Unexpected error occurred when trying to retrieve all university allocation from database ##\n\n");
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<UniversityAllocation> getUniversityAllocationsForYear(int year) {
        try {

            return jdbcTemplate.query(
                    GET_ALL_UNIVERSITY_ALLOCATIONS_FOR_YEAR,
                    universityAllocationRowMapper, year);

        } catch (EmptyResultDataAccessException e) {
            System.out.println("\n\n## COuld not retrieve university allocations for year: " + year + " ##\n\n");
            System.out.println(e.getMessage());
            return null;
        } catch (Exception e) {
            System.out
                    .println(
                            "\n\n## Unexpected error occurred when retrieving university allocation for year, at repository level ##\n\n");
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<UniversityAllocation> getUniversityAllocationsForUniversity(String universityName) {
        try {
            int universityId = new UniversityRepository(jdbcTemplate)
                    .getUniversityIdByName(universityName)
                    .getUniversityId();

            return jdbcTemplate.query(
                    GET_ALL_UNIVERSITY_ALLOCATIONS_FOR_UNIVERSITY,
                    universityAllocationRowMapper, universityId);

        } catch (EmptyResultDataAccessException e) {
            System.out.println(
                    "\n\n## COuld not retrieve university allocations for university: " + universityName + " ##\n\n");
            System.out.println(e.getMessage());
            return null;
        } catch (Exception e) {
            System.out
                    .println(
                            "\n\n## Unexpected error occurred when retrieving university allocation for university, at repository level ##\n\n");
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public UniversityAllocation getUniversityAllocationsForUniversityYear(int year, String universityName) {
        try {

            int universityId = new UniversityRepository(jdbcTemplate)
                    .getUniversityIdByName(universityName)
                    .getUniversityId();

            return jdbcTemplate.queryForObject(
                    GET_ALL_UNIVERSITY_ALLOCATIONS_FOR_UNIVERSITY_YEAR,
                    universityAllocationRowMapper, year, universityId);

        } catch (EmptyResultDataAccessException e) {
            System.out.println("\n\n## COuld not retrieve university allocations for university " + universityName + ", for year: " + year + " ##\n\n");
            System.out.println(e.getMessage());
            return null;
        } catch (Exception e) {
            System.out.println("\n\n## Unexpected error occurred when retrieving university allocation for university, for year, at repository level ##\n\n");
            System.out.println(e.getMessage());
            return null;
        }
    }

    public BigDecimal getRemainingAmountInFundForYear(int year, String universityName) {
         
        try {
            int universityId = new UniversityRepository(jdbcTemplate)
                    .getUniversityIdByName(universityName)
                    .getUniversityId();

            SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withFunctionName("udfCalculateRemainingUniversityFundsNotRejectedApplications");

            SqlParameterSource in = new MapSqlParameterSource().addValues(
                new HashMap<String, Object>() {{
                    put("Year", year);
                    put("UniversityID", universityId);
                }}
            );

            BigDecimal remainingAmount = simpleJdbcCall.executeFunction(BigDecimal.class, in);

            return remainingAmount;
            
            } catch (NullPointerException e) {
                System.out.println("Unable to retrieve remaining amount for university:" + universityName + ", for year: " + year);
                return null;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return null;
            }
    }

    private final RowMapper<UniversityAllocation> universityAllocationRowMapper = ((res, rowNum) -> 
    new UniversityAllocation(
        res.getInt("universityAllocationID"),
        res.getInt("universityId"),
        res.getBigDecimal("amount"),
        res.getInt("bursaryDetailsId")

    ));


}
