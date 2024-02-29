package com.ukukhula.bursaryapi.repositories;

import com.ukukhula.bursaryapi.BursaryApiApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import com.ukukhula.bursaryapi.entities.Department;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.Properties;

@Repository
public class DepartmentRepository {

    private static final String GET_DEPARTMENT_BY_ID =
            "SELECT [DepartmentID] ,[DepartmentName] " +
                    "FROM [dbo].[Departments] " +
                    "WHERE [dbo].[Departments].[DepartmentID] = ?";
    private static final String GET_DEPARTMENTID_BY_NAME =
            "SELECT [DepartmentID] ,[DepartmentName] " +
                    "FROM [dbo].[Departments] " +
                    "WHERE [DepartmentName] = ?";
    private static final String GET_ALL_DEPARTMENTS =
            "SELECT [DepartmentID] ,[DepartmentName] " +
                    "FROM [dbo].[Departments]";

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public DepartmentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public Department getDepartmentById(int departmentId) {

        try {
            return jdbcTemplate.queryForObject(
                    GET_DEPARTMENT_BY_ID,
                    departmentRowMapper, departmentId
            );

        } catch (EmptyResultDataAccessException e) {
            System.out.println("\n\n## Department not found with ID: " + departmentId + " ##\n\n");
            System.out.println(e.getMessage());
            return null;
        } catch (Exception e) {
            System.out.println("\n\n## Unexpected error occurred when returning department, at repository level ##\n\n");
            System.out.println(e.getMessage());
            return null;
        }
    }

        public Department getDepartmentIdByName(String departmentName) {
            
        try {

            return jdbcTemplate.queryForObject(
                    GET_DEPARTMENTID_BY_NAME,
                    departmentRowMapper, departmentName
            );

        } catch (EmptyResultDataAccessException e) {
            System.out.println("\n\n## Department not found with name: " + departmentName + " ##\n\n");
            System.out.println(e.getMessage());
            return null;
        } catch (Exception e) {
            System.out.println("\n\n## Unexpected error occurred when returning department, at repository level ##\n\n");
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<Department> getAllDepartments() {
        try {
            return jdbcTemplate.query(
                    GET_ALL_DEPARTMENTS,
                    departmentRowMapper);

        } catch (EmptyResultDataAccessException e) {
            System.out.println("\n\n## Couldn't retrieve department list from database ##\n\n");
            System.out.println(e.getMessage());
            return null;
        } catch (Exception e) {
            System.out.println("\n\n## Unexpected error occurred when trying to retrieve all departments from database ##\n\n");
            System.out.println(e.getMessage());
            return null;
        }
    }


    private final RowMapper<Department> departmentRowMapper = ((res, rowNum) ->

            new Department(
                    res.getInt("DepartmentID"),
                    res.getString("DepartmentName")

            ));
}
