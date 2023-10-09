package com.project.cloudContactKeeper.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.project.cloudContactKeeper.model.Contact;
import com.project.cloudContactKeeper.model.User;
import com.project.cloudContactKeeper.repository.ContactRepo;
import com.project.cloudContactKeeper.repository.UserRepo;

@RestController
public class SearchController {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ContactRepo contactRepo;
	
	@GetMapping("/search/{query}")
	public ResponseEntity<?> searchContactHandler(@PathVariable("query") String query, Principal principal) {

		User user = this.userRepo.getUserByUserName(principal.getName());

		List<Contact> contactResult = this.contactRepo.findByFirstNameContainingAndUser(query, user);

		return ResponseEntity.ok(contactResult);
	}
	
	

}
