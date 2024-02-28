package com.ukukhula.bursaryapi.repositories;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.ukukhula.bursaryapi.entities.Ethnicity;
import com.ukukhula.bursaryapi.entities.UserRole;


@Repository
public class EthnicityRepository {

    private JdbcTemplate template;

    public EthnicityRepository(JdbcTemplate template)
    {
        this.template=template;
    }

    public List<Ethnicity> getAll()
    {
        String query="SELECT EthnicityID,Ethnicity FROM Ethnicities";

        return template.query(query, EthnicityRowMapper);

    }

     private final RowMapper<Ethnicity> EthnicityRowMapper = ((resultSet, rowNumber) -> new   Ethnicity(resultSet.getInt("EthnicityID"),
            resultSet.getString("Ethnicity")));
    
}
