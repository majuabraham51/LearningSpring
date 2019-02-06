package com.davinta.react.repositories;

import org.springframework.data.repository.CrudRepository;

import com.davinta.react.models.Contact;

public interface ContactRepository extends  CrudRepository<Contact, String> {
    @Override
    void delete(Contact deleted);
    
    Contact findByNameLikeIgnoreCaseAndPhone(String name,String phone);

	Contact findByName(String username);
}
