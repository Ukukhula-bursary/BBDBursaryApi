package com.ukukhula.bursaryapi.repositories;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.ukukhula.bursaryapi.entities.Department;
import com.ukukhula.bursaryapi.entities.Ethnicity;
import com.ukukhula.bursaryapi.entities.UserRole;


@Repository
public class EthnicityRepository {

    private JdbcTemplate template;
    private static final String GET_ETHNICITY_BY_ID = 
        "SELECT [EthnicityID] ,[Ethnicity] " +
            "FROM [dbo].[Ethnicities] " +
            "WHERE [EthnicityID] = ?";
    private static final String GET_ETHNICITYID_BY_ETHNICITY = 
        "SELECT [EthnicityID] ,[Ethnicity] " +
            "FROM [dbo].[Ethnicities] " +
            "WHERE [Ethnicity] = ?";

    public EthnicityRepository(JdbcTemplate template)
    {
        this.template=template;
    }

    public List<Ethnicity> getAll()
    {
        String query = "SELECT EthnicityID,Ethnicity FROM Ethnicities";

        return template.query(query, EthnicityRowMapper);

    }

     public Ethnicity getEthnicityById(int ethnicityID) {

        try {
            return template.queryForObject(
                    GET_ETHNICITY_BY_ID,
                    EthnicityRowMapper, ethnicityID
            );

        } catch (EmptyResultDataAccessException e) {
            System.out.println("\n\n## Ethnicity not found with ID: " + ethnicityID + " ##\n\n");
            System.out.println(e.getMessage());
            return null;
        } catch (Exception e) {
            System.out.println("\n\n## Unexpected error occurred when ethnicity by ID, at repository level ##\n\n");
            System.out.println(e.getMessage());
            return null;
        }
    }

        public Ethnicity getEthnicityIdByName(String ethnicity) {
            
        try {

            return template.queryForObject(
                    GET_ETHNICITYID_BY_ETHNICITY,
                    EthnicityRowMapper, ethnicity
            );

        } catch (EmptyResultDataAccessException e) {
            System.out.println("\n\n## Ethnicity not found with name: " + ethnicity + " ##\n\n");
            System.out.println(e.getMessage());
            return null;
        } catch (Exception e) {
            System.out.println("\n\n## Unexpected error occurred when retrieving ethnicity by name, at repository level ##\n\n");
            System.out.println(e.getMessage());
            return null;
        }
    }
    


     private final RowMapper<Ethnicity> EthnicityRowMapper = ((resultSet, rowNumber) -> new   Ethnicity(resultSet.getInt("EthnicityID"),
            resultSet.getString("Ethnicity")));
    
}
