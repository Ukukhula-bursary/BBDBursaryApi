package com.ukukhula.bursaryapi.services;

import com.ukukhula.bursaryapi.entities.Statuses;
import com.ukukhula.bursaryapi.repositories.StatusesRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class StatusesService {

  StatusesRepository statusesRepository;

  public StatusesService(StatusesRepository statusesRepository) {
    this.statusesRepository = statusesRepository;
  }

  public List<Statuses> getAll() {
    return statusesRepository.getAll();
  }
}
