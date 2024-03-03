package com.ukukhula.bursaryapi.repositories;

import com.ukukhula.bursaryapi.entities.StudentApplication;
import com.ukukhula.bursaryapi.entities.StudentApplicationDetails;
import com.ukukhula.bursaryapi.entities.StudentApplicationDetails_ActiveStudent;
import com.ukukhula.bursaryapi.entities.StudentApplicationDetails_NewStudent;
import com.ukukhula.bursaryapi.entities.University;
import com.ukukhula.bursaryapi.entities.UniversityStaff;
import com.ukukhula.bursaryapi.entities.User;
import com.ukukhula.bursaryapi.entities.Dto.StudentApplicationDto;
import com.ukukhula.bursaryapi.entities.Request.StudentApplicationRequest;
import com.ukukhula.bursaryapi.entities.Request.UpdateStudentApplicationRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class StudentApplicationRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    private static final String INSERT_APPLICATION_NEW_STUDENT = 
            "{? = call [dbo].[uspAddStudentApplication_NewStudent](?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
    private static final String GET_STUDENT_APPLICATION_BY_ID = 
        "SELECT [StudentApplicationID] " +
            ",[StudentID] " +
            ",[Motivation] " +
            ",[BursaryAmount] " +
            ",[StatusID] " +
            ",[Reviewer_UserID] " +
            ",[ReviewerComment] " +
            ",[Date] " +
            ",[UniversityStaffID] " +
            ",[BursaryDetailsID] " +
            "FROM [dbo].[StudentApplications]" +
            "WHERE [StudentApplicationID] = ?";
 

    public StudentApplicationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<StudentApplication> studentRowMapper = (resultSet, rowNumber) -> {
        return new StudentApplication(
                resultSet.getInt("StudentApplicationID"),
                resultSet.getInt("StudentID"),
                resultSet.getString("Motivation"),
                resultSet.getBigDecimal("BursaryAmount"),
                resultSet.getInt("StatusID"),
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
                studentApplication.getStudentApplicationID(),
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

    private String getStatus(int statusID) {
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


    public int studentApplication_ActiveStudent(StudentApplicationDetails_ActiveStudent studentApplicationDetails_ActiveStudent) {
        try {

            int bursaryDetailsId = new BursaryDetailsRepository(jdbcTemplate)
                    .getBursaryDetailsByYear(studentApplicationDetails_ActiveStudent.getYear())
                    .getBursaryDetailsId();

   

            SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                    .withTableName("StudentApplications")
                    .usingGeneratedKeyColumns("StudentApplicationID");

            SqlParameterSource in = new MapSqlParameterSource().addValues(
                    new HashMap<String, Object>() {
                        {
                            put("StudentID", studentApplicationDetails_ActiveStudent.getStudentID());
                            put("Motivation", studentApplicationDetails_ActiveStudent.getMotivation());
                            put("BursaryAmount", studentApplicationDetails_ActiveStudent.getBursaryAmount());
                            put("StatusID", studentApplicationDetails_ActiveStudent.getStatusID());
                            put("Reviewer_UserID", studentApplicationDetails_ActiveStudent.getReviewer_UserID());
                            put("ReviewerComment", studentApplicationDetails_ActiveStudent.getReviewerComment());
                            put("Date", studentApplicationDetails_ActiveStudent.getDate());
                            put("UniversityStaffID", studentApplicationDetails_ActiveStudent.getUniversityStaffID());
                            put("BursaryDetailsID", bursaryDetailsId);
                        }
                    });

            Number newStudentApplicationId = simpleJdbcInsert.executeAndReturnKey(in);

            System.out.println("\n\n\nNew student application id: " + newStudentApplicationId + "\n\n\n\n");

            return newStudentApplicationId.intValue();


        } catch (NullPointerException e) {
            System.out.println("Unable to create new student application");
            return 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }

    public int studentApplication_NewStudent(StudentApplicationDetails_NewStudent studentApplicationDetails_NewStudent) {
        try {

            int bursaryDetailsId = new BursaryDetailsRepository(jdbcTemplate)
                    .getBursaryDetailsByYear(studentApplicationDetails_NewStudent.getYear())
                    .getBursaryDetailsId();


            SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate);

            int universityId = new UniversityRepository(jdbcTemplate)
                    .getUniversityIdByName(studentApplicationDetails_NewStudent.getUniversityName())
                    .getUniversityId();

            int departmentId = new DepartmentRepository(jdbcTemplate)
                    .getDepartmentIdByName(studentApplicationDetails_NewStudent.getDepartmentName())
                    .getDepartmentId();

            int ethnicityID = new EthnicityRepository(jdbcTemplate)
                    .getEthnicityIdByName(studentApplicationDetails_NewStudent.getEthnicity())
                    .getId();

            SqlParameterSource in = new MapSqlParameterSource().addValues(
                    new HashMap<String, Object>() {
                        {
                            put("FirstName", studentApplicationDetails_NewStudent.getFirstName());
                            put("LastName", studentApplicationDetails_NewStudent.getLastName());

                            put("PhoneNumber", studentApplicationDetails_NewStudent.getPhoneNumber());
                            put("Email", studentApplicationDetails_NewStudent.getEmail());

                            put("RoleID", 4);

                            put("IDNumber", studentApplicationDetails_NewStudent.getIdNumber());
                            put("EthnicityID", ethnicityID);
                            put("UniversityID", universityId);
                            put("DepartmentID", departmentId);

                            put("Motivation", studentApplicationDetails_NewStudent.getMotivation());
                            put("BursaryAmount", studentApplicationDetails_NewStudent.getBursaryAmount());
                            put("StatusID", studentApplicationDetails_NewStudent.getStatusID());
                            put("Reviewer_UserID", studentApplicationDetails_NewStudent.getReviewer_UserID());
                            put("ReviewerComment", studentApplicationDetails_NewStudent.getReviewerComment());
                            put("Date", studentApplicationDetails_NewStudent.getDate());
                            put("UniversityStaffID", studentApplicationDetails_NewStudent.getUniversityStaffID());
                            put("BursaryDetailsID", bursaryDetailsId);
                        }
                    });

            Map<String, Object> out = simpleJdbcCall.withProcedureName("uspAddStudentApplication_NewStudent")
                    .execute(in);

            System.out.println("\n\n\nNew student application id: "
                    + Integer.parseInt(out.get("NewStudentApplicationID").toString()) + "\n\n\n\n");

            return Integer.parseInt(out.get("NewStudentApplicationID").toString());

        } catch (NullPointerException e) {
            System.out.println("Unable to create new student application");
            return 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }

     public StudentApplication getStudentApplicationById(int id) {
        try {

            return jdbcTemplate.queryForObject(
                    GET_STUDENT_APPLICATION_BY_ID,
                    studentRowMapper, id);

        } catch (EmptyResultDataAccessException e) {
            System.out.println("\n\n## Student Applications not found with ID: " + id + " ##\n\n");
            System.out.println(e.getMessage());
            return null;
        } catch (Exception e) {
            System.out
                    .println("\n\n## Unexpected error occurred when retrieving student applications with ID, at repository level ##\n\n");
            System.out.println(e.getMessage());
            return null;
        }
    }

}
