package com.jobportal.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.jobportal.model.SavedJob;
import com.jobportal.model.User;
import java.util.List;
import com.jobportal.model.Job;



public interface SavedJobRepository extends MongoRepository<SavedJob,String> {

	List<SavedJob> findByUser(User user);
	void deleteByUserAndJob(User user, Job job);
}
