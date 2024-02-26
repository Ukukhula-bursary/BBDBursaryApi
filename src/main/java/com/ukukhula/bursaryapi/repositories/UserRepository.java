package com.ukukhula.bursaryapi.repositories;

import com.ukukhula.bursaryapi.entities.Contact;
import com.ukukhula.bursaryapi.entities.User;
import com.ukukhula.bursaryapi.entities.Request.UserRequest;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<User> getUserByEmail(String email) {
        String GET_USER_BY_EMAIL = "SELECT * FROM [dbo].[Users] LEFT JOIN " +
                "Contacts" +
                " " +
                "ON [dbo].[Users].ContactID = Contacts.ContactID WHERE Contacts.Email = ?";
        List<User> users = jdbcTemplate.query(GET_USER_BY_EMAIL, userRowMapper, email);
        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
    }

    public List<User> findAll() {
        String ALL_USERS_QUERY = "SELECT * FROM [dbo].[Users]";
        return jdbcTemplate.query(ALL_USERS_QUERY, userRowMapper);
    }

    public Boolean userExists(String email) {
        String USER_EXISTS_QUERY = "SELECT COUNT(*) FROM [dbo].[Users] WHERE ContactID = " +
                "(SELECT ContactID FROM Contacts WHERE Email = ?)";
        Integer count = jdbcTemplate.queryForObject(USER_EXISTS_QUERY, Integer.class, email);
        return count != null && count > 0;
    }

    private final RowMapper<User> userRowMapper = ((resultSet, rowNumber) -> new User(resultSet.getString("FirstName"),
            resultSet.getString("LastName"),
            resultSet.getInt("ContactID"),
            resultSet.getInt("IsActiveID")));

    public int addUser(UserRequest user) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("AddUser");
        try {
            MapSqlParameterSource inParams = new MapSqlParameterSource()
                    .addValue("FirstName", user.getFirstName())
                    .addValue("LastName", user.getLastName())
                    .addValue("PhoneNumber", user.getPhoneNumber())
                    .addValue("Email", user.getEmail())
                    .addValue("IsActive", 1);

            Map<String, Object> result = simpleJdbcCall.execute(inParams);

            return (Integer) result.get("NewUserID");
        } catch (Exception e) {
            // Handle exception
            System.err.println("User exists");
            return -1;
        }
    }

    public int updateUser(UserRequest user) {
        try {
            SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withProcedureName("UpdateUser");

            MapSqlParameterSource inParams = new MapSqlParameterSource()

                    .addValue("FirstName", user.getFirstName())
                    .addValue("LastName", user.getLastName())
                    .addValue("PhoneNumber", user.getPhoneNumber())
                    .addValue("Email", user.getEmail())
                    .addValue("IsActive", user.getIsActiveID());

            simpleJdbcCall.execute(inParams);

            return 1;
        } catch (Exception e) {

            System.err.println("Error updating user: " + e.getMessage());
            return -1;
        }
    }

    public boolean UpdateRole(String email,int RoleID) {
        String query = "INSERT INTO UserRole(UserID, RoleID)" +
                "SELECT u.UserID, ? AS RoleID" +
                "FROM Users u" +
                "INNER JOIN Contacts c ON u.ContactID = c.ContactID"+
                "WHERE c.Email = ?";
        try {

            int rowsAffected = jdbcTemplate.update(query,RoleID, email);

            return rowsAffected > 0;
        } catch (Exception e) {

            return false;
        }
    }
}