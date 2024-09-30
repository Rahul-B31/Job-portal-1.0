package com.jobportal.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.jobportal.model.Application;
import com.jobportal.model.Job;
import com.jobportal.model.User;

public interface ApplicationRepository extends MongoRepository<Application,String> {
	
	 boolean existsByJobAndUser(Job job, User user);
	 List<Application> findByUser(User user);
	 List<Application> findByJob(Job job);

}
