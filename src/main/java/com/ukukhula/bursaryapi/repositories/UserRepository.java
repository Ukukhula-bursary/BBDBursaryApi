package com.ukukhula.bursaryapi.repositories;

import com.ukukhula.bursaryapi.entities.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    
    public Optional<User> getUserByEmail(String email) {
        String GET_USER_BY_EMAIL = "SELECT * FROM [dbo].[Users] LEFT JOIN " +
                "Contact" +
                " " +
                "ON [dbo].[User].ContactID = Contact.ID WHERE Contact.Email = ?";
        List<User> users = jdbcTemplate.query(GET_USER_BY_EMAIL, userRowMapper, email);
        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
    }

    
    public List<User> findAll() {
        String ALL_USERS_QUERY = "SELECT * FROM [dbo].[Users]";
        return jdbcTemplate.query(ALL_USERS_QUERY, userRowMapper);
    }

    
    public Boolean userExists(String email) {
        String USER_EXISTS_QUERY = "SELECT COUNT(*) FROM [dbo].[Users] WHERE ContactID = " +
                "(SELECT ID FROM Contact WHERE Email = ?)";
        Integer count = jdbcTemplate.queryForObject(USER_EXISTS_QUERY, Integer.class, email);
        return count != null && count > 0;
    }

    private final RowMapper<User> userRowMapper = ((resultSet, rowNumber) ->
            new User(resultSet.getString("firstName"),
                    resultSet.getString("lastName"),
                    resultSet.getInt("ContactID")/* , resultSet.getInt("UserRoleID")
                    */, resultSet.getBoolean("userActive")));
}
