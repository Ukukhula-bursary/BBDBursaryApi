package com.ukukhula.bursaryapi.repositories;

import com.ukukhula.bursaryapi.entities.University;
import com.ukukhula.bursaryapi.entities.UniversityStaff;
import com.ukukhula.bursaryapi.entities.UniversityStaffDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Repository
public class UniversityStaffRepository {

    private static final String INSERT_UNIVERSITYSTAFF =
        "{? = call [dbo].[uspAddUniversityStaff](?, ?, ?, ?, ?, ?, ?)}";
    private static final String GET_UNIVERSITYSTAFF_BY_ID = "SELECT [UniversityStaffID] ,[UserID] ,[UniversityID], [DepartmentID] " +
        "FROM [dbo].[UniversityStaff] " +
        "WHERE [UniversityStaffID] = ?";

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public UniversityStaffRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Integer addUniversityStaff(UniversityStaffDetails universityStaffDetails) {

         
        try {
            int universityId = new UniversityRepository(jdbcTemplate)
                    .getUniversityIdByName(universityStaffDetails.getUniversityName())
                    .getUniversityId();

            int departmentId = new DepartmentRepository(jdbcTemplate)
                    .getDepartmentIdByName(universityStaffDetails.getDepartmentName())
                    .getDepartmentId();

            KeyHolder keyHolder = new GeneratedKeyHolder();

            SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate);
            SqlParameterSource in = new MapSqlParameterSource().addValues(
                new HashMap<String, Object>() {{
                    put("FirstName", universityStaffDetails.getFirstName());
                    put("LastName", universityStaffDetails.getLastName());
                    put("PhoneNumber", universityStaffDetails.getPhoneNumber());
                    put("Email", universityStaffDetails.getEmail());
                    put("UniversityID", universityId);
                    put("DepartmentID", departmentId);
                    put("RoleID", 4);
                }}
            );

            Map<String, Object> out = simpleJdbcCall.withProcedureName("uspAddUniversityStaff").execute(in);

            return Integer.parseInt(out.get("NewUniversityStaffID").toString());
            
            } catch (NullPointerException e) {
                System.out.println("UniversityStaff: '" + universityStaffDetails.toString() + "' was not inserted");
                return 0;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return 0;
            }
    }
    
    public UniversityStaff getUniversityStaffById(int id) {
        try {

            return jdbcTemplate.queryForObject(
                    GET_UNIVERSITYSTAFF_BY_ID,
                    universityStaffRowMapper, id);

        } catch (EmptyResultDataAccessException e) {
            System.out.println("\n\n## University Staff not found with ID: " + id + " ##\n\n");
            System.out.println(e.getMessage());
            return null;
        } catch (Exception e) {
            System.out
                    .println("\n\n## Unexpected error occurred when retrieving university staff with ID, at repository level ##\n\n");
            System.out.println(e.getMessage());
            return null;
        }
    }
  

    private final RowMapper<UniversityStaff> universityStaffRowMapper = ((res, rowNum) -> 
    new UniversityStaff(
        res.getInt("UniversityStaffID"),
        res.getInt("UserID"),
        res.getInt("UniversityID"),
        res.getInt("DepartmentID")

    ));
    
}
