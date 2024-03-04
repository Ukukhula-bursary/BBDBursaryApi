package com.ukukhula.bursaryapi.repositories;

import com.ukukhula.bursaryapi.entities.Contact;
import com.ukukhula.bursaryapi.entities.User;
import com.ukukhula.bursaryapi.entities.Dto.jwtUser;
import com.ukukhula.bursaryapi.entities.Request.UserRequest;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
// @CrossOrigin(origins = "http://localhost:8080")
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<User> getUserById(int id) {
        String GET_USER_BY_ID = "SELECT * FROM [dbo].[Users] WHERE UserID = ?";
        List<User> users = jdbcTemplate.query(GET_USER_BY_ID, userRowMapper, id);
        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
    }

    public Optional<User> getUserByEmail(String email) {
        String GET_USER_BY_EMAIL = "SELECT * FROM [dbo].[Users] LEFT JOIN " +
                "Contacts" +
                " " +
                "ON [dbo].[Users].ContactID = Contacts.ContactID WHERE Contacts.Email = ?";
        List<User> users = jdbcTemplate.query(GET_USER_BY_EMAIL, userRowMapper, email);
        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
    }
    public jwtUser createJwtUser(User user,String email){
        int id=getUserIdByEmail(email);
        int role=getUserRoleById(id);
        return new jwtUser(id,user.getFirstName(), user.getLastName(), email, user.getContactId(), role, user.isIsActiveUser());
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
            resultSet.getBoolean("IsActiveUser")));

    public int addUser(UserRequest user) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("AddUser");
        try {

            System.out.println("\n\n\n######## Role ID:" + user.getRoleId() + "##################\n\n\n\n");

            MapSqlParameterSource inParams = new MapSqlParameterSource()
                    .addValue("FirstName", user.getFirstName())
                    .addValue("LastName", user.getLastName())
                    .addValue("PhoneNumber", user.getPhoneNumber())
                    .addValue("Email", user.getEmail())
                    .addValue("IsActiveUser", 1)
                    .addValue("RoleID", user.getRoleId())
                    .addValue("NewUserID", 0)
                    ;

            Map<String, Object> result = simpleJdbcCall.execute(inParams);
            System.out.println("\n\n\n\n****" + (Integer) result.get("NewUserID") + "**********\n\n\n\n\n");
            return (Integer) result.get("NewUserID");
        } catch (Exception e) {
            // Handle exception
            System.out.println(e.getMessage());
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
                    .addValue("IsActiveUser", user.getIsActiveUser());

            simpleJdbcCall.execute(inParams);

            return 1;
        } catch (Exception e) {

            System.err.println("Error updating user: " + e.getMessage());
            return -1;
        }
    }

    // public int getRole(String email) {
    //     String query = "SELECT r. FROM UserRole ur " +
    //             "LEFT JOIN Roles r ON ur.RoleID = r.RoleID " +
    //             "LEFT JOIN Users u ON ur.UserID = u.UserID " +
    //             "LEFT JOIN Contacts c ON u.ContactID = c.ContactID " +
    //             "WHERE c.Email = ?";
            
    //     return jdbcTemplate.queryForObject(query, String.class, email);
    // }
    public int getUserIdByEmail(String email) {
        String GET_USER_ID = "SELECT UserID FROM [dbo].[Users] LEFT JOIN " +
                "Contacts" +
                " " +
                "ON [dbo].[Users].ContactID = Contacts.ContactID WHERE Contacts.Email = ?";
       int id=jdbcTemplate.queryForObject(GET_USER_ID,Integer.class, email);
       return id;
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
    // public int getUserRole(int userID)
    // {
    //     String GETUSERROLE="SELECT UserID FROM USERS u LEFT JOIN"+
    //     "UserRole r"+
    //     " "+
    //     "ON u.UserID=r.UserID WHERE UserID=?";
    //     return jdbcTemplate.queryForObject(GETUSERROLE,Integer.class,userID);
    // }
    public int getUserRoleById(int userID) {
       String USERROLE="SELECT RoleID FROM UserRole WHERE UserID=?";
       
    //    "SELECT Role FROM Roles r "+
    //    "JOIN UserRole u ON r.RoleID=u.RoleID WHERE u.UserID= ?";
       int role=jdbcTemplate.queryForObject(USERROLE,Integer.class, userID);
       return role;
    }
}