package com.jobportal.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.jobportal.model.User;


@Repository
public interface UserRepository extends MongoRepository<User,String> {
	
	public Optional<User> findByEmail(String email);

}
