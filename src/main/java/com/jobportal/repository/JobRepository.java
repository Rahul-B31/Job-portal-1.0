package com.jobportal.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.jobportal.model.Job;
import com.jobportal.model.User;

import java.util.List;
import com.jobportal.model.Company;



public interface JobRepository extends MongoRepository<Job,String> {
	
	List<Job> findByRecruiter(User recruiter);
	List<Job> findByLocation(String location);
	List<Job> findByCompany(Company company);
	List<Job> findByLocationAndCompany(String location, Company company);
	

}
