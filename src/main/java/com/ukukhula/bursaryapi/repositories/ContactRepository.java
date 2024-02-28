package com.ukukhula.bursaryapi.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.ukukhula.bursaryapi.entities.Contact;
import com.ukukhula.bursaryapi.entities.Ethnicity;

@Repository
public class ContactRepository {
    private JdbcTemplate template;

    public ContactRepository(JdbcTemplate template)
    {
        this.template=template;
    }

    public Contact getById(int id)
    {
        String query= "SELECT PhoneNumber, Email FROM Contacts WHERE ContactID= ?";

         return template.queryForObject(query, ContactRowMapper,id);

        
    }
     private final RowMapper<Contact> ContactRowMapper = ((resultSet, rowNumber) -> new Contact(resultSet.getString("PhoneNumber"),
            resultSet.getString("Email")));
}
