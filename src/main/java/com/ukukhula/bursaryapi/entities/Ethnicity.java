package com.ukukhula.bursaryapi.entities;

import lombok.Data;

@Data
public class Ethnicity {
    private int id;
    private String type;

    public Ethnicity(int id, String type)
    {
        this.id=id;
        this.type=type;
    }

    
    
}
