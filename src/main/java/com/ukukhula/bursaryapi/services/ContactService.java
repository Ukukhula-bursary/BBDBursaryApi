package com.ukukhula.bursaryapi.services;

import org.springframework.stereotype.Service;

import com.ukukhula.bursaryapi.entities.Contact;
import com.ukukhula.bursaryapi.repositories.ContactRepository;

@Service
public class ContactService {
    private ContactRepository contactsRepo;

    public ContactService(ContactRepository contactsRepo)
    {
        this.contactsRepo=contactsRepo;
    }

    public Contact getById(int id)
    {
        return contactsRepo.getById(id);
    }
}
