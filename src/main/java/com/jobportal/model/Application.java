package com.jobportal.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection  = "Applications")
public class Application {
	
	
	    @Id
	    private String appId;

	    @DBRef
	    private Job job; 
	    
	    @DBRef
	    private User user;  

	    private String status;  // Enum can be represented as String
	    private String resume;
	    private String skills;
	    private Integer experience;
	    private String education;
	    private String name;
	    
	    private LocalDateTime createdAt;
	    
	    
	    public LocalDateTime getCreatedAt() {
			return createdAt;
		}


		public void setCreatedAt(LocalDateTime createdAt) {
			this.createdAt = createdAt;
		}


		public Application() {
			// TODO Auto-generated constructor stub
		}


		public Application(Job job, User user, String status, String resume, String skills, Integer experience,
				String education, String name) {
			super();
			this.job = job;
			this.user = user;
			this.status = status;
			this.resume = resume;
			this.skills = skills;
			this.experience = experience;
			this.education = education;
			this.name = name;
		}


		public String getAppId() {
			return appId;
		}


		public void setAppId(String appId) {
			this.appId = appId;
		}


		public Job getJob() {
			return job;
		}


		public void setJob(Job job) {
			this.job = job;
		}


		public User getUser() {
			return user;
		}


		public void setUser(User user) {
			this.user = user;
		}


		public String getStatus() {
			return status;
		}


		public void setStatus(String status) {
			this.status = status;
		}


		public String getResume() {
			return resume;
		}


		public void setResume(String resume) {
			this.resume = resume;
		}


		public String getSkills() {
			return skills;
		}


		public void setSkills(String skills) {
			this.skills = skills;
		}


		public Integer getExperience() {
			return experience;
		}


		public void setExperience(Integer experience2) {
			this.experience = experience2;
		}


		public String getEducation() {
			return education;
		}


		public void setEducation(String education) {
			this.education = education;
		}


		public String getName() {
			return name;
		}


		public void setName(String name) {
			this.name = name;
		}
	    
		
		
	    


}
