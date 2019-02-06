package com.davinta.service.service;

import java.util.Optional;

import com.davinta.react.models.Contact;

public interface AuthenticationService {

	  Iterable<Contact> contact() ;
	  Contact save(Contact user);
	 Optional<Contact> showById(String id);
	 Contact findByNameLikeIgnoreCaseAndPhone(String name,String phone);
	 Contact updateById(String id,Contact contact);
	 String deleteById(String id);
}
