package com.ukukhula.bursaryapi.repositories;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.ukukhula.bursaryapi.entities.DataTransferObject.Statuses;

@Repository
public class StatusesRepository {

  private JdbcTemplate jdbcTemplate;

  public StatusesRepository() {
    this.jdbcTemplate = jdbcTemplate;
  }

  public List<Statuses> getAll() {
    String GET_STATUSES = "SELECT StatusID, Status FROM Statuses";
    return jdbcTemplate.query(GET_STATUSES, StatusRowMapper);
  }

  private final RowMapper<Statuses> StatusRowMapper =
    (
      (resultSet, rowNumber) ->
        new Statuses(
          resultSet.getInt("StatusID"),
          resultSet.getString("Status")
        )
    );
}
