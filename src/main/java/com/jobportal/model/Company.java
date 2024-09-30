package com.jobportal.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection  = "companies")
public class Company {
	
	    @Id
	    private String compId;
	    private String name;
	    private String logoUrl;
	    private LocalDateTime createdAt;
	    
	    
	    public Company() {
			
		}


		public Company(String name, String logoUrl, LocalDateTime createdAt) {
			super();
			this.name = name;
			this.logoUrl = logoUrl;
			this.createdAt = createdAt;
		}


		public String getCompId() {
			return compId;
		}


		public void setCompId(String compId) {
			this.compId = compId;
		}


		public String getName() {
			return name;
		}


		public void setName(String name) {
			this.name = name;
		}


		public String getLogoUrl() {
			return logoUrl;
		}


		public void setLogoUrl(String logoUrl) {
			this.logoUrl = logoUrl;
		}


		public LocalDateTime getCreatedAt() {
			return createdAt;
		}


		public void setCreatedAt(LocalDateTime createdAt) {
			this.createdAt = createdAt;
		}
		
		
		
		
		
	    
	    

}
