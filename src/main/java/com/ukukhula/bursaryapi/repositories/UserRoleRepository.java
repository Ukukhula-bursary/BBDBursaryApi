package com.ukukhula.bursaryapi.repositories;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.ukukhula.bursaryapi.entities.User;
import com.ukukhula.bursaryapi.entities.UserRole;

@Repository
public class UserRoleRepository {
    private JdbcTemplate jdbcTemplate;

    public UserRoleRepository(JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate=jdbcTemplate;

    }
    public String findRoleById(int id)
    {
         String query="SELECT Role FROM Roles WHERE RoleID= ? ";

        String roleid= jdbcTemplate.queryForObject(query, String.class, id);
        return roleid;
    }
    public List<UserRole> findAll()
    {
        String query="SELECT RoleID, Role FROM Roles";

        List<UserRole> roles=jdbcTemplate.query(query,userRoleRowMapper);

        return roles;
        
    }
      private final RowMapper<UserRole> userRoleRowMapper = ((resultSet, rowNumber) -> new UserRole(resultSet.getInt("RoleID"),
            resultSet.getString("Role")));
    
}
