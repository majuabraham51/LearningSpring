package com.davinta.service.serviceimpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.davinta.react.models.Contact;
import com.davinta.react.repositories.ContactRepository;
import com.davinta.service.service.AuthenticationService;

public class AuthenticationServiceImpl2 implements UserDetailsService, AuthenticationService {
	 @Autowired
	 ContactRepository contactRepository;

	@Override
	public Iterable<Contact> contact() {
		 return contactRepository.findAll();
	}

	@Override
	public Contact save(Contact contact) {
		 return contactRepository.save(contact);
	}

	@Override
	public Optional<Contact> showById(String id) {
		// TODO Auto-generated method stub
		return contactRepository.findById(id);
	}

	@Override
	public Contact findByNameLikeIgnoreCaseAndPhone(String name, String phone) {
		// TODO Auto-generated method stub
		return contactRepository.findByNameLikeIgnoreCaseAndPhone(name, phone);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Contact user=contactRepository.findByName(username);
		if(user == null){
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getName(), user.getPhone(), getAuthority());
	}

	private List<SimpleGrantedAuthority> getAuthority() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
	}

	@Override
	public Contact updateById(String id,Contact contact) {
		 Optional<Contact> optcontact = contactRepository.findById(id);
	        Contact c = optcontact.get();
	        if(contact.getName() != null)
	            c.setName(contact.getName());
	        if(contact.getAddress() != null)
	            c.setAddress(contact.getAddress());
	        if(contact.getCity() != null)
	            c.setCity(contact.getCity());
	        if(contact.getPhone() != null)
	            c.setPhone(contact.getPhone());
	        if(contact.getEmail() != null)
	            c.setEmail(contact.getEmail());
	        contactRepository.save(c);
	        return c;
	}

	@Override
	public String deleteById(String id) {
		// TODO Auto-generated method stub
		Optional<Contact> optcontact = contactRepository.findById(id);
        Contact contact = optcontact.get();
        contactRepository.delete(contact);
		return "Success";
	}
}
