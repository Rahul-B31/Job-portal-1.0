package com.jobportal.model;


import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Jobs")
public class Job {
	
	    @Id
	    private String jobId;
	    
	    private LocalDateTime createdAt;
	    
	    
	    private String title;
	    
	    
	    private String jobDescription;
	    
	    
	    private String location;
	    
	    
	    private String requirements;
	    
	    @DBRef
	    private User recruiter; 
	    
	    private boolean isOpen;

	    @DBRef  // Reference to the company
	    private Company company;
	    
	    
	    private Long applicationCount=0L;
	    
	    
	    public Long getApplicationCount() {
			return applicationCount;
		}


		public void setApplicationCount(Long applicationCount) {
			this.applicationCount = applicationCount;
		}


		public User getRecruiter() {
			return recruiter;
		}


		public void setRecruiter(User recruiter) {
			this.recruiter = recruiter;
		}


		public Job() {
			// TODO Auto-generated constructor stub
		}


		public Job(LocalDateTime createdAt, String title, String jobDescription, String location, String requirements,
				boolean isOpen, Company company) {
			super();
			this.createdAt = createdAt;
			this.title = title;
			this.jobDescription = jobDescription;
			this.location = location;
			this.requirements = requirements;
			this.isOpen = isOpen;
			this.company = company;
		}


		public String getJobId() {
			return jobId;
		}


		public void setJobId(String jobId) {
			this.jobId = jobId;
		}


		public LocalDateTime getCreatedAt() {
			return createdAt;
		}


		public void setCreatedAt(LocalDateTime createdAt) {
			this.createdAt = createdAt;
		}


		public String getTitle() {
			return title;
		}


		public void setTitle(String title) {
			this.title = title;
		}


		public String getJobDescription() {
			return jobDescription;
		}


		public void setJobDescription(String jobDescription) {
			this.jobDescription = jobDescription;
		}


		public String getLocation() {
			return location;
		}


		public void setLocation(String location) {
			this.location = location;
		}


		public String getRequirements() {
			return requirements;
		}


		public void setRequirements(String requirements) {
			this.requirements = requirements;
		}


		public boolean isOpen() {
			return isOpen;
		}


		public void setOpen(boolean isOpen) {
			this.isOpen = isOpen;
		}


		public Company getCompany() {
			return company;
		}


		public void setCompany(Company company) {
			this.company = company;
		}
	    
	    
	    

}
