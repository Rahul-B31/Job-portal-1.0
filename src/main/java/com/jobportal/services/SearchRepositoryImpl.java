package com.jobportal.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.stereotype.Component;

import com.jobportal.model.Job;
import com.jobportal.repository.SearchRepository;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;


@Component
public class SearchRepositoryImpl implements SearchRepository {
	
	@Autowired
	MongoClient client;
	
	@Autowired
	MongoConverter converter;

	@Override
	public List<Job> searchJobsByKeywords(String keyword) {
		
		
		List<Job> jobs = new ArrayList<Job>();
		
		MongoDatabase database = client.getDatabase("DB");
		MongoCollection<Document> collection = database.getCollection("Jobs");
		AggregateIterable<Document> result = collection.aggregate(Arrays.asList(new Document("$search", 
		    new Document("index", "default")
		            .append("text", 
		    new Document("query",keyword)
		                .append("path", Arrays.asList("jobDescription", "requirements", "location", "title")))), 
		    new Document("$limit", 1L)));
		
		result.forEach((doc)->jobs.add(converter.read(Job.class, doc)));
		return jobs;
	}

}
