package com.project.cloudContactKeeper.repository;

import java.util.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.cloudContactKeeper.model.Contact;
import com.project.cloudContactKeeper.model.User;

public interface ContactRepo extends JpaRepository<Contact, Integer> {

	@Query("from Contact as c where user.userId =:id")
	public Page<Contact> getContactsByUserName(@Param("id") int id, Pageable pageable);
	
	
	// for searching
	public List<Contact> findByFirstNameContainingAndUser(String name, User user);
	
}
