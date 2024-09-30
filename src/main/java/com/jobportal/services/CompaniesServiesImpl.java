package com.jobportal.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jobportal.model.Company;
import com.jobportal.repository.CompanyRepository;


@Service
public class CompaniesServiesImpl implements ICompaniesService {
	
	

	@Value("${company.logo-dir}")
	String uploadDir;
	
	@Autowired
	CompanyRepository companyRepository;

	@Override
	public Company addCompany(MultipartFile file, String companyName) throws IOException {
		
		Path uploadPath = Paths.get(uploadDir);
		//check if the dir already exist or not 
		
		if(!Files.exists(uploadPath))
		{
			Files.createDirectories(uploadPath);
		}
		
		String filename = file.getOriginalFilename();
		String uid = UUID.randomUUID().toString();
		String UniqueFileName = uid + filename.substring(filename.lastIndexOf("."));
		
		Path filepath = uploadPath.resolve(UniqueFileName);
		
		//Save to the folder
		Files.copy(file.getInputStream(),filepath);
		
		Company company = new Company();
		
		company.setName(companyName);
		company.setLogoUrl(filepath.toString());
		company.setCreatedAt(LocalDateTime.now());
		
		return companyRepository.save(company);
	}
	
	
	public List<Company> getAllCompanies()
	{
		List<Company> companies = companyRepository.findAll();
		return companies;
	}

}
