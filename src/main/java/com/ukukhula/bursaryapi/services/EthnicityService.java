package com.ukukhula.bursaryapi.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ukukhula.bursaryapi.entities.Ethnicity;
import com.ukukhula.bursaryapi.repositories.EthnicityRepository;

@Service
public class EthnicityService {
    

    private EthnicityRepository repo;

    public EthnicityService(EthnicityRepository repo)
    {
        this.repo=repo;
    }

    public List<Ethnicity>getAll()
    {
        return repo.getAll();
    }

     public Ethnicity getEthnicityById(int id) {
        return repo.getEthnicityById(id);
    }

    public Ethnicity getEthnicityIdByName(String name) {
        return repo.getEthnicityIdByName(name);
    }

}
