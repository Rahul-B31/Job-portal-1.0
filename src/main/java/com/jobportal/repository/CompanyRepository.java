package com.jobportal.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.jobportal.model.Company;

public interface CompanyRepository extends MongoRepository<Company,String> {

}
