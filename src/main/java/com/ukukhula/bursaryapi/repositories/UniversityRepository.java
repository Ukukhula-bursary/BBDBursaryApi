package com.ukukhula.bursaryapi.repositories;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Repository;

import com.ukukhula.bursaryapi.entities.University;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

@Repository
public class UniversityRepository {

  private static final String INSERT_UNIVERSITY = "{CALL " +
          "uspAddUniversityByName(?)}";
  private static final String GET_UNIVERSITY_BY_ID = "{CALL " +
          "uspGetUniversityById(?)}";
  private static final String GET_ALL_UNIVERSITIES = "SELECT  UniversityName FROM Universities";

  
  private final JdbcTemplate jdbcTemplate;

  public UniversityRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }


  public Integer addUniversity(String name) {
    try {
      KeyHolder keyHolder = new GeneratedKeyHolder();

      jdbcTemplate.update(
          connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_UNIVERSITY,
                Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, name);

            return ps;
          }, keyHolder);

      return Objects.requireNonNull(keyHolder.getKey()).intValue();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }


  public University getUniversityById(int id) {
    try {
      return jdbcTemplate.queryForObject(GET_UNIVERSITY_BY_ID, universityRowMapper, id);
    } catch (EmptyResultDataAccessException e) {
      throw new RuntimeException("University not found with ID: " + id, e);
    } catch (Exception e) {
      throw new RuntimeException("Unexpected error occurred");
    }
  }

  
  public List<University> getAllUniversities() {
    try {
      return jdbcTemplate.query(GET_ALL_UNIVERSITIES, universityRowMapper);
    } catch (EmptyResultDataAccessException e) {
      throw new RuntimeException("No university allocations to show");
    } catch (Exception e) {
      throw new RuntimeException("Unexpected error occurred", e);
    }
  }


  private final RowMapper<University> universityRowMapper = ((resultSet,
      rowNumber) -> new University(resultSet.getString("UniversityName")));
}
