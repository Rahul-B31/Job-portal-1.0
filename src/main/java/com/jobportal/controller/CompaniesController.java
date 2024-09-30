package com.jobportal.controller;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jobportal.model.Company;
import com.jobportal.services.ICompaniesService;

@RestController
@RequestMapping("/api")
public class CompaniesController {
	
	@Autowired
	ICompaniesService companiesService;
	
	
	@PostMapping("/register-company")
	public ResponseEntity<?> addCompany(@RequestParam MultipartFile logo,
	                                    @RequestParam String name) throws IOException
	{
		
		Company company = companiesService.addCompany(logo, name);
		return new ResponseEntity<Company>(company,HttpStatus.ACCEPTED);		
		
	}
	@GetMapping("/companies")
	public ResponseEntity<?> getAllCompanies() throws IOException
	{
		
		// Get all the companies details 
	    List<Company> allCompanies = companiesService.getAllCompanies();
		return new ResponseEntity<List<Company>>(allCompanies,HttpStatus.OK);		
		
	}

}
