package com.ukukhula.bursaryapi.repositories;

import com.ukukhula.bursaryapi.BursaryApiApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import com.ukukhula.bursaryapi.entities.Student;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.Properties;

@Repository
public class StudentsRepository {

    private static final String GET_STUDENT_BY_ID =
            "SELECT [StudentID]" +
                    ",[UserID]" +
                    ",[IDNumber]" +
                    ",[EthnicityID]" +
                    ",[UniversityID]" +
                    ",[DepartmentID]" +
            "FROM [dbo].[Students]" +
            "WHERE [StudentID] = ?";
    private static final String GET_STUDENTS_BY_UNIVERSITY_ID =
            "SELECT [StudentID]" +
                ",[UserID]" +
                ",[IDNumber]" +
                ",[EthnicityID]" +
                ",[UniversityID]" +
                ",[DepartmentID]" +
            "FROM [dbo].[Students]" +
            "WHERE [UniversityID] = ?";
    private static final String GET_ACTIVE_STUDENTS_BY_UNIVERSITY_ID =
            "SELECT [StudentID] " +
                ",s.[UserID] " +
                ",s.[IDNumber] " +
                ",s.[EthnicityID] " +
                ",s.[UniversityID] " +
                ",s.[DepartmentID] " +
            "FROM [dbo].[Students] s " +
                "INNER JOIN [dbo].[Users] u " +
                "ON s.[UserID] = u.[UserID] " +
            "WHERE [UniversityID] = ? " +
            "AND u.[IsActiveUser] = 1";
    private static final String GET_ALL_STUDENTS =
            "SELECT [StudentID]" +
                ",[UserID]" +
                ",[IDNumber]" +
                ",[EthnicityID]" +
                ",[UniversityID]" +
                ",[DepartmentID]" +
            "FROM [dbo].[Students]";

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public StudentsRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public Student getStudentById(int studentId) {

        try {
            return jdbcTemplate.queryForObject(
                    GET_STUDENT_BY_ID,
                    studentRowMapper, studentId
            );

        } catch (EmptyResultDataAccessException e) {
            System.out.println("\n\n## Student not found with ID: " + studentId + " ##\n\n");
            System.out.println(e.getMessage());
            return null;
        } catch (Exception e) {
            System.out.println("\n\n## Unexpected error occurred when retrieving student by ID, at repository level ##\n\n");
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<Student> getStudentsByUniversityName(String universityName) {
        
        try {
            int universityId = new UniversityRepository(jdbcTemplate)
                    .getUniversityIdByName(universityName)
                    .getUniversityId();

        return jdbcTemplate.query(
                GET_STUDENTS_BY_UNIVERSITY_ID,
                studentRowMapper, universityId
        );

    } catch (EmptyResultDataAccessException e) {
        System.out.println("\n\n## Students not found for university: " + universityName + " ##\n\n");
        System.out.println(e.getMessage());
        return null;
    } catch (Exception e) {
        System.out.println("\n\n## Unexpected error occurred when retrieveing students for university: " + universityName + ", at repository level ##\n\n");
        System.out.println(e.getMessage());
        return null;
    }
}

    public List<Student> getActiveStudentsByUniversityName(String universityName) {
        
        try {
            int universityId = new UniversityRepository(jdbcTemplate)
                    .getUniversityIdByName(universityName)
                    .getUniversityId();

        return jdbcTemplate.query(
                GET_ACTIVE_STUDENTS_BY_UNIVERSITY_ID,
                studentRowMapper, universityId
        );

    } catch (EmptyResultDataAccessException e) {
        System.out.println("\n\n## Active students not found for university: " + universityName + " ##\n\n");
        System.out.println(e.getMessage());
        return null;
    } catch (Exception e) {
        System.out.println("\n\n## Unexpected error occurred when returning active students for university: " + universityName + ", at repository level ##\n\n");
        System.out.println(e.getMessage());
        return null;
    }
}

    public List<Student> getAllStudents() {
        try {
            return jdbcTemplate.query(
                    GET_ALL_STUDENTS,
                    studentRowMapper);

        } catch (EmptyResultDataAccessException e) {
            System.out.println("\n\n## Couldn't retrieve all student list from database ##\n\n");
            System.out.println(e.getMessage());
            return null;
        } catch (Exception e) {
            System.out.println("\n\n## Unexpected error occurred when trying to retrieve all students from database ##\n\n");
            System.out.println(e.getMessage());
            return null;
        }
    }


    private final RowMapper<Student> studentRowMapper = ((res, rowNum) ->

            new Student(
                    res.getInt("studentID"),
                    res.getInt("userID"),
                    res.getString("idNumber"),
                    res.getInt("ethnicityId"),
                    res.getInt("universityId"),
                    res.getInt("departmentId")

            ));
}
