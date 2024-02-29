package com.ukukhula.bursaryapi.repositories;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ukukhula.bursaryapi.entities.University;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

@Repository
public class UniversityRepository {

  private static final String INSERT_UNIVERSITY = "INSERT INTO [dbo].[Universities] " +
      "VALUES (?, 0)";
  private static final String GET_UNIVERSITY_BY_ID = "SELECT [UniversityID] ,[UniversityName] ,[IsActiveRecipient] " +
      "FROM [dbo].[Universities] " +
      "WHERE [UniversityID] = ?";
  private static final String GET_ALL_UNIVERSITIES = "SELECT [UniversityID] ,[UniversityName] ,[IsActiveRecipient] " +
      "FROM [dbo].[Universities]";
  private static final String GET_UNIVERSITYID_BY_NAME = "SELECT [UniversityID] ,[UniversityName] ,[IsActiveRecipient] " +
      "FROM [dbo].[Universities] " +
      "WHERE [UniversityName] = ?";

  @Autowired
  private final JdbcTemplate jdbcTemplate;

  public UniversityRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public Integer addUniversity(University university) {
    try {
      KeyHolder keyHolder = new GeneratedKeyHolder();

      jdbcTemplate.update(
          connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_UNIVERSITY,
                Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, university.getUniversityName());

            return ps;
          }, keyHolder);
      try {
        return keyHolder.getKey().intValue();
      } catch (NullPointerException e) {
        System.out.println("University: '" + university.getUniversityName() + "' was not inserted");
        return 0;
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return 0;
    }
  }

  public University getUniversityById(int id) {
    try {

      return jdbcTemplate.queryForObject(
          GET_UNIVERSITY_BY_ID,
          universityRowMapper, id);

    } catch (EmptyResultDataAccessException e) {
      System.out.println("\n\n## University not found with ID: " + id + " ##\n\n");
      System.out.println(e.getMessage());
      return null;
    } catch (Exception e) {
      System.out.println("\n\n## Unexpected error occurred when retrieving university, at repository level ##\n\n");
      System.out.println(e.getMessage());
      return null;
    }
  }

  public University getUniversityIdByName(String universityName) {
    try {

      return jdbcTemplate.queryForObject(
          GET_UNIVERSITYID_BY_NAME,
          universityRowMapper, universityName);

    } catch (EmptyResultDataAccessException e) {
      System.out.println("\n\n## University not found with name: " + universityName + " ##\n\n");
      System.out.println(e.getMessage());
      return null;
    } catch (Exception e) {
      System.out.println(
          "\n\n## Unexpected error occurred when retrieving university name by ID, at repository level ##\n\n");
      System.out.println(e.getMessage());
      return null;
    }
  }

  public List<University> getAllUniversities() {
    try {
      return jdbcTemplate.query(GET_ALL_UNIVERSITIES, universityRowMapper);
    } catch (EmptyResultDataAccessException e) {
      System.out.println("\n\n## Couldn't retrieve university list from database ##\n\n");
      System.out.println(e.getMessage());
      return null;
    } catch (Exception e) {
      System.out
          .println("\n\n## Unexpected error occurred when trying to retrieve all universities from database ##\n\n");
      System.out.println(e.getMessage());
      return null;
    }
  }

  private final RowMapper<University> universityRowMapper = ((res, rowNum) ->

  new University(
      res.getInt("UniversityID"),
      res.getString("UniversityName"),
      res.getInt("IsActiveRecipient")

  ));
}
