package com.davinta.react.controllers;

import com.davinta.react.models.Contact;
import com.davinta.react.repositories.ContactRepository;
import com.davinta.service.service.AuthenticationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class ContactController {
    @Autowired
    private AuthenticationService authenticationService;

    @RequestMapping(method=RequestMethod.GET, value="/contacts")
    public Iterable<Contact> contact() {
        return authenticationService.contact();
    }

    @RequestMapping(method=RequestMethod.POST, value="/contacts")
    public Contact save(@RequestBody Contact contact) {
    	authenticationService.save(contact);
        return contact;
    }

    @RequestMapping(method=RequestMethod.GET, value="/contacts/{id}")
    public Optional<Contact> show(@PathVariable String id) {
        return authenticationService.showById(id);
    }
    @RequestMapping(method=RequestMethod.POST, value="/login")
    public Contact login( @RequestBody Contact contact) {
    	 Contact optcontact = authenticationService.findByNameLikeIgnoreCaseAndPhone(contact.getName(), contact.getPhone());
    	 if(optcontact==null) {
    		 throw new NotFoundException("not found");
    	 }
         return optcontact;
    }

    @RequestMapping(method=RequestMethod.PUT, value="/contacts/{id}")
    public Contact update(@PathVariable String id, @RequestBody Contact contact) {
    	Contact c= authenticationService.updateById(id,contact);
        return c;
    }

    @RequestMapping(method=RequestMethod.DELETE, value="/contacts/{id}")
    public String delete(@PathVariable String id) {
        authenticationService.deleteById(id);
        return "";
    }
    
    public String getPreviousDate() {
		DateFormat dateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE,-7);
		Date todate1= cal.getTime();
		String fromdate= dateFormat.format(todate1);
		return fromdate;
	}
}
