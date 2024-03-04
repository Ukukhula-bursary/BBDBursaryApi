package com.ukukhula.bursaryapi.repositories;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.ukukhula.bursaryapi.entities.Department;
import com.ukukhula.bursaryapi.entities.UserRole;
import com.ukukhula.bursaryapi.entities.Role;

@Repository
public class RoleRepository {

    private JdbcTemplate jdbcTemplate;
    private static final String GET_ETHNICITY_BY_ID = 
        "SELECT [RoleID] ,[Role] " +
            "FROM [dbo].[Roles] " +
            "WHERE [RoleID] = ?";
    private static final String GET_ETHNICITYID_BY_ETHNICITY = 
        "SELECT [RoleID] ,[Role] " +
            "FROM [dbo].[Roles] " +
            "WHERE [Role] = ?";
    private static final String GET_ALL_ROLES = 
        "SELECT [RoleID] ,[Role] " +
            "FROM [dbo].[Roles]";

    public RoleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public Role getRoleById(int roleID) {

        try {
            return jdbcTemplate.queryForObject(
                    GET_ETHNICITY_BY_ID,
                    roleRowMapper, roleID);

        } catch (EmptyResultDataAccessException e) {
            System.out.println("\n\n## Role not found with ID: " + roleID + " ##\n\n");
            System.out.println(e.getMessage());
            return null;
        } catch (Exception e) {
            System.out.println("\n\n## Unexpected error occurred when role by ID, at repository level ##\n\n");
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Role getRoleIdByName(String role) {

        try {

            return jdbcTemplate.queryForObject(
                    GET_ETHNICITYID_BY_ETHNICITY,
                    roleRowMapper, role);

        } catch (EmptyResultDataAccessException e) {
            System.out.println("\n\n## Role not found with name: " + role + " ##\n\n");
            System.out.println(e.getMessage());
            return null;
        } catch (Exception e) {
            System.out.println(
                    "\n\n## Unexpected error occurred when retrieving role by name, at repository level ##\n\n");
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<Role> getAllRoles() {
        try {
            return jdbcTemplate.query(
                    GET_ALL_ROLES,
                    roleRowMapper);

        } catch (EmptyResultDataAccessException e) {
            System.out.println("\n\n## Couldn't retrieve roles list from database ##\n\n");
            System.out.println(e.getMessage());
            return null;
        } catch (Exception e) {
            System.out.println(
                    "\n\n## Unexpected error occurred when trying to retrieve all roles from database ##\n\n");
            System.out.println(e.getMessage());
            return null;
        }
    }

    private final RowMapper<Role> roleRowMapper = ((res, rowNum) ->

    new Role(
            res.getInt("RoleID"),
            res.getString("Role")

    ));
}
