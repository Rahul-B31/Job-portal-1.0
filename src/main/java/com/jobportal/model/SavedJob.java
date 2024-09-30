package com.jobportal.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection ="SavedJob")
public class SavedJob {
	
	    @Id
	    private String id;

	    @DBRef
	    private Job job;

	    @DBRef
	    private User user;
	    
	    public SavedJob() {
			
		}

		public SavedJob(Job job, User user) {
			super();
			this.job = job;
			this.user = user;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
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
	    
	    
		

	

}
