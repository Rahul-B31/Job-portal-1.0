package com.jobportal.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core .userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jobportal.model.User;
import com.jobportal.repository.UserRepository;




@Service
public class CustomUserService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) {
		
		Optional<User> result = userRepository.findByEmail(username);
		 User user = result.orElseThrow(()->new UsernameNotFoundException("User Not Found"));
		 
		 List<GrantedAuthority> authorities = new ArrayList<>();
		 return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),authorities);
		
	}

}
