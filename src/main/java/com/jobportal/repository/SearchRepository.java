package com.jobportal.repository;

import java.util.List;

import com.jobportal.model.Job;

public interface SearchRepository {
     
	public List<Job> searchJobsByKeywords(String keyword);
}
