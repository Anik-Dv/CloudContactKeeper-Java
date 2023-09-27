package com.project.cloudContactKeeper.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.project.cloudContactKeeper.model.User;
import com.project.cloudContactKeeper.repository.UserRepo;

public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserRepo userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		// fetching User from Database
		User user = this.userRepository.getUserByUserName(username);

		if (user == null) {
			throw new UsernameNotFoundException("Sorry User Not Found!");
		}

		CustomUserDetails customUserDetails = new CustomUserDetails(user);

		return customUserDetails;
	}

}
