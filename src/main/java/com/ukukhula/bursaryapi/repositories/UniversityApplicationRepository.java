package com.ukukhula.bursaryapi.repositories;

import com.ukukhula.bursaryapi.entities.StudentApplication;
import com.ukukhula.bursaryapi.entities.UniversityApplication;
import com.ukukhula.bursaryapi.entities.Dto.UniversityApplicationDto;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.Year;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UniversityApplicationRepository {

    private final JdbcTemplate jdbcTemplate;

    public UniversityApplicationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    
    public UniversityApplication getApplicationByUniversityId(int universityId) {
        String GET_APPLICATION_BY_UNIVERSITY_ID = "SELECT * FROM " +
                "UniversityApplication WHERE UniversityID = ?";
        return jdbcTemplate.queryForObject(GET_APPLICATION_BY_UNIVERSITY_ID,
                universityApplicationRowMapper, universityId);
    }
    public List<UniversityApplication> getAllUniversityApplication()
    {
        String GET_ALL_UNIVERSITY_APPLICATION = "SELECT * FROM UniversityApplications";
        List<UniversityApplication> universityApplications = jdbcTemplate.query(GET_ALL_UNIVERSITY_APPLICATION, universityApplicationRowMapper);
        return universityApplications;
    }

    private final RowMapper<UniversityApplication> universityApplicationRowMapper = ((resultSet, rowNumber) -> {
        return new UniversityApplication(resultSet.getInt("UniversityApplicationID"),
                resultSet.getInt("UniversityID"), resultSet.getString(
                        "Motivation"), resultSet.getInt("StatusID"),
             resultSet.getString("Date"),
                resultSet.getInt("ReviewerID_UserID"),
                resultSet.getString("ReviewerComment"));
    });

    public List<UniversityApplicationDto> getUniversityApplicationFormated() {
        List<UniversityApplication> universityApplication = this.getAllUniversityApplication();
        return universityApplication.stream()
                .map(this::maptoUniApplicationDto)
                .toList();
    }
       private UniversityApplicationDto maptoUniApplicationDto(UniversityApplication application){

        String status=getStatus(application.getStatusID());
        String reviewername=getName(application.getReviewerID_UserID());
        String budget = getBudget(application.getUniversityID(),application.getDate()).toString();
       
        return new UniversityApplicationDto(
            application.getUniversityID(),
            application.getUniversityApplicationID(),
            budget,
            status,
            application.getMotivation(),
            application.getDate().toString(),
            reviewername,
            application.getReviewerComment());
     
    }
    private BigDecimal getBudget(int universityID, String date) {
        int year = Integer.parseInt(date.substring(0, 4));
        System.out.println(year);
        System.out.println(universityID);
        String budgetQuery = "SELECT ua.Amount " +
                             "FROM UniversityAllocation ua " +
                             "LEFT JOIN BursaryDetails bd ON ua.BursaryDetailsID = bd.BursaryDetailsID " +
                             "WHERE  bd.Year = ? AND ua.UniversityID = ?";
        
        try {
        return jdbcTemplate.queryForObject(budgetQuery, BigDecimal.class, year, universityID);
    } catch (EmptyResultDataAccessException e) {
        // Log the exception or handle the scenario where no budget amount is found
        System.err.println("No budget amount found for universityID: " + universityID + " and year: " + year);
        return BigDecimal.ZERO; // Return a default value (0) or handle the scenario accordingly
    }
    }
    
    private String getStatus(int statusID) {
        String statusq="SELECT Status from Statuses WHERE StatusID=?";
        String status= jdbcTemplate.queryForObject(statusq,String.class,statusID);
        return status;
 
     }
    private String getName(int UserID) {
        String name="SELECT FirstName from Users WHERE UserID=?";
        String lastname="SELECT LastName FROM Users WHERE UserID=?";

        String Firstname= jdbcTemplate.queryForObject(name,String.class,UserID);
        String LastName= jdbcTemplate.queryForObject(lastname, String.class,UserID);

        return Firstname +" "+LastName;

    }

}
