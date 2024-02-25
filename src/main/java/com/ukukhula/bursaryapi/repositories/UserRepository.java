package com.ukukhula.bursaryapi.repositories;

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
            new User(resultSet.getString("FirstName"),
                    resultSet.getString("LastName"),
                    resultSet.getInt("ContactID"),
                     resultSet.getInt("IsActiveID")));

    public int add(UserRequest user) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
.withProcedureName("AddUser");
// Map inParamMap = new HashMap();
// inParamMap.put("FirstName", user.getFirstName());
// inParamMap.put("LastName", user.getLastName());
// inParamMap.put("PhoneNumber", user.getPhoneNumber());
// inParamMap.put("Email", user.getLastName());

// SqlParameterSource in = new MapSqlParameterSource(inParamMap);
// Map simpleJdbcCallResult = simpleJdbcCall.execute(in);
// System.out.println(simpleJdbcCallResult);
        String NEW_USER="{CALL AddUser(?,?,?,?,?)}";
        int userRecord=jdbcTemplate.update(NEW_USER , user.getFirstName(),user.getLastName(),user.getPhoneNumber(),user.getEmail(),1);
        return userRecord;
    }
}
