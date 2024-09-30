package com.jobportal.services;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.jobportal.model.Company;



public interface ICompaniesService {
	
	public Company addCompany(MultipartFile file,String companyName) throws IOException; 
	public List<Company> getAllCompanies();

}
