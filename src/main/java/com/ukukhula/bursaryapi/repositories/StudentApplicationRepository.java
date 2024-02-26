package com.ukukhula.bursaryapi.repositories;

import com.ukukhula.bursaryapi.entities.StudentApplication;
import com.ukukhula.bursaryapi.entities.Request.StudentApplicationRequest;
import com.ukukhula.bursaryapi.entities.Request.UpdateStudentApplicationRequest;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

@Repository
public class StudentApplicationRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public StudentApplicationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<StudentApplication> studentRowMapper = ((resultSet,
            rowNumber) -> {
        return new StudentApplication(resultSet.getInt("StudentApplicationID"),
                resultSet.getInt("StudentID"),
                resultSet.getString("Motivation"),
                resultSet.getBigDecimal("BursaryAmount"),
                resultSet.getString("StatusID"),
                resultSet.getString("ReviewerComment"),
                resultSet.getDate("Date"));
    });

    
    public StudentApplication findByStudentID(int studentID) {
        String SQL = "SELECT * FROM StudentApplications WHERE StudentID = ?";

        StudentApplication students = jdbcTemplate.queryForObject(SQL, studentRowMapper, studentID);
        return students;
    }

    
    public List<StudentApplication> getAllStudentsApplications() {
        final String SQL = "SELECT * FROM StudentApplications";

        List<StudentApplication> students = jdbcTemplate.query(SQL, studentRowMapper);
        return students;
    }

    
    public Integer updateStudentsApplicationStatus(int studentID, String status) {
        final String SQL = "UPDATE StudentApplications SET Status = ? WHERE StudentID = ?";

        return jdbcTemplate.update(SQL, status, studentID);
    }


    public Integer updateStudentsApplicationColumnValue(int studentID, String columnName, String value) {

        final String SQL = "UPDATE StudentApplications SET " + columnName + " = ? WHERE StudentID = ?";

        return jdbcTemplate.update(SQL, value, studentID);
    }


    public int createApplication(StudentApplicationRequest student) {
        System.out.println(student.getBursaryAmount());
        System.out.println(student.getIDNumber());
        System.out.println(student.getHodEmail());
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("AddStudentApplication");
        try {
            MapSqlParameterSource inParams = new MapSqlParameterSource()
                    .addValue("FirstName", student.getFirstName())
                    .addValue("LastName", student.getLastName())
                    .addValue("PhoneNumber", student.getPhoneNumber())
                    .addValue("Email", student.getEmail())
                    .addValue("Motivation", student.getMotivation())
                    .addValue("BursaryAmount", student.getBursaryAmount())
                    .addValue("IDNumber", student.getIDNumber())
                    .addValue("EthnicityID", student.getEthnicityID())
                    .addValue("HoDEmail", student.getHodEmail());
    
            simpleJdbcCall.execute(inParams);
    
            return 1;
        } catch (Exception e) {
          
            e.printStackTrace();
            return -1;
        }
    }


    public int updateApplication(UpdateStudentApplicationRequest student) {
            return 1;
    }
    

}
