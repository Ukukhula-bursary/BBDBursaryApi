package com.ukukhula.bursaryapi.repositories;

import com.ukukhula.bursaryapi.entities.StudentApplication;
import com.ukukhula.bursaryapi.entities.University;
import com.ukukhula.bursaryapi.entities.User;
import com.ukukhula.bursaryapi.entities.Dto.StudentApplicationDto;
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

    private final RowMapper<StudentApplication> studentRowMapper = (resultSet, rowNumber) -> {
        return new StudentApplication(
                resultSet.getInt("StudentApplicationID"),
                resultSet.getInt("StudentID"),
                resultSet.getString("Motivation"),
                resultSet.getBigDecimal("BursaryAmount"),
                resultSet.getString("StatusID"),
                resultSet.getInt("Reviewer_UserID"),
                resultSet.getString("ReviewerComment"),
                resultSet.getDate("Date"),
                resultSet.getInt("UniversityStaffID"),
                resultSet.getInt("BursaryDetailsID")
        );
    };

    private final RowMapper<University> UniversityRowMapper = ((resultSet,
            rowNumber) -> {
        return new University(resultSet.getInt("UniversityID"),
                resultSet.getString("UniversityName"),
                resultSet.getInt("isActiveRecipient"));
    });

    public StudentApplication findByStudentID(int studentID) {
        String SQL = "SELECT * FROM StudentApplications WHERE StudentID = ?";

        StudentApplication students = jdbcTemplate.queryForObject(SQL, studentRowMapper, studentID);
        return students;
    }
    public University findStudentUniversity(int studentId) {
        String query = "SELECT * FROM Universities u LEFT JOIN Students s ON u.UniversityID = s.UniversityID WHERE s.StudentID = ?";
        return jdbcTemplate.queryForObject(query, UniversityRowMapper, studentId);
    }
    
    public String getEthnicity(int studentid)
    {
        String query ="SELECT Ethnicity FROM Ethnicities e "+
        "LEFT JOIN Students s "+
        "ON s.EthnicityID=e.EthnicityID WHERE s.StudentID=?";

        String ethnicity= jdbcTemplate.queryForObject(query,String.class,studentid);
        return ethnicity;
    }

    private StudentApplicationDto mapToStudentApplicationDto(StudentApplication studentApplication) {

        University uni=findStudentUniversity(studentApplication.getStudentID());
        
        String ethin= getEthnicity(studentApplication.getStudentID());
        String status=getStatus(studentApplication.getStatusID());
        String studentname=getStudentName(studentApplication.getStudentID());
        String reviewer=getName(studentApplication.getReviewer_UserID());
        return new StudentApplicationDto(
                uni.getUniversityId(),
                studentApplication.getStudentID(),
                uni.getUniversityName(),
                studentname,
                ethin,
                status,
                studentApplication.getMotivation(),
                studentApplication.getBursaryAmount(),
                studentApplication.getDate().toString(), // Assuming date is a string representation
                reviewer,
                studentApplication.getReviewerComment()
        );
    }

    private String getStudentName(int studentID) {
        String queryuserid="SELECT UserID FROM Students s WHERE s.StudentID=?";
        int id=jdbcTemplate.queryForObject(queryuserid, Integer.class,studentID);
        return getName(id);
      
    }

    private String getName(int UserID) {
        String name="SELECT FirstName from Users WHERE UserID=?";
        String lastname="SELECT LastName FROM Users WHERE UserID=?";

        String Firstname= jdbcTemplate.queryForObject(name,String.class,UserID);
        String LastName= jdbcTemplate.queryForObject(lastname, String.class,UserID);

        return Firstname +" "+LastName;

    }

    private String getStatus(String statusID) {
       String statusq="SELECT Status from Statuses WHERE StatusID=?";
       String status= jdbcTemplate.queryForObject(statusq,String.class,statusID);
       return status;

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

    public int updateApplication(UpdateStudentApplicationRequest request) {

        String sql = "{CALL UpdateStudentApplication (?, ?, ?, ?, ?, ?, ?, ?)}";
        System.out.println(request.studentID);
        System.out.println(request.studentApplicationID);
        System.out.println(request.getMotivation());
        return jdbcTemplate.update(sql,
                request.getStudentApplicationID(),
                request.getFirstName(),
                request.getLastName(),
                request.getPhoneNumber(),
                request.getEmail(),
                request.getMotivation(),
                request.getBursaryAmount(),
                request.getStudentID());

    }

    public List<StudentApplicationDto> getStudentApplicationFormated() {
        List<StudentApplication> studentApplications = this.getAllStudentsApplications();
        return studentApplications.stream()
                .map(this::mapToStudentApplicationDto)
                .toList();
    }

    public int deleteApplication(int applicationID) {
        String sql = "DELETE FROM StudentApplications WHERE StudentApplicationID = ?";
        return jdbcTemplate.update(sql, applicationID);
    }

}
